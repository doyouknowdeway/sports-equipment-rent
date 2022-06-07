package com.doyouknowdeway.sportsequipmentrent.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(builder = OrderItemDto.OrderItemDtoBuilder.class)
public class OrderItemDto {

    private Integer id;

    @NotNull(message = "Item Id is null.")
    @Positive(message = "Item Id is negative ot zero.")
    @JsonProperty("item_id")
    private Integer itemId;

    @NotNull(message = "Order Id is null.")
    @Positive(message = "Order Id is negative ot zero.")
    @JsonProperty("order_id")
    private Integer orderId;

    @JsonPOJOBuilder(withPrefix = "")
    public static class OrderItemDtoBuilder {

    }

}
