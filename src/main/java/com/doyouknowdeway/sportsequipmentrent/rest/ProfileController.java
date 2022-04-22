package com.doyouknowdeway.sportsequipmentrent.rest;

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
    public ProfileController(ProfileService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ProfileDto createProfile(@RequestBody ProfileCreateDto profileDto) {
        return service.createProfile(profileDto);
    }

    @PutMapping("/update/{profileId}")
    public void updateProfile(@RequestBody ProfileCreateDto profileDto, @PathVariable int profileId) {
        service.updateProfile(profileDto, profileId);
    }

    @GetMapping("/{profileId}")
    public ProfileDto getProfileById(@PathVariable int profileId) {
        return service.getProfileById(profileId);
    }

    @DeleteMapping("/delete/{profileId}")
    public void deleteProfileById(@PathVariable int profileId) {
        service.deleteProfileById(profileId);
    }

    @GetMapping
    public List<ProfileDto> getAllProfiles() {
        return service.getAllProfiles();
    }

}
