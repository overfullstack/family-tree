package Software;

import graph.FamilyGraph;
import loader.FileLoaderService;
import loader.LoaderService;
import org.junit.Before;
import printer.ConsolePrintService;
import printer.PrintService;
import validation.AgeValidator;
import validation.GenderValidator;
import validation.IValidator;
import validation.RelationshipValidator;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Test Class holding setUp logic and is inherited by all Test Classes.
 */
public class SoftwareTest {

    protected static ByteArrayOutputStream outContent;
    protected static FamilyGraph family;
    protected static LoaderService loader;
    protected static PrintService printer;
    protected static IValidator validator;

    private static IValidator setUpValidation() {
        IValidator genderValidator = new GenderValidator();
        IValidator ageValidator = new AgeValidator();
        IValidator relationShipValidator = new RelationshipValidator();

        genderValidator.setNextValidatorInChain(ageValidator);
        ageValidator.setNextValidatorInChain(relationShipValidator);

        return genderValidator;
    }

    @Before
    public void setUp() {
        validator = setUpValidation();
        family = new FamilyGraph(validator);
        try {
            loader = new FileLoaderService(new BufferedReader(new FileReader(new File("src//main//resources//MyFamily.txt"))));
            loader.loadFamily(family);
            printer = new ConsolePrintService(System.out);

            outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));
        } catch (IOException e) {
            System.out.println("Exception while loading family from file: " + e.getMessage());
        }
    }

}
