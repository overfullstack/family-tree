/*
 * Copyright (c) 2016, Gopal S Akshintala
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * http://creativecommons.org/licenses/by-sa/4.0/
 */

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
import validation.IValidator;
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
    public IValidator provideValidator() {
        IValidator genderValidator = new GenderValidator();
        IValidator ageValidator = new AgeValidator();
        IValidator relationShipValidator = new RelationshipValidator();

        genderValidator.setNextValidatorInChain(ageValidator);
        ageValidator.setNextValidatorInChain(relationShipValidator);

        return genderValidator;
    }
}
