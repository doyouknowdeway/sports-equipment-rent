package com.doyouknowdeway.sportsequipmentrent.model.entity.enums;

import java.util.Optional;

public enum OrderStatus {

    IN_FORMING(1),
    NEW(2),
    IN_PROCESSING(3),
    READY_FOR_DELIVERY(4),
    ACCEPTED_BY_CUSTOMER(5),
    COMPLETED(6),
    CANCELED(7);

    private final int id;

    OrderStatus(int id) {
        this.id = id;
    }

    public static Optional<OrderStatus> of(Integer id) {
        if (id == null) {
            return Optional.empty();
        }

        for (var value: OrderStatus.values()) {
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
