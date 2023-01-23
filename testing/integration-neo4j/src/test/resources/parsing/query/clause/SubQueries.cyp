CALL {
  RETURN "Hello" AS msg
}
RETURN msg;
CALL {
  WITH xs
  UNWIND xs AS x
  MATCH (n:Node {name: x})
  RETURN n.name AS name
}
RETURN name;
