/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.visualization.listeners;

import com.albertoventurini.graphdbplugin.database.api.data.GraphNode;
import com.albertoventurini.graphdbplugin.visualization.events.EventType;
import com.albertoventurini.graphdbplugin.visualization.events.NodeCallback;
import prefuse.controls.ControlAdapter;
import prefuse.visual.NodeItem;
import prefuse.visual.VisualItem;

import java.awt.event.MouseEvent;
import java.util.Map;

public class NodeListener extends ControlAdapter {

    private EventType type;
    private NodeCallback callback;
    private Map<String, GraphNode> graphNodeMap;

    public NodeListener(EventType type, NodeCallback callback, Map<String, GraphNode> graphNodeMap) {
        this.type = type;
        this.callback = callback;
        this.graphNodeMap = graphNodeMap;
    }

    @Override
    public void itemEntered(VisualItem item, MouseEvent e) {
        if (type == EventType.HOVER_START && item instanceof NodeItem) {
            callback.accept(graphNodeMap.get(item.get("id")), item, e);
        }
    }

    @Override
    public void itemExited(VisualItem item, MouseEvent e) {
        if (type == EventType.HOVER_END && item instanceof NodeItem) {
            callback.accept(graphNodeMap.get(item.get("id")), item, e);
        }
    }

    @Override
    public void itemClicked(VisualItem item, MouseEvent e) {
        if (type == EventType.CLICK && item instanceof NodeItem) {
            callback.accept(graphNodeMap.get(item.get("id")), item, e);
        }
    }

}
