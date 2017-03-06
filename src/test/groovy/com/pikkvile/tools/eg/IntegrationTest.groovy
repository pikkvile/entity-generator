package com.pikkvile.tools.eg

import spock.lang.Specification

import javax.tools.JavaCompiler
import javax.tools.ToolProvider
import java.nio.file.Files
import java.nio.file.Paths

class IntegrationTest extends Specification {

    def TEST_OUT_DIR = './generated'
    def TEST_PACKAGE = 'org.test'

    def setup() {
        def outputDir = new File(TEST_OUT_DIR)
        outputDir.deleteDir()
    }

    def 'test simple entity generation'() {
        when:
        App.main('--source=./src/test/resources/simple-entity.eg', '--output=' + TEST_OUT_DIR,
                 '--package=' + TEST_PACKAGE)
        then:
        Files.exists(Paths.get(TEST_OUT_DIR).resolve('org/test/Address.java'))
        compile('Address')
        //checkClassValid('Address')
    }

    def compile(String className) {
        JavaCompiler javac = ToolProvider.getSystemJavaCompiler()
        javac.run(null, null, null, Paths.get(TEST_OUT_DIR).resolve('org/test/Address.java').toAbsolutePath().toString())

    }

    def checkClassValid(String className) {
        File src = new File(TEST_OUT_DIR)
        URL url = src.toURI().toURL()
        ClassLoader cl = new URLClassLoader(url)
        Class cls = cl.loadClass(TEST_PACKAGE + '.Address')
        true
    }
}
