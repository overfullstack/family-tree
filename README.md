# Family Tree (Spicy)
### Features
1.  Display Family Tree
2.  Display Shortest Relation Chain
3.  Display Members from Generation Level
4.  Display Family Members in Ascending order of Age
5.  Display Family Members in Descending order of Age
6.  Display Male Family Members
7.  Display Female Family Members
8.  Display Aggregate Relation
9.  Display Persons by Relation (Ex: Display al Persons who are related to this Person as AUNTs)
10. Display Persons Related to someone with Relation (Ex: Display all Persons who are MOTHERs to someone)

### Frameworks or Libs Used
- Lombok - to auto-generate boiler plate code
- Google Guice - For Dependency Injection
- Gradle - For Build
- Java 8

### Design Decisions
- Family is represented as a Directed graph, with Persons as Nodes and Relations as Edges. Adjacency list graph representation is used. 
- Edge is called 'Connection' and it holds From Person, To Person, Generic Relation and Relation Level.
- The system can support both Generic and Specific type of relations.
- Relation level of a connection represents the difference of levels above or below, between the persons in relation.
 It also helps in representing relations like GREAT GREAT GRANDPARENT or GREAT GRANDCHILD.
- graph stores only generic relations and they are converted back to specific relations based on gender.
- Most of the relations come in pairs, as one is reverse of the other and one is alternate to another. They are coupled 
together, and one depends on another to get reverse relation in `getReverseRelation()`. 
- Specific relations are tied to generic relations with method: `getGenericRelation()`
- Using enum to represent Relations, as all the relations have static state and enum promotes type safety.
- Classes are Loosely coupled, depending on interfaces rather than concrete implementations. Components can be 
replaced and extended easily.
- Classes are designed to be highly Cohesive.
- Most of the methods are Polymorphic, written for various use-cases.
- Template pattern followed for Relations, so that every relations implements a stipulated template of methods.
- Chain of Responsibilities pattern used for validation. Connections have to go through a series of validations like 
age, gender and possible relationships.
- Violated 'Law of Demeter' to reduce wrapping and complexity. Eg: `edge.relation().getGenericRelation()`
- Violated SRP for DRY in methods like `traverseFamilyGraph(), load()`.

### Assumptions/Limitations
- PARENT, SIBLING, CHILD are direct relations and KIN, COUSIN, NIBLING are indirect relations. So, when there is no 
direct connection between two persons, they are assumed to have a indirect relation between them. For example, KIN of
 a CHILD is treated as COUSIN, instead of SIBLING.
- COUSIN is both generic and specific relation.

### Glossary
- KIN: Generic gender neutral word chosen to represent UNCLE and AUNT.
- NIBLING: Generic gender neutral word chosen to represent NEPHEW and NIECE

## License
<a rel="license" href="https://opensource.org/licenses/MIT">
<img alt="MIT License" 
style="border-width:0" src="https://upload.wikimedia.org/wikipedia/commons/thumb/0/0c/MIT_logo.svg/800px-MIT_logo.svg.png" width="88" height="31"/></a><br/>
All Software written by Gopal S Akshintala, is licensed under <a rel="license" href="https://opensource.org/licenses/MIT">MIT</a>.