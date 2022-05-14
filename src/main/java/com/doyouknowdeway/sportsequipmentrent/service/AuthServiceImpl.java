package com.doyouknowdeway.sportsequipmentrent.service;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.doyouknowdeway.sportsequipmentrent.model.dto.JwtTokenDto;
import com.doyouknowdeway.sportsequipmentrent.model.dto.UserDetailsDto;
import com.doyouknowdeway.sportsequipmentrent.model.entity.enums.JwtTokenType;
import com.doyouknowdeway.sportsequipmentrent.model.response.JwtResponse;
import com.doyouknowdeway.sportsequipmentrent.model.response.LoginResponse;
import com.doyouknowdeway.sportsequipmentrent.provider.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.security.auth.message.AuthException;

@Service
public class AuthServiceImpl implements AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtTokenService jwtTokenService;
    private final UserDetailsService userDetailsService;

    @Autowired
    public AuthServiceImpl(final JwtTokenProvider jwtTokenProvider, final JwtTokenService jwtTokenService,
                           final UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtTokenService = jwtTokenService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public LoginResponse login(final UserDetailsDto userDetailsDto) {
        final String accessToken = jwtTokenProvider.generateAccessToken(userDetailsDto);
        final String refreshToken = jwtTokenProvider.generateRefreshToken(userDetailsDto);
        final String login = userDetailsDto.getLogin();

        final JwtTokenDto jwtAccessTokenDto = JwtTokenDto.builder()
                .token(accessToken)
                .type(JwtTokenType.ACCESS)
                .login(login)
                .isDeleted(false)
                .build();
        final JwtTokenDto jwtRefreshTokenDto = JwtTokenDto.builder()
                .token(refreshToken)
                .type(JwtTokenType.REFRESH)
                .login(login)
                .isDeleted(false)
                .build();

        jwtTokenService.createJwtToken(jwtAccessTokenDto);
        jwtTokenService.createJwtToken(jwtRefreshTokenDto);
        return new LoginResponse(accessToken, refreshToken);
    }

    @Override
    public JwtResponse getNewAccessToken(final String refreshToken) throws AuthException {
        try {
            if (jwtTokenProvider.validateRefreshToken(refreshToken)) {
                final String login = jwtTokenProvider.getLogin(refreshToken);
                if (!login.isEmpty() && !login.isBlank()) {
                    final String refreshTokenFromDB = jwtTokenService.getJwtTokenByLogin(login, JwtTokenType.REFRESH);
                    if (refreshTokenFromDB.equals(refreshToken)) {
                        final UserDetailsDto userDetailsDto = (UserDetailsDto) userDetailsService.loadUserByUsername(login);
                        final String accessToken = jwtTokenProvider.generateAccessToken(userDetailsDto);

                        final JwtTokenDto jwtAccessTokenDto = JwtTokenDto.builder()
                                .token(accessToken)
                                .type(JwtTokenType.ACCESS)
                                .login(login)
                                .isDeleted(false)
                                .build();

                        jwtTokenService.updateJwtToken(jwtAccessTokenDto);
                        return new JwtResponse(accessToken, refreshToken);
                    }
                }
            }
        } catch (final TokenExpiredException exception) {
            final String login = jwtTokenProvider.getLogin(refreshToken);
            jwtTokenService.deleteJwtTokenByLogin(login);
        }
        throw new AuthException("Jwt Token Invalid!!!");
    }

    @Override
    public JwtResponse getNewRefreshToken(final String refreshToken) throws AuthException {
        if (jwtTokenProvider.validateRefreshToken(refreshToken)) {
            final String login = jwtTokenProvider.getLogin(refreshToken);
            if (!login.isEmpty() && !login.isBlank()) {
                final String refreshTokenFromDB = jwtTokenService.getJwtTokenByLogin(login, JwtTokenType.REFRESH);
                if (refreshTokenFromDB.equals(refreshToken)) {
                    final UserDetailsDto userDetailsDto = (UserDetailsDto) userDetailsService.loadUserByUsername(login);
                    final String accessToken = jwtTokenProvider.generateAccessToken(userDetailsDto);
                    final String newRefreshToken = jwtTokenProvider.generateNewRefreshToken(userDetailsDto, refreshToken);

                    final JwtTokenDto jwtAccessTokenDto = JwtTokenDto.builder()
                            .token(accessToken)
                            .type(JwtTokenType.ACCESS)
                            .login(login)
                            .isDeleted(false)
                            .build();
                    final JwtTokenDto jwtRefreshTokenDto = JwtTokenDto.builder()
                            .token(newRefreshToken)
                            .type(JwtTokenType.REFRESH)
                            .login(login)
                            .isDeleted(false)
                            .build();

                    jwtTokenService.updateJwtToken(jwtAccessTokenDto);
                    jwtTokenService.updateJwtToken(jwtRefreshTokenDto);
                    return new JwtResponse(accessToken, newRefreshToken);
                }
            }
        }
        throw new AuthException("Jwt Token Invalid!!!");
    }

    @Override
    public void logout(final String login) {
        jwtTokenService.deleteJwtTokenByLogin(login);
    }

}
