package modules;

import client.FamilyNetworkClient;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Names;
import loader.FileLoaderService;
import loader.LoaderService;
import printer.ConsolePrintService;
import printer.PrintService;
import validation.AgeValidator;
import validation.GenderValidator;
import validation.Validator;
import validation.RelationshipValidator;

import java.io.OutputStream;

/**
 * Guice module for DI
 */
public class FamilyModule extends AbstractModule {
    @Override
    protected void configure() {
        requestStaticInjection(FamilyNetworkClient.class);
        bind(String.class).annotatedWith(Names.named("family-file")).toInstance("src//main//resources//MyFamily.txt");
        bind(LoaderService.class).to(FileLoaderService.class);
        bind(PrintService.class).to(ConsolePrintService.class);
        bind(OutputStream.class).toInstance(System.out);
    }

    @Provides
    public Validator provideValidator() {
        Validator genderValidator = new GenderValidator();
        Validator ageValidator = new AgeValidator();
        Validator relationShipValidator = new RelationshipValidator();

        genderValidator.setNextValidatorInChain(ageValidator);
        ageValidator.setNextValidatorInChain(relationShipValidator);

        return genderValidator;
    }
}
