package com.doyouknowdeway.sportsequipmentrent.rest;

import com.doyouknowdeway.sportsequipmentrent.model.dto.ProfileCreateDto;
import com.doyouknowdeway.sportsequipmentrent.model.dto.UserDetailsDto;
import com.doyouknowdeway.sportsequipmentrent.model.request.JwtRequest;
import com.doyouknowdeway.sportsequipmentrent.model.request.LoginRequest;
import com.doyouknowdeway.sportsequipmentrent.model.response.GoogleUserInfoResponse;
import com.doyouknowdeway.sportsequipmentrent.model.response.JwtResponse;
import com.doyouknowdeway.sportsequipmentrent.model.response.LoginResponse;
import com.doyouknowdeway.sportsequipmentrent.service.AuthService;
import com.doyouknowdeway.sportsequipmentrent.service.GoogleHttpService;
import com.doyouknowdeway.sportsequipmentrent.service.ProfileService;
import com.doyouknowdeway.sportsequipmentrent.utils.SecurityContextFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.message.AuthException;
import javax.validation.Valid;
import java.net.URI;

@AllArgsConstructor
@RestController
@RequestMapping
public class AuthController {

    private final AuthService authService;
    private final ProfileService profileService;
    private final GoogleHttpService googleHttpService;
    private final SecurityContextFacade securityContextFacade;
    protected AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid final LoginRequest loginRequest) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        securityContextFacade.getContext().setAuthentication(authentication);

        final UserDetailsDto userDetailsDto = (UserDetailsDto) authentication.getPrincipal();
        final LoginResponse loginResponse = authService.login(userDetailsDto);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @GetMapping("/login/google")
    public ResponseEntity<HttpHeaders> loginWithGoogle() {
        final HttpHeaders headers = new HttpHeaders();
        final String authorizationEndpointUrl = googleHttpService.buildAuthorizationEndpointUrl();
        headers.setLocation(URI.create(authorizationEndpointUrl));
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @GetMapping("/login/auth/google")
    public ResponseEntity<LoginResponse> authGoogleResponse(@RequestParam final String code) {
        final String accessToken = googleHttpService.getAccessToken(code);
        final GoogleUserInfoResponse googleUserInfoResponse = googleHttpService.getUserInfo(accessToken);
        final String firstName = googleUserInfoResponse.getGivenName();
        final String lastName = googleUserInfoResponse.getFamilyName();
        final String email = googleUserInfoResponse.getEmail();

        if (profileService.existsByProfileEmail(email)) {
            final UserDetailsDto userDetailsDto = profileService.getProfileByEmail(email);
            final LoginResponse loginResponse = authService.login(userDetailsDto);
            return new ResponseEntity<>(loginResponse, HttpStatus.OK);
        } else {
            final ProfileCreateDto profileCreateDto = ProfileCreateDto.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .login(email)
                    .email(email)
                    .build();
            profileService.createProfile(profileCreateDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    @PostMapping("/newAccessToken")
    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody @Valid final JwtRequest jwtRequest) throws AuthException {
        final JwtResponse jwtResponse = authService.getNewAccessToken(jwtRequest.getRefreshToken());
        return new ResponseEntity<>(jwtResponse, HttpStatus.CREATED);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        final String email = (String) securityContextFacade.getContext().getAuthentication().getPrincipal();
        authService.logout(email);
        return new ResponseEntity<>("Logged out successfully!", HttpStatus.OK);
    }

}

