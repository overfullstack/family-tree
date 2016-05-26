package loader;

import graph.FamilyGraph;

import java.io.IOException;

/**
 * Interface for LoaderService
 */
public interface LoaderService {
    // Passing family as method arg instead of constructor arg, because instead of creating new service instance for 
    // every family, the same service instance can be reused to print multiple families
    void loadFamily(FamilyGraph family) throws IOException;
}
