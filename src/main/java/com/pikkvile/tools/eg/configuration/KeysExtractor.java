package com.pikkvile.tools.eg.configuration;

import com.pikkvile.tools.eg.configuration.Key;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KeysExtractor {

    private final Pattern longKeyPattern = Pattern.compile(LONG_KEY_PATTERN);
    private final Pattern shortKeyPattern = Pattern.compile(SHORT_KEY_PATTERN);

    private static final String LONG_KEY_PATTERN = "^--[a-zA-Z]+=.+$";
    private static final String SHORT_KEY_PATTERN = "^-[A-Z].+$";

    public void extractKeyToMap(String arg, Map<Key, String> keys) {
        if (arg.startsWith("--")) {
            extractLong(arg, keys);
        } else {
            extractShort(arg, keys);
        }
    }

    private void extractLong(String arg, Map<Key, String> keys) {
        Matcher matcher = longKeyPattern.matcher(arg);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Wrong key format: " + arg);
        }
        String[] keyAndValue = arg.split("=");
        Key key = Key.forLongRef(keyAndValue[0].substring(2)).orElseThrow(() ->
            new IllegalArgumentException("Unknown key: " + arg));
        keys.put(key, keyAndValue[1]);
    }

    private void extractShort(String arg, Map<Key, String> keys) {
        Matcher matcher = shortKeyPattern.matcher(arg);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Wrong key format: " + arg);
        }
        Key key = Key.forShortRef(arg.substring(1,2)).orElseThrow(() ->
                new IllegalArgumentException("Unknown key: " + arg));
        keys.put(key, arg.substring(2));
    }
}
