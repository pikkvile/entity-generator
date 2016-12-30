package com.pikkvile.tools.eg

import com.pikkvile.tools.eg.configuration.Config
import com.pikkvile.tools.eg.configuration.Key
import spock.lang.Specification

class TypeParserTest extends Specification {

    def params = new EnumMap<>(Key.class)
    def config = new Config(params)
    def typeParser = new TypeParser(config)

    def "test parse full canonical type"() {
        when:
        def type = typeParser.parseFullType("com.pikkvile.Type")
        then:
        type.toString() == "com.pikkvile.Type"
    }

    def "test parse well known embedded type"() {
        when:
        def type = typeParser.parseFullType("String")
        then:
        type.toString() == "java.lang.String"
    }

    def "test parse well known type"() {
        when:
        def type = typeParser.parseFullType("BigDecimal")
        then:
        type.toString() == "java.math.BigDecimal"
    }

    def "test parse unknown type, use default configured package"() {
        setup:
        params.put(Key.PACKAGE, "com.pikkvile.project.entities")
        when:
        def type = typeParser.parseFullType("MyType")
        then:
        type.toString() == "com.pikkvile.project.entities.MyType"
    }
}
