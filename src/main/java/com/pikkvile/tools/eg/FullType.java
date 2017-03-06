package com.pikkvile.tools.eg;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FullType {

    private final String pkgName;
    private final String selfName;

    private static final String PACKAGE_DECLARATION = "package %s;";
    private static final String IMPORT_DECLARATION = "import %s;";
    private static final String CLASS_DECLARATION_START = "public class %s {";
    private static final String FIELD_DECLARATION_START = "    private final %s ";

    private static final String JAVA_LANG = "java.lang";
    private static final Set<String> PRIMITIVES =
            new HashSet<>(Arrays.asList("byte", "short", "int", "long", "float", "double", "char", "boolean"));

    public FullType(String pkgName, String selfName) {
        this.pkgName = pkgName;
        this.selfName = selfName;
    }

    public String getSelfName() {
        return selfName;
    }

    @Override
    public String toString() {
        return pkgName.isEmpty() ? selfName : pkgName + "." + selfName;
    }

    public void printPackage(PrintWriter writer) {
        if (!pkgName.isEmpty()) {
            writer.println(String.format(PACKAGE_DECLARATION, pkgName));
        }
    }

    public void printImport(PrintWriter writer, FullType entityType) {
        if (!this.isInSamePackage(entityType) && !this.isEmbedded()) {
            writer.println(String.format(IMPORT_DECLARATION, pkgName));
        }
    }

    public void printClassDeclarationStart(PrintWriter writer) {
        writer.println(String.format(CLASS_DECLARATION_START, selfName));
    }

    public void printFieldDeclarationStart(PrintWriter writer) {
        writer.print(String.format(FIELD_DECLARATION_START, selfName));
    }

    private boolean isEmbedded() {
        return pkgName.equals(JAVA_LANG) || PRIMITIVES.contains(selfName);
    }

    private boolean isInSamePackage(FullType that) {
        return pkgName.equals(that.pkgName);
    }
}
