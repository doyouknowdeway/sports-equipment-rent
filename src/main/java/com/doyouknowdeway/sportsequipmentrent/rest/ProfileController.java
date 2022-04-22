package com.doyouknowdeway.sportsequipmentrent.rest;

import com.doyouknowdeway.sportsequipmentrent.model.dto.OrderDto;
import com.doyouknowdeway.sportsequipmentrent.model.dto.ProfileDto;
import com.doyouknowdeway.sportsequipmentrent.model.dto.create_dto.ProfileCreateDto;
import com.doyouknowdeway.sportsequipmentrent.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profiles")
public class ProfileController {

    private final ProfileService service;

    @Autowired
    public ProfileController(final ProfileService service) {
        this.service = service;
    }

    @PutMapping("/{profileId}")
    public void updateProfile(@RequestBody final ProfileCreateDto profileDto, @PathVariable final int profileId) {
        service.updateProfile(profileDto, profileId);
    }

    @GetMapping("/{profileId}")
    public ProfileDto getProfileById(@PathVariable final int profileId) {
        return service.getProfileById(profileId);
    }

    @DeleteMapping("/{profileId}")
    public void deleteProfileById(@PathVariable final int profileId) {
        service.deleteProfileById(profileId);
    }

    @GetMapping("")
    public List<ProfileDto> getAllProfiles() {
        return service.getAllProfiles();
    }

    @GetMapping("/{profileId}/orders")
    public List<OrderDto> getAllProfileOrders(@PathVariable final int profileId) {
        return service.getProfileOrders(profileId);
    }

}
