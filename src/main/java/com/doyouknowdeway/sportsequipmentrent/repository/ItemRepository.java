package com.doyouknowdeway.sportsequipmentrent.repository;

import com.doyouknowdeway.sportsequipmentrent.model.entity.ItemEntity;
import com.doyouknowdeway.sportsequipmentrent.model.entity.enums.Age;
import com.doyouknowdeway.sportsequipmentrent.model.entity.enums.Season;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Integer>, JpaSpecificationExecutor<ItemEntity> {

    @Override
    List<ItemEntity> findAll(Specification<ItemEntity> specification);

    static Specification<ItemEntity> hasItemName(String itemName) {
        return (root, query, criteriaBuilder) -> {
            final Predicate itemNamePredicate = Strings.isBlank(itemName) ? null :
                    criteriaBuilder.like(root.get("name"), "%" + itemName + "%");

            final Predicate[] objects = Stream.of(itemNamePredicate)
                    .filter(Objects::nonNull)
                    .toArray(Predicate[]::new);

            return criteriaBuilder.and(objects);
        };
    }

    static Specification<ItemEntity> hasSeasonAndAge(String season, String age) {
        return (root, query, criteriaBuilder) -> {
            final Predicate seasonPredicate = Strings.isBlank(season) ? null :
                    criteriaBuilder.equal(root.get("season"), Season.valueOf(season));
            final Predicate agePredicate = Strings.isBlank(age) ? null :
                    criteriaBuilder.equal(root.get("age"), Age.valueOf(age));

            final Predicate[] objects = Stream.of(seasonPredicate, agePredicate)
                    .filter(Objects::nonNull)
                    .toArray(Predicate[]::new);

            return criteriaBuilder.and(objects);
        };
    }

}
