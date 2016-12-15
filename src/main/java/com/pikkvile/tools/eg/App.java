package com.pikkvile.tools.eg;

import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App {

    private final String[] args;
    private Config config;
    private Collection<String> entitiesSrc;

    public static void main(String[] args) {
        App app = new App(args);
        try {
            app.run();
        } catch (IllegalArgumentException iae) {
            System.out.println(iae.getMessage());
        }
    }

    public App(String[] args) {
        this.args = args;
    }

    private void run() {
        parseArgs();
        if (entitiesSrc == null) {
            entitiesSrc = config.getSourceFileName()
                    .map(SrcFileReader::new)
                    .map(SrcFileReader::read)
                    .orElseThrow(() -> new IllegalArgumentException("No sources provided"));
        }
        EntityParser entityParser = new EntityParser(config.getDefaultPackage());
        entitiesSrc.stream().map(entityParser::parse);
    }

    private void parseArgs() {

        // Syntax 1
        // eg "our.own.package.Person: String name, Integer age, our.own.package.Address address, our.own.package.Position: String title, BigDecimal salary"
        // separate entities definitions by comma
        // packages can be provided explicitly, or inferred: a) go up until "java" folder b) using -Pour.own.package, --package=our.own.package key
        // this will substitute this package everywhere where's no explicit package
        // packages for some common-used types can be guessed, for example, BigDecimal. otherwise you will have to alt-enter it
        // in your IDE

        // Syntax 2
        // eg -Sinput.eg, eg --source=input.eg

        // -O, --output available in both syntaxes, package is inferred from this folder, current by default

        if (args.length < 1) {
            throw new IllegalArgumentException("Not enough arguments. Run eg --help for reference.");
        }

        Map<Key, String> keys = new EnumMap<>(Key.class);
        KeysExtractor extractor = new KeysExtractor();
        for (String arg: args) {
            if (arg.startsWith("-")) {
                extractor.extractKeyToMap(arg, keys);
            } else {
                entitiesSrc = Stream.of(arg.split(",")).map(String::trim).collect(Collectors.toList());
            }
        }

        config = new Config(keys);
    }
}
