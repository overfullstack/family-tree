package validation;

import coreGraph.FamilyGraph;
import coreGraph.Person;
import relationship.GenericRelation;
import relationship.SpecificRelation;

/**
 * Interface to abstract Validators
 */
public interface Validator {
    void setNextValidatorInChain(Validator validator);

    boolean validate(Person p1, GenericRelation genericRelation, Person p2, int relationLevel, FamilyGraph family);

    boolean validate(Person p1, SpecificRelation specificRelation, Person p2, int relationLevel, FamilyGraph family);
}
