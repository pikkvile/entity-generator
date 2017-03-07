package com.pikkvile.tools.eg;

import com.pikkvile.tools.eg.configuration.Config;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EntityPrinter {

    private final Path interfacesPath;
    private final Path implPath;

    private static final String EXT = ".java";

    public EntityPrinter(Config config) {
        this.interfacesPath = Paths.get(config.getOutputPath()).resolve(Paths.get(config.packageToPath()));
        this.implPath = interfacesPath.resolve("./impl");
    }

    public void print(Entity entity) {
        try {
            printInterface(entity);
            printImplementation(entity);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void printImplementation(Entity entity) throws IOException {
        OutputStream outputStream = prepareImplFile(entity);
        PrintWriter writer = new PrintWriter(outputStream);
        entity.printImpl(writer);
        writer.close();
    }

    private void printInterface(Entity entity) throws IOException {
        OutputStream outputStream = prepareInterfaceFile(entity);
        PrintWriter writer = new PrintWriter(outputStream);
        entity.printInterface(writer);
        writer.close();
    }

    private OutputStream prepareInterfaceFile(Entity entity) throws IOException {
        return prepareFile(interfacesPath, entity.selfTypeName() + EXT);
    }

    private OutputStream prepareImplFile(Entity entity) throws IOException {
        return prepareFile(implPath, entity.selfTypeName() + FullType.IMPL + EXT);
    }

    private OutputStream prepareFile(Path path, String name) throws IOException {
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        } else if (!Files.isDirectory(path)) {
            throw new EgException("File " + path + " already exists and is not a directory.");
        }
        Path filePath = path.resolve(name);
        return Files.newOutputStream(filePath);
    }
}
