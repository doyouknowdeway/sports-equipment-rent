package com.doyouknowdeway.sportsequipmentrent.service;

import com.doyouknowdeway.sportsequipmentrent.exception.EntityNotFoundException;
import com.doyouknowdeway.sportsequipmentrent.mapper.OrderMapperImpl;
import com.doyouknowdeway.sportsequipmentrent.mapper.ProfileMapperImpl;
import com.doyouknowdeway.sportsequipmentrent.model.dto.ProfileCreateDto;
import com.doyouknowdeway.sportsequipmentrent.model.dto.ProfileDto;
import com.doyouknowdeway.sportsequipmentrent.model.dto.UserDetailsDto;
import com.doyouknowdeway.sportsequipmentrent.model.entity.OrderEntity;
import com.doyouknowdeway.sportsequipmentrent.model.entity.OrderStatus;
import com.doyouknowdeway.sportsequipmentrent.model.entity.ProfileEntity;
import com.doyouknowdeway.sportsequipmentrent.model.entity.Role;
import com.doyouknowdeway.sportsequipmentrent.repository.OrderRepository;
import com.doyouknowdeway.sportsequipmentrent.repository.ProfileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProfileServiceTest {

    @Spy
    private OrderMapperImpl orderMapperImpl;

    @Spy
    private ProfileMapperImpl profileMapperImpl;

    @Spy
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private OrderRepository orderRepositoryMock;

    @Mock
    private ProfileRepository profileRepositoryMock;

    @InjectMocks
    private ProfileServiceImpl profileServiceImpl;

    private final int id = 1;
    private final String login = "login";
    private final String firstName = "Alex";
    private final String lastName = "Smirnov";
    private final String email = "email@email.ru";
    private final String password = "45";

    private final ProfileEntity profileEntity = ProfileEntity.builder()
            .id(id)
            .login(login)
            .firstName(firstName)
            .lastName(lastName)
            .email(email)
            .password(password)
            .role(Role.USER)
            .build();

    private final ProfileCreateDto profileCreateDto = ProfileCreateDto.builder()
            .login(login)
            .firstName(firstName)
            .lastName(lastName)
            .email(email)
            .password(password)
            .build();

    private final ProfileDto profileDto = ProfileDto.builder()
            .id(id)
            .login(login)
            .firstName(firstName)
            .lastName(lastName)
            .email(email)
            .role(Role.USER)
            .build();

    final UserDetailsDto userDetailsDto = UserDetailsDto.builder()
            .id(id)
            .email(email)
            .password(password)
            .roles(List.of(Role.USER))
            .build();

    private final OrderEntity orderEntity = OrderEntity.builder()
            .orderStatus(OrderStatus.NEW)
            .profile(profileEntity)
            .orderDatetime(Instant.now())
            .destinationDatetime(Instant.now())
            .expireDatetime(Instant.now())
            .build();

    @Test
    public void createItem() {
        // given
        when(profileMapperImpl.profileCreateDtoToProfileEntity(profileCreateDto)).thenReturn(profileEntity);
        when(profileRepositoryMock.save(profileEntity)).thenReturn(profileEntity);
        final String encodePassword = bCryptPasswordEncoder.encode(password);

        // when
        profileServiceImpl.createProfile(profileCreateDto);

        // then
        verify(profileRepositoryMock, times(1)).save(profileEntity);
    }

    @Test
    public void updateProfile() {
        // given
        when(profileRepositoryMock.findById(id)).thenReturn(Optional.of(profileEntity));
        doNothing().when(profileMapperImpl).mergeProfileEntityToProfileCreateDto(profileEntity, profileCreateDto);
        when(profileRepositoryMock.save(profileEntity)).thenReturn(profileEntity);

        // when
        profileServiceImpl.updateProfile(profileCreateDto, id);

        // then
        verify(profileRepositoryMock, times(1)).save(profileEntity);
    }

    @Test
    public void updateProfileWhenOptionalNull() {
        // given
        when(profileRepositoryMock.findById(id)).thenReturn(Optional.empty());

        // when then
        final Exception actualException = assertThrows(EntityNotFoundException.class,
                () -> profileServiceImpl.updateProfile(profileCreateDto, id));
        verify(profileRepositoryMock, never()).save(profileEntity);
        assertEquals(actualException.getMessage(), "Profile not found!");
    }

    @Test
    public void getProfileById() {
        // given
        when(profileRepositoryMock.findById(id)).thenReturn(Optional.of(profileEntity));
        when(profileMapperImpl.profileEntityToProfileDto(profileEntity)).thenReturn(profileDto);

        // when
        final ProfileDto profileDtoActual = profileServiceImpl.getProfileById(id);

        // then
        verify(profileRepositoryMock, times(1)).findById(id);
        assertEquals(profileDto, profileDtoActual);
    }

    @Test
    public void getProfileByIdWhenProfileDoesNotExist() {
        // given
        when(profileRepositoryMock.findById(id)).thenReturn(Optional.empty());

        // when then
        final Exception actualException = assertThrows(EntityNotFoundException.class,
                () -> profileServiceImpl.getProfileById(id));
        verify(profileRepositoryMock, times(1)).findById(id);
        assertEquals(actualException.getMessage(), "Profile not found!");
    }

    @Test
    public void getProfileByEmail() {
        // given
        when(profileRepositoryMock.findByEmail(email)).thenReturn(Optional.of(profileEntity));

        // when
        final UserDetailsDto userDetailsDtoActual = profileServiceImpl.getProfileByEmail(email);

        // then
        verify(profileRepositoryMock, times(1)).findByEmail(email);
        assertEquals(userDetailsDto, userDetailsDtoActual);
    }

    @Test
    public void getProfileByEmailWhenProfileDoesNotExist() {
        // given
        when(profileRepositoryMock.findByEmail(email)).thenReturn(Optional.empty());

        // when then
        final Exception actualException = assertThrows(EntityNotFoundException.class,
                () -> profileServiceImpl.getProfileByEmail(email));
        verify(profileRepositoryMock, times(1)).findByEmail(email);
        assertEquals(actualException.getMessage(), "Profile not found!");
    }

    @Test
    public void existsByProfileEmail() {
        // given
        when(profileRepositoryMock.existsByEmail(email)).thenReturn(true);

        // when
        final boolean existsByProfileEmailActual = profileServiceImpl.existsByProfileEmail(email);

        // then
        verify(profileRepositoryMock, times(1)).existsByEmail(email);
        assertTrue(existsByProfileEmailActual);
    }

    @Test
    public void doestNotExistByProfileEmail() {
        // given
        when(profileRepositoryMock.existsByEmail(email)).thenReturn(false);

        // when
        final boolean existsByProfileEmailActual = profileServiceImpl.existsByProfileEmail(email);

        // then
        verify(profileRepositoryMock, times(1)).existsByEmail(email);
        assertFalse(existsByProfileEmailActual);
    }

    @Test
    public void deleteProfileById() {
        // given
        when(profileRepositoryMock.findById(id)).thenReturn(Optional.of(profileEntity));

        // when
        profileServiceImpl.deleteProfileById(id);

        // then
        verify(profileRepositoryMock, times(1)).findById(id);
        verify(profileRepositoryMock, times(1)).deleteById(id);
    }

    @Test
    public void deleteProfileByIdWhenProfileDoesNotExist() {
        // given
        when(profileRepositoryMock.findById(id)).thenReturn(Optional.empty());

        // when then
        final Exception actualException = assertThrows(EntityNotFoundException.class,
                () -> profileServiceImpl.deleteProfileById(id));
        verify(profileRepositoryMock, times(1)).findById(id);
        assertEquals(actualException.getMessage(), "Profile not found!");
    }

    @Test
    public void listProfiles() {
        // given
        when(profileRepositoryMock.findAll()).thenReturn(List.of(profileEntity));
        when(profileMapperImpl.profileEntitiesToProfileDtoList(List.of(profileEntity)))
                .thenReturn(List.of(profileDto));

        // when
        profileServiceImpl.listProfiles();

        // then
        verify(profileRepositoryMock, times(1)).findAll();
    }

    @Test
    public void listProfileOrders() {
        // given
        when(profileRepositoryMock.findById(id)).thenReturn(Optional.of(profileEntity));
        when(orderRepositoryMock.findAllByProfileId(id)).thenReturn(List.of(orderEntity));

        // when
        profileServiceImpl.listProfileOrders(id);

        // then
        verify(orderRepositoryMock, times(1)).findAllByProfileId(id);
    }

}
