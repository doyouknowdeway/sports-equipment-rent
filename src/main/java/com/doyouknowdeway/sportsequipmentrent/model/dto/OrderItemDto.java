package com.doyouknowdeway.sportsequipmentrent.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDto {

    private Integer id;

    @JsonProperty("item_id")
    private Integer itemId;

    @JsonProperty("order_id")
    private Integer orderId;

}
