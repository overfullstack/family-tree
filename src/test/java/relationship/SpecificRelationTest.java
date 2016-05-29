/*
 * Copyright (c) 2016, Gopal S Akshintala
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * http://creativecommons.org/licenses/by-sa/4.0/
 */

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