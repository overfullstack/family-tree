package loader;

import core.FamilyGraph;

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
    public void loadFamily(FamilyGraph family) {
        // Do necessary to read family from db
    }
}
