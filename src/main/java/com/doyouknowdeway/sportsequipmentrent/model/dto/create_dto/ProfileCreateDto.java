package com.doyouknowdeway.sportsequipmentrent.model.dto.create_dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileCreateDto extends UserCreateDto {

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("second_name")
    private String secondName;

    @JsonProperty("last_name")
    private String lastName;

    private String login;

    private String email;

    private String password;

}
