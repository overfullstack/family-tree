package relationship;

import org.junit.Assert;
import org.junit.Test;

/**
 * Class to test SpecificRelation
 */
public class SpecificRelationTest {

    @Test
    public void getGenericRelation() {
        Assert.assertEquals(GenericRelation.PARENT, SpecificRelation.MOTHER.getGenericRelation());
    }

}