package coreGraph;

import com.google.inject.Inject;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import relationship.GenericRelation;
import relationship.IGenericRelation;
import relationship.IRelation;
import relationship.ISpecificRelation;
import relationship.SpecificRelation;
import validation.IValidator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import static utils.FilterUtils.filterConnectionsByGenerationLevel;
import static utils.FilterUtils.filterConnectionsBySpecificRelation;
import static utils.FilterUtils.filterPersonsByGender;
import static utils.RelationUtils.parseToGenericRelation;

/**
 * This is the central Data Structure that holds all the Persons in the family and their corresponding connections.
 */
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class FamilyGraph {
    private final Map<String, Person> mPersonIdMap = new HashMap<>(); // Represents all the persons put into the graph.
    private final Map<Person, Set<ConnectionEdge>> mRelationMap = new HashMap<>();
    @NonNull
    private final IValidator mValidator;

    /**
     * Returns all the neighbour direct relations of a persons
     *
     * @param person Person to find neighbours
     * @return neighbours of the person
     */
    public Set<ConnectionEdge> getAllNeighbourConnections(Person person) {
        return mRelationMap.get(person);
    }

    /**
     * Adds a Person to family graph, ignores if the person is already present
     *
     * @param person Person to add
     */
    public void addPerson(Person person) {
        if (!mRelationMap.containsKey(person)) {
            mPersonIdMap.put(person.getId(), person);
            mRelationMap.put(person, new HashSet<>());
        }
    }

    /**
     * Adds a Person through Person attributes
     *
     * @param id     Id of the person
     * @param name   Name of the person
     * @param age    Age of the person
     * @param isMale Gender of the person
     */
    public void addPerson(String id, String name, String age, String isMale) {
        this.addPerson(new Person(id, name, age, isMale));
    }

    /**
     * Connects two persons in family, this method wrote to ease unit testing
     *
     * @param p1Id     From Person Id
     * @param relation Relation between Persons as String
     * @param p2Id     To Person Id
     */
    public void connectPersons(String p1Id, String relation, String p2Id) {
        Person p1 = mPersonIdMap.get(p1Id);
        Person p2 = mPersonIdMap.get(p2Id);
        // If we are given only ID, we can't create a person object without other attributes, so we check his existence 
        // instead of adding him to family if he is new.
        if (p1 == null) {
            throw new IllegalArgumentException("Person with Id: " + p1Id + " not found in family to connect");
        }
        if (p2 == null) {
            throw new IllegalArgumentException("Person with Id: " + p2Id + " not found in family to connect");
        }
        // relation string parameter can either be generic or specific
        IGenericRelation IGenericRelation = parseToGenericRelation(relation);
        connectPersons(p1, IGenericRelation, p2, IGenericRelation.getRelationLevel(), true);
    }


    /**
     * Connects two persons in the family with Generic relation
     *
     * @param p1               From Person
     * @param IGenericRelation Generic Relation between Persons
     * @param p2               To Person
     * @param doValidate       Switch to turn validation on or off
     */
    public void connectPersons(Person p1, IGenericRelation IGenericRelation, Person p2, int relationLevel, boolean
            doValidate) {
        addPerson(p1);
        addPerson(p2);
        if (doValidate && !mValidator.validate(p1, IGenericRelation, p2, relationLevel, this)) {
            throw new IllegalArgumentException(new ConnectionEdge(p1, IGenericRelation, p2) + " is NOT a valid Relation");
        }
        mRelationMap.get(p1).add(new ConnectionEdge(p1, IGenericRelation, p2, relationLevel));
        mRelationMap.get(p2).add(new ConnectionEdge(p2, IGenericRelation.getReverseRelation(), p1, -relationLevel));
    }

    /**
     * Connects two persons in the family with Specific relation
     *
     * @param p1                From Person Id
     * @param ISpecificRelation Specific Relation between Persons
     * @param p2                To Person Id
     */
    public void connectPersons(Person p1, ISpecificRelation ISpecificRelation, Person p2, int relationLevel, boolean
            doValidate) {
        this.connectPersons(p1, ISpecificRelation.getGenericRelation(), p2, relationLevel, doValidate);
    }

    /**
     * Batch connects multiple pairs of persons
     *
     * @param connections List of connections to connect
     */
    public void batchConnectPersons(Set<ConnectionEdge> connections) {
        connections.forEach(connection -> {
            Person from = connection.from();
            Person to = connection.to();
            if (!arePersonsDirectlyConnected(from, to)) {
                // No need of validation as the connections are already validated while initially connecting.
                connectPersons(from, connection.relation(), to, connection.relationLevel(), false);
            }
        });
    }

    /**
     * Disconnects persons
     *
     * @param p1 From person
     * @param p2 To Person
     */
    public void removeDirectConnection(Person p1, Person p2) {
        if (!arePersonsDirectlyConnected(p1, p2)) {
            throw new IllegalArgumentException(p1 + " is NOT directly connected to " + p2);
        }
        for (Iterator<ConnectionEdge> iterator = getAllNeighbourConnections(p1).iterator(); iterator.hasNext(); ) {
            ConnectionEdge connection = iterator.next();
            if (connection.to().equals(p2)) {
                iterator.remove();
                return;
            }
        }
    }

    /**
     * Checks if two person are directly connected
     *
     * @param p1 Person 1
     * @param p2 Person 2
     * @return True if directly Connected
     */
    public boolean arePersonsDirectlyConnected(Person p1, Person p2) {
        for (ConnectionEdge connection : getAllNeighbourConnections(p1)) {
            if (p2.equals(connection.to()))
                return true;
        }
        return false;
    }

    /**
     * Returns Person with that Id.
     *
     * @param pId Id of person
     * @return Person with Id
     */
    public Person getPersonById(String pId) {
        Person person = mPersonIdMap.get(pId);
        if (person == null) {
            throw new IllegalArgumentException("Person Id: " + pId + " NOT present in family");
        }
        return person;
    }

    /**
     * Returns all Persons in family
     *
     * @return Collection of all persons in family
     */
    public Collection<Person> getAllPersonsInFamily() {
        return mPersonIdMap.values();
    }

    /**
     * Returns the direct/indirect connection between two persons
     *
     * @param p1 From Person
     * @param p2 To Person
     * @param doBatchConnect
     * @return Connection
     */
    public ConnectionEdge getConnection(Person p1, Person p2, boolean doBatchConnect) {
        ConnectionEdge connection = bfsTraverseFamilyGraph(p1, p2, null, doBatchConnect);
        // If p2 is not reached, both are not connected
        return (connection != null && connection.to().equals(p2)) ? connection : null;
    }

    /**
     * Returns all connections the person have with all other persons in family
     *
     * @param person Person for whom the graph is queried
     * @param doBatchConnect
     * @return List of all Connections the person have with all other persons in family
     */
    public Collection<ConnectionEdge> getFamilyGraphForPerson(Person person, boolean doBatchConnect) {
        Set<ConnectionEdge> connections = new HashSet<>();
        bfsTraverseFamilyGraph(person, null, connections, doBatchConnect);
        return connections;
    }

    /**
     * Traverse Family graph in Breadth-First way, to populate connections and returns connection with aggregate
     * relation. This is used by both getFamilyGraphForPerson and getConnection
     *
     * @param p1          From Person
     * @param p2          To Person
     * @param connections Connections to be populated for family graph
     * @param doBatchConnect
     * @return Connection with aggregate relation
     */
    private ConnectionEdge bfsTraverseFamilyGraph(Person p1, Person p2, Set<ConnectionEdge> connections, boolean doBatchConnect) {
        if (p1 == null || mPersonIdMap.get(p1.getId()) == null) {
            throw new IllegalArgumentException("Person " + p1 + " not found in family");
        }
        if (p2 != null && mPersonIdMap.get(p2.getId()) == null) {
            throw new IllegalArgumentException("Person " + p2 + " not found in family");
        }

        Queue<Person> queue = new LinkedList<>();
        Map<Person, Boolean> visited = new HashMap<>();
        Map<Person, ConnectionEdge> relationMap = new HashMap<>();
        ConnectionEdge previousConnection = null;
        Person neighbourRelative;
        IGenericRelation currentRelation, nextRelation;

        if (p2 == null && connections == null) {
            connections = new HashSet<>();
        }

        queue.add(p1);
        visited.put(p1, true);
        loop:
        while (!queue.isEmpty()) {
            Person p = queue.poll();
            for (ConnectionEdge edge : getAllNeighbourConnections(p)) {
                if (visited.get(edge.to()) == null) {
                    neighbourRelative = edge.to();
                    previousConnection = relationMap.get(edge.from());
                    if (previousConnection == null) {
                        previousConnection = edge;
                    } else {
                        currentRelation = edge.relation();
                        nextRelation = currentRelation.getNextGenericRelation(previousConnection.relation());
                        previousConnection = new ConnectionEdge(p1, nextRelation, neighbourRelative,
                                previousConnection.relationLevel() + currentRelation.getRelationLevel());
                    }

                    if (p2 == null) { // For getFamilyGraphForPerson
                        connections.add(previousConnection);
                    } else if (neighbourRelative.equals(p2)) {
                        break loop;
                    }
                    relationMap.put(neighbourRelative, previousConnection);
                    queue.add(neighbourRelative);
                    visited.put(neighbourRelative, true);
                }
            }
        }
        if (p2 == null) {
            // Adding connection results as we find, to improve future searches
            batchConnectPersons(connections);
        }
        return previousConnection;
    }

    /**
     * Returns map containing path from one Person to another
     *
     * @param p1 From Person
     * @param p2 To Person
     * @return Path map.
     */
    private Map<Person, ConnectionEdge> getConnectionPath(Person p1, Person p2) {
        if (p1 == null || mPersonIdMap.get(p1.getId()) == null) {
            throw new IllegalArgumentException("Person " + p1 + " not found in family");
        }
        if (p2 == null || mPersonIdMap.get(p2.getId()) == null) {
            throw new IllegalArgumentException("Person " + p2 + " not found in family");
        }
        Map<Person, ConnectionEdge> connectionPathMap = new HashMap<>();
        Queue<Person> queue = new LinkedList<>();
        Map<Person, Boolean> visited = new HashMap<>();

        queue.add(p1);
        visited.put(p1, true);
        while (!queue.isEmpty()) {
            Person p = queue.poll();
            for (ConnectionEdge edge : getAllNeighbourConnections(p)) {
                if (visited.get(edge.to()) == null) {
                    Person neighbourRelative = edge.to();
                    connectionPathMap.put(neighbourRelative, edge);
                    if (neighbourRelative.equals(p2)) {
                        break;
                    }
                    queue.add(neighbourRelative);
                    visited.put(p, true);
                }
            }
        }
        return connectionPathMap;
    }

    public List<ConnectionEdge> getShortestRelationChain(Person p1, Person p2) {
        List<ConnectionEdge> connections = new ArrayList<>();
        getAggregateRelationWithRelationChain(p1, p2, getConnectionPath(p1, p2), connections);
        return connections;
    }

    public ConnectionEdge getAggregateConnection(Person p1, Person p2) {
        return getAggregateRelationWithRelationChain(p1, p2, getConnectionPath(p1, p2), null);
    }

    /**
     * Returns aggregate relation and also populates connections chain that led to that relation
     *
     * @param p1             From Person
     * @param p2             To Person
     * @param connectionPath Map having path from p1 to p2
     * @param connections    List to be populated with connection chain
     * @return Aggregate relation
     */
    private ConnectionEdge getAggregateRelationWithRelationChain(Person p1, Person p2, Map<Person, ConnectionEdge>
            connectionPath, List<ConnectionEdge> connections) {
        ConnectionEdge nextEdge, aggregateConnection = null;
        IGenericRelation nextRelation, aggregateRelation = null;
        Person nextPerson = p2;

        while (!nextPerson.equals(p1)) {
            nextEdge = connectionPath.get(nextPerson);
            nextPerson = nextEdge.from();
            nextRelation = nextEdge.relation();
            if (aggregateRelation == null) {
                aggregateRelation = nextRelation;
                aggregateConnection = nextEdge;
            } else {
                aggregateRelation = aggregateRelation.getNextGenericRelation(nextRelation);
                aggregateConnection = new ConnectionEdge(nextPerson, aggregateRelation, p2, nextRelation
                        .getRelationLevel() + aggregateConnection.relationLevel());
            }
            if (connections != null) {
                connections.add(nextEdge);
            }
        }
        if (connections != null) {
            Collections.reverse(connections);
        }
        return aggregateConnection;
    }

    public Collection<ConnectionEdge> getAllMembersFromGenerationLevel(Person person, int generationLevel) {
        // Need to check relations in reverse, so taking inverse of generationLevel
        return filterConnectionsByGenerationLevel(person, -generationLevel, getFamilyGraphForPerson(person, false));
    }

    public Collection<Person> getFamilyInOrderOfAge(boolean isOrderAscending) {
        Comparator<Person> ascendingAgeComparator = (p1, p2) -> {
            if (p1.getAge() == p2.getAge()) return 0;
            return (p1.getAge() > p2.getAge()) ? 1 : -1;
        };
        List<Person> sortedPersons = new ArrayList(mPersonIdMap.values());
        if (isOrderAscending) {
            Collections.sort(sortedPersons, ascendingAgeComparator);
        } else {
            Collections.sort(sortedPersons, Collections.reverseOrder(ascendingAgeComparator));
        }
        return sortedPersons;
    }

    public Collection<Person> getAllFamilyMembersOfGender(Boolean isMale) {
        return filterPersonsByGender(isMale, new ArrayList<>(mPersonIdMap.values()));
    }

    public Collection<Person> getAllPersonsByRelation(Person person, IRelation iRelation, int relationLevel) {
        if (iRelation instanceof GenericRelation) {
            return this.getAllPersonsByRelation(person, (GenericRelation) iRelation, relationLevel);
        } else {
            return this.getAllPersonsByRelation(person, (SpecificRelation) iRelation, relationLevel);
        }
    }

    public Collection<Person> getAllPersonsByRelation(Person person, IGenericRelation genericRelation, int relationLevel) {
        return this.getAllPersonsByRelation(person, genericRelation, null, relationLevel);
    }

    /**
     * Returns all the Person who are related with that Specific relation
     *
     * @param person
     * @param specificRelation
     * @param relationLevel
     * @return
     */
    public Collection<Person> getAllPersonsByRelation(Person person, ISpecificRelation specificRelation, int
            relationLevel) {
        return this.getAllPersonsByRelation(person, specificRelation.getGenericRelation(), specificRelation
                .isRelationMale(), relationLevel);
    }

    private Collection<Person> getAllPersonsByRelation(Person person, IGenericRelation genericRelation, Boolean
            isRelationMale, int relationLevel) {
        Collection<Person> persons = new ArrayList<>();
        IGenericRelation reverseRelation = genericRelation.getReverseRelation();
        persons.addAll(filterConnectionsBySpecificRelation(reverseRelation,
                isRelationMale, -relationLevel, getFamilyGraphForPerson(person, false)).stream().map(ConnectionEdge::to)
                .collect(Collectors.toList()));
        return persons;
    }

    public boolean isPersonRelatedWithRelation(Person person, IRelation iRelation, int relationLevel) {
        if (iRelation instanceof GenericRelation) {
            return this.isPersonRelatedWithRelation(person, (GenericRelation) iRelation, relationLevel);
        } else {
            return this.isPersonRelatedWithRelation(person, (SpecificRelation) iRelation, relationLevel);
        }
    }

    public boolean isPersonRelatedWithRelation(Person person, ISpecificRelation specificRelation, int relationLevel) {
        return this.isPersonRelatedWithRelation(person, specificRelation.getGenericRelation(), specificRelation
                .isRelationMale(), relationLevel, getFamilyGraphForPerson(person, false));
    }

    public boolean isPersonRelatedWithRelation(Person person, IGenericRelation genericRelation, int relationLevel) {
        return this.isPersonRelatedWithRelation(person, genericRelation, null, relationLevel, getFamilyGraphForPerson
                (person, false));
    }

    private boolean isPersonRelatedWithRelation(Person person, IGenericRelation genericRelation, Boolean
            isRelationMale, int relationLevel, Collection<ConnectionEdge> allConnections) {
        if (isRelationMale != null && person.isGenderMale() != isRelationMale) return false;
        for (ConnectionEdge connection : allConnections) {
            if (connection.relationLevel() == relationLevel
                    && connection.relation().equals(genericRelation)) {
                return true;
            }
        }
        return false;
    }

}
