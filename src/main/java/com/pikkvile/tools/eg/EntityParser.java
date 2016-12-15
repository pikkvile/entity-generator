package com.pikkvile.tools.eg;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EntityParser {

    private final String defaultPackage;

    public EntityParser(String defaultPackage) {
        this.defaultPackage = defaultPackage;
    }

    public Entity parse(String src) {

        // Entity is like Name: Type1 name1, Type2 name2, name 3
        // by default type is String, so it can be ommited
        // packages can be set explicitly in names
        // otherwise default package (provided or inferred) is used

        String[] fullNameAndFields = src.split(":");
        FullType entityType = fullType(fullNameAndFields[0]);
        Collection<Field> fields = Stream.of(fullNameAndFields[1]).map(fsrc -> {
                String[] typeAndName = fsrc.trim().split(" ");
                String fullTypeName = typeAndName[0];
                FullType fullType = parseFullType(fullTypeName);
                return new Field(fullType, typeAndName[1]);
            }).collect(Collectors.toList());
        return new Entity(entityType, fields);
    }

    private FullType fullType(String fullName) {
        return parseFullType(fullName);
    }

    private FullType parseFullType(String fullTypeName) {
        String[] packagesAndName = fullTypeName.split("\\.");
        if (packagesAndName.length == 0) {
            return new FullType("", fullTypeName);
        } else {
            return new FullType(
                    String.join(".", (CharSequence[]) Arrays.copyOf(packagesAndName, packagesAndName.length - 1)),
                    packagesAndName[packagesAndName.length - 1]);
        }
    }
}
