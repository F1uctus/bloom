package com.f1uctus.bloom.plugins.speech;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Archives {
    /**
     * @return The topmost directory of this archive.
     * Equal to target if there's no top directory inside of archive.
     */
    public static Path unzip(InputStream source, Path target) throws IOException {
        Path rootDirectory = null;
        try (var zipStream = new ZipInputStream(source)) {
            ZipEntry nextEntry;
            while ((nextEntry = zipStream.getNextEntry()) != null) {
                var name = nextEntry.getName();
                if (!name.endsWith("/")) {
                    var nextFile = target.resolve(name);
                    var parent = nextFile.getParent();
                    if (parent != null) {
                        Files.createDirectories(parent);
                    }
                    Files.copy(zipStream, nextFile);
                } else if (rootDirectory == null) {
                    rootDirectory = target.resolve(name);
                }
            }
            if (rootDirectory == null) {
                return target;
            }
            return rootDirectory;
        }
    }
}
