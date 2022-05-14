package com.doyouknowdeway.sportsequipmentrent.provider;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.doyouknowdeway.sportsequipmentrent.model.dto.UserDetailsDto;
import com.doyouknowdeway.sportsequipmentrent.model.entity.enums.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider implements Serializable {

    @Value("${jwt.access.expirationInMinutes}")
    public long accessTokenExpirationInMinutes;

    @Value("${jwt.refresh.expirationInDays}")
    public long refreshTokenExpirationInDays;

    @Value("${jwt.access.secret}")
    private String accessSecret;

    @Value("${jwt.refresh.secret}")
    private String refreshSecret;

    public String generateAccessToken(final UserDetailsDto userDetailsDto) throws IllegalArgumentException, JWTCreationException {
        final Role role = userDetailsDto.getRoles().stream().findFirst().orElse(Role.USER);
        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstant = now.plusMinutes(accessTokenExpirationInMinutes)
                .atZone(ZoneId.systemDefault())
                .toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);

        return JWT.create()
                .withIssuer("Dance Studio")
                .withIssuedAt(new Date())
                .withSubject(userDetailsDto.getId().toString())
                .withClaim("login", userDetailsDto.getLogin())
                .withClaim("role", role.name())
                .withExpiresAt(accessExpiration)
                .sign(Algorithm.HMAC256(accessSecret));
    }

    public String generateRefreshToken(final UserDetailsDto userDetailsDto) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant refreshExpirationInstant = now.plusDays(refreshTokenExpirationInDays)
                .atZone(ZoneId.systemDefault())
                .toInstant();
        final Date refreshExpiration = Date.from(refreshExpirationInstant);

        return JWT.create()
                .withIssuer("Dance Studio")
                .withIssuedAt(new Date())
                .withSubject(userDetailsDto.getId().toString())
                .withClaim("login", userDetailsDto.getLogin())
                .withExpiresAt(refreshExpiration)
                .sign(Algorithm.HMAC256(refreshSecret));
    }

    public String generateNewRefreshToken(final UserDetailsDto userDetailsDto, final String oldRefreshToken) {
        final DecodedJWT jwt = JWT.decode(oldRefreshToken);
        final Date oldRefreshExpiration = jwt.getExpiresAt();

        return JWT.create()
                .withIssuer("Dance Studio")
                .withIssuedAt(new Date())
                .withSubject(userDetailsDto.getId().toString())
                .withClaim("login", userDetailsDto.getLogin())
                .withExpiresAt(oldRefreshExpiration)
                .sign(Algorithm.HMAC256(refreshSecret));
    }

    public boolean validateAccessToken(final String token) {
        return validateToken(token, accessSecret);
    }

    public boolean validateRefreshToken(final String token) {
        return validateToken(token, refreshSecret);
    }

    private boolean validateToken(final String token, final String secret) {
        try {
            final JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
            verifier.verify(token);
            return true;
        } catch (final TokenExpiredException exception) {
            throw new TokenExpiredException(exception.getMessage());
        } catch (final JWTVerificationException exception) {
            throw new JWTVerificationException("Token Invalid!!!");
        }
    }

    public String getLogin(final String token) {
        final DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("login").asString();
    }

}