package com.doyouknowdeway.sportsequipmentrent.model.dto;

import com.doyouknowdeway.sportsequipmentrent.annotation.LoginValid;
import com.doyouknowdeway.sportsequipmentrent.annotation.PasswordValid;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(builder = ProfileCreateDto.ProfileCreateDtoBuilder.class)
public class ProfileCreateDto {

    @NotNull(message = "First Name is null.")
    @NotBlank(message = "First Name is blank.")
    @Pattern(regexp = "^([A-Z]|[a-z])+$", message = "First Name mustn't contain a number.")
    @JsonProperty("first_name")
    private String firstName;

    @NotNull(message = "Second Name is null.")
    @NotBlank(message = "Second Name is blank.")
    @Pattern(regexp = "^([A-Z]|[a-z])+$", message = "Second Name mustn't contain a number.")
    @JsonProperty("second_name")
    private String secondName;

    @NotNull(message = "Last Name is null.")
    @NotBlank(message = "Last Name is blank.")
    @Pattern(regexp = "^([A-Z]|[a-z])+$", message = "Last Name mustn't contain a number.")
    @JsonProperty("last_name")
    private String lastName;

    @NotNull(message = "Login is null.")
    @NotBlank(message = "Login is blank.")
    @LoginValid
    private String login;

    @NotNull(message = "Email is null.")
    @NotBlank(message = "Email is blank.")
    @Email(message = "Email invalid.")
    private String email;

    @NotNull(message = "Password is null.")
    @NotBlank(message = "Password is blank.")
    @PasswordValid
    private String password;

    @JsonPOJOBuilder(withPrefix = "")
    public static class ProfileCreateDtoBuilder {

    }

}
