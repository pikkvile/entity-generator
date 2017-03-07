package com.pikkvile.tools.eg;

import java.io.PrintWriter;

public class Field {

    private final FullType type;
    private final String name;

    private static final String FIELD_DECLARATION_STOP = "%s;";
    private static final String ABSTRACT_GETTER = "    %s get%s();";
    private static final String GETTER = "\n    @Override\n    public %s get%s() {\n        return %s;\n    }";
    private static final String INIT_FROM_BUILDER = "        this.%1$s = builder.%1$s;";

    public Field(FullType type, String name) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return type.toString() + " " + name;
    }

    public void printImport(PrintWriter writer, FullType entityType) {
        type.printImport(writer, entityType);
    }

    public void printField(PrintWriter writer) {
        type.printFieldDeclarationStart(writer);
        writer.println(String.format(FIELD_DECLARATION_STOP, name));
    }

    public void printFieldForBuilder(PrintWriter writer) {
        writer.println(String.format("      private %s %s;", type.getSelfName(), name));
    }

    public void printAbstractGetter(PrintWriter writer) {
        writer.println(String.format(ABSTRACT_GETTER, type.getSelfName(), forGetter(name)));
    }

    public void printGetter(PrintWriter writer) {
        writer.println(String.format(GETTER, type.getSelfName(), forGetter(name), name));
    }

    public void printInitFromBuilder(PrintWriter writer) {
        writer.println(String.format(INIT_FROM_BUILDER, name));
    }

    private String forGetter(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}
