package com.doyouknowdeway.sportsequipmentrent.model.dto;

import com.doyouknowdeway.sportsequipmentrent.model.entity.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(builder = OrderUpdateStatusDto.OrderUpdateStatusDtoBuilder.class)
public class OrderUpdateStatusDto {

    @JsonProperty("order_status")
    private OrderStatus orderStatus;

    @JsonPOJOBuilder(withPrefix = "")
    public static class OrderUpdateStatusDtoBuilder {

    }

}
