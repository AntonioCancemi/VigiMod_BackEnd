package com.vigimod.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vigimod.api.entity.Image;
import java.util.List;

@Repository
public interface ImageDaoRepo extends JpaRepository<Image, Long> {
    List<Image> findByProductId(Long productId);
}