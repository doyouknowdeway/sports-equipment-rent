package com.doyouknowdeway.sportsequipmentrent.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(builder = ProfileCreateDto.ProfileCreateDtoBuilder.class)
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

    @JsonPOJOBuilder(withPrefix = "")
    public static class ProfileCreateDtoBuilder {

    }

}
