package com.doyouknowdeway.sportsequipmentrent.model.entity.enums;

import java.util.Optional;

public enum Role {

    ADMIN(1),
    USER(2);

    private final int id;

    Role(int id) {
        this.id = id;
    }

    public static Optional<Role> of(Integer id) {
        if (id == null) {
            return Optional.empty();
        }

        for (var value: Role.values()) {
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
