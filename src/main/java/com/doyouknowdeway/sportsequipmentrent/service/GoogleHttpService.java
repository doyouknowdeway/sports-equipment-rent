package com.doyouknowdeway.sportsequipmentrent.service;

import com.doyouknowdeway.sportsequipmentrent.model.response.GoogleUserInfoResponse;

public interface GoogleHttpService {

    String buildAuthorizationEndpointUrl();

    String getAccessToken(String code);

    GoogleUserInfoResponse getUserInfo(String accessToken);

}
