package relationship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Class to test Generic Relation
 */
class GenericRelationTest {

    @Test
    void getGenderSpecificRelation() {
        assertEquals(SpecificRelation.MOTHER, GenericRelation.PARENT.getGenderSpecificRelation(false));
        assertEquals(SpecificRelation.MOTHER, GenericRelation.PARENT.getFemaleRelation());
    }

    @Test
    void getReverseRelation() {
        assertEquals(GenericRelation.KIN, GenericRelation.NIBLING.getReverseRelation());
    }

    @Test
    void getAlternateRelation() {
        assertEquals(GenericRelation.PARENT, GenericRelation.KIN.getAlternateRelation());
    }

    @Test
    void getRelationLevel() {
        assertEquals(-2, GenericRelation.GRANDCHILD.getRelationLevel());
    }
}