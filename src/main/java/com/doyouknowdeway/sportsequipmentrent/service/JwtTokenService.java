package com.doyouknowdeway.sportsequipmentrent.service;

import com.doyouknowdeway.sportsequipmentrent.model.dto.JwtTokenDto;
import com.doyouknowdeway.sportsequipmentrent.model.entity.enums.JwtTokenType;

public interface JwtTokenService {

    void createJwtToken(JwtTokenDto jwtTokenDto);

    String getJwtTokenByLogin(String login, JwtTokenType type);

    boolean existsByToken(String token);

    void updateJwtToken(final JwtTokenDto jwtTokenDto);

    void deleteJwtTokenByLogin(String login);

}
