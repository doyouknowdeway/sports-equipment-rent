package com.doyouknowdeway.sportsequipmentrent.service;

import com.doyouknowdeway.sportsequipmentrent.exception.EntityCreationException;
import com.doyouknowdeway.sportsequipmentrent.exception.EntityNotFoundException;
import com.doyouknowdeway.sportsequipmentrent.mapper.JwtTokenMapper;
import com.doyouknowdeway.sportsequipmentrent.model.dto.JwtTokenDto;
import com.doyouknowdeway.sportsequipmentrent.model.entity.JwtTokenEntity;
import com.doyouknowdeway.sportsequipmentrent.model.entity.JwtTokenType;
import com.doyouknowdeway.sportsequipmentrent.model.entity.ProfileEntity;
import com.doyouknowdeway.sportsequipmentrent.repository.JwtTokenRepository;
import com.doyouknowdeway.sportsequipmentrent.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class JwtTokenServiceImpl implements JwtTokenService {

    private final JwtTokenRepository jwtTokenRepository;
    private final ProfileRepository profileRepository;
    private final JwtTokenMapper jwtTokenMapper;

    @Override
    @Transactional
    public void createJwtToken(final JwtTokenDto jwtTokenDto) {
        final Optional<ProfileEntity> profileEntity = profileRepository.findByEmail(jwtTokenDto.getEmail());
        profileEntity.ifPresentOrElse(
                (profile) -> {
                    final JwtTokenEntity jwtTokenEntity = Optional.of(jwtTokenDto)
                            .map(jwtTokenMapper::jwtTokenDtoToJwtTokenEntity)
                            .map((jwtToken) -> {
                                jwtToken.setProfile(profile);
                                return jwtTokenRepository.save(jwtToken);
                            })
                            .orElseThrow(() -> new EntityCreationException("Token not created!"));
                    log.info("Token with type = {} for email = {} with id = {} has been created.",
                            jwtTokenDto.getType(), jwtTokenDto.getEmail(), jwtTokenEntity.getId());
                },
                () -> {
                    log.warn("Token with type = {} for email = {} hasn't been created.",
                            jwtTokenDto.getType(), jwtTokenDto.getEmail());
                    throw new EntityCreationException("Token not created!");
                }
        );
    }

    @Override
    @Transactional(readOnly = true)
    public String getJwtTokenByEmail(final String email, final JwtTokenType type) {
        final Optional<JwtTokenEntity> jwtTokenEntity = jwtTokenRepository.findByProfileEmailAndType(email, type);
        jwtTokenEntity.ifPresentOrElse(
                (token) -> log.info("Token with type = {} for email = {} with id = {} has been found.", type, email, token.getId()),
                () -> log.warn("Token with type = {} for email = {} hasn't been found.", type, email));
        return jwtTokenEntity.orElseThrow(() -> new EntityNotFoundException("Token not found!")).getToken();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByToken(final String token) {
        final boolean existsByToken = jwtTokenRepository.existsByToken(token);
        if (existsByToken) {
            log.info("Token exists.");
        } else {
            log.warn("Token doesn't exist.");
        }
        return existsByToken;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUserEmail(final String email) {
        final boolean existsByUserEmail = existsByUserEmailAndType(email, JwtTokenType.ACCESS)
                && existsByUserEmailAndType(email, JwtTokenType.REFRESH);
        if (existsByUserEmail) {
            log.info("Tokens for email = {} exist.", email);
        } else {
            log.warn("Tokens for email = {} don't exist.", email);
        }
        return existsByUserEmail;
    }

    private boolean existsByUserEmailAndType(final String email, final JwtTokenType type) {
        return jwtTokenRepository.existsByProfileEmailAndType(email, type);
    }

    @Override
    @Transactional
    public void updateJwtToken(final JwtTokenDto jwtTokenDto) {
        final JwtTokenEntity jwtTokenEntity = jwtTokenRepository.findByProfileEmailAndType(
                jwtTokenDto.getEmail(), jwtTokenDto.getType()).orElseThrow(() -> {
            log.warn("Token with type = {} for email = {} doesn't exist", jwtTokenDto.getType(), jwtTokenDto.getEmail());
            throw new EntityCreationException("New access token hasn't been created!");
        });
        jwtTokenMapper.mergeJwtTokenEntityAndJwtTokenDto(jwtTokenEntity, jwtTokenDto);
        final JwtTokenEntity updatedJwtTokenEntity = jwtTokenRepository.save(jwtTokenEntity);
        log.info("Token with type = {} for email = {} with id = {} has been updated.",
                jwtTokenDto.getType(), jwtTokenDto.getEmail(), updatedJwtTokenEntity.getId());
    }

    @Override
    @Transactional
    public void deleteJwtTokensByEmail(final String email) {
        if (existsByUserEmailAndType(email, JwtTokenType.ACCESS) && existsByUserEmailAndType(email, JwtTokenType.REFRESH)) {
            log.info("Tokens for email = {} have been found.", email);
        } else {
            log.warn("Tokens for email = {} haven't been found.", email);
            throw new EntityNotFoundException("Tokens not found!");
        }
        deleteJwtTokenByEmail(email, JwtTokenType.ACCESS);
        deleteJwtTokenByEmail(email, JwtTokenType.REFRESH);
        log.info("Tokens for email = {} have been deleted.", email);
    }

    private void deleteJwtTokenByEmail(final String email, final JwtTokenType type) {
        jwtTokenRepository.deleteByProfileEmailAndType(email, type);
    }

}
