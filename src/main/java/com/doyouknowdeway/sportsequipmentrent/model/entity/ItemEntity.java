package com.doyouknowdeway.sportsequipmentrent.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Table(name = "items")
@Entity(name = "items")
public class ItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "cost_per_hour")
    private BigDecimal costPerHour;

    @Column(name = "age_id")
    private Age age;

    @Column(name = "season_id")
    private Season season;

    @Column(name = "count")
    private Integer count;

    @Column(name = "image")
    private byte[] image;

}
