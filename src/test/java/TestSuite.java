/*
 * Copyright (c) 2016, Gopal S Akshintala
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * http://creativecommons.org/licenses/by-sa/4.0/
 */

import graph.ConnectionEdgeTest;
import graph.FamilyGraphTest;
import loader.FileLoaderServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import relationship.GenericRelationTest;
import relationship.SpecificRelationTest;
import validation.AgeValidatorTest;
import validation.GenderValidatorTest;
import validation.RelationshipValidatorTest;

/**
 * Test Suite to aggregate all tests
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ConnectionEdgeTest.class,
        FamilyGraphTest.class,
        FileLoaderServiceTest.class,
        GenericRelationTest.class,
        SpecificRelationTest.class,
        AgeValidatorTest.class,
        GenderValidatorTest.class,
        RelationshipValidatorTest.class
})

public class TestSuite {

}
