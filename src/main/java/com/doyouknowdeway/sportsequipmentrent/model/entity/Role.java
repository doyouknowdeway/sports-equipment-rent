package com.doyouknowdeway.sportsequipmentrent.model.entity;

import org.springframework.security.core.GrantedAuthority;

import java.util.Optional;

public enum Role implements GrantedAuthority {

    ADMIN(1),
    USER(2);

    private final int id;

    Role(final int id) {
        this.id = id;
    }

    public static Optional<Role> of(final Integer id) {
        if (id == null) {
            return Optional.empty();
        }

        for (final var value : Role.values()) {
            if (value.id == id) {
                return Optional.of(value);
            }
        }

        return Optional.empty();
    }

    public int getId() {
        return id;
    }

    @Override
    public String getAuthority() {
        return "ROLE_" + this.name().toUpperCase();
    }

}
