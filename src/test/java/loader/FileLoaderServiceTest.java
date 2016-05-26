package loader;

import graph.ConnectionEdge;
import graph.FamilyGraph;
import graph.Person;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import validation.AgeValidator;
import validation.GenderValidator;
import validation.IValidator;
import validation.RelationshipValidator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Class to test FileLoaderService
 */
public class FileLoaderServiceTest {

    private LoaderService loaderService;
    private FamilyGraph family;

    private static IValidator setUpValidation() {
        IValidator genderValidator = new GenderValidator();
        IValidator ageValidator = new AgeValidator();
        IValidator relationShipValidator = new RelationshipValidator();

        genderValidator.setNextValidatorInChain(ageValidator);
        ageValidator.setNextValidatorInChain(relationShipValidator);

        return genderValidator;
    }

    @Before
    public void setUp() throws IOException {
        loaderService = new FileLoaderService(new BufferedReader(new FileReader(new File(
                "src//test//resources//testFamilyToLoad.txt"))));
        IValidator validator = setUpValidation();
        family = new FamilyGraph(validator);
        loaderService.loadFamily(family);
    }

    @Test
    public void loadPersons() {
        Map<Integer, Person> persons = new HashMap<>();
        persons.put(1, family.getPersonById("1"));
        persons.put(2, family.getPersonById("2"));
        persons.put(3, family.getPersonById("3"));
        Assert.assertTrue(persons.values().containsAll(family.getAllPersonsInFamily()));
    }

    @Test
    public void loadConnections() {
        Set<ConnectionEdge> expectedConnections = new HashSet<>();
        expectedConnections.add(new ConnectionEdge("1", "SIBLING", "2", 0, family));
        expectedConnections.add(new ConnectionEdge("1", "CHILD", "3", -1, family));
        Assert.assertEquals(expectedConnections, family.getAllNeighbourConnections(family.getPersonById("1")));
    }
}