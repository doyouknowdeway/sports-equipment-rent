package com.doyouknowdeway.sportsequipmentrent.rest;

import com.doyouknowdeway.sportsequipmentrent.model.dto.ProfileDto;
import com.doyouknowdeway.sportsequipmentrent.model.dto.create_dto.ProfileCreateDto;
import com.doyouknowdeway.sportsequipmentrent.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    private final ProfileService service;

    @Autowired
    public MainController(final ProfileService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ProfileDto createProfile(@RequestBody final ProfileCreateDto profileDto) {
        return service.createProfile(profileDto);
    }

}
