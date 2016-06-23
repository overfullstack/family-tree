/*
 * Copyright (c) 2016, Gopal S Akshintala
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * http://creativecommons.org/licenses/by-sa/4.0/
 */

package coreGraph;

import Software.SoftwareTest;
import org.junit.Assert;
import org.junit.Test;
import relationship.SpecificRelation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static utils.RelationUtils.parseToRelation;

/**
 * Class to test Family graph
 */
public class FamilyGraphTest extends SoftwareTest {

    @Test
    public void addPerson() {
        Person person = new Person("13", "Devil", 100, true);
        family.addPerson(person);
        Assert.assertEquals(person, family.getPersonById("13"));
    }

    @Test
    public void addPerson1() {
        Person person = new Person("13", "Devil", 100, true);
        family.addPerson("13", "Devil", "100", "true");
        Assert.assertEquals(person, family.getPersonById("13"));
    }

    @Test
    public void connectPersons() {
        family.connectPersons("2", "SIBLING", "10");
    }

    @Test(expected = IllegalArgumentException.class)
    public void connectPersonsWithWrongRelation() {
        family.connectPersons("2", "FATHER", "10");
    }

    @Test(expected = IllegalArgumentException.class)
    public void connectPersonsWithPersonNotInFamily() {
        family.connectPersons("2", "FATHER", "20");
    }

    @Test
    public void removeDirectConnection() {
        Person p1 = new Person("1", "Swathi", 23, false);
        Person p2 = new Person("2", "Sai", 25, true);
        family.removeDirectConnection(p1, p2);
        Assert.assertFalse(family.arePersonsDirectlyConnected(p1, p2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeDirectConnectionNotConnected() {
        Person p1 = new Person("1", "Swathi", 23, false);
        Person p2 = new Person("10", "Srinu", 35, true);
        family.removeDirectConnection(p1, p2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getPersonByIdNotPresent() {
        family.getPersonById("123");
    }

    @Test
    public void getConnection() {
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

        family.getAllPersonsInFamily().stream().filter(person -> !person.equals(origin)).forEach(person -> Assert
                .assertEquals(expectedConnections.get(Integer.valueOf(person.getId()) - 2), family.getConnection
                        (origin, person, false)));
    }

    @Test
    public void getShortestRelationChain() {
        Person p1 = new Person("1", "Swathi", 23, false);
        Person p2 = new Person("10", "Srinu", 35, true);
        List<ConnectionEdge> expectedConnections = new ArrayList<>();
        expectedConnections.add(new ConnectionEdge("1", "DAUGHTER", "3", -1, family));
        expectedConnections.add(new ConnectionEdge("3", "BROTHER", "9", 0, family));
        expectedConnections.add(new ConnectionEdge("9", "MOTHER", "10", 1, family));
        Assert.assertEquals(expectedConnections, family.getShortestRelationChain(p1, p2));
    }

    @Test
    public void getAggregateRelation() {
        Person p1 = new Person("6", "Pranavi", 1, false);
        Person p2 = new Person("7", "Lakshmi", 108, false);
        Assert.assertEquals(new ConnectionEdge("6", "GRANDDAUGHTER", "7", -4, family), family.getAggregateConnection(p1,
                p2));
    }

    @Test
    public void getAllMembersFromGenerationLevel() {
        Person person = new Person("1", "Swathi", 23, false);
        Set<ConnectionEdge> expectedConnections = new HashSet<>();
        expectedConnections.add(new ConnectionEdge("1", "DAUGHTER", "3", -1, family));
        expectedConnections.add(new ConnectionEdge("1", "DAUGHTER", "4", -1, family));
        expectedConnections.add(new ConnectionEdge("1", "NIECE", "9", -1, family));

        Assert.assertEquals(expectedConnections, family.getAllMembersFromGenerationLevel(person, 1));
    }

    @Test
    public void getFamilyInOrderOfAge() {
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
        Assert.assertEquals(expectedOrder, family.getFamilyInOrderOfAge(true));
        Collections.reverse(expectedOrder);
        Assert.assertEquals(expectedOrder, family.getFamilyInOrderOfAge(false));
    }

    @Test
    public void getAllFamilyMembersOfGender() {
        Map<Integer, Person> expectedMaleMembers = new HashMap<>();
        expectedMaleMembers.put(1, family.getPersonById("2"));
        expectedMaleMembers.put(2, family.getPersonById("3"));
        expectedMaleMembers.put(3, family.getPersonById("8"));
        expectedMaleMembers.put(4, family.getPersonById("10"));
        Assert.assertTrue(expectedMaleMembers.values().containsAll(family.getAllFamilyMembersOfGender(true)));

        Map<Integer, Person> expectedFemaleMembers = new HashMap<>();
        expectedFemaleMembers.put(1, family.getPersonById("1"));
        expectedFemaleMembers.put(2, family.getPersonById("4"));
        expectedFemaleMembers.put(3, family.getPersonById("5"));
        expectedFemaleMembers.put(4, family.getPersonById("6"));
        expectedFemaleMembers.put(5, family.getPersonById("7"));
        expectedFemaleMembers.put(6, family.getPersonById("9"));
        Assert.assertTrue(expectedFemaleMembers.values().containsAll(family.getAllFamilyMembersOfGender(false)));
    }

    /**
     * Keeping this test at last as it involves batch connecting people which may manipulate results for other tests
     */
    @Test
    public void getFamilyGraphForPerson() {
        Person origin = family.getPersonById("1");
        for (ConnectionEdge connection : family.getFamilyGraphForPerson(origin, false)) {
            // Since getConnection is already tested, it can be relied on to test getFamilyGraphForPerson
            Assert.assertEquals(family.getConnection(connection.from(), connection.to(), false), connection);
        }
    }

    @Test
    public void getAllPersonByRelation() {
        List<Person> persons = new ArrayList<>();
        persons.add(family.getPersonById("4"));
        persons.add(family.getPersonById("9"));

        Assert.assertEquals(persons, family.getAllPersonByRelation(family.getPersonById("6"), SpecificRelation
                .GRANDMOTHER, 2));
    }

    @Test
    public void isPersonRelatedWithRelation() {
        Assert.assertTrue(family.isPersonRelatedWithRelation(family.getPersonById("4"), parseToRelation("mother"), 1));
        Assert.assertFalse(family.isPersonRelatedWithRelation(family.getPersonById("1"), parseToRelation("aunt"), 1));
        Assert.assertTrue(family.isPersonRelatedWithRelation(family.getPersonById("5"), parseToRelation("grandmother"),
                2));
    }

    @Test
    public void batchConnectPersons() {
        Set<ConnectionEdge> connections = new HashSet<>();
        Person p1 = family.getPersonById("1");
        Person p2 = family.getPersonById("6");
        connections.add(new ConnectionEdge("1", "AUNT", "6", -1, family));
        family.batchConnectPersons(connections);
        Assert.assertEquals(SpecificRelation.MOTHER, family.getAggregateConnection(p1, p2).relation()
                .getGenderSpecificRelation(p1.isGenderMale()));
    }
}