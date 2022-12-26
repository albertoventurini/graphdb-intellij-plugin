# Graph Database plugin for IntelliJ Platform

This plugin adds support for graph databases to the IntelliJ IDEA platform.
At the moment, the only supported database is Neo4j.

The plugin provides useful developer features for working with graph databases, such as:
* syntax highlighting and autocompletion for 
the [Cypher](https://opencypher.org/) query language
* connecting to and querying local and remote graph databases.

![Screenshot 2022-11-21 at 09 08 11](https://user-images.githubusercontent.com/5089391/202986692-78fbc25b-2d60-42bc-a746-d67db1da72b6.png)

This plugin is maintained and developed in my free time as a personal project,
for no commercial reasons.
Contributions and donations are welcome! Please see the [Getting involved](#getting-involved)
section for more information on how to contribute to this project.

This plugin is based on the
[Graph Database Support](https://github.com/neueda/jetbrains-plugin-graph-database-support)
plugin, originally developed by [Neueda Technologies](http://technologies.neueda.com/).
The original plugin has not been updated for a long time and does not work with recent versions of IntelliJ and Neo4j.

## Installation

The plugin can be installed directly within an IntelliJ IDEA-based IDE.

1. Go to `Preferences` -> `Plugins` -> `Marketplace`
2. Search for `Graph Database`.
3. Install plugin and restart the IDE.

Alternatively, you can navigate to the [plugin homepage](https://plugins.jetbrains.com/plugin/20417-graph-database)
on the JetBrains marketplace and click on the 'Install' button.

## Usage

### Data Sources

After the plugin is installed, you should configure a data source. In order to do that,
expand the "Graph Database" tab on the right-hand side of the IDE, then click on the "+" symbol.
Fill in the fields, and test that the connection works.

### Cypher syntax highlighting

The plugin activates syntax highlighting for Cypher for files with extensions `.cyp`, `.cypher`, or `.cql`.

It is also possible to add syntax highlighting to Java Strings containing Cypher queries
via [language injection](https://www.jetbrains.com/help/idea/using-language-injections.html).
Simply add the following comment right before a Cypher String:
```java
// language=Cypher
```

## Building the plugin

### Prerequisites

The plugin depends on JDK17 and the latest release of IntelliJ. Please make sure
you have those installed on your machine.

Integration tests depend on Docker, because they run Neo4j server in a [Testcontainer](https://www.testcontainers.org/). Please ensure you have Docker up and running if you'd like to run integration tests.

If you plan on making changes to a grammar or lexer files, you may find
it useful to install the [Grammar-Kit](https://github.com/JetBrains/Grammar-Kit)
IntelliJ plugin. This is not required but nice to have.

### Build instructions

The plugin can be built with the following Gradle command:

```shell
./gradlew :graph-database-plugin:buildPlugin
```

Before a release, the plugin should also be tested and verified with the following commands:

```shell
./gradlew test
./gradlew :graph-database-plugin:runPluginVerifier
```

The plugin can also be manually tested on a sandboxed instance of IntelliJ with the command:

```shell
./gradlew :graph-database-plugin:runIde
```

## Getting help

Please feel free to report any bugs by creating a
[new issue on GitHub](https://github.com/albertoventurini/graphdb-intellij-plugin/issues/new).

## Getting involved

Please feel free to contribute code by opening pull requests!

Alternatively, if you find this plugin useful, please feel free to 
<a href="https://ko-fi.com/albertoventurini">buy me a coffee</a>.

## Contacts

Any other comments or words of encouragement? Please get in touch
via email at aventurini AT gmail DOT com.
