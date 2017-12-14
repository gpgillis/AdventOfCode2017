package com.gpg.util;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by ggillis on 12/8/2017.
 */
public class Logging {

    private static final Logger LOG = Logger.getAnonymousLogger();

    /**
     * Display message, log exception, and quit, this method will never return normally
     */
    public static void fail(String message, Exception err) {
        LOG.log(Level.SEVERE, err.getMessage(), err);
        LOG.log(Level.SEVERE, message);
        System.exit(-1);
    }
}
