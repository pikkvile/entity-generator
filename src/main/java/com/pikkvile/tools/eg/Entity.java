package com.pikkvile.tools.eg;

import java.util.Collection;
import java.util.List;

public class Entity {

    private final FullType type;
    private final Collection<Field> fields;

    public Entity(FullType type, Collection<Field> fields) {
        this.type = type;
        this.fields = fields;
    }
}