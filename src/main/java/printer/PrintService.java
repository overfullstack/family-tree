/*
 * Copyright (c) 2016, Gopal S Akshintala
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * http://creativecommons.org/licenses/by-sa/4.0/
 */

package printer;

import coreGraph.FamilyGraph;

/**
 * Interface for PrintService
 */
public interface PrintService {
    void printFamilyTree(String pId, FamilyGraph family);

    void printShortestRelationChain(String p1Id, String p2Id, FamilyGraph family);

    void printAggregateRelation(String p1Id, String p2Id, FamilyGraph family);

    void printAllMembersFromGenerationLevel(String pId, int generationLevel, FamilyGraph family);

    void printAllPersonsInFamily(FamilyGraph family);

    void printFamilyInAscendingOrderOfAge(FamilyGraph family);

    void printFamilyInDescendingOrderOfAge(FamilyGraph family);

    void printAllMaleFamilyMembers(FamilyGraph family);

    void printAllFeMaleFamilyMembers(FamilyGraph family);

    void printPersonsByRelation(String pId, String relation, int relationLevel, FamilyGraph family);

    void printPersonsRelatedWithRelation(String relation, int relationLevel, FamilyGraph family);

}
