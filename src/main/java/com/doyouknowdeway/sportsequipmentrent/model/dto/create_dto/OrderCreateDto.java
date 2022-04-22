package com.doyouknowdeway.sportsequipmentrent.model.dto.create_dto;

import com.doyouknowdeway.sportsequipmentrent.model.entity.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class OrderCreateDto {

    @JsonProperty("order_status")
    private OrderStatus orderStatus;

    @JsonProperty("profile_id")
    private Integer profileId;

    @JsonProperty("order_datetime")
    private Instant orderDatetime;

    @JsonProperty("destination_datetime")
    private Instant destinationDatetime;

    @JsonProperty("expire_datetime")
    private Instant expireDatetime;

    @JsonProperty("total_price")
    private Double totalPrice;

}
