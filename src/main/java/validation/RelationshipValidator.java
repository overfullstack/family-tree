package validation;

import core.ConnectionEdge;
import core.FamilyGraph;
import core.Person;
import relationship.GenericRelation;
import relationship.SpecificRelation;

/**
 * Class to validate Possible relationship
 */
public class RelationshipValidator implements Validator {
    private Validator nextValidator;

    @Override
    public void setNextValidatorInChain(Validator validator) {
        this.nextValidator = validator;
    }

    @Override
    public boolean validate(Person p1, GenericRelation genericRelation, Person p2, int relationLevel, FamilyGraph family) {
        // It's Ok to compare generic relations as it has already passed the gender validation.
        ConnectionEdge possibleConnection = family.getConnection(p1, p2, false);
        boolean isValid;
        if (possibleConnection == null) {
            // Which means these two Persons are not connected at all, directly or indirectly.
            isValid = true;
        } else {
            boolean isRelationLevelValid;
            switch (genericRelation) {
                case GRANDPARENT:
                    isRelationLevelValid = relationLevel >= possibleConnection.relationLevel();
                    break;
                case GRANDCHILD:
                    isRelationLevelValid = relationLevel <= possibleConnection.relationLevel();
                    break;
                default:
                    isRelationLevelValid = relationLevel == possibleConnection.relationLevel();
            }
            isValid = isRelationLevelValid &&
                    (genericRelation.equals(possibleConnection.relation())
                            || genericRelation.getAlternateRelation().equals(possibleConnection.relation()));
        }
        return (nextValidator == null) ? isValid : isValid && nextValidator.validate(p1, genericRelation, p2, relationLevel,
                family);
    }

    @Override
    public boolean validate(Person p1, SpecificRelation specificRelation, Person p2, int relationLevel, FamilyGraph family) {
        return this.validate(p1, specificRelation.getGenericRelation(), p2, relationLevel, family);
    }
}
