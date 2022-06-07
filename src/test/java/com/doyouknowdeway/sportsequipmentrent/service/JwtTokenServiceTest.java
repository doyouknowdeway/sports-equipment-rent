package com.doyouknowdeway.sportsequipmentrent.service;

import com.doyouknowdeway.sportsequipmentrent.exception.EntityCreationException;
import com.doyouknowdeway.sportsequipmentrent.exception.EntityNotFoundException;
import com.doyouknowdeway.sportsequipmentrent.mapper.JwtTokenMapperImpl;
import com.doyouknowdeway.sportsequipmentrent.model.dto.JwtTokenDto;
import com.doyouknowdeway.sportsequipmentrent.model.entity.JwtTokenEntity;
import com.doyouknowdeway.sportsequipmentrent.model.entity.JwtTokenType;
import com.doyouknowdeway.sportsequipmentrent.model.entity.ProfileEntity;
import com.doyouknowdeway.sportsequipmentrent.repository.JwtTokenRepository;
import com.doyouknowdeway.sportsequipmentrent.repository.ProfileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JwtTokenServiceTest {

    @Spy
    private JwtTokenMapperImpl jwtTokenMapperImpl;

    @Mock
    private ProfileRepository profileRepositoryMock;

    @Mock
    private JwtTokenRepository jwtTokenRepositoryMock;

    @InjectMocks
    private JwtTokenServiceImpl jwtTokenServiceImpl;

    private final int id = 1;
    private final String firstName = "Alex";
    private final String lastName = "Smirnov";
    private final String email = "email@email.com";

    private final ProfileEntity profileEntity = ProfileEntity.builder()
            .firstName(firstName)
            .lastName(lastName)
            .email(email)
            .build();

    private final String accessToken = "access";
    private final String newAccessToken = "newAccess";

    private final JwtTokenEntity jwtTokenEntity = JwtTokenEntity.builder()
            .id(id)
            .token(accessToken)
            .type(JwtTokenType.ACCESS)
            .profile(profileEntity)
            .build();

    private final JwtTokenEntity newJwtTokenEntity = JwtTokenEntity.builder()
            .id(id)
            .token(newAccessToken)
            .type(JwtTokenType.ACCESS)
            .profile(profileEntity)
            .build();

    private final JwtTokenDto jwtTokenDto = JwtTokenDto.builder()
            .token(accessToken)
            .type(JwtTokenType.ACCESS)
            .email(email)
            .build();

    private final JwtTokenDto newJwtTokenDto = JwtTokenDto.builder()
            .token(newAccessToken)
            .type(JwtTokenType.ACCESS)
            .email(email)
            .build();

    @Test
    public void createJwtToken() {
        // given
        when(profileRepositoryMock.findByEmail(jwtTokenDto.getEmail())).thenReturn(Optional.ofNullable(profileEntity));
        when(jwtTokenMapperImpl.jwtTokenDtoToJwtTokenEntity(jwtTokenDto)).thenReturn(jwtTokenEntity);
        when(jwtTokenRepositoryMock.save(jwtTokenEntity)).thenReturn(jwtTokenEntity);

        // when
        jwtTokenServiceImpl.createJwtToken(jwtTokenDto);

        // then
        verify(jwtTokenRepositoryMock, times(1)).save(jwtTokenEntity);
    }

    @Test
    public void createJwtTokenWhenUserDoesNotExist() {
        // given
        when(profileRepositoryMock.findByEmail(jwtTokenDto.getEmail())).thenReturn(Optional.empty());

        // when then
        final Exception actualException = assertThrows(EntityCreationException.class,
                () -> jwtTokenServiceImpl.createJwtToken(jwtTokenDto));
        verify(jwtTokenRepositoryMock, never()).save(jwtTokenEntity);
        assertEquals(actualException.getMessage(), "Token not created!");
    }

    @Test
    public void createJwtTokenWhenJwtTokenIsNull() {
        // given
        when(profileRepositoryMock.findByEmail(jwtTokenDto.getEmail())).thenReturn(Optional.ofNullable(profileEntity));
        when(jwtTokenMapperImpl.jwtTokenDtoToJwtTokenEntity(jwtTokenDto)).thenReturn(jwtTokenEntity);
        when(jwtTokenRepositoryMock.save(jwtTokenEntity)).thenReturn(null);

        // when then
        final Exception actualException = assertThrows(EntityCreationException.class,
                () -> jwtTokenServiceImpl.createJwtToken(jwtTokenDto));
        verify(jwtTokenRepositoryMock, times(1)).save(jwtTokenEntity);
        assertEquals(actualException.getMessage(), "Token not created!");
    }

    @Test
    public void getJwtTokenByEmailAndType() {
        // given
        when(jwtTokenRepositoryMock.findByProfileEmailAndType(email, JwtTokenType.ACCESS)).thenReturn(Optional.of(jwtTokenEntity));

        // when
        final String tokenActual = jwtTokenServiceImpl.getJwtTokenByEmailAndType(email, JwtTokenType.ACCESS);

        // then
        verify(jwtTokenRepositoryMock, times(1)).findByProfileEmailAndType(email, JwtTokenType.ACCESS);
        assertEquals(accessToken, tokenActual);
    }

    @Test
    public void getJwtTokenByEmailAndTypeWhenJwtTokenDoesNotExist() {
        // given
        when(jwtTokenRepositoryMock.findByProfileEmailAndType(email, JwtTokenType.ACCESS)).thenReturn(Optional.empty());

        // when then
        final Exception actualException = assertThrows(EntityNotFoundException.class,
                () -> jwtTokenServiceImpl.getJwtTokenByEmailAndType(email, JwtTokenType.ACCESS));
        verify(jwtTokenRepositoryMock, times(1)).findByProfileEmailAndType(email, JwtTokenType.ACCESS);
        assertEquals(actualException.getMessage(), "Token not found!");
    }

    @Test
    public void existsByToken() {
        // given
        when(jwtTokenRepositoryMock.existsByToken(accessToken)).thenReturn(true);

        // when
        final boolean tokenExists = jwtTokenServiceImpl.existsByToken(accessToken);

        // then
        verify(jwtTokenRepositoryMock, times(1)).existsByToken(accessToken);
        assertTrue(tokenExists);
    }

    @Test
    public void notExistsByToken() {
        // given
        when(jwtTokenRepositoryMock.existsByToken(accessToken)).thenReturn(false);

        // when
        final boolean tokenExists = jwtTokenServiceImpl.existsByToken(accessToken);

        // then
        verify(jwtTokenRepositoryMock, times(1)).existsByToken(accessToken);
        assertFalse(tokenExists);
    }

    @Test
    public void existsByUserEmail() {
        // given
        when(jwtTokenRepositoryMock.existsByProfileEmailAndType(email, JwtTokenType.ACCESS)).thenReturn(true);
        when(jwtTokenRepositoryMock.existsByProfileEmailAndType(email, JwtTokenType.REFRESH)).thenReturn(true);

        // when
        final boolean tokenExists = jwtTokenServiceImpl.existsByUserEmail(email);

        // then
        verify(jwtTokenRepositoryMock, times(1)).existsByProfileEmailAndType(email, JwtTokenType.ACCESS);
        verify(jwtTokenRepositoryMock, times(1)).existsByProfileEmailAndType(email, JwtTokenType.REFRESH);
        assertTrue(tokenExists);
    }

    @Test
    public void existsByUserEmailWhenAccessDoesNotExist() {
        // given
        when(jwtTokenRepositoryMock.existsByProfileEmailAndType(email, JwtTokenType.ACCESS)).thenReturn(false);

        // when
        final boolean tokenExists = jwtTokenServiceImpl.existsByUserEmail(email);

        // then
        verify(jwtTokenRepositoryMock, times(1)).existsByProfileEmailAndType(email, JwtTokenType.ACCESS);
        verify(jwtTokenRepositoryMock, never()).existsByProfileEmailAndType(email, JwtTokenType.REFRESH);
        assertFalse(tokenExists);
    }

    @Test
    public void existsByUserEmailWhenRefreshDoesNotExist() {
        // given
        when(jwtTokenRepositoryMock.existsByProfileEmailAndType(email, JwtTokenType.ACCESS)).thenReturn(true);
        when(jwtTokenRepositoryMock.existsByProfileEmailAndType(email, JwtTokenType.REFRESH)).thenReturn(false);

        // when
        final boolean tokenExists = jwtTokenServiceImpl.existsByUserEmail(email);

        // then
        verify(jwtTokenRepositoryMock, times(1)).existsByProfileEmailAndType(email, JwtTokenType.ACCESS);
        verify(jwtTokenRepositoryMock, times(1)).existsByProfileEmailAndType(email, JwtTokenType.REFRESH);
        assertFalse(tokenExists);
    }

    @Test
    public void updateJwtToken() {
        // given
        when(jwtTokenRepositoryMock.findByProfileEmailAndType(email, JwtTokenType.ACCESS)).thenReturn(Optional.ofNullable(jwtTokenEntity));
        when(jwtTokenRepositoryMock.save(newJwtTokenEntity)).thenReturn(newJwtTokenEntity);

        // when
        jwtTokenServiceImpl.updateJwtToken(newJwtTokenDto);

        // then
        verify(jwtTokenRepositoryMock, times(1)).save(newJwtTokenEntity);
    }

    @Test
    public void updateJwtTokenWhenJwtTokenDoesNotExist() {
        // given
        when(jwtTokenRepositoryMock.findByProfileEmailAndType(email, JwtTokenType.ACCESS)).thenReturn(Optional.empty());

        // when then
        final Exception actualException = assertThrows(EntityCreationException.class,
                () -> jwtTokenServiceImpl.updateJwtToken(newJwtTokenDto));
        verify(jwtTokenRepositoryMock, never()).save(newJwtTokenEntity);
        assertEquals(actualException.getMessage(), "New access token hasn't been created!");
    }

    @Test
    public void deleteJwtTokensByEmail() {
        // given
        when(jwtTokenRepositoryMock.existsByProfileEmailAndType(email, JwtTokenType.ACCESS)).thenReturn(true);
        when(jwtTokenRepositoryMock.existsByProfileEmailAndType(email, JwtTokenType.REFRESH)).thenReturn(true);

        // when
        jwtTokenServiceImpl.deleteJwtTokensByEmail(email);

        // then
        verify(jwtTokenRepositoryMock, times(1)).deleteByProfileEmailAndType(email, JwtTokenType.ACCESS);
        verify(jwtTokenRepositoryMock, times(1)).deleteByProfileEmailAndType(email, JwtTokenType.REFRESH);
    }

    @Test
    public void deleteJwtTokensByEmailWhenAccessDoesNotExist() {
        // given
        when(jwtTokenRepositoryMock.existsByProfileEmailAndType(email, JwtTokenType.ACCESS)).thenReturn(false);

        // when then
        final Exception actualException = assertThrows(EntityNotFoundException.class,
                () -> jwtTokenServiceImpl.deleteJwtTokensByEmail(email));
        verify(jwtTokenRepositoryMock, never()).deleteByProfileEmailAndType(email, JwtTokenType.ACCESS);
        verify(jwtTokenRepositoryMock, never()).deleteByProfileEmailAndType(email, JwtTokenType.REFRESH);
        assertEquals(actualException.getMessage(), "Tokens not found!");
    }

    @Test
    public void deleteJwtTokensByEmailWhenRefreshDoesNotExist() {
        // given
        when(jwtTokenRepositoryMock.existsByProfileEmailAndType(email, JwtTokenType.ACCESS)).thenReturn(true);
        when(jwtTokenRepositoryMock.existsByProfileEmailAndType(email, JwtTokenType.REFRESH)).thenReturn(false);

        // when then
        final Exception actualException = assertThrows(EntityNotFoundException.class,
                () -> jwtTokenServiceImpl.deleteJwtTokensByEmail(jwtTokenDto.getEmail()));
        verify(jwtTokenRepositoryMock, never()).deleteByProfileEmailAndType(email, JwtTokenType.ACCESS);
        verify(jwtTokenRepositoryMock, never()).deleteByProfileEmailAndType(email, JwtTokenType.REFRESH);
        assertEquals(actualException.getMessage(), "Tokens not found!");
    }

}

