package com.doyouknowdeway.sportsequipmentrent.service;

import com.doyouknowdeway.sportsequipmentrent.mapper.JwtTokenMapper;
import com.doyouknowdeway.sportsequipmentrent.model.dto.JwtTokenDto;
import com.doyouknowdeway.sportsequipmentrent.model.entity.JwtTokenEntity;
import com.doyouknowdeway.sportsequipmentrent.model.entity.ProfileEntity;
import com.doyouknowdeway.sportsequipmentrent.model.entity.enums.JwtTokenType;
import com.doyouknowdeway.sportsequipmentrent.repository.JwtTokenRepository;
import com.doyouknowdeway.sportsequipmentrent.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class JwtTokenServiceImpl implements JwtTokenService {

    private final JwtTokenRepository jwtTokenRepository;
    private final ProfileRepository profileRepository;
    private final JwtTokenMapper jwtTokenMapper;

    @Autowired
    public JwtTokenServiceImpl(final JwtTokenRepository jwtTokenRepository, final ProfileRepository profileRepository,
                               final JwtTokenMapper jwtTokenMapper) {
        this.jwtTokenRepository = jwtTokenRepository;
        this.profileRepository = profileRepository;
        this.jwtTokenMapper = jwtTokenMapper;
    }

    @Override
    public void createJwtToken(final JwtTokenDto jwtTokenDto) {
        final ProfileEntity profileEntity = profileRepository.findByLogin(jwtTokenDto.getLogin()).orElseThrow();
        final JwtTokenEntity jwtTokenEntity = jwtTokenMapper.jwtTokenDtoToJwtTokenEntity(jwtTokenDto);
        jwtTokenEntity.setProfile(profileEntity);
        jwtTokenRepository.save(jwtTokenEntity);
    }

    @Override
    public String getJwtTokenByLogin(final String login, final JwtTokenType type) {
        return jwtTokenRepository.findByProfileLoginAndType(login, type).orElseThrow().getToken();
    }

    @Override
    public boolean existsByToken(final String token) {
        return jwtTokenRepository.existsByToken(token);
    }

    @Override
    public void updateJwtToken(final JwtTokenDto jwtTokenDto) {
        final JwtTokenEntity jwtTokenEntity = jwtTokenRepository.findByProfileLoginAndType(
                jwtTokenDto.getLogin(), jwtTokenDto.getType()).orElseThrow();
        jwtTokenMapper.mergeJwtTokenEntityAndJwtTokenDto(jwtTokenEntity, jwtTokenDto);
        jwtTokenRepository.save(jwtTokenEntity);
    }

    @Override
    public void deleteJwtTokenByLogin(final String login) {
        deleteJwtTokenByLogin(login, JwtTokenType.ACCESS);
        deleteJwtTokenByLogin(login, JwtTokenType.REFRESH);
    }

    private void deleteJwtTokenByLogin(final String login, final JwtTokenType type) {
        jwtTokenRepository.markAsDeletedByUserLoginAndType(login, type);
    }

}
