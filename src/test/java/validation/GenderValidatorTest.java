package validation;

import Software.SoftwareTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import relationship.GenericRelation;
import relationship.SpecificRelation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Class to test GenderValidator
 */
class GenderValidatorTest extends SoftwareTest {

    @BeforeEach
    void init() {
        validator = new GenderValidator();
    }

    @Test
    void validate() {
        var p1 = family.getPersonById("1");
        var p2 = family.getPersonById("8");
        assertTrue(validator.validate(p1, GenericRelation.SPOUSE, p2, 0, null));
    }

    @Test
    void validate1() {
        var p1 = family.getPersonById("2");
        var p2 = family.getPersonById("8");
        assertFalse(validator.validate(p1, GenericRelation.SPOUSE, p2, 0, null));
    }

    @Test
    void validate2() {
        var p1 = family.getPersonById("1");
        var p2 = family.getPersonById("8");
        assertTrue(validator.validate(p1, SpecificRelation.WIFE, p2, 0, null));
    }

    @Test
    void validate3() {
        var p1 = family.getPersonById("1");
        var p2 = family.getPersonById("2");
        assertFalse(validator.validate(p1, SpecificRelation.BROTHER, p2, 0, null));
    }

    @Test
    void validate4() {
        var p1 = family.getPersonById("1");
        var p2 = family.getPersonById("2");
        assertTrue(validator.validate(p1, SpecificRelation.SISTER, p2, 0, null));
    }

}