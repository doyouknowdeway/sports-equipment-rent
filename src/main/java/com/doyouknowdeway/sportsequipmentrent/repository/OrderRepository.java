package com.doyouknowdeway.sportsequipmentrent.repository;

import com.doyouknowdeway.sportsequipmentrent.model.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Integer>, JpaSpecificationExecutor<OrderEntity> {

    List<OrderEntity> findAllByProfileId(Integer profileId);

}
