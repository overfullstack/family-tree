package validation;

import Software.SoftwareTest;
import core.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import relationship.GenericRelation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Class to Test AgeValidator
 */
class AgeValidatorTest extends SoftwareTest {
    @BeforeEach
    void init() {
        validator = new AgeValidator();
    }

    @Test
    void validate() {
        Person p1 = family.getPersonById("1");
        Person p2 = family.getPersonById("6");
        assertTrue(validator.validate(p1, GenericRelation.PARENT, p2, 1, null));
    }

    @Test
    void validate1() {
        Person p1 = family.getPersonById("1");
        Person p2 = family.getPersonById("6");
        assertFalse(validator.validate(p2, GenericRelation.PARENT, p1, 1, null));
    }

}