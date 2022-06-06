package com.doyouknowdeway.sportsequipmentrent.model.entity;

import java.util.Optional;

public enum Season {

    WINTER(1),
    SUMMER(2),
    SPRING(3),
    AUTUMN(4),
    WINTER_AUTUMN(5),
    SPRING_SUMMER(6);

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
