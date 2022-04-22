package com.doyouknowdeway.sportsequipmentrent.model.entity;

import com.doyouknowdeway.sportsequipmentrent.model.entity.enums.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@Table(name = "users", schema = "sportsequipmentrent")
@Entity(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "role_id")
    private Role role;

    @Column(name = "registration_datetime")
    private Instant registrationDatetime;

}
