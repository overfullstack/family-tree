package utils;

import relationship.GenericRelation;
import relationship.IGenericRelation;
import relationship.IRelation;
import relationship.SpecificRelation;

/**
 * Utility Class to deal with relation
 */
public interface RelationUtils {
    static IGenericRelation parseToGenericRelation(String relation) {
        relation = relation.toUpperCase();
        try {
            return GenericRelation.valueOf(relation);
        } catch (IllegalArgumentException e) {
            return SpecificRelation.valueOf(relation).getGenericRelation();
        }
    }

    static IRelation parseToRelation(String relation) {
        relation = relation.toUpperCase();
        try {
            return GenericRelation.valueOf(relation);
        } catch (IllegalArgumentException e) {
            return SpecificRelation.valueOf(relation);
        }
    }
}
