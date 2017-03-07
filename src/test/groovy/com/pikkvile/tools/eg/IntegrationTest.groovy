package com.pikkvile.tools.eg

import spock.lang.Specification

import javax.tools.ToolProvider
import java.nio.file.Files
import java.nio.file.Paths

class IntegrationTest extends Specification {

    def javac = ToolProvider.getSystemJavaCompiler()
    def fileManager = javac.getStandardFileManager(null, null, null)

    def TEST_GENERATED_DIR = Paths.get('generated')
    def TEST_SRC_DIR = TEST_GENERATED_DIR.resolve('src')
    def TEST_CP = TEST_GENERATED_DIR.resolve('classes')
    def TEST_PACKAGE = 'org.test'

    def setup() {
        TEST_GENERATED_DIR.toFile().deleteDir()
        Files.createDirectories(TEST_CP)
    }

    def 'test simple entity generation'() {
        when:
        App.main('--source=./src/test/resources/simple-entity.eg', '--output=' + TEST_SRC_DIR,
                 '--package=' + TEST_PACKAGE)
        then:
        def interfacePath = TEST_SRC_DIR.resolve('org/test/Address.java')
        Files.exists(interfacePath)
        def implPath = TEST_SRC_DIR.resolve('org/test/impl/AddressImpl.java')
        Files.exists(implPath)
        def ctask = compile(["-d", TEST_CP.toAbsolutePath().toString()],
                fileManager.getJavaFileObjectsFromFiles([interfacePath.toFile(), implPath.toFile()]))
        ctask.call()
    }

    def compile(opts, units) {
        javac.getTask(null, null, null, opts, null, units)

    }

    def checkClassValid(String className) {
        File src = new File(TEST_OUT_DIR)
        URL url = src.toURI().toURL()
        ClassLoader cl = new URLClassLoader(url)
        Class cls = cl.loadClass(TEST_PACKAGE + '.Address')
        true
    }
}
