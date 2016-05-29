/*
 * Copyright (c) 2016, Gopal S Akshintala
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * http://creativecommons.org/licenses/by-sa/4.0/
 */

package relationship;

/**
 * Interface to abstract methods related to Specific relations
 */
public interface ISpecificRelation extends IRelation {
    Boolean isRelationMale();

    IGenericRelation getGenericRelation();

    void setGenericRelation(IGenericRelation IGenericRelation);
}
