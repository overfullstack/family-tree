package Software;

import com.google.inject.Guice;
import com.google.inject.Inject;
import coreGraph.FamilyGraph;
import loader.LoaderService;
import modules.FamilyModule;
import org.junit.jupiter.api.BeforeEach;
import validation.IValidator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Test Class holding setUpAll logic and is inherited by all Test Classes.
 */
public class SoftwareTest {
    @Inject
    protected static IValidator validator;
    @Inject
    protected static FamilyGraph family;
    @Inject
    protected static LoaderService loader;
    @Inject
    protected static ByteArrayOutputStream outContent;
    
    @BeforeEach
    void setUpAll() throws IOException {
        Guice.createInjector(new FamilyModule() {
            @Override
            protected void configure() {
                super.configure();
                requestStaticInjection(SoftwareTest.class);
            }
        });
        loader.loadFamily(family);
        System.setOut(new PrintStream(outContent));
    }
}
