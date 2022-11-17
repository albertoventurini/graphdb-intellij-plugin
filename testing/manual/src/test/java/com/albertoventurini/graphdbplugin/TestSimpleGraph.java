/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin;

import com.albertoventurini.graphdbplugin.database.api.query.GraphQueryResult;
import com.albertoventurini.graphdbplugin.database.neo4j.bolt.Neo4jBoltConfiguration;
import com.albertoventurini.graphdbplugin.database.neo4j.bolt.Neo4jBoltDatabase;
import com.albertoventurini.graphdbplugin.jetbrains.services.IdeaLookAndFeelService;
import com.albertoventurini.graphdbplugin.visualization.PrefuseVisualization;
import com.albertoventurini.graphdbplugin.visualization.events.EventType;


import javax.swing.JFrame;
import java.util.HashMap;
import java.util.Map;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class TestSimpleGraph {

    public static void main(String[] argv) {
        PrefuseVisualization v = new PrefuseVisualization(new IdeaLookAndFeelService());

        Map<String, String> config = new HashMap<>();
        config.put(Neo4jBoltConfiguration.HOST, "localhost");
//        config.put(Neo4jBoltConfiguration.PASSWORD)
//        config.put(Neo4jBoltConfiguration.PORT)
//        config.put(Neo4jBoltConfiguration.USER)
        Neo4jBoltDatabase db = new Neo4jBoltDatabase(config);

        GraphQueryResult execute = db.execute("MATCH (n:TEST)-[r]-(n) RETURN * LIMIT 1;");
//        GraphQueryResult execute = db.execute("MATCH (n)-[r]-(m) RETURN * LIMIT 10;");
        execute.getNodes().forEach(v::addNode);
        execute.getRelationships().forEach(v::addRelation);

        v.addNodeListener(EventType.CLICK, (id, item, event) -> System.out.println("Node clicked: " + id));
        v.addNodeListener(EventType.HOVER_START, (id, item, event) -> System.out.println("Node hovered: " + id));
        v.addEdgeListener(EventType.CLICK, (id, item, event) -> System.out.println("Edge clicked: " + id));
        v.addEdgeListener(EventType.HOVER_START, (id, item, event) -> System.out.println("Edge hovered: " + id));

        JFrame frame = new JFrame("Liquid Lama");
        frame.getContentPane().add(v.getCanvas());
        frame.pack();
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);

        v.paint();
    }

}

