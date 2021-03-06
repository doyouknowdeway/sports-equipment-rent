package com.doyouknowdeway.sportsequipmentrent.model.request;

import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GoogleTokenRequest {

    private String code;
    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String grantType;

}
