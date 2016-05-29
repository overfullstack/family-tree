/*
 * Copyright (c) 2016, Gopal S Akshintala
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * http://creativecommons.org/licenses/by-sa/4.0/
 */

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
