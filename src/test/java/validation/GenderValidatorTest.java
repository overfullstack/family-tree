/*
 * Copyright (c) 2016, Gopal S Akshintala
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * http://creativecommons.org/licenses/by-sa/4.0/
 */

package validation;

import Software.SoftwareTest;
import graph.Person;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import relationship.GenericRelation;
import relationship.SpecificRelation;

/**
 * Class to test GenderValidator
 */
public class GenderValidatorTest extends SoftwareTest {

    @Before
    public void init() {
        validator = new GenderValidator();
    }

    @Test
    public void validate() {
        Person p1 = family.getPersonById("1");
        Person p2 = family.getPersonById("8");
        Assert.assertTrue(validator.validate(p1, GenericRelation.SPOUSE, p2, 0, null));
    }

    @Test
    public void validate1() {
        Person p1 = family.getPersonById("2");
        Person p2 = family.getPersonById("8");
        Assert.assertFalse(validator.validate(p1, GenericRelation.SPOUSE, p2, 0, null));
    }

    @Test
    public void validate2() {
        Person p1 = family.getPersonById("1");
        Person p2 = family.getPersonById("8");
        Assert.assertTrue(validator.validate(p1, SpecificRelation.WIFE, p2, 0, null));
    }

    @Test
    public void validate3() {
        Person p1 = family.getPersonById("1");
        Person p2 = family.getPersonById("2");
        Assert.assertFalse(validator.validate(p1, SpecificRelation.BROTHER, p2, 0, null));
    }

    @Test
    public void validate4() {
        Person p1 = family.getPersonById("1");
        Person p2 = family.getPersonById("2");
        Assert.assertTrue(validator.validate(p1, SpecificRelation.SISTER, p2, 0, null));
    }

}