/*
 * Copyright (c) 2016, Gopal S Akshintala
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * http://creativecommons.org/licenses/by-sa/4.0/
 */

package graph;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;
import relationship.IGenericRelation;
import relationship.ISpecificRelation;

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

    public ConnectionEdge(Person from, IGenericRelation relation, Person to) {
        this(from, relation, to, relation.getRelationLevel());
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
        this(family.getPersonById(fromPid), parseToGenericRelation(relation), family.getPersonById(toPid),
                relationLevel);
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
