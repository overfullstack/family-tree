package loader;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.name.Names;
import com.google.inject.util.Modules;
import graph.ConnectionEdge;
import graph.FamilyGraph;
import graph.Person;
import modules.FamilyModule;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Class to test FileLoaderService
 */
public class FileLoaderServiceTest { 
    @Inject
    private static FamilyGraph family;
    @Inject
    private static LoaderService loader;

    @Before
    public void setUp() throws IOException {
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
    public void loadPersons() throws IOException {
        Map<Integer, Person> persons = new HashMap<>();
        persons.put(1, family.getPersonById("1"));
        persons.put(2, family.getPersonById("2"));
        persons.put(3, family.getPersonById("3"));
        Assert.assertTrue(persons.values().containsAll(family.getAllPersonsInFamily()));
    }

    @Test
    public void loadConnections() throws IOException {
        Set<ConnectionEdge> expectedConnections = new HashSet<>();
        expectedConnections.add(new ConnectionEdge("1", "SIBLING", "2", 0, family));
        expectedConnections.add(new ConnectionEdge("1", "CHILD", "3", -1, family));
        Assert.assertEquals(expectedConnections, family.getAllNeighbourConnections(family.getPersonById("1")));
    }
}