package core;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

/**
 * Class to represent Person.
 */
@Value
@AllArgsConstructor
public final class Person {
    @NonNull
    String id;
    @NonNull
    String name;
    int age;
    boolean isGenderMale;

    public Person(String id, String name, String age, String isGenderMale) {
        this(id, name, Integer.parseInt(age), Boolean.parseBoolean(isGenderMale));
    }
    
}
