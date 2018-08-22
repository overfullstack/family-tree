package validation;

import Software.SoftwareTest;
import coreGraph.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import relationship.GenericRelation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Class to test RelationShipValidator
 */
class RelationshipValidatorTest extends SoftwareTest {
    @BeforeEach
    void init() {
        validator = new RelationshipValidator();
    }

    @Test
    void validate() {
        Person p1 = family.getPersonById("3");
        Person p2 = family.getPersonById("8");
        assertTrue(validator.validate(p1, GenericRelation.KIN, p2, 1, family));
    }

    @Test
    void validate1() {
        Person p1 = family.getPersonById("3");
        Person p2 = family.getPersonById("8");
        assertFalse(validator.validate(p1, GenericRelation.PARENT, p2, 1, family));
    }

    @Test
    void validate2() {
        Person p1 = family.getPersonById("4");
        Person p2 = family.getPersonById("2");
        assertTrue(validator.validate(p1, GenericRelation.PARENT, p2, 1, family));
    }

    @Test
    void validateRelationLevel() {
        Person p1 = family.getPersonById("4");
        Person p2 = family.getPersonById("2");
        assertFalse(validator.validate(p1, GenericRelation.PARENT, p2, 2, family));
        assertFalse(validator.validate(p1, GenericRelation.GRANDPARENT, p2, 5, family));
        assertFalse(validator.validate(p1, GenericRelation.GRANDCHILD, p2, -5, family));
    }
}