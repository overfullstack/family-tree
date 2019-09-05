package utils;

import core.ConnectionEdge;
import core.Person;
import relationship.GenericRelation;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Utility class to store methods that filter
 */
public interface FilterUtils {
    static Collection<ConnectionEdge> filterConnectionsByGenerationLevel(Person person, int generationLevel,
                                                                         Collection<ConnectionEdge> allConnections) {
        return filter(allConnections, connection -> connection.relationLevel() == generationLevel);
    }

    static Collection<Person> filterPersonsByGender(Boolean gender, Collection<Person> allPersons) {
        return filter(allPersons, person -> person.isGenderMale() == gender);
    }

    static Collection<ConnectionEdge> filterConnectionsBySpecificRelation(GenericRelation genericRelation,
                                                                          Boolean isRelationGenderMale, int relationLevel,
                                                                          Collection<ConnectionEdge> allConnections) {
        return filter(allConnections, connection -> connection.relationLevel() == relationLevel
                && (isRelationGenderMale != null && connection.to().isGenderMale() == isRelationGenderMale)
                && connection.relation().equals(genericRelation));
    }

    static <T> Collection<T> filter(Collection<T> allConnections, Predicate<T> filterFunction) {
        return allConnections.stream().filter(filterFunction).collect(Collectors.toSet());
    }
}
