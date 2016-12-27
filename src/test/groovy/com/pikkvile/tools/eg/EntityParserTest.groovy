package com.pikkvile.tools.eg;

import spock.lang.*

class EntityParserTest extends Specification {

    def params = new EnumMap<>(Key.class)
    def config = new Config(params)
    def typeParser = new TypeParser(config)
    def entityParser = new EntityParser(typeParser, config);

    def "parse one simple entity, with default field type and default package"() {
        setup:
        params.put(Key.PACKAGE, "com.pikkvile.project.entities")
        when:
        def e1 = entityParser.parse("E1: f")
        then:
        e1.toString() == config.defaultPackage + ".E1: " + config.defaultType + " f"
    }

    def "parse entity with many fields and default package"() {
        setup:
        params.put(Key.PACKAGE, "com.pikkvile.project.entities")
        when:
        def e2 = entityParser.parse("E2: int i, s, com.example.Clazz clz")
        then:
        e2.toString() == "com.pikkvile.project.entities.E2: int i, " + config.defaultType + " s, com.example.Clazz clz"
    }
}