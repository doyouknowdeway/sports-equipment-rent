package com.doyouknowdeway.sportsequipmentrent.model.entity.enums;

import java.util.Optional;

public enum Age {

    UNDER_15(1),
    OVER_15(2),
    OVER_18(3),
    OVER_70(4);

    private final int id;

    Age(final int id) {
        this.id = id;
    }

    public static Optional<Age> of(final Integer id) {
        if (id == null) {
            return Optional.empty();
        }

        for (final var value: Age.values()) {
            if (value.id == id) {
                return Optional.of(value);
            }
        }

        return Optional.empty();
    }

    public int getId() {
        return id;
    }

}
