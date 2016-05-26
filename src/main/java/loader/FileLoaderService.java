package loader;

import graph.FamilyGraph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 * Class to load Family from file.
 */
public class FileLoaderService implements LoaderService {

    private final Reader reader;

    public FileLoaderService(Reader reader) {
        this.reader = reader;
    }

    @Override
    public void loadFamily(FamilyGraph family) throws IOException {
        load(family, (BufferedReader) reader, true);
        load(family, (BufferedReader) reader, false);
    }

    private void load(FamilyGraph family, BufferedReader reader, boolean isLoadingPersons) throws IOException {
        String line = reader.readLine();
        while ((line != null) && (line.length() > 0)) {
            String[] vals = line.split(",");
            trimVals(vals);
            if (isLoadingPersons) {
                family.addPerson(vals[0], vals[1], vals[2], vals[3]);
            } else {
                family.connectPersons(vals[0], vals[1], vals[2]);
            }
            line = reader.readLine();
        }
    }

    private void trimVals(String[] vals) {
        int len = vals.length;
        for (int i = 0; i < len; i++) {
            vals[i] = vals[i].trim();
        }
    }
}
