package com.doyouknowdeway.sportsequipmentrent.mapper;

import com.doyouknowdeway.sportsequipmentrent.model.entity.ProfileEntity;
import com.doyouknowdeway.sportsequipmentrent.model.dto.ProfileDto;
import com.doyouknowdeway.sportsequipmentrent.model.dto.create_dto.ProfileCreateDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    ProfileDto fromEntity(ProfileEntity entity);

    ProfileEntity toEntity(ProfileDto dto);

    ProfileEntity toEntity(ProfileCreateDto dto);

    void merge(ProfileCreateDto dto, @MappingTarget ProfileEntity entity);

    List<ProfileDto> fromEntities(Iterable<ProfileEntity> entities);

}
