package com.doyouknowdeway.sportsequipmentrent.mapper;

import com.doyouknowdeway.sportsequipmentrent.model.dto.JwtTokenDto;
import com.doyouknowdeway.sportsequipmentrent.model.entity.JwtTokenEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface JwtTokenMapper {

    JwtTokenEntity jwtTokenDtoToJwtTokenEntity(JwtTokenDto dto);

    void mergeJwtTokenEntityAndJwtTokenDto(@MappingTarget JwtTokenEntity entity, JwtTokenDto dto);

}
