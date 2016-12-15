package com.pikkvile.tools.eg;

import java.util.Map;
import java.util.Optional;

public class Config {

    private final Map<Key, String> keys;

    private static final String DEFAULT_OUTPUT = ".";

    public Config(Map<Key, String> keys) {
        this.keys = keys;
    }

    public Optional<String> getSourceFileName() {
        return Optional.ofNullable(keys.get(Key.SOURCE));
    }

    public String getOutputPath() {
        return Optional.ofNullable(keys.get(Key.OUTPUT)).orElse(DEFAULT_OUTPUT);
    }

    public String getDefaultPackage() {
        return Optional.ofNullable(keys.get(Key.PACKAGE)).orElse(defaultPackage());
    }

    private String defaultPackage() {
        return "to.do";
    }
}
