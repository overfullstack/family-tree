/*
 * Copyright (c) 2016, Gopal S Akshintala
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * http://creativecommons.org/licenses/by-sa/4.0/
 */

package relationship;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Class representing Specific relations
 */
@RequiredArgsConstructor
public enum SpecificRelation implements ISpecificRelation {
    HUSBAND(true),
    WIFE(false),

    FATHER(true),
    MOTHER(false),

    SON(true),
    DAUGHTER(false),

    GRANDFATHER(true),
    GRANDMOTHER(false),

    GRANDSON(true),
    GRANDDAUGHTER(false),

    BROTHER(true),
    SISTER(false),

    UNCLE(true),
    AUNT(false),

    NEPHEW(true),
    NIECE(false),

    COUSIN(null);

    @Getter
    @Accessors(fluent = true)
    private final Boolean isRelationMale;

    @Getter
    @Setter
    private IGenericRelation genericRelation;
}
