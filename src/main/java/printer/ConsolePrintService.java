/*
 * Copyright (c) 2016, Gopal S Akshintala
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * http://creativecommons.org/licenses/by-sa/4.0/
 */

package printer;

import com.google.inject.Inject;
import graph.ConnectionEdge;
import graph.FamilyGraph;
import graph.Person;
import lombok.Cleanup;
import relationship.IRelation;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Collection;

import static utils.RelationUtils.parseToRelation;

/**
 * Class which holds all Print related methods of the graph.
 */
public class ConsolePrintService implements PrintService {
    @Inject
    private OutputStream out;

    @Override
    public void printFamilyTree(String pId, FamilyGraph family) {
        printConnections(family.getFamilyGraphForPerson(family.getPersonById(pId), false));
    }

    @Override
    public void printShortestRelationChain(String p1Id, String p2Id, FamilyGraph family) {
        Person p1 = family.getPersonById(p1Id);
        Person p2 = family.getPersonById(p2Id);
        printConnections(family.getShortestRelationChain(p1, p2));
    }

    @Override
    public void printAggregateRelation(String p1Id, String p2Id, FamilyGraph family) {
        @Cleanup PrintStream printOut = new PrintStream(out);
        Person p1 = family.getPersonById(p1Id);
        Person p2 = family.getPersonById(p2Id);
        printOut.println(family.getAggregateConnection(p1, p2));
    }

    @Override
    public void printAllMembersFromGenerationLevel(String pId, int generationLevel, FamilyGraph family) {
        printConnections(family.getAllMembersFromGenerationLevel(family.getPersonById(pId), generationLevel));
    }

    private void printConnections(Collection<ConnectionEdge> connections) {
        @Cleanup PrintStream printOut = new PrintStream(out);
        connections.forEach(printOut::println);
    }

    @Override
    public void printAllPersonsInFamily(FamilyGraph family) {
        printPersons(family.getAllPersonsInFamily());
    }

    @Override
    public void printFamilyInAscendingOrderOfAge(FamilyGraph family) {
        printPersons(family.getFamilyInOrderOfAge(true));
    }

    @Override
    public void printFamilyInDescendingOrderOfAge(FamilyGraph family) {
        printPersons(family.getFamilyInOrderOfAge(false));
    }

    @Override
    public void printAllMaleFamilyMembers(FamilyGraph family) {
        printPersons(family.getAllFamilyMembersOfGender(true));
    }

    @Override
    public void printAllFeMaleFamilyMembers(FamilyGraph family) {
        printPersons(family.getAllFamilyMembersOfGender(false));
    }

    @Override
    public void printPersonsByRelation(String pId, String relation, int relationLevel, FamilyGraph family) {
        IRelation iRelation = parseToRelation(relation);
        printPersons(family.getAllPersonByRelation(family.getPersonById(pId), iRelation, relationLevel));
    }

    @Override
    public void printPersonsRelatedWithRelation(String relation, int relationLevel, FamilyGraph family) {
        @Cleanup PrintStream printOut = new PrintStream(out);
        family.getAllPersonsInFamily().stream().filter(person -> family.isPersonRelatedWithRelation(person,
                parseToRelation(relation), relationLevel)).forEach(printOut::println);
    }

    private void printPersons(Collection<Person> persons) {
        @Cleanup PrintStream printOut = new PrintStream(out);
        persons.forEach(printOut::println);
    }
}
