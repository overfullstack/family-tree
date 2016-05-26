package graph;

/**
 * Class to represent Person.
 */
public final class Person {
    private final String id;
    private final String name;
    private final int age;
    private final boolean isGenderMale; // Male-true, Female-false

    public Person(String id, String name, int age, boolean isGenderMale) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.isGenderMale = isGenderMale;
    }

    public Person(String id, String name, String age, String isGenderMale) {
        this.id = id;
        this.name = name;
        this.age = Integer.valueOf(age);
        this.isGenderMale = Boolean.valueOf(isGenderMale);
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public boolean isGenderMale() {
        return isGenderMale;
    }

    /* No setters to achieve Immutability */

    public boolean areAllAttributesMatching(Person person) {
        return this.id.equalsIgnoreCase(person.id) && this.name.equalsIgnoreCase(person.name) && this.age == person
                .age && this.isGenderMale == person.isGenderMale;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Person) && ((Person) obj).id.equals(this.id);
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    @Override
    public String toString() {
        return "(" + this.id + ")" + this.name;
    }
}
