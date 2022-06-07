package com.doyouknowdeway.sportsequipmentrent.model.response;

import com.doyouknowdeway.sportsequipmentrent.model.entity.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class LoginResponse {

    @JsonProperty("login")
    private final String login;

    @JsonProperty("role")
    private final Role role;

    @JsonProperty("access_token")
    private final String accessToken;

    @JsonProperty("refresh_token")
    private final String refreshToken;

}
