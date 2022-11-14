/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.metadata.actions;

import javax.swing.*;

public class MetadataRelationshipAction extends MetadataAction {

    private static final String QUERY = "MATCH p=()-[r:`%s`]->() RETURN p LIMIT 25";

    MetadataRelationshipAction(String data, String dataSourceUuid, String title, String description, Icon icon) {
        super(data, dataSourceUuid, title, description, icon);
    }

    @Override
    protected String getQuery(String relationship) {
        return String.format(QUERY, relationship);
    }
}
