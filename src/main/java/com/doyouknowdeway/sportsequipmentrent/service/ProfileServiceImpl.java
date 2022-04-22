package com.doyouknowdeway.sportsequipmentrent.service;

import com.doyouknowdeway.sportsequipmentrent.mapper.ProfileMapper;
import com.doyouknowdeway.sportsequipmentrent.model.dto.ProfileDto;
import com.doyouknowdeway.sportsequipmentrent.model.dto.create_dto.ProfileCreateDto;
import com.doyouknowdeway.sportsequipmentrent.model.entity.ProfileEntity;
import com.doyouknowdeway.sportsequipmentrent.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    @Autowired
    public ProfileServiceImpl(ProfileRepository profileRepository, ProfileMapper profileMapper) {
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
    }

    @Override
    public ProfileDto createProfile(ProfileCreateDto profileDto) {
        return profileMapper.fromEntity(profileRepository.save(profileMapper.toEntity(profileDto)));
    }

    @Override
    public void updateProfile(ProfileCreateDto profileDto, int profileId) {
        ProfileEntity profileEntity = profileRepository.getById(profileId);
        profileMapper.merge(profileDto, profileEntity);
        profileRepository.save(profileEntity);
    }

    @Override
    public ProfileDto getProfileById(int profileId) {
        return profileMapper.fromEntity(profileRepository.findById(profileId).orElseThrow());
    }

    @Override
    public void deleteProfileById(int profileId) {
        profileRepository.deleteById(profileId);
    }

    @Override
    public List<ProfileDto> getAllProfiles() {
        return profileMapper.fromEntities(profileRepository.findAll());
    }
}
