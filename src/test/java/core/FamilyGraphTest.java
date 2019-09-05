package core;

import Software.SoftwareTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import relationship.SpecificRelation;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static relationship.SpecificRelation.*;

/**
 * Class to test Family graph
 */
@DisplayName("Family Graph Test")
class FamilyGraphTest extends SoftwareTest {

    @Test
    void addPerson() {
        Person person = new Person("13", "Devil", 100, true);
        family.addPerson(person);
        assertEquals(person, family.getPersonById("13"));
    }

    @Test
    void addPerson1() {
        Person person = new Person("13", "Devil", 100, true);
        family.addPerson("13", "Devil", "100", "true");
        assertEquals(person, family.getPersonById("13"));
    }

    @Test
    void connectPersons() {
        family.connectPersons("2", "SIBLING", "10");
    }

    @Test
    void connectPersonsWithWrongRelation() {
        assertThrows(IllegalArgumentException.class, () -> family.connectPersons("2", "FATHER", "10"));
    }

    @Test
    void connectPersonsWithPersonNotInFamily() {
        assertThrows(IllegalArgumentException.class, () -> family.connectPersons("2", "FATHER", "20"));
        
    }

    @Test
    void removeDirectConnection() {
        Person p1 = new Person("1", "Swathi", 23, false);
        Person p2 = new Person("2", "Sai", 25, true);
        family.removeDirectConnection(p1, p2);
        assertFalse(family.arePersonsDirectlyConnected(p1, p2));
    }

    @Test
    void removeDirectConnectionNotConnected() {
        assertThrows(IllegalArgumentException.class, () -> {
            Person p1 = new Person("1", "Swathi", 23, false);
            Person p2 = new Person("10", "Srinu", 35, true);
            family.removeDirectConnection(p1, p2);
        });
    }

    @Test
    void getPersonByIdNotPresent() {
        assertThrows(IllegalArgumentException.class, () -> family.getPersonById("123"));
    }

    @Test
    void getConnection() {
        Person origin = family.getPersonById("1");
        List<ConnectionEdge> expectedConnections = new ArrayList<>();

        expectedConnections.add(new ConnectionEdge("1", "SISTER", "2", 0, family));
        expectedConnections.add(new ConnectionEdge("1", "DAUGHTER", "3", -1, family));
        expectedConnections.add(new ConnectionEdge("1", "DAUGHTER", "4", -1, family));
        expectedConnections.add(new ConnectionEdge("1", "GRANDDAUGHTER", "5", -2, family));
        expectedConnections.add(new ConnectionEdge("1", "MOTHER", "6", 1, family));
        expectedConnections.add(new ConnectionEdge("1", "GRANDDAUGHTER", "7", -3, family));
        expectedConnections.add(new ConnectionEdge("1", "WIFE", "8", 0, family));
        expectedConnections.add(new ConnectionEdge("1", "NIECE", "9", -1, family));
        expectedConnections.add(new ConnectionEdge("1", "COUSIN", "10", 0, family));

        family.getAllPersonsInFamily().stream().filter(person -> !person.equals(origin)).forEach(person -> 
                assertEquals(expectedConnections.get(Integer.valueOf(person.getId()) - 2), family.getConnection
                        (origin, person, false)));
    }

    @Test
    void getShortestRelationChain() {
        Person p1 = new Person("1", "Swathi", 23, false);
        Person p2 = new Person("10", "Srinu", 35, true);
        List<ConnectionEdge> expectedConnections = new ArrayList<>();
        expectedConnections.add(new ConnectionEdge("1", "DAUGHTER", "3", -1, family));
        expectedConnections.add(new ConnectionEdge("3", "BROTHER", "9", 0, family));
        expectedConnections.add(new ConnectionEdge("9", "MOTHER", "10", 1, family));
        assertEquals(expectedConnections, family.getShortestRelationChain(p1, p2));
    }

    @Test
    void getAggregateRelation() {
        Person p1 = new Person("6", "Pranavi", 1, false);
        Person p2 = new Person("7", "Lakshmi", 108, false);
        assertEquals(new ConnectionEdge("6", "GRANDDAUGHTER", "7", -4, family), family.getAggregateConnection(p1,
                p2));
    }

    @Test
    void getAllMembersFromGenerationLevel() {
        Person person = new Person("1", "Swathi", 23, false);
        Set<ConnectionEdge> expectedConnections = new HashSet<>();
        expectedConnections.add(new ConnectionEdge("1", "DAUGHTER", "3", -1, family));
        expectedConnections.add(new ConnectionEdge("1", "DAUGHTER", "4", -1, family));
        expectedConnections.add(new ConnectionEdge("1", "NIECE", "9", -1, family));

        assertEquals(expectedConnections, family.getAllMembersFromGenerationLevel(person, 1));
    }

    @Test
    void getFamilyInOrderOfAge() {
        List<Person> expectedOrder = new ArrayList<>();
        expectedOrder.add(family.getPersonById("6"));
        expectedOrder.add(family.getPersonById("1"));
        expectedOrder.add(family.getPersonById("2"));
        expectedOrder.add(family.getPersonById("8"));
        expectedOrder.add(family.getPersonById("10"));
        expectedOrder.add(family.getPersonById("4"));
        expectedOrder.add(family.getPersonById("3"));
        expectedOrder.add(family.getPersonById("9"));
        expectedOrder.add(family.getPersonById("5"));
        expectedOrder.add(family.getPersonById("7"));
        assertEquals(expectedOrder, family.getFamilyInOrderOfAge(true));
        Collections.reverse(expectedOrder);
        assertEquals(expectedOrder, family.getFamilyInOrderOfAge(false));
    }

    @Test
    void getAllFamilyMembersOfGender() {
        Map<Integer, Person> expectedMaleMembers = new HashMap<>();
        expectedMaleMembers.put(1, family.getPersonById("2"));
        expectedMaleMembers.put(2, family.getPersonById("3"));
        expectedMaleMembers.put(3, family.getPersonById("8"));
        expectedMaleMembers.put(4, family.getPersonById("10"));
        assertTrue(expectedMaleMembers.values().containsAll(family.getAllFamilyMembersOfGender(true)));

        Map<Integer, Person> expectedFemaleMembers = new HashMap<>();
        expectedFemaleMembers.put(1, family.getPersonById("1"));
        expectedFemaleMembers.put(2, family.getPersonById("4"));
        expectedFemaleMembers.put(3, family.getPersonById("5"));
        expectedFemaleMembers.put(4, family.getPersonById("6"));
        expectedFemaleMembers.put(5, family.getPersonById("7"));
        expectedFemaleMembers.put(6, family.getPersonById("9"));
        assertTrue(expectedFemaleMembers.values().containsAll(family.getAllFamilyMembersOfGender(false)));
    }

    /**
     * Keeping this test at last as it involves batch connecting people which may manipulate results for other tests
     */
    @Test
    void getAllConnectionsInFamilyForPerson() {
        Person origin = family.getPersonById("1");
        for (ConnectionEdge connection : family.getAllConnectionsInFamilyForPerson(origin, false)) {
            // Since getConnection is already tested, it can be relied on to test getAllConnectionsInFamilyForPerson
            assertEquals(family.getConnection(connection.from(), connection.to(), false), connection);
        }
    }

    @Test
    void getAllPersonByRelation() {
        List<Person> persons = new ArrayList<>();
        persons.add(family.getPersonById("4"));
        persons.add(family.getPersonById("9"));

        assertTrue(persons.containsAll(family.getAllPersonsByRelation(family.getPersonById("6"), SpecificRelation
                .GRANDMOTHER, 2)));
    }

    @Test
    void isPersonRelatedWithRelation() {
        assertTrue(family.isPersonRelatedWithRelation(family.getPersonById("4"), MOTHER, 1));
        assertFalse(family.isPersonRelatedWithRelation(family.getPersonById("1"), AUNT, 1));
        assertTrue(family.isPersonRelatedWithRelation(family.getPersonById("5"), GRANDMOTHER, 2));
    }

    @Test
    void batchConnectPersons() {
        Set<ConnectionEdge> connections = new HashSet<>();
        Person p1 = family.getPersonById("1");
        Person p2 = family.getPersonById("6");
        connections.add(new ConnectionEdge("1", "AUNT", "6", -1, family));
        family.batchConnectPersons(connections);
        assertEquals(MOTHER, family.getAggregateConnection(p1, p2).relation()
                .getGenderSpecificRelation(p1.isGenderMale()));
    }
}