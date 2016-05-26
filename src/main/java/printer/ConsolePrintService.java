package printer;

import graph.ConnectionEdge;
import graph.FamilyGraph;
import graph.Person;
import relationship.IRelation;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Collection;

import static utils.RelationUtils.parseToRelation;

/**
 * Class which holds all Print related methods of the graph.
 */
public class ConsolePrintService implements PrintService {
    private final PrintStream out;

    public ConsolePrintService(OutputStream out) {
        this.out = new PrintStream(out);
    }

    @Override
    public void printFamilyTree(String pId, FamilyGraph family) {
        printConnections(family.getFamilyGraphForPerson(family.getPersonById(pId)));
    }

    @Override
    public void printShortestRelationChain(String p1Id, String p2Id, FamilyGraph family) {
        Person p1 = family.getPersonById(p1Id);
        Person p2 = family.getPersonById(p2Id);
        printConnections(family.getShortestRelationChain(p1, p2));
    }

    @Override
    public void printAggregateRelation(String p1Id, String p2Id, FamilyGraph family) {
        Person p1 = family.getPersonById(p1Id);
        Person p2 = family.getPersonById(p2Id);
        out.println(family.getAggregateConnection(p1, p2));
    }

    @Override
    public void printAllMembersFromGenerationLevel(String pId, int generationLevel, FamilyGraph family) {
        printConnections(family.getAllMembersFromGenerationLevel(family.getPersonById(pId), generationLevel));
    }

    private void printConnections(Collection<ConnectionEdge> connections) {
        StringBuilder str = new StringBuilder();
        for (ConnectionEdge edge : connections) {
            str.append(edge).append("\n");
        }
        out.println(str);
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
        for (Person person : family.getAllPersonsInFamily()) {
            if (family.isPersonRelatedWithRelation(person, parseToRelation(relation), relationLevel)) {
                out.println(person);
            }
        }
    }

    private void printPersons(Collection<Person> persons) {
        StringBuilder str = new StringBuilder();
        for (Person p : persons) {
            str.append(p).append("\n");
        }
        out.print(str);
    }
}
