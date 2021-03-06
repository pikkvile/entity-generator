package com.pikkvile.tools.eg;

import com.pikkvile.tools.eg.configuration.Config;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EntityParser {

    private final TypeParser typeParser;
    private final Config config;

    static final String ENTITIES_DELIMITER = ";";
    private static final String NAME_FIELDS_DELIMITER = ":";
    private static final String FIELDS_DELIMITER = ",";
    private static final String FIELD_TYPE_NAME_DELIMITER = " ";

    public EntityParser(TypeParser typeParser, Config config) {
        this.typeParser = typeParser;
        this.config = config;
    }

    public Entity parse(String src) {

        String[] fullNameAndFields = src.split(NAME_FIELDS_DELIMITER);
        FullType entityType = typeParser.parseFullType(fullNameAndFields[0]);
        Collection<Field> fields = Stream.of(fullNameAndFields[1].split(FIELDS_DELIMITER)).map(fsrc -> {
                String[] typeAndName = fsrc.trim().split(FIELD_TYPE_NAME_DELIMITER);
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
