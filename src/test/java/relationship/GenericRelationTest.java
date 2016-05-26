package relationship;

import org.junit.Assert;
import org.junit.Test;

/**
 * Class to test Generic Relation
 */
public class GenericRelationTest {

    @Test
    public void getGenderSpecificRelation() {
        Assert.assertEquals(SpecificRelation.MOTHER, GenericRelation.PARENT.getGenderSpecificRelation(false));
        Assert.assertEquals(SpecificRelation.MOTHER, GenericRelation.PARENT.getFemaleRelation());
    }

    @Test
    public void getReverseRelation() {
        Assert.assertEquals(GenericRelation.KIN, GenericRelation.NIBLING.getReverseRelation());
    }

    @Test
    public void getAlternateRelation() {
        Assert.assertEquals(GenericRelation.PARENT, GenericRelation.KIN.getAlternateRelation());
    }

    @Test
    public void getRelationLevel() {
        Assert.assertEquals(-2, GenericRelation.GRANDCHILD.getRelationLevel());
    }
}