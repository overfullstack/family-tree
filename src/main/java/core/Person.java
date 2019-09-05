package core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.Objects;

/**
 * Class to represent Person.
 */
@AllArgsConstructor
public final class Person {
    @NonNull
    @Getter
    private final String id;
    @NonNull
    @Getter
    private final String name;
    @Getter
    private final int age;
    @Getter
    private final boolean isGenderMale; // Male-true, Female-false

    public Person(String id, String name, String age, String isGenderMale) {
        this(id, name, Integer.parseInt(age), Boolean.parseBoolean(isGenderMale));
    }

    /* No setters written, to achieve Immutability */

    public boolean areAllAttributesMatching(Person person) {
        return this.id.equalsIgnoreCase(person.id)
                && this.name.equalsIgnoreCase(person.name)
                && this.age == person.age
                && this.isGenderMale == person.isGenderMale;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Person)
                && ((Person) obj).id.equals(this.id);
    }

    @Override
    public String toString() {
        return "(" + this.id + ")" + this.name;
    }
}
