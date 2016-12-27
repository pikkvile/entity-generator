package com.pikkvile.tools.eg;

import java.util.Collection;
import java.util.stream.Collectors;

public class Entity {

    private final FullType type;
    private final Collection<Field> fields;

    public Entity(FullType type, Collection<Field> fields) {
        this.type = type;
        this.fields = fields;
    }

    @Override
    public String toString() {
        return type.toString() + ": " + fields.stream().map(Field::toString).collect(Collectors.joining(", "));
    }
}
