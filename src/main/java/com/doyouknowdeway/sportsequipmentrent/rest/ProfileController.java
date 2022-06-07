package com.doyouknowdeway.sportsequipmentrent.rest;

import com.doyouknowdeway.sportsequipmentrent.model.dto.OrderDto;
import com.doyouknowdeway.sportsequipmentrent.model.dto.ProfileCreateDto;
import com.doyouknowdeway.sportsequipmentrent.model.dto.ProfileDto;
import com.doyouknowdeway.sportsequipmentrent.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/profiles")
public class ProfileController {

    private final ProfileService profileService;

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PutMapping("/{profileId}")
    public void updateProfile(@RequestBody @Valid final ProfileCreateDto profileDto, @PathVariable final int profileId) {
        profileService.updateProfile(profileDto, profileId);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/{profileId}")
    public ProfileDto getProfileById(@PathVariable final int profileId) {
        return profileService.getProfileById(profileId);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{profileId}")
    public void deleteProfileById(@PathVariable final int profileId) {
        profileService.deleteProfileById(profileId);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping
    public List<ProfileDto> getAllProfiles() {
        return profileService.listProfiles();
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/{profileId}/orders")
    public List<OrderDto> getAllProfileOrders(@PathVariable final int profileId) {
        return profileService.listProfileOrders(profileId);
    }

}
