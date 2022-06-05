package com.doyouknowdeway.sportsequipmentrent.model.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(builder = LoginRequest.LoginRequestBuilder.class)
public class LoginRequest {

    private String email;
    private String password;

    @JsonPOJOBuilder(withPrefix = "")
    public static class LoginRequestBuilder {

    }

}
