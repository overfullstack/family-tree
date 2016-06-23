/*
 * Copyright (c) 2016, Gopal S Akshintala
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * http://creativecommons.org/licenses/by-sa/4.0/
 */

package validation;

import Software.SoftwareTest;
import coreGraph.Person;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import relationship.GenericRelation;

/**
 * Class to Test AgeValidator
 */
public class AgeValidatorTest extends SoftwareTest {
    @Before
    public void init() {
        validator = new AgeValidator();
    }

    @Test
    public void validate() {
        Person p1 = family.getPersonById("1");
        Person p2 = family.getPersonById("6");
        Assert.assertTrue(validator.validate(p1, GenericRelation.PARENT, p2, 1, null));
    }

    @Test
    public void validate1() {
        Person p1 = family.getPersonById("1");
        Person p2 = family.getPersonById("6");
        Assert.assertFalse(validator.validate(p2, GenericRelation.PARENT, p1, 1, null));
    }

}