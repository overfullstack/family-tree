/*
 * Copyright (c) 2016, Gopal S Akshintala
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * http://creativecommons.org/licenses/by-sa/4.0/
 */

package loader;

import com.google.inject.Inject;
import graph.FamilyGraph;
import lombok.Cleanup;

import javax.inject.Named;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/**
 * Class to load Family from file.
 */
public class FileLoaderService implements LoaderService {
    @Inject
    @Named("family-file")
    private String familyFile;
    
    @Override
    public void loadFamily(FamilyGraph family) throws IOException {
        @Cleanup Reader reader = new BufferedReader(new FileReader(new File(familyFile)));
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
