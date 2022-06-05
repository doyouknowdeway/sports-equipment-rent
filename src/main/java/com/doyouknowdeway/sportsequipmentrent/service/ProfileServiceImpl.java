package com.doyouknowdeway.sportsequipmentrent.service;

import com.doyouknowdeway.sportsequipmentrent.exception.EntityNotFoundException;
import com.doyouknowdeway.sportsequipmentrent.mapper.OrderMapper;
import com.doyouknowdeway.sportsequipmentrent.mapper.ProfileMapper;
import com.doyouknowdeway.sportsequipmentrent.model.dto.OrderDto;
import com.doyouknowdeway.sportsequipmentrent.model.dto.ProfileCreateDto;
import com.doyouknowdeway.sportsequipmentrent.model.dto.ProfileDto;
import com.doyouknowdeway.sportsequipmentrent.model.entity.OrderEntity;
import com.doyouknowdeway.sportsequipmentrent.model.entity.ProfileEntity;
import com.doyouknowdeway.sportsequipmentrent.repository.OrderRepository;
import com.doyouknowdeway.sportsequipmentrent.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProfileServiceImpl implements ProfileService {

    private final PasswordEncoder passwordEncoder;
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public ProfileDto createProfile(final ProfileCreateDto profileDto) {
        final String password = passwordEncoder.encode(profileDto.getPassword());
        final ProfileEntity profileEntity = profileMapper.profileCreateDtoToProfileEntity(profileDto);
        profileEntity.setPassword(password);
        final ProfileDto profileDtoResult = profileMapper.profileEntityToProfileDto(profileRepository.save(profileEntity));
        log.info("Profile with id = {} has been created.", profileDtoResult.getId());
        return profileDtoResult;
    }

    @Override
    public void updateProfile(final ProfileCreateDto profileDto, final int profileId) {
        final ProfileEntity profileEntity = profileRepository.findById(profileId)
                .orElseThrow(() -> new EntityNotFoundException("Profile not found!"));
        profileMapper.mergeProfileEntityToProfileCreateDto(profileEntity, profileDto);
        profileRepository.save(profileEntity);
        log.info("Profile with id = {} has been updated.", profileId);
    }

    @Override
    public ProfileDto getProfileById(final int profileId) {
        final Optional<ProfileEntity> profileEntity = profileRepository.findById(profileId);
        profileEntity.ifPresentOrElse(
                (user) -> log.info("Profile with id = {} has been found.", user.getId()),
                () -> log.warn("Profile with id = {} hasn't been found.", profileId));
        return profileEntity.map(profileMapper::profileEntityToProfileDto)
                .orElseThrow(() -> new EntityNotFoundException("Profile not found!"));
    }

    @Override
    public void deleteProfileById(final int profileId) {
        final Optional<ProfileEntity> profileEntity = profileRepository.findById(profileId);
        profileEntity.ifPresentOrElse(
                (user) -> log.info("Profile with id = {} has been found.", profileId),
                () -> {
                    log.warn("Profile with id = {} hasn't been found.", profileId);
                    throw new EntityNotFoundException("Profile not found!");
                });
        profileRepository.deleteById(profileId);
        log.info("Profile with id = {} has been deleted.", profileId);
    }

    @Override
    public List<ProfileDto> getAllProfiles() {
        final List<ProfileEntity> profileEntities = profileRepository.findAll();
        log.info("There have been found {} profiles.", profileEntities.size());
        return profileMapper.profileEntityToProfileDtoList(profileEntities);
    }

    @Override
    public List<OrderDto> getProfileOrders(final int profileId) {
        final List<OrderEntity> orderEntities = orderRepository.findAllByProfileId(profileId);
        log.info("There have been found {} orders for user with id = {}.", orderEntities.size(), profileId);
        return orderMapper.orderEntitiesToOrderDtoList(orderEntities);
    }

}
