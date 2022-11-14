/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.platform;

public class ShouldNeverHappenException extends RuntimeException {

    public ShouldNeverHappenException(String developer, String reason) {
        super(developer + " claims that " + reason + "should never happen");
    }

}
