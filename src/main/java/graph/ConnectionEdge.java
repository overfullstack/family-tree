package graph;

import relationship.IGenericRelation;
import relationship.ISpecificRelation;

import static utils.RelationUtils.parseToGenericRelation;

/**
 * Class representing Connection between persons
 */
public final class ConnectionEdge {
    private final Person from, to;
    private final IGenericRelation relation;
    private int relationLevel;

    public ConnectionEdge(Person from, IGenericRelation relation, Person to) {
        this.from = from;
        this.to = to;
        this.relation = relation;
        this.relationLevel = relation.getRelationLevel();
    }

    public ConnectionEdge(Person from, ISpecificRelation relation, Person to) {
        this(from, relation.getGenericRelation(), to);
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
        this.from = family.getPersonById(fromPid);
        this.to = family.getPersonById(toPid);
        this.relation = parseToGenericRelation(relation);
        this.relationLevel = relationLevel;
    }

    public ConnectionEdge(Person from, IGenericRelation relation, Person to, int relationLevel) {
        this(from, relation, to);
        this.relationLevel = relationLevel;
    }

    public Person from() {
        return this.from;
    }

    public Person to() {
        return this.to;
    }

    public IGenericRelation relation() {
        return this.relation;
    }

    public int getRelationLevel() {
        return this.relationLevel;
    }

    public void setRelationLevel(int relationLevel) {
        this.relationLevel = relationLevel;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ConnectionEdge) {
            ConnectionEdge edge = (ConnectionEdge) obj;
            return this.from.equals(edge.from) && this.to.equals(edge.to) && this.relation.equals(edge.relation) && this
                    .relationLevel == edge.relationLevel;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.from.hashCode() + this.to.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder grandRelationPrefix = new StringBuilder();

        int absoluteRelationLevel = Math.abs(this.relationLevel);
        if (absoluteRelationLevel > 2) {
            grandRelationPrefix = generateGrandRelationPrefix(absoluteRelationLevel);
        }
        return this.from + " is " + grandRelationPrefix.append(this.relation().getGenderSpecificRelation(this.from
                .isGenderMale())) + " of " + this.to + " RelationLevel: " + this.relationLevel;
    }

    private StringBuilder generateGrandRelationPrefix(int relationLevel) {
        StringBuilder grandPrefix = new StringBuilder();
        while (relationLevel-- > 2) {
            grandPrefix.append("GREAT ");
        }
        return grandPrefix;
    }
}
