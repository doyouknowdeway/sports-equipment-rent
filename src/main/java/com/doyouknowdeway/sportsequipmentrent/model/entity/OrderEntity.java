package com.doyouknowdeway.sportsequipmentrent.model.entity;

import com.doyouknowdeway.sportsequipmentrent.model.entity.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Table(name = "orders", schema = "sportsequipmentrent")
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
