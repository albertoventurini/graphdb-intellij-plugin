/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.visualization;

import com.albertoventurini.graphdbplugin.database.api.data.GraphNode;
import com.albertoventurini.graphdbplugin.database.api.data.GraphRelationship;
import com.albertoventurini.graphdbplugin.visualization.events.EventType;
import com.albertoventurini.graphdbplugin.visualization.events.NodeCallback;
import com.albertoventurini.graphdbplugin.visualization.events.RelationshipCallback;
import com.albertoventurini.graphdbplugin.visualization.services.LookAndFeelService;

import javax.swing.*;

public class PrefuseVisualization implements VisualizationApi {

    private GraphDisplay display;

    public PrefuseVisualization(LookAndFeelService lookAndFeelService) {
        this.display = new GraphDisplay(lookAndFeelService);
    }

    @Override
    public void addNode(GraphNode node) {
        display.addNode(node);
    }

    @Override
    public void addRelation(GraphRelationship relationship) {
        display.addRelationship(relationship);
    }

    @Override
    public void clear() {
        display.clearGraph();
    }

    @Override
    public void paint() {
        display.startLayout();
    }

    @Override
    public void stop() {
        display.stopLayout();
    }

    @Override
    public JComponent getCanvas() {
        return display;
    }

    @Override
    public void addNodeListener(EventType type, NodeCallback action) {
        display.addNodeListener(type, action);
    }

    @Override
    public void addEdgeListener(EventType type, RelationshipCallback action) {
        display.addEdgeListener(type, action);
    }

    @Override
    public void resetPan() {
        display.zoomAndPanToFit();
    }

    @Override
    public void updateSettings() {
        display.updateSettings();
    }
}
