package utils;

import graph.ConnectionEdge;
import graph.Person;
import relationship.IGenericRelation;

import java.util.Collection;
import java.util.Iterator;

/**
 * Utility class to store methods that filter
 */
public class FilterUtils {
    public static Collection<ConnectionEdge> filterConnectionsByGenerationLevel(Person person, int generationLevel,
                                                                                Collection<ConnectionEdge> allConnections) {
        for (Iterator<ConnectionEdge> iterator = allConnections.iterator(); iterator.hasNext(); ) {
            ConnectionEdge connection = iterator.next();
            if (connection.relationLevel() != generationLevel) {
                iterator.remove();
            }
        }
        return allConnections;
    }

    public static Collection<Person> filterPersonsByGender(Boolean isMale, Collection<Person> allPersons) {
        for (Iterator<Person> iterator = allPersons.iterator(); iterator.hasNext(); ) {
            Person person = iterator.next();
            // Need to check relations in reverse, so taking inverse of generationLevel
            if (person.isGenderMale() != isMale) {
                iterator.remove();
            }
        }
        return allPersons;
    }

    public static Collection<ConnectionEdge> filterConnectionsBySpecificRelation(IGenericRelation genericRelation,
                                                                                 Boolean isRelationGenderMale, int
                                                                                         relationLevel, Collection<ConnectionEdge> allConnections) {
        for (Iterator<ConnectionEdge> iterator = allConnections.iterator(); iterator.hasNext(); ) {
            ConnectionEdge connection = iterator.next();
            // Need to check relations in reverse, so taking inverse of generationLevel
            if (connection.relationLevel() != relationLevel
                    || (isRelationGenderMale != null && connection.to().isGenderMale() != isRelationGenderMale)
                    || !connection.relation().equals(genericRelation)) {
                iterator.remove();
            }
        }
        return allConnections;
    }

}
