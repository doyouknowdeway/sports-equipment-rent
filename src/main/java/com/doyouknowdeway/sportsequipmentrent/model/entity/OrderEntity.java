package com.doyouknowdeway.sportsequipmentrent.model.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "orders")
@Entity(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "status_id")
    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private ProfileEntity profile;

    @Column(name = "order_datetime")
    private Instant orderDatetime;

    @Column(name = "destination_datetime")
    private Instant destinationDatetime;

    @Column(name = "expire_datetime")
    private Instant expireDatetime;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

}
