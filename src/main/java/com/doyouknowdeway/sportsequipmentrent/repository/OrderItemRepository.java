package com.doyouknowdeway.sportsequipmentrent.repository;

import com.doyouknowdeway.sportsequipmentrent.model.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Integer>, JpaSpecificationExecutor<OrderItemEntity> {
}
