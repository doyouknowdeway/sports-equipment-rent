package com.doyouknowdeway.sportsequipmentrent.mapper;

import com.doyouknowdeway.sportsequipmentrent.model.dto.ProfileDto;
import com.doyouknowdeway.sportsequipmentrent.model.dto.UserDetailsDto;
import com.doyouknowdeway.sportsequipmentrent.model.dto.create_dto.ProfileCreateDto;
import com.doyouknowdeway.sportsequipmentrent.model.entity.ProfileEntity;
import com.doyouknowdeway.sportsequipmentrent.model.entity.enums.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    ProfileEntity profileCreateDtoToProfileEntity(ProfileCreateDto dto);

    ProfileDto profileEntityToProfileDto(ProfileEntity entity);

    @Mapping(target = "roles", source = "role", qualifiedByName = "role")
    UserDetailsDto profileEntityToUserDetailsDto(ProfileEntity entity);

    @Named(value = "role")
    default List<Role> mapRoles(final Role role) {
        return List.of(role);
    }

    void merge(@MappingTarget ProfileEntity entity, ProfileCreateDto dto);

    List<ProfileDto> fromEntities(Iterable<ProfileEntity> entities);

}
