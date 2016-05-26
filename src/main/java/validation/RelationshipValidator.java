package validation;

import graph.ConnectionEdge;
import graph.FamilyGraph;
import graph.Person;
import relationship.GenericRelation;
import relationship.IGenericRelation;
import relationship.ISpecificRelation;

/**
 * Class to validate Possible relationship
 */
public class RelationshipValidator implements IValidator {
    private IValidator nextValidator;

    @Override
    public void setNextValidatorInChain(IValidator validator) {
        this.nextValidator = validator;
    }

    @Override
    public boolean validate(Person p1, IGenericRelation genericRelation, Person p2, int relationLevel, FamilyGraph family) {
        // It's Ok to compare generic relations as it has already passed the gender validation.
        ConnectionEdge possibleConnection = family.getConnection(p1, p2);
        boolean isValid;
        if (possibleConnection == null) {
            // Which means these two Persons are not connected at all, directly or indirectly.
            isValid = true;
        } else {
            boolean isRelationLevelValid;
            switch ((GenericRelation) genericRelation) {
                case GRANDPARENT:
                    isRelationLevelValid = relationLevel >= possibleConnection.getRelationLevel();
                    break;
                case GRANDCHILD:
                    isRelationLevelValid = relationLevel <= possibleConnection.getRelationLevel();
                    break;
                default:
                    isRelationLevelValid = relationLevel == possibleConnection.getRelationLevel();
            }
            isValid = isRelationLevelValid &&
                    (genericRelation.equals(possibleConnection.relation())
                            || genericRelation.getAlternateRelation().equals(possibleConnection.relation()));
        }
        return (nextValidator == null) ? isValid : isValid && nextValidator.validate(p1, genericRelation, p2, relationLevel,
                family);
    }

    @Override
    public boolean validate(Person p1, ISpecificRelation specificRelation, Person p2, int relationLevel, FamilyGraph family) {
        return this.validate(p1, specificRelation.getGenericRelation(), p2, relationLevel, family);
    }
}
