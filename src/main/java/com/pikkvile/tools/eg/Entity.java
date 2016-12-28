package com.pikkvile.tools.eg;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.stream.Collectors;

public class Entity {

    private final FullType type;
    private final Collection<Field> fields;

    private static final String CLASS_DECLARATION_STOP = "}";

    public Entity(FullType type, Collection<Field> fields) {
        this.type = type;
        this.fields = fields;
    }

    public String selfTypeName() {
        return type.getSelfName();
    }

    @Override
    public String toString() {
        return type.toString() + ": " + fields.stream().map(Field::toString).collect(Collectors.joining(", "));
    }

    public void print(PrintWriter writer) {
        type.printPackage(writer);
        fields.forEach(field -> field.printImport(writer, type));
        writer.println();
        type.printClassDeclarationStart(writer);
        writer.println();
        fields.forEach(field -> field.printField(writer));
        writer.println(CLASS_DECLARATION_STOP);
        writer.println();
    }
}
