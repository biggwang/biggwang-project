package com.biggwang.web.repository;

import com.biggwang.web.entity.ProductEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<ProductEntity, Integer> {

    List<ProductEntity> findAllByStartedAtLessThanEqualAndFinishedAtGreaterThanEqual(LocalDateTime now1, LocalDateTime now2);
}
