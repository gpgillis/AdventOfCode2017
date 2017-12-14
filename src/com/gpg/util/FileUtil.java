package com.gpg.util;

import java.io.File;
import java.io.IOException;

public class FileUtil {
    public static String replaceExtension(String filename, String newExtension) {
        return filenameWithoutExtension(filename) + "." + newExtension;
    }

    public static void mustDelete(File file) throws IOException {
        if (file.exists() && !file.delete()) {
            throw new IOException("Failed to delete file: " + file);
        }
    }

    public static void deleteRecursive(File file) throws IOException {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files == null) {
                throw new IOException("Failed to open folder: " + file);
            }

            for (File c : files) {
                deleteRecursive(c);
            }
        }

        mustDelete(file);
    }

    public static String filenameWithoutExtension(String filename) {
        return filename.substring(0, filename.lastIndexOf('.'));
    }
}
