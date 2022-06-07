package com.doyouknowdeway.sportsequipmentrent.model.dto;

import com.doyouknowdeway.sportsequipmentrent.model.entity.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.Instant;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
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
