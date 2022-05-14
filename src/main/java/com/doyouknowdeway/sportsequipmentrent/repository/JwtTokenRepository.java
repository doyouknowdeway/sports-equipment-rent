package com.doyouknowdeway.sportsequipmentrent.repository;

import com.doyouknowdeway.sportsequipmentrent.model.entity.JwtTokenEntity;
import com.doyouknowdeway.sportsequipmentrent.model.entity.enums.JwtTokenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JwtTokenRepository extends JpaRepository<JwtTokenEntity, Integer> {

    @Modifying
    @Query("DELETE FROM jwt_tokens " +
            "WHERE profile.id = (SELECT id FROM profiles WHERE login = ?1) AND type = ?2")
    void markAsDeletedByUserLoginAndType(final String login, JwtTokenType type);

    Optional<JwtTokenEntity> findByProfileLoginAndType(String login, JwtTokenType type);

    boolean existsByToken(String token);

}
