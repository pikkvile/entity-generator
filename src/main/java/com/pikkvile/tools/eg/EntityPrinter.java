package com.pikkvile.tools.eg;

import com.pikkvile.tools.eg.configuration.Config;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EntityPrinter {

    public final Config config;
    public final Path outputPath;

    private static final String EXT = ".java";

    public EntityPrinter(Config config) {
        this.config = config;
        this.outputPath = Paths.get(config.getOutputPath());
    }

    public void print(Entity entity) {
        try {
            OutputStream outputStream = prepareFile(entity);
            PrintWriter writer = new PrintWriter(outputStream);
            entity.print(writer);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private OutputStream prepareFile(Entity entity) throws IOException {
        String fileName = entity.selfTypeName() + EXT;
        Path filePath = outputPath.resolve(fileName);
        return Files.newOutputStream(filePath);
    }
}
