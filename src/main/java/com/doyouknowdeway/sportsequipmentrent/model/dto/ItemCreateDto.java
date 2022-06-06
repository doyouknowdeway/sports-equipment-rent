package com.doyouknowdeway.sportsequipmentrent.model.dto;

import com.doyouknowdeway.sportsequipmentrent.model.entity.Age;
import com.doyouknowdeway.sportsequipmentrent.model.entity.Season;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@JsonDeserialize(builder = ItemCreateDto.ItemCreateDtoBuilder.class)
public class ItemCreateDto {

    @NotNull(message = "Name is null.")
    @NotBlank(message = "Name is blank.")
    private String name;

    private String description;

    @NotNull(message = "Cost Per Hour is null.")
    @Positive(message = "Cost Per Hour is negative ot zero.")
    @JsonProperty("cost_per_hour")
    private Double costPerHour;

    private Age age;

    private Season season;

    @JsonProperty("base64_string_image")
    private String base64StringImage;

    @JsonPOJOBuilder(withPrefix = "")
    public static class ItemCreateDtoBuilder {

    }

}
