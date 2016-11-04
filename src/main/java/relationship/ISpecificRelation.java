package relationship;

/**
 * Interface to abstract methods related to Specific relations
 */
public interface ISpecificRelation extends IRelation {
    Boolean isRelationMale();

    IGenericRelation getGenericRelation();

    void setGenericRelation(IGenericRelation IGenericRelation);
}
