# SBR Integration Tests
[![license](https://img.shields.io/github/license/mashape/apistatus.svg)](./LICENSE)

This project houses integration tests for Statistical Business Register (SBR).

The current focus is on testing APIs ([sbr-control-api](https://github.com/ONSdigital/sbr-control-api) and
[sbr-api](https://github.com/ONSdigital/sbr-control-api)) against a known dataset.

The longer-term goal is to feed a known dataset into the very beginning of the data pipeline process,
testing the output via the APIs.  When this is achieved the tests will have become full end-to-end tests.

### Overview

This project contains:
* scripts to populate a target HBase database with a known dataset
* API tests that establish whether or not client requests against the known dataset achieve the expected results

##### Populating the Database
_TODO: provide overview of scripts_

##### Testing the APIs

The test dataset is located at [src/test/resources/uk/gov/ons/sbr/sit/data/csv](https://github.com/ONSdigital/sbr-int-test/tree/master/src/test/resources/uk/gov/ons/sbr/sit/data/csv).
This is parsed, and the expected Json representation of each entity is derived.  Any invalid rows are discarded.

We then use a [ScalaCheck](https://www.scalacheck.org/) __oneOf__ generator to randomly select an entity,
query the relevant API, and confirm that the returned payload matches the expected Json representation.

[ScalaTest](http://www.scalatest.org/user_guide/generator_driven_property_checks) is used to drive the test,
and via __forAll__ will repeat the test a number of times in accordance with the configured __PropertyCheckConfig__.
By default, this value is 10.


### Running Locally
1. Start a local HBase instance

   ```bin/start-hbase.sh```

2. Start the HBase REST Service

   ```bin/hbase rest start```

3. Populate the database with the test data set

   _TODO: example script usage_

4. Start sbr-control-api

   ```sbt run``` _(in sbr-control-api project directory)_

   Note that your project _conf/application.conf_ must configure _db.hbase-rest_ to point to the local HBase instance
   started earlier.  This is the default configuration.

5. Run integration tests

   ```sbt clean test``` _(in this project directory)_

   Note that your project _src/test/resources/application.conf_ must configure _api.sbr.control_ to point to the local
   server started earlier.  This is the default configuration.


### License

Copyright © 2018, Office for National Statistics (https://www.ons.gov.uk)

Released under MIT license, see [LICENSE](./LICENSE) for details.
