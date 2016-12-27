package com.pikkvile.tools.eg;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EntityParser {

    private final TypeParser typeParser;
    private final Config config;

    public EntityParser(TypeParser typeParser, Config config) {
        this.typeParser = typeParser;
        this.config = config;
    }

    public Entity parse(String src) {

        String[] fullNameAndFields = src.split(":");
        FullType entityType = typeParser.parseFullType(fullNameAndFields[0]);
        Collection<Field> fields = Stream.of(fullNameAndFields[1].split(",")).map(fsrc -> {
                String[] typeAndName = fsrc.trim().split(" ");
                if (typeAndName.length == 2) {
                    String fullTypeName = typeAndName[0];
                    FullType fullType = typeParser.parseFullType(fullTypeName);
                    return new Field(fullType, typeAndName[1]);
                } else {
                    FullType fullType = typeParser.parseFullType(config.getDefaultType());
                    return new Field(fullType, typeAndName[0]);
                }
            }).collect(Collectors.toList());
        return new Entity(entityType, fields);
    }


}
