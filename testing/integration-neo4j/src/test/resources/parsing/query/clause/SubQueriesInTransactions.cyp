MATCH (n:Label) WHERE n.prop > 100
CALL {
  WITH n
  DETACH DELETE n
} IN TRANSACTIONS
RETURN msg;
CALL {
  WITH line
  CREATE (:Person {name: line[1], age: toInteger(line[2])})
} IN TRANSACTIONS OF 2 ROWS
RETURN msg