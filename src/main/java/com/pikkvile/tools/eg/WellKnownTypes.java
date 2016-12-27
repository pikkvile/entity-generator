package com.pikkvile.tools.eg;

import java.util.Optional;
import java.util.stream.Stream;

public enum WellKnownTypes {
    STRING("String", ""),
    INT("int", "");
    private final String selfName;
    private final String packageName;

    WellKnownTypes(String selfName, String packageName) {
        this.selfName = selfName;
        this.packageName = packageName;
    }

    public static Optional<FullType> forSelfName(String selfName) {
        return Stream.of(values()).filter(wellKnownType -> wellKnownType.selfName.equals(selfName)).findFirst()
                .map(wellKnownType -> new FullType(wellKnownType.packageName, selfName));
    }
}
