package com.doyouknowdeway.sportsequipmentrent.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDto {

    private Integer id;

    private String name;

    private String description;

    @JsonProperty("cost_per_hour")
    private Double costPerHour;

    @JsonProperty("age_id")
    private Integer ageId;

    @JsonProperty("season_id")
    private Integer seasonId;

}
