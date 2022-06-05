package com.doyouknowdeway.sportsequipmentrent.rest;

import com.doyouknowdeway.sportsequipmentrent.model.dto.ProfileCreateDto;
import com.doyouknowdeway.sportsequipmentrent.model.dto.UserDetailsDto;
import com.doyouknowdeway.sportsequipmentrent.model.response.LoginResponse;
import com.doyouknowdeway.sportsequipmentrent.service.AuthService;
import com.doyouknowdeway.sportsequipmentrent.service.ProfileService;
import com.doyouknowdeway.sportsequipmentrent.utils.SecurityContextFacade;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class MainController {

    private final AuthService authService;
    private final ProfileService profileService;
    private final SecurityContextFacade securityContextFacade;
    protected AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public LoginResponse createProfile(@RequestBody final ProfileCreateDto profileDto) {
        profileService.createProfile(profileDto);
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(profileDto.getEmail(), profileDto.getPassword()));
        securityContextFacade.getContext().setAuthentication(authentication);

        final UserDetailsDto userDetailsDto = (UserDetailsDto) authentication.getPrincipal();
        return authService.login(userDetailsDto);
    }

}
