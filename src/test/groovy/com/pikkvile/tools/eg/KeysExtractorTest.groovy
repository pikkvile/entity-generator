package com.pikkvile.tools.eg

import com.pikkvile.tools.eg.configuration.Key
import com.pikkvile.tools.eg.configuration.KeysExtractor
import spock.lang.Specification

class KeysExtractorTest extends Specification {

    def keysExtractor = new KeysExtractor()

    def "test parse bad formatted string"() {
        when:
        def map = new EnumMap<>(Key.class)
        keysExtractor.extractKeyToMap("wtf", map)
        then:
        thrown IllegalArgumentException
    }

    def "test parse invalid key"() {
        when:
        def map = new EnumMap<>(Key.class)
        keysExtractor.extractKeyToMap("--some-key=some-value", map)
        then:
        thrown IllegalArgumentException
    }

    def "test long syntax"() {
        when:
        def map = new EnumMap<>(Key.class)
        keysExtractor.extractKeyToMap("--package=some.pkg", map)
        then:
        map.get(Key.PACKAGE) == "some.pkg"
    }

    def "test short syntax"() {
        when:
        def map = new EnumMap<>(Key.class)
        keysExtractor.extractKeyToMap("-Psome.pkg", map)
        then:
        map.get(Key.PACKAGE) == "some.pkg"
    }
}
