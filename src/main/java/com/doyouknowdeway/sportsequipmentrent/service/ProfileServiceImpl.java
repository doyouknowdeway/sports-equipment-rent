package com.doyouknowdeway.sportsequipmentrent.service;

import com.doyouknowdeway.sportsequipmentrent.mapper.OrderMapper;
import com.doyouknowdeway.sportsequipmentrent.mapper.ProfileMapper;
import com.doyouknowdeway.sportsequipmentrent.model.dto.OrderDto;
import com.doyouknowdeway.sportsequipmentrent.model.dto.ProfileDto;
import com.doyouknowdeway.sportsequipmentrent.model.dto.create_dto.ProfileCreateDto;
import com.doyouknowdeway.sportsequipmentrent.model.entity.ProfileEntity;
import com.doyouknowdeway.sportsequipmentrent.repository.OrderRepository;
import com.doyouknowdeway.sportsequipmentrent.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final PasswordEncoder passwordEncoder;
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Autowired
    public ProfileServiceImpl(final PasswordEncoder passwordEncoder, final ProfileRepository profileRepository,
                              final ProfileMapper profileMapper, final OrderRepository orderRepository,
                              final OrderMapper orderMapper) {
        this.passwordEncoder = passwordEncoder;
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public ProfileDto createProfile(final ProfileCreateDto profileDto) {
        final String password = passwordEncoder.encode(profileDto.getPassword());
        final ProfileEntity profileEntity = profileMapper.profileCreateDtoToProfileEntity(profileDto);
        profileEntity.setPassword(password);
        return profileMapper.profileEntityToProfileDto(
                profileRepository.save(profileEntity));
    }

    @Override
    public void updateProfile(final ProfileCreateDto profileDto, final int profileId) {
        final ProfileEntity profileEntity = profileRepository.getOne(profileId);
        profileMapper.merge(profileEntity, profileDto);
        profileRepository.save(profileEntity);
    }

    @Override
    public ProfileDto getProfileById(final int profileId) {
        return profileMapper.profileEntityToProfileDto(profileRepository.findById(profileId).orElseThrow());
    }

    @Override
    public void deleteProfileById(final int profileId) {
        profileRepository.deleteById(profileId);
    }

    @Override
    public List<ProfileDto> getAllProfiles() {
        return profileMapper.fromEntities(profileRepository.findAll());
    }

    @Override
    public List<OrderDto> getProfileOrders(final int profileId) {
        return orderMapper.fromEntities(orderRepository.findAllByProfileId(profileId));
    }

}
