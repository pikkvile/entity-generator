package com.pikkvile.tools.eg;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Config {

    private final Map<Key, String> keys;

    private static final String DEFAULT_OUTPUT = ".";
    private static final String DEFAULT_TYPE = "String";

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

    public String getDefaultType() {
        return Optional.ofNullable(keys.get(Key.TYPE)).orElse(DEFAULT_TYPE);
    }

    private String defaultPackage() {
        Path path = Paths.get("").toAbsolutePath();
        if (path.toString().contains("/java/")) {
            List<String> pathToJava = Arrays.asList(inferDefaultPackage(path).split("\\."));
            Collections.reverse(pathToJava);
            return pathToJava.stream().collect(Collectors.joining("."));
        } else {
            return "";
        }
    }

    private String inferDefaultPackage(Path path) {
        if (path.getFileName().toString().equals("java")) return "";
        else return path.getFileName() + "." + inferDefaultPackage(path.getParent());
    }
}
