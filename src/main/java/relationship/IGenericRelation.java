package relationship;

/**
 * Interface to abstract methods related to Generic relations
 */
public interface IGenericRelation extends IRelation {
    ISpecificRelation getFemaleRelation();

    ISpecificRelation getMaleRelation();

    ISpecificRelation getGenderSpecificRelation(boolean isMale);

    IGenericRelation getReverseRelation();

    IGenericRelation getAlternateRelation();

    int getRelationLevel();

    IGenericRelation getNextGenericRelation(IGenericRelation curRelation);
}
