package com.doyouknowdeway.sportsequipmentrent.rest;

import com.doyouknowdeway.sportsequipmentrent.model.dto.UserDetailsDto;
import com.doyouknowdeway.sportsequipmentrent.model.request.JwtRequest;
import com.doyouknowdeway.sportsequipmentrent.model.request.LoginRequest;
import com.doyouknowdeway.sportsequipmentrent.model.response.JwtResponse;
import com.doyouknowdeway.sportsequipmentrent.model.response.LoginResponse;
import com.doyouknowdeway.sportsequipmentrent.service.AuthService;
import com.doyouknowdeway.sportsequipmentrent.utils.SecurityContextFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.message.AuthException;

@RestController
@RequestMapping
public class AuthController {

    private final AuthService authService;
    private final SecurityContextFacade securityContextFacade;
    protected AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(final AuthService authService, final SecurityContextFacade securityContextFacade,
                              final AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.securityContextFacade = securityContextFacade;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody final LoginRequest loginRequest) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword()));
        securityContextFacade.getContext().setAuthentication(authentication);

        final UserDetailsDto userDetailsDto = (UserDetailsDto) authentication.getPrincipal();
        final LoginResponse loginResponse = authService.login(userDetailsDto);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/newAccessToken")
    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody final JwtRequest jwtRequest) throws AuthException {
        final JwtResponse jwtResponse = authService.getNewAccessToken(jwtRequest.getRefreshToken());
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/newRefreshToken")
    public ResponseEntity<JwtResponse> getNewRefreshToken(@RequestBody final JwtRequest jwtRequest) throws AuthException {
        final JwtResponse jwtResponse = authService.getNewRefreshToken(jwtRequest.getRefreshToken());
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        final String login = (String) securityContextFacade.getContext().getAuthentication().getPrincipal();
        authService.logout(login);
        return ResponseEntity.ok("Logged out successfully!!!");
    }

}

