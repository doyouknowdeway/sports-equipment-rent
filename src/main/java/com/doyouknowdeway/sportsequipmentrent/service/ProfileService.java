package com.doyouknowdeway.sportsequipmentrent.service;

import com.doyouknowdeway.sportsequipmentrent.model.dto.ProfileDto;
import com.doyouknowdeway.sportsequipmentrent.model.dto.create_dto.ProfileCreateDto;

import java.util.List;

public interface ProfileService {

    ProfileDto createProfile(ProfileCreateDto profileDto);

    void updateProfile(ProfileCreateDto profileDto, int profileId);

    ProfileDto getProfileById(int profileId);

    void deleteProfileById(int profileId);

    List<ProfileDto> getAllProfiles();

}
