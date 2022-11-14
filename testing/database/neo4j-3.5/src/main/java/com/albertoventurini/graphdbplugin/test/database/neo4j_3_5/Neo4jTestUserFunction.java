/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.test.database.neo4j_3_5;

import org.neo4j.procedure.Name;
import org.neo4j.procedure.UserFunction;

public class Neo4jTestUserFunction {

    @UserFunction
    public String firstTestFunction() {
        return "test";
    }

    @UserFunction
    public String secondTestFunction(@Name("param") String param) {
        return "test";
    }
}
