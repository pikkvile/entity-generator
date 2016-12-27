package com.pikkvile.tools.eg;

public class Field {

    private final FullType type;
    private final String name;

    public Field(FullType type, String name) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return type.toString() + " " + name;
    }
}
