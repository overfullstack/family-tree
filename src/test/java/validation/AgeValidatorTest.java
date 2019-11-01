package validation;

import Software.SoftwareTest;
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
        var p1 = family.getPersonById("1");
        var p2 = family.getPersonById("6");
        assertTrue(validator.validate(p1, GenericRelation.PARENT, p2, 1, null));
    }

    @Test
    void validate1() {
        var p1 = family.getPersonById("1");
        var p2 = family.getPersonById("6");
        assertFalse(validator.validate(p2, GenericRelation.PARENT, p1, 1, null));
    }

}