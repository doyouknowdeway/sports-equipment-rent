package com.doyouknowdeway.sportsequipmentrent.repository;

import com.doyouknowdeway.sportsequipmentrent.model.entity.JwtTokenEntity;
import com.doyouknowdeway.sportsequipmentrent.model.entity.JwtTokenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JwtTokenRepository extends JpaRepository<JwtTokenEntity, Integer> {

    void deleteByProfileEmailAndType(String email, JwtTokenType type);

    Optional<JwtTokenEntity> findByProfileEmailAndType(String email, JwtTokenType type);

    boolean existsByToken(String token);

    boolean existsByProfileEmailAndType(String email, JwtTokenType type);

}
