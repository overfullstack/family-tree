package loader;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.name.Names;
import com.google.inject.util.Modules;
import core.ConnectionEdge;
import core.FamilyGraph;
import core.Person;
import modules.FamilyModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Class to test FileLoaderService
 */
class FileLoaderServiceTest { 
    @Inject
    private static FamilyGraph family;
    @Inject
    private static LoaderService loader;

    @BeforeEach
    void setUp() throws IOException {
        // The line 41 can essentially be placed in SoftwareTest setup and remove this entire method and make it extend SoftwareTest.
        // But this is retained just to show how can the module be overriden.
        Guice.createInjector(Modules.override(new FamilyModule()).with(new AbstractModule() {
            @Override
            protected void configure() {
                requestStaticInjection(FileLoaderServiceTest.class);
                bind(String.class).annotatedWith(Names.named("family-file")).toInstance
                        ("src//test//resources//testFamilyToLoad.txt");
            }
        }));
        loader.loadFamily(family);
    }

    @Test
    void loadPersons() {
        Map<Integer, Person> persons = new HashMap<>();
        persons.put(1, family.getPersonById("1"));
        persons.put(2, family.getPersonById("2"));
        persons.put(3, family.getPersonById("3"));
        assertTrue(persons.values().containsAll(family.getAllPersonsInFamily()));
    }

    @Test
    void loadConnections() {
        Set<ConnectionEdge> expectedConnections = new HashSet<>();
        expectedConnections.add(new ConnectionEdge("1", "SIBLING", "2", 0, family));
        expectedConnections.add(new ConnectionEdge("1", "CHILD", "3", -1, family));
        assertEquals(expectedConnections, family.getAllNeighbourConnections(family.getPersonById("1")));
    }
}