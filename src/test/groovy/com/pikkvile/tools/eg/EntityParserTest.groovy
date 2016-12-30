package com.pikkvile.tools.eg

import com.pikkvile.tools.eg.configuration.Config
import com.pikkvile.tools.eg.configuration.Key;
import spock.lang.*

class EntityParserTest extends Specification {

    def params = new EnumMap<>(Key.class)
    def config = new Config(params)
    def typeParser = new TypeParser(config)
    def entityParser = new EntityParser(typeParser, config)

    def setup() {
        params.put(Key.PACKAGE, "com.pikkvile.project.entities")
    }

    def "parse one simple entity, with default field type and default package"() {
        when:
        def e1 = entityParser.parse("E1: f")
        then:
        def defaultType = typeParser.parseFullType(config.defaultType)
        config.defaultPackage + ".E1: " + defaultType + " f" == e1.toString()
    }

    def "parse entity with many fields and default package"() {
        when:
        def e2 = entityParser.parse("E2: int i, s, com.example.Clazz clz")
        then:
        def defaultType = typeParser.parseFullType(config.defaultType)
        e2.toString() == "com.pikkvile.project.entities.E2: int i, " + defaultType + " s, com.example.Clazz clz"
    }
}