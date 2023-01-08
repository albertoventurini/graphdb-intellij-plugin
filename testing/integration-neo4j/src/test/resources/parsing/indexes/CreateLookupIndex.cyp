CREATE LOOKUP INDEX node_label_lookup_index FOR (n) ON EACH labels(n);
CREATE LOOKUP INDEX rel_type_lookup_index FOR ()-[r]-( ) ON type(r);