package loader;

import com.google.inject.Inject;
import core.FamilyGraph;
import lombok.Cleanup;

import javax.inject.Named;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class to load Family from file.
 */
public class FileLoaderService implements LoaderService {
    @Inject
    @Named("family-file")
    private String familyFile;
    
    @Override
    public void loadFamily(FamilyGraph family) throws IOException {
        @Cleanup var reader = new BufferedReader(new FileReader(new File(familyFile)));
        load(family, reader, true);
        load(family, reader, false);
    }

    private void load(FamilyGraph family, BufferedReader reader, boolean isLoadingPersons) throws IOException {
        var line = reader.readLine();
        while ((line != null) && (line.length() > 0)) {
            var vals = line.split(",");
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
        var len = vals.length;
        for (var i = 0; i < len; i++) {
            vals[i] = vals[i].trim();
        }
    }
}
