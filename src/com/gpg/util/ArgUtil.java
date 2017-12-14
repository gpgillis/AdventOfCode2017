package com.gpg.util;

import java.io.File;
import java.util.Arrays;

public class ArgUtil {

    public static String getEnv(String[] args) {
        try {
            return getArgument("env", args);
        } catch (MissingArgumentException e) {
            return "local";
        }
    }

    public static String getArgument(String argName, String[] args)
            throws MissingArgumentException {
        String argStart = "--" + argName + "=";
        for (String arg : args) {
            if (arg.startsWith(argStart)) {
                return arg.replaceFirst(argStart, "");
            }
        }
        throw new MissingArgumentException(
                "Unable to find " + argName + " in " + Arrays.toString(args));
    }

    public static File requireFile(String argName, String[] args) throws MissingArgumentException {
        File file = getFile(argName, args);
        if (file.exists()) {
            return file;
        }
        else {
            throw new IllegalArgumentException(file.getAbsolutePath() + " does not exist");
        }
    }

    public static File getFile(String argName, String[] args) throws MissingArgumentException {
        return new File(getArgument(argName, args));
    }

    public static class MissingArgumentException extends Exception {
        public MissingArgumentException(String msg) {
            super(msg);
        }
    }
}
