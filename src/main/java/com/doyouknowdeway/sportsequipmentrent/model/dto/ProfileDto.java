package com.doyouknowdeway.sportsequipmentrent.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileDto extends UserDto {

    private Integer id;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("second_name")
    private String secondName;

    @JsonProperty("last_name")
    private String lastName;

    private String login;

    private String email;

}
