package com.doyouknowdeway.sportsequipmentrent.model.dto;

import com.doyouknowdeway.sportsequipmentrent.model.entity.Age;
import com.doyouknowdeway.sportsequipmentrent.model.entity.Season;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemCreateDto {

    private String name;

    private String description;

    @JsonProperty("cost_per_hour")
    private Double costPerHour;

    private Age age;

    private Season season;

}