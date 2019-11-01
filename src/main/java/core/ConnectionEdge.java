package core;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;
import relationship.GenericRelation;
import relationship.SpecificRelation;

import static utils.RelationUtils.parseToGenericRelation;

/**
 * Class representing Connection between persons
 */
@Value
@AllArgsConstructor
public final class ConnectionEdge {
    @NonNull
    private final Person from;
    @NonNull
    private final GenericRelation relation;
    @NonNull
    private final Person to;
    private int relationLevel;

    public ConnectionEdge(Person from, SpecificRelation relation, Person to) {
        this(from, relation.getGenericRelation(), to);
    }

    public ConnectionEdge(Person from, GenericRelation relation, Person to) {
        this(from, relation, to, relation.getRelationLevel());
    }

    /**
     * This Constructor mainly defined to ease Unit testing
     *
     * @param fromPid
     * @param relation
     * @param toPid
     * @param relationLevel
     * @param family
     */
    public ConnectionEdge(String fromPid, String relation, String toPid, int relationLevel, FamilyGraph family) {
        this(family.getPersonById(fromPid), parseToGenericRelation(relation), family.getPersonById(toPid),
                relationLevel);
    }

    @Override
    public String toString() {
        var absoluteRelationLevel = Math.abs(this.relationLevel);
        var grandRelationPrefix = generateGrandRelationPrefix(absoluteRelationLevel);
        return String.format("%s is %s of %s RelationLevel: %d", 
                this.from, grandRelationPrefix.append(this.relation.getGenderSpecificRelation(this.from.isGenderMale())), 
                this.to, this.relationLevel);
    }

    private StringBuilder generateGrandRelationPrefix(int relationLevel) {
        var grandPrefix = new StringBuilder();
        while (relationLevel-- > 2) {
            grandPrefix.append("GREAT ");
        }
        return grandPrefix;
    }
}
