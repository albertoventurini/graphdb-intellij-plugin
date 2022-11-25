# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [0.2.2]

### Fixed

- Fail gracefully when retrieving metadata for Neo4j 3.x (#21)

## [0.2.1]

### Fixed

- Renamed notification group for update activity (#16). This fixes possible NullPointerExceptions on IDE startup.

## [0.2.0]

### Added

- Upgraded Neo4j driver to 5.2.0
- Added support for current Bolt URI schemes (https://neo4j.com/docs/bolt/current/driver-api/#uri-schemes)
- Added authentication choice (either "No auth" or "User & Password") in the data source wizard.

### Fixed

- Fixed bugs related to application services being retrieved as project services.

## [0.1.4]

### Added

- Add support for IntelliJ 2022.3

## [0.1.3]

### Added

- First release of the Graph Database plugin, based on the "Graph Database Support" plugin developed by Neueda Technologies, Ltd.

### Removed

- Support for Gremlin data sources (such as CosmosDB)
- Tracking via Google analytics

This plugin is based on the 'Graph Database Support plugin' developed by Neueda Technologies, Ltd. The original plugin has not been updated in a long time.
This plugin has been updated to run with modern versions of IntelliJ and related IDEs. Just like the original plugin, it supports Neo4j and Cypher data sources; however, support for Gremlin data sources (such as Cosmos DB) has been dropped.