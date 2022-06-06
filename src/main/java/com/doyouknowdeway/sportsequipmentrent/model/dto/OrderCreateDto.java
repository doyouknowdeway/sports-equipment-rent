package com.doyouknowdeway.sportsequipmentrent.model.dto;

import com.doyouknowdeway.sportsequipmentrent.annotation.DatetimeValid;
import com.doyouknowdeway.sportsequipmentrent.model.entity.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(builder = OrderCreateDto.OrderCreateDtoBuilder.class)
public class OrderCreateDto {

    @JsonProperty("order_status")
    private OrderStatus orderStatus;

    @NotNull(message = "Profile Id is null.")
    @Positive(message = "Profile Id is negative ot zero.")
    @JsonProperty("profile_id")
    private Integer profileId;

    @DatetimeValid(message = "Order Datetime invalid.")
    @JsonProperty("order_datetime")
    private String orderDatetime;

    @DatetimeValid(message = "Destination Datetime invalid.")
    @JsonProperty("destination_datetime")
    private String destinationDatetime;

    @DatetimeValid(message = "Expire Datetime invalid.")
    @JsonProperty("expire_datetime")
    private String expireDatetime;

    @NotNull(message = "Total Price is null.")
    @Positive(message = "Total Price is negative ot zero.")
    @JsonProperty("total_price")
    private Double totalPrice;

    @JsonPOJOBuilder(withPrefix = "")
    public static class OrderCreateDtoBuilder {

    }

}
