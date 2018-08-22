package relationship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Class to test SpecificRelation
 */
class SpecificRelationTest {

    @Test
    void getGenericRelation() {
        assertEquals(GenericRelation.PARENT, SpecificRelation.MOTHER.getGenericRelation());
    }

}