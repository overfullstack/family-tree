/*
 * Copyright (c) 2016, Gopal S Akshintala
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * http://creativecommons.org/licenses/by-sa/4.0/
 */

package validation;

import coreGraph.FamilyGraph;
import coreGraph.Person;
import relationship.GenericRelation;
import relationship.IGenericRelation;
import relationship.ISpecificRelation;
import relationship.SpecificRelation;

/**
 * Class to validate Gender criteria for a relation
 */
public class GenderValidator implements IValidator {
    private IValidator nextValidator;

    @Override
    public void setNextValidatorInChain(IValidator validator) {
        this.nextValidator = validator;
    }

    @Override
    public boolean validate(Person p1, IGenericRelation genericRelation, Person p2, int relationLevel, FamilyGraph family) {
        boolean isValid;
        switch ((GenericRelation) genericRelation) {
            case SPOUSE:
                isValid = (p1.isGenderMale() != p2.isGenderMale());
                break;
            default:
                isValid = true;
        }
        return (nextValidator == null) ? isValid : isValid && nextValidator.validate(p1, genericRelation, p2, relationLevel,
                family);
    }

    @Override
    public boolean validate(Person p1, ISpecificRelation specificRelation, Person p2, int relationLevel, FamilyGraph family) {
        boolean isValid = (specificRelation.isRelationMale() == p1.isGenderMale());

        switch ((SpecificRelation) specificRelation) {
            case HUSBAND:
            case WIFE:
                isValid &= (p1.isGenderMale() != p2.isGenderMale());
        }
        return (nextValidator == null) ? isValid : isValid && nextValidator.validate(p1, specificRelation, p2,
                relationLevel, family);
    }
}
