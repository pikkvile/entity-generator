package com.pikkvile.tools.eg;

import java.util.Optional;
import java.util.stream.Stream;

public enum Key {

    SOURCE("-S", "--source="),
    OUTPUT("-O", "--output="),
    PACKAGE("-P", "--package="),
    TYPE("-T", "--default-type=");

    private final String shortRef;
    private final String longRef;

    Key(String shortRef, String longRef) {
        this.shortRef = shortRef;
        this.longRef = longRef;
    }

    public static Optional<Key> forShortRef(String s) {
        return Stream.of(values()).filter(k -> k.shortRef.equals(s)).findFirst();
    }

    public static Optional<Key> forLongRef(String s) {
        return Stream.of(values()).filter(k -> k.longRef.equals(s)).findFirst();
    }
}
