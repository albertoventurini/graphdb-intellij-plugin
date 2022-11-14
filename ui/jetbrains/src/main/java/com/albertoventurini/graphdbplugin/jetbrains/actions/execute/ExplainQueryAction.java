/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.actions.execute;

public class ExplainQueryAction extends ExecuteQueryAction {

    @Override
    protected String decorateQuery(String query) {
        return "EXPLAIN " + super.decorateQuery(query);
    }
}
