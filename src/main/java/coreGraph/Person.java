/*
 * Copyright (c) 2016, Gopal S Akshintala
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * http://creativecommons.org/licenses/by-sa/4.0/
 */

package coreGraph;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

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
        this(id, name, Integer.valueOf(age), Boolean.valueOf(isGenderMale));
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
