package com.doyouknowdeway.sportsequipmentrent.model.dto;

import com.doyouknowdeway.sportsequipmentrent.model.entity.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class OrderDto {

    private Integer id;

    @JsonProperty("order_status")
    private OrderStatus orderStatus;

    @JsonProperty("order_datetime")
    private Instant orderDatetime;

    @JsonProperty("destination-datetime")
    private Instant destinationDatetime;

    @JsonProperty("expire_datetime")
    private Instant expireDatetime;

    @JsonProperty("total_price")
    private Double totalPrice;

}
