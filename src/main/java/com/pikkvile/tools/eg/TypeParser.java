package com.pikkvile.tools.eg;

import java.util.Arrays;

public class TypeParser {

    private final Config config;

    public TypeParser(Config config) {
        this.config = config;
    }

    public FullType parseFullType(String fullTypeName) {
        String[] packagesAndName = fullTypeName.split("\\.");
        if (packagesAndName.length == 1) {
            return WellKnownTypes.forSelfName(fullTypeName)
                    .orElse(new FullType(config.getDefaultPackage(), fullTypeName));
        } else {
            return new FullType(
                    String.join(".", (CharSequence[]) Arrays.copyOf(packagesAndName, packagesAndName.length - 1)),
                    packagesAndName[packagesAndName.length - 1]);
        }
    }
}
