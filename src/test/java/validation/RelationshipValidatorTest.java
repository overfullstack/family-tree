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

/**
 * Class to test RelationShipValidator
 */
public class RelationshipValidatorTest extends SoftwareTest {
    @Before
    public void init() {
        validator = new RelationshipValidator();
    }

    @Test
    public void validate() {
        Person p1 = family.getPersonById("3");
        Person p2 = family.getPersonById("8");
        Assert.assertTrue(validator.validate(p1, GenericRelation.KIN, p2, 1, family));
    }

    @Test
    public void validate1() {
        Person p1 = family.getPersonById("3");
        Person p2 = family.getPersonById("8");
        Assert.assertFalse(validator.validate(p1, GenericRelation.PARENT, p2, 1, family));
    }

    @Test
    public void validate2() {
        Person p1 = family.getPersonById("4");
        Person p2 = family.getPersonById("2");
        Assert.assertTrue(validator.validate(p1, GenericRelation.PARENT, p2, 1, family));
    }

    @Test
    public void validateRelationLevel() {
        Person p1 = family.getPersonById("4");
        Person p2 = family.getPersonById("2");
        Assert.assertFalse(validator.validate(p1, GenericRelation.PARENT, p2, 2, family));
        Assert.assertFalse(validator.validate(p1, GenericRelation.GRANDPARENT, p2, 5, family));
        Assert.assertFalse(validator.validate(p1, GenericRelation.GRANDCHILD, p2, -5, family));
    }
}