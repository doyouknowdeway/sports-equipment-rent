package com.doyouknowdeway.sportsequipmentrent.service;

import com.doyouknowdeway.sportsequipmentrent.mapper.ProfileMapperImpl;
import com.doyouknowdeway.sportsequipmentrent.model.dto.UserDetailsDto;
import com.doyouknowdeway.sportsequipmentrent.model.entity.ProfileEntity;
import com.doyouknowdeway.sportsequipmentrent.model.entity.Role;
import com.doyouknowdeway.sportsequipmentrent.repository.ProfileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceTest {

    @Spy
    private ProfileMapperImpl profileMapperImpl;

    @Spy
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private ProfileRepository profileRepositoryMock;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsServiceImpl;

    private final String email = "email@email.ru";

    @Test
    public void loadUserByUsername() {
        // given
        final String password = "45";
        final String encodePassword = bCryptPasswordEncoder.encode(password);

        final int id = 1;
        final String login = "login";
        final String firstName = "Alex";
        final String lastName = "Smirnov";
        final ProfileEntity profileEntity = ProfileEntity.builder()
                .id(id)
                .login(login)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(encodePassword)
                .role(Role.USER)
                .build();
        final UserDetailsDto userDetailsDtoExpected = UserDetailsDto.builder()
                .email(email)
                .password(password)
                .build();

        doReturn(userDetailsDtoExpected).when(profileMapperImpl).profileEntityToUserDetailsDto(profileEntity);
        when(profileRepositoryMock.findByEmail(userDetailsDtoExpected.getEmail())).thenReturn(Optional.of(profileEntity));

        // when
        final UserDetails userDetailsActual = userDetailsServiceImpl.loadUserByUsername(userDetailsDtoExpected.getEmail());

        // then
        verify(profileRepositoryMock, times(1)).findByEmail(userDetailsDtoExpected.getEmail());
        assertEquals(userDetailsDtoExpected, userDetailsActual);
    }

    @Test
    public void loadUserByUsernameWhenUserNotFound() {
        // given
        final String password = "45";
        final UserDetailsDto userDetailsDtoExpected = UserDetailsDto.builder()
                .email(email)
                .password(password)
                .build();
        when(profileRepositoryMock.findByEmail(userDetailsDtoExpected.getEmail())).thenReturn(Optional.empty());

        // when then
        final var actualException = assertThrows(UsernameNotFoundException.class,
                () -> userDetailsServiceImpl.loadUserByUsername(userDetailsDtoExpected.getEmail()));
        verify(profileRepositoryMock, times(1)).findByEmail(userDetailsDtoExpected.getEmail());
        assertEquals(actualException.getMessage(), "No such user in the database!");
    }

}

