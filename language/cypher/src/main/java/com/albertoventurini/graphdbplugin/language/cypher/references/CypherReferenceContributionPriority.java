/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.language.cypher.references;

/**
 * Cypher specific priorities.
 *
 * @author dmitry@vrublevsky.me
 */
public enum CypherReferenceContributionPriority {
    VARIABLE(200.0),
    LABEL_NAME(180.0),
    REL_TYPE_NAME(180.0),
    PROPERTY_KEY_NAME(170.0);

    private final double priority;

    CypherReferenceContributionPriority(double priority) {
        this.priority = priority;
    }

    public double getPriority() {
        return priority;
    }
}
