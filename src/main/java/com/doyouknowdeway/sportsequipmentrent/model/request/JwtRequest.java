package com.doyouknowdeway.sportsequipmentrent.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(builder = JwtRequest.JwtRequestBuilder.class)
public class JwtRequest {

    @NotNull(message = "Token is null.")
    @NotBlank(message = "Token is blank.")
    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonPOJOBuilder(withPrefix = "")
    public static class JwtRequestBuilder {

    }

}