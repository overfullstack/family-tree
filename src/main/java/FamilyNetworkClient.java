import graph.FamilyGraph;
import loader.FileLoaderService;
import loader.LoaderService;
import printer.ConsolePrintService;
import printer.PrintService;
import validation.AgeValidator;
import validation.GenderValidator;
import validation.IValidator;
import validation.RelationshipValidator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Client to make use of features of this software
 */
public class FamilyNetworkClient {

    private static final FamilyGraph family;
    private static final PrintService printer;
    private static final IValidator validator;
    private static LoaderService loader;

    static {
        validator = setUpValidator();
        family = new FamilyGraph(validator);
        printer = new ConsolePrintService(System.out);
        try {
            loader = new FileLoaderService(new BufferedReader(new FileReader(new File("src//main//resources//MyFamily.txt"))));
            loader.loadFamily(family);
        } catch (IOException e) {
            System.out.println("Exception while loading family from file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
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
        main(null);
    }

    private static void displayPersonsRelatedWithRelation() {
        Scanner scn = new Scanner(System.in);
        System.out.println("Enter Relation RelationLevel: ");
        String relation = scn.next();
        int relationLevel = scn.nextInt();
        printer.printPersonsRelatedWithRelation(relation, relationLevel, family);
        pause();
    }

    private static void displayPersonsByRelation() {
        printer.printAllPersonsInFamily(family);
        Scanner scn = new Scanner(System.in);
        System.out.println("Enter Id Relation RelationLevel: ");
        String pId = scn.next();
        String relation = scn.next();
        int relationLevel = scn.nextInt();
        printer.printPersonsByRelation(pId, relation, relationLevel, family);
        pause();
    }

    private static void displayAggregateRelation() {
        printer.printAllPersonsInFamily(family);
        Scanner scn = new Scanner(System.in);
        System.out.println("Enter Ids of Persons to print Aggregate relation between them: ");
        String p1Id = scn.next();
        String p2Id = scn.next();
        printer.printAggregateRelation(p1Id, p2Id, family);
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
        Scanner scn = new Scanner(System.in);
        System.out.println("Enter Id of Person and Generation Level to Print all members above/below that Generation:");
        String pId = scn.next();
        int generationLevel = scn.nextInt();
        printer.printAllMembersFromGenerationLevel(pId, generationLevel, family);
        pause();
    }

    private static void displayShortestRelationChain() {
        printer.printAllPersonsInFamily(family);
        Scanner scn = new Scanner(System.in);
        System.out.println("Enter Ids of Persons to check to Print Relation Chain: ");
        String p1Id = scn.next();
        String p2Id = scn.next();
        printer.printShortestRelationChain(p1Id, p2Id, family);
        pause();
    }

    private static void displayFamilyTree() {
        printer.printAllPersonsInFamily(family);
        Scanner scn = new Scanner(System.in);
        System.out.println("Choose an Id to Print Family tree: ");
        String pId = scn.next();
        printer.printFamilyTree(pId, family);
        pause();
    }

    private static IValidator setUpValidator() {
        IValidator genderValidator = new GenderValidator();
        IValidator ageValidator = new AgeValidator();
        IValidator relationShipValidator = new RelationshipValidator();

        genderValidator.setNextValidatorInChain(ageValidator);
        ageValidator.setNextValidatorInChain(relationShipValidator);

        return genderValidator;
    }

    private static void pause() {
        System.out.println("Move On? :(y/n)");
        Scanner scn = new Scanner(System.in);
        String inp = scn.next();
        switch (inp) {
            case "n":
            case "N":
                pause();
        }
    }
}
