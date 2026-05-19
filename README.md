<img src="https://www.ga4gh.org/wp-content/themes/ga4gh/dist/assets/svg/logos/logo-full-color.svg" alt="GA4GH Logo" style="width: 400px;"/>

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg?style=flat-square)](https://opensource.org/licenses/Apache-2.0)
[![Java 11+](https://img.shields.io/badge/java-11+-blue.svg?style=flat-square)](https://www.java.com)
[![Gradle 7.3.3+](https://img.shields.io/badge/gradle-7.3.3+-blue.svg?style=flat-square)](https://gradle.org/)
[![GitHub Actions](https://img.shields.io/github/workflow/status/ga4gh/ga4gh-starter-kit-passport-broker/test/main)](https://github.com/ga4gh/ga4gh-starter-kit-passport-broker/actions)
[![Codecov](https://img.shields.io/codecov/c/github/ga4gh/ga4gh-starter-kit-passport-broker?style=flat-square)](https://app.codecov.io/gh/ga4gh/ga4gh-starter-kit-passport-broker)

# GA4GH Starter Kit Passport Broker

Starter Kit Passport Broker implementation, part of the [GA4GH Passports Specification](https://github.com/ga4gh-duri/ga4gh-duri.github.io/blob/master/researcher_ids/ga4gh_passport_v1.md).

Please see [starterkit.ga4gh.org](https://starterkit.ga4gh.org) for the full documentation on how to run the Starter Kit, including the Passport Broker.

Basic instructions for running Starter Kit Passport Broker in a dev environment are included here.

## Run Passport Broker Locally

### Docker

Pull the image:
```
docker pull ga4gh/ga4gh-starter-kit-passport-broker:latest
```

Run container with default settings:
```
docker run -p 4500:4500 ga4gh/ga4gh-starter-kit-passport-broker:latest
```

### Native

The service can also be installed locally in cases where docker deployments are not possible, or for development of the codebase. Native installations require:
* Java 11+
* Gradle 7.3.2+
* SQLite (for creating the dev database)

First, clone the repository from Github:
```
git clone https://github.com/ga4gh/ga4gh-starter-kit-passport-broker.git
cd ga4gh-starter-kit-passport-broker
```

The service can be run in development mode directly via gradle:

Run with all defaults
```
./gradlew bootRun
```

Alternatively, the service can be built as a jar and run:

Build jar:
```
./gradlew bootJar
```

Run the application
```
java -jar build/libs/ga4gh-starter-kit-passport-broker-${VERSION}.jar
```

### Confirm server is running

Whether running via docker or natively on a local machine, confirm the Passport Broker API is up running by visiting its `service-info` endpoint, you should receive a valid `ServiceInfo` response.

```
GET http://localhost:4500/ga4gh/passport/v1/service-info

Response:
{
    "id": "org.ga4gh.starterkit.passport.broker",
    "name": "GA4GH Starter Kit Passport Broker Service",
    "description": "Starter Kit implementation of a Passport Broker service, outlined in the GA4GH Passports specification. Manages researcher permissions to data and compute, and enables this information to be minted as JWTs and passed to downstream clearinghouses.",
    "contactUrl": "mailto:info@ga4gh.org",
    "documentationUrl": "https://github.com/ga4gh/ga4gh-starter-kit-passport-broker",
    "createdAt": "2022-04-28T09:00:00Z",
    "updatedAt": "2022-04-28T09:00:00Z",
    "environment": "test",
    "version": "0.0.2",
    "type": {
        "group": "org.ga4gh",
        "artifact": "passport-broker",
        "version": "1.0.0"
    },
    "organization": {
        "name": "Global Alliance for Genomics and Health",
        "url": "https://ga4gh.org"
    }
}
```

## Development

Additional setup steps to run the Passport Broker service in a local environment for development and testing.

### Setup dev database

A local SQLite database must be set up for running the Passport Broker service in a development context. If `make` and `sqlite3` are already installed on the system `PATH`, this database can be created and populated with a dev dataset by simply running: 

```
make sqlite-db-refresh
```

This will create a SQLite database named `ga4gh-starter-kit.dev.db` in the current directory.

###### What is included in the Dev Dataset?

Four example users, four example visas and eight example assertions. An assertion is created every time a visa is assigned to a user.

If `make` and/or `sqlite` are not installed, [this file](./database/sqlite/create-tables.sql) contains SQLite commands for creating the database schema, and [this file](./database/sqlite/add-dev-dataset.sql) contains SQLite commands for populating it with the dev dataset.

Confirm the Passport Broker service can connect to the dev database by submitting a request to learn more about a specific visa.

```
GET http://localhost:4501/admin/ga4gh/passport/v1/visas/670cc2e7-9a9c-4273-9334-beb40d364e5c

Response:
{
    "id": "670cc2e7-9a9c-4273-9334-beb40d364e5c",
    "visaName": "StarterKitDatasetsControlledAccessGrants",
    "visaIssuer": "https://datasets.starterkit.ga4gh.org/",
    "visaDescription": "Controlled access dev datasets for the GA4GH Starter Kit",
    "visaSecret": "87A3B5D68FD88197254D9889B4AAB",
    "passportVisaAssertions": [
        {
            "status": "active",
            "assertedAt": 1652187600,
            "passportUser": {
                "id": "85ff5a54-48b9-4294-a91d-2be50bd2a77d"
            }
        }
    ]
}
```

**NOTE:** If running via docker, the dev database is already bundled within the container.

**NOTE:** The unit and end-to-end test suite is predicated on a preconfigured database. The SQLite dev database must be present for tests to pass.

## Configuring the Service with Config Files

Check the [configuration document](./CONFIGURATION.md) to learn more.

## Maintainers

* GA4GH Tech Team [ga4gh-tech-team@ga4gh.org](mailto:ga4gh-tech-team@ga4gh.org)
