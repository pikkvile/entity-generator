package com.pikkvile.tools.eg;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.pikkvile.tools.eg.EntityParser.ENTITIES_DELIMITER;

public class SrcFileReader {

    private final String fileName;

    public SrcFileReader(String fileName) {
        this.fileName = fileName;
    }

    public Collection<String> read() {
        try {
            String content = new String(Files.readAllBytes(Paths.get(fileName)));
            return Stream.of(content.split(ENTITIES_DELIMITER))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new EgException("Can't read source file", e);
        }
    }
}
