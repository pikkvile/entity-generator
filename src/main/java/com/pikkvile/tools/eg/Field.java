package com.pikkvile.tools.eg;

import java.io.PrintWriter;

public class Field {

    private final FullType type;
    private final String name;

    private static final String FIELD_DECLARATION_STOP = "%s;";

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
}
