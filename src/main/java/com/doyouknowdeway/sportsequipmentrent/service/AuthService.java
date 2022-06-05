package com.doyouknowdeway.sportsequipmentrent.service;

import com.doyouknowdeway.sportsequipmentrent.model.dto.UserDetailsDto;
import com.doyouknowdeway.sportsequipmentrent.model.response.JwtResponse;
import com.doyouknowdeway.sportsequipmentrent.model.response.LoginResponse;

import javax.security.auth.message.AuthException;

public interface AuthService {

    LoginResponse login(UserDetailsDto userDetailsDto);

    JwtResponse getNewAccessToken(String refreshToken) throws AuthException;

    void logout(String email);

}
