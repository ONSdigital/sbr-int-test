package uk.gov.ons.sbr.scat.it.matchers

import org.scalatest.matchers.{MatchResult, Matcher}
import play.api.libs.json.{JsObject, JsValue}

trait JsonMatchers {
  /*
   * If the comparison is between Json objects we use a custom matcher that attempts to isolate the specific
   * fields that differ.  Otherwise we perform a simple equality based match.
   */
  class JsonMatcher(expected: JsValue) extends Matcher[JsValue] {
    override def apply(actual: JsValue): MatchResult =
      (expected, actual) match {
        case (expectedObject: JsObject, actualObject: JsObject) =>
          new JsObjectMatcher(expectedObject)(actualObject)
        case _ =>
          matchValues(expected, actual)
      }

    private def matchValues(expected: JsValue, actual: JsValue) =
      MatchResult(
        matches = expected == actual,
        rawFailureMessage = s"Expected [$expected] but was [$actual].",
        rawNegatedFailureMessage = s"Expected [$expected] was [$actual]."
      )
  }

  def beJsonMatching(expected: JsValue) =
    new JsonMatcher(expected)

  /*
   * We enrich any failure message with the names of those fields that differ.
   * The default equality matcher just prints the expected & actual, which in the case of large Json structures is not
   * particularly helpful.
   */
  private class JsObjectMatcher(expected: JsObject) extends Matcher[JsObject] {
    override def apply(actual: JsObject): MatchResult =
      MatchResult(
        matches = expected == actual,
        rawFailureMessage =
          s"""|Expected [$expected] but was [$actual].
              |Differences found at [${JsObjectMatcher.diff(expected, actual, parent = None).mkString(",")}]""".stripMargin,
        rawNegatedFailureMessage = s"Expected [$expected] was [$actual]."
      )
  }

  private object JsObjectMatcher {
    def diff(left: JsObject, right: JsObject, parent: Option[String]): Set[String] = {
      val (leftSubObjects, leftValues) = left.value.partition(kv => isJsObject(kv._2))
      val (rightSubObjects, rightValues) = right.value.partition(kv => isJsObject(kv._2))
      val valueDiffs = leftValues.toSet.diff(rightValues.toSet) ++ rightValues.toSet.diff(leftValues.toSet)
      val valueDiffNames = valueDiffs.map(_._1).toSeq
      val leftOnlyNames = leftSubObjects.keySet.diff(rightSubObjects.keySet).toSeq
      val rightOnlyNames = rightSubObjects.keySet.diff(leftSubObjects.keySet).toSeq
      val fieldNames = prefixWithParentName(parent, valueDiffNames ++ leftOnlyNames ++ rightOnlyNames).toSet
      val commonSubObjects = leftSubObjects.filterKeys(rightSubObjects.keySet.contains).flatMap { case (name, lvalue) =>
        rightSubObjects.get(name).map { rvalue =>
          name -> (lvalue, rvalue)
        }
      }

      commonSubObjects.foldLeft(fieldNames) { case (acc, (name, (lvalue, rvalue))) =>
        acc ++ diff(lvalue.asInstanceOf[JsObject], rvalue.asInstanceOf[JsObject], descendant(parent, name))
      }
    }

    private def prefixWithParentName(parent: Option[String], children: Seq[String]): Seq[String] =
      parent.fold(children) { _ =>
        children.flatMap(descendant(parent, _))
      }

    private def descendant(parent: Option[String], child: String): Option[String] =
      parent.fold(Some(child)) { parentName =>
        Some(s"$parentName.$child")
      }

    private def isJsObject(json: JsValue): Boolean =
      json.isInstanceOf[JsObject]
  }
}

object JsonMatchers extends JsonMatchers