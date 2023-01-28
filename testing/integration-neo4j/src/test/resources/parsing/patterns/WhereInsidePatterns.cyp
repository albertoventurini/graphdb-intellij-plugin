WITH 30 AS minAge
MATCH (a:Person WHERE a.name = 'Andy')-[:KNOWS]->(b:Person {city: "Somewhere"} WHERE b.age > minAge)
RETURN b.name;
MATCH (a:Person)-[r:KNOWS WHERE r.since < 2023]->(b:Person)
RETURN r.since;