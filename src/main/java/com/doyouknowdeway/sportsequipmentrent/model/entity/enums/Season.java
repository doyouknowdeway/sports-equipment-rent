package com.doyouknowdeway.sportsequipmentrent.model.entity.enums;

import java.util.Optional;

public enum Season {

    WINTER(1),
    SUMMER(2);

    private final int id;

    Season(int id) {
        this.id = id;
    }

    public static Optional<Season> of(Integer id) {
        if (id == null) {
            return Optional.empty();
        }

        for (var value: Season.values()) {
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
