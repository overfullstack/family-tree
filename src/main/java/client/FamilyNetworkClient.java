package client;

import com.google.inject.Guice;
import com.google.inject.Inject;
import core.FamilyGraph;
import loader.LoaderService;
import modules.FamilyModule;
import printer.PrintService;

import java.io.IOException;
import java.util.Scanner;

/**
 * Client to make use of features of this software
 */
public class FamilyNetworkClient {
    @Inject
    private static FamilyGraph family;
    @Inject
    private static PrintService printer;
    @Inject
    private static LoaderService loader;

    public static void main(String[] args) throws IOException {
        Guice.createInjector(new FamilyModule());
        loader.loadFamily(family);
        
        try(Scanner scn = new Scanner(System.in)) {
            System.out.println("Select option to Display: ");
            System.out.println("1. Family Tree\n2. Shortest Relation Chain\n3. Members from Generation Level\n" +
                    "4. Family Members in Ascending order of Age\n5. Family Members in Descending order of Age\n" +
                    "6. Male Family Members\n7. Female Family Members\n8. Aggregate Relation\n9. Persons by Relation\n" +
                    "10.Persons Related to someone with Relation\n11.Exit");
            switch (scn.nextInt()) {
                case 1:
                    displayFamilyTree();
                    break;
                case 2:
                    displayShortestRelationChain();
                    break;
                case 3:
                    displayAllMembersFromGenerationLevel();
                    break;
                case 4:
                    displayFamilyInAscendingOrderOfAge();
                    break;
                case 5:
                    displayFamilyInDescendingOrderOfAge();
                    break;
                case 6:
                    displayAllMaleFamilyMembers();
                    break;
                case 7:
                    displayAllFeMaleFamilyMembers();
                    break;
                case 8:
                    displayAggregateRelation();
                    break;
                case 9:
                    displayPersonsByRelation();
                    break;
                case 10:
                    displayPersonsRelatedWithRelation();
                    break;
                default:
                    return;
            }
        }
        main(null);
    }

    private static void displayPersonsRelatedWithRelation() {
        System.out.println("Enter Relation RelationLevel: ");
        try(Scanner scn = new Scanner(System.in)) {
            String relation = scn.next();
            int relationLevel = scn.nextInt();
            printer.printPersonsRelatedWithRelation(relation, relationLevel, family);
        }
        pause();
    }

    private static void displayPersonsByRelation() {
        printer.printAllPersonsInFamily(family);
        System.out.println("Enter Id Relation RelationLevel: ");
        try (Scanner scn = new Scanner(System.in)) {
            String pId = scn.next();
            String relation = scn.next();
            int relationLevel = scn.nextInt();
            printer.printPersonsByRelation(pId, relation, relationLevel, family);
        }
        pause();
    }

    private static void displayAggregateRelation() {
        printer.printAllPersonsInFamily(family);
        System.out.println("Enter Ids of Persons to print Aggregate relation between them: ");
        try (Scanner scn = new Scanner(System.in)) {
            String p1Id = scn.next();
            String p2Id = scn.next();
            printer.printAggregateRelation(p1Id, p2Id, family);
        }
        pause();
    }

    private static void displayAllFeMaleFamilyMembers() {
        System.out.println("All Female members in Family:");
        printer.printAllFeMaleFamilyMembers(family);
        pause();
    }

    private static void displayAllMaleFamilyMembers() {
        System.out.println("All Male members in Family:");
        printer.printAllMaleFamilyMembers(family);
        pause();
    }

    private static void displayFamilyInDescendingOrderOfAge() {
        System.out.println("Family in Descending Order of Age:");
        printer.printFamilyInDescendingOrderOfAge(family);
        pause();
    }

    private static void displayFamilyInAscendingOrderOfAge() {
        System.out.println("Family in Ascending Order of Age:");
        printer.printFamilyInAscendingOrderOfAge(family);
        pause();
    }

    private static void displayAllMembersFromGenerationLevel() {
        printer.printAllPersonsInFamily(family);
        System.out.println("Enter Id of Person and Generation Level to Print all members above/below that Generation:");
        try (Scanner scn = new Scanner(System.in)) {
            String pId = scn.next();
            int generationLevel = scn.nextInt();
            printer.printAllMembersFromGenerationLevel(pId, generationLevel, family);
        }
        pause();
    }

    private static void displayShortestRelationChain() {
        printer.printAllPersonsInFamily(family);
        System.out.println("Enter Ids of Persons to check to Print Relation Chain: ");
        try (Scanner scn = new Scanner(System.in)) {
            String p1Id = scn.next();
            String p2Id = scn.next();
            printer.printShortestRelationChain(p1Id, p2Id, family);
        }
        pause();
    }

    private static void displayFamilyTree() {
        printer.printAllPersonsInFamily(family);
        System.out.println("Choose an Id to Print Family tree: ");
        try (Scanner scn = new Scanner(System.in)) {
            String pId = scn.next();
            printer.printFamilyTree(pId, family);
        }
        pause();
    }

    private static void pause() {
        System.out.println("Move On? :(y/n)");
        try (Scanner scn = new Scanner(System.in)) {
            String inp = scn.next();
            switch (inp) {
                case "n":
                case "N":
                    pause();
            }
        }
    }
}
