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
    private static final String IMPL_CLASS_DECLARATION_START = "public class %s implements %s {";
    private static final String INTERFACE_DECLARATION_START = "public interface %s {";
    private static final String FIELD_DECLARATION_START = "    private final %s ";
    private static final String BUILDER_CONSTRUCTOR_START = "\n    %s(Builder builder) {";
    public static final String IMPL = "Impl";

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

    public void printImplPackage(PrintWriter writer) {
        if (!pkgName.isEmpty()) {
            writer.println(String.format(PACKAGE_DECLARATION, pkgName + ".impl"));
        }
    }

    public void printImport(PrintWriter writer, FullType entityType) {
        if (!this.isInSamePackage(entityType) && !this.isEmbedded()) {
            writer.println(String.format(IMPORT_DECLARATION, pkgName));
        }
    }

    public void printImplClassDeclarationStart(PrintWriter writer) {
        writer.println(String.format(IMPL_CLASS_DECLARATION_START, selfName + IMPL, selfName));
    }

    public void printInterfaceDeclarationStart(PrintWriter writer) {
        writer.println(String.format(INTERFACE_DECLARATION_START, selfName));
    }

    public void printFieldDeclarationStart(PrintWriter writer) {
        writer.print(String.format(FIELD_DECLARATION_START, selfName));
    }

    public void printInterfaceImport(PrintWriter writer) {
        writer.println(String.format(IMPORT_DECLARATION, pkgName + "." + selfName));
    }

    public void printBuilderConstructorStart(PrintWriter writer) {
        writer.println(String.format(BUILDER_CONSTRUCTOR_START, selfName + IMPL));
    }

    private boolean isEmbedded() {
        return pkgName.equals(JAVA_LANG) || PRIMITIVES.contains(selfName);
    }

    private boolean isInSamePackage(FullType that) {
        return pkgName.equals(that.pkgName);
    }
}
