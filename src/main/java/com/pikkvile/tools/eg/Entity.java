package com.pikkvile.tools.eg;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.stream.Collectors;

public class Entity {

    private final FullType type;
    private final Collection<Field> fields;

    private static final String CLASS_DECLARATION_STOP = "}";
    private static final String METHOD_DECLARATION_STOP = "    }";
    private static final String BUILDER_START = "\n    public static class Builder {";

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

    public void printInterface(PrintWriter writer) {
        type.printPackage(writer);
        fields.forEach(field -> field.printImport(writer, type));
        writer.println();
        type.printInterfaceDeclarationStart(writer);
        writer.println();
        fields.forEach(field -> field.printAbstractGetter(writer));
        writer.println(CLASS_DECLARATION_STOP);
        writer.println();
    }

    public void printImpl(PrintWriter writer) {
        type.printImplPackage(writer);
        writer.println();
        type.printInterfaceImport(writer);
        fields.forEach(field -> field.printImport(writer, type));
        writer.println();
        type.printImplClassDeclarationStart(writer);
        writer.println();
        fields.forEach(field -> field.printField(writer));
        type.printBuilderConstructorStart(writer);
        fields.forEach(field -> field.printInitFromBuilder(writer));
        writer.println(METHOD_DECLARATION_STOP);
        fields.forEach(field -> field.printGetter(writer));
        writer.println(BUILDER_START);
        fields.forEach(field -> field.printFieldForBuilder(writer));
        writer.println(METHOD_DECLARATION_STOP);
        writer.println(CLASS_DECLARATION_STOP);
        writer.println();
    }
}
