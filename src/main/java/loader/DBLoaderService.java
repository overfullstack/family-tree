/*
 * Copyright (c) 2016, Gopal S Akshintala
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * http://creativecommons.org/licenses/by-sa/4.0/
 */

package loader;

import coreGraph.FamilyGraph;

import java.io.IOException;

/**
 * Dummy Class for reading family from DB.
 */
public class DBLoaderService implements LoaderService {

    private final Object dbReader;

    public DBLoaderService(Object dbReader) {
        // Get necessary params in the constructor to read from DB.
        this.dbReader = dbReader;
    }

    @Override
    public void loadFamily(FamilyGraph family) throws IOException {
        // Do necessary to read family from db
    }
}
