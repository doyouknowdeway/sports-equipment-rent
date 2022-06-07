package com.doyouknowdeway.sportsequipmentrent.service;

import com.doyouknowdeway.sportsequipmentrent.model.dto.OrderDto;
import com.doyouknowdeway.sportsequipmentrent.model.dto.ProfileDto;
import com.doyouknowdeway.sportsequipmentrent.model.dto.ProfileCreateDto;
import com.doyouknowdeway.sportsequipmentrent.model.dto.UserDetailsDto;

import java.util.List;

public interface ProfileService {

    ProfileDto createProfile(ProfileCreateDto profileDto);

    void updateProfile(ProfileCreateDto profileDto, int profileId);

    ProfileDto getProfileById(int profileId);

    UserDetailsDto getProfileByEmail(String email);

    boolean existsByProfileEmail(String email);

    void deleteProfileById(int profileId);

    List<ProfileDto> listProfiles();

    List<OrderDto> listProfileOrders(int profileId);

}
