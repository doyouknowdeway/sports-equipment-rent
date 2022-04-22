package com.doyouknowdeway.sportsequipmentrent.mapper;

import com.doyouknowdeway.sportsequipmentrent.model.dto.ProfileDto;
import com.doyouknowdeway.sportsequipmentrent.model.dto.UserDetailsDto;
import com.doyouknowdeway.sportsequipmentrent.model.dto.create_dto.ProfileCreateDto;
import com.doyouknowdeway.sportsequipmentrent.model.entity.ProfileEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    ProfileEntity profileCreateDtoToProfileEntity(ProfileCreateDto dto);

    ProfileDto profileEntityToProfileDto(ProfileEntity entity);

    UserDetailsDto profileEntityToUserDetailsDto(ProfileEntity entity);

    void merge(@MappingTarget ProfileEntity entity, ProfileCreateDto dto);

    List<ProfileDto> fromEntities(Iterable<ProfileEntity> entities);

}
