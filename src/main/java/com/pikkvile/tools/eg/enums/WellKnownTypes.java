package com.pikkvile.tools.eg.enums;

import com.pikkvile.tools.eg.FullType;

import java.util.Optional;
import java.util.stream.Stream;

public enum WellKnownTypes {

    STRING("String", "java.lang"),
    INT("int", ""),
    BIG_DECIMAL("BigDecimal", "java.math");

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
