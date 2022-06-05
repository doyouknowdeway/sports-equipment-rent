package com.doyouknowdeway.sportsequipmentrent.rest;

import com.doyouknowdeway.sportsequipmentrent.model.dto.OrderDto;
import com.doyouknowdeway.sportsequipmentrent.model.dto.ProfileCreateDto;
import com.doyouknowdeway.sportsequipmentrent.model.dto.ProfileDto;
import com.doyouknowdeway.sportsequipmentrent.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/profiles")
public class ProfileController {

    private final ProfileService profileService;

    @PutMapping("/{profileId}")
    public void updateProfile(@RequestBody final ProfileCreateDto profileDto, @PathVariable final int profileId) {
        profileService.updateProfile(profileDto, profileId);
    }

    @GetMapping("/{profileId}")
    public ProfileDto getProfileById(@PathVariable final int profileId) {
        return profileService.getProfileById(profileId);
    }

    @DeleteMapping("/{profileId}")
    public void deleteProfileById(@PathVariable final int profileId) {
        profileService.deleteProfileById(profileId);
    }

    @GetMapping("")
    public List<ProfileDto> getAllProfiles() {
        return profileService.getAllProfiles();
    }

    @GetMapping("/{profileId}/orders")
    public List<OrderDto> getAllProfileOrders(@PathVariable final int profileId) {
        return profileService.getProfileOrders(profileId);
    }

}
