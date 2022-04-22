package com.doyouknowdeway.sportsequipmentrent.model.entity.enums;

import java.util.Optional;

public enum Season {

    WINTER(1),
    SUMMER(2);

    private final int id;

    Season(final int id) {
        this.id = id;
    }

    public static Optional<Season> of(final Integer id) {
        if (id == null) {
            return Optional.empty();
        }

        for (final var value: Season.values()) {
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
