CREATE INDEX index_with_config
FOR (n:Label) ON (n.prop2)
OPTIONS {
  indexConfig: {
    `spatial.cartesian.min`: [-100.0, -100.0],
    `spatial.cartesian.max`: [100.0, 100.0],
    anotherOption: "a string value"
  }
}