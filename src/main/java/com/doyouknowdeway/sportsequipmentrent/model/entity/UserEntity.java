package com.doyouknowdeway.sportsequipmentrent.model.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "users")
@Entity(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Integer id;

    @Column(name = "role_id")
    protected Role role;

    @Column(name = "provider")
    @Enumerated(EnumType.STRING)
    protected Provider provider;

    @Column(name = "registration_datetime")
    protected Instant registrationDatetime;

}
