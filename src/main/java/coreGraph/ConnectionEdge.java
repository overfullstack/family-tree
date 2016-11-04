package coreGraph;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;
import relationship.IGenericRelation;
import relationship.ISpecificRelation;

import java.util.Objects;

import static utils.RelationUtils.parseToGenericRelation;

/**
 * Class representing Connection between persons
 */
@AllArgsConstructor(suppressConstructorProperties = true)
@Accessors(fluent = true)
public final class ConnectionEdge {
    @NonNull
    @Getter
    private final Person from;
    @NonNull
    @Getter
    private final IGenericRelation relation;
    @NonNull
    @Getter
    private final Person to;
    @Getter
    private int relationLevel;

    public ConnectionEdge(Person from, ISpecificRelation relation, Person to) {
        this(from, relation.getGenericRelation(), to);
    }

    public ConnectionEdge(Person from, IGenericRelation relation, Person to) {
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
    public int hashCode() {
        return Objects.hash(this.from, this.to);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ConnectionEdge) {
            ConnectionEdge edge = (ConnectionEdge) obj;
            return Objects.equals(this.from, edge.from)
                    && Objects.equals(this.to, edge.to)
                    && Objects.equals(this.relation, edge.relation)
                    && this.relationLevel == edge.relationLevel;
        }
        return false;
    }

    @Override
    public String toString() {
        int absoluteRelationLevel = Math.abs(this.relationLevel);
        StringBuilder grandRelationPrefix = generateGrandRelationPrefix(absoluteRelationLevel);
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
