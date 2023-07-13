package com.vigimod.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vigimod.api.entity.Product;

import java.util.List;

@Repository
public interface ProductDaoRepo extends JpaRepository<Product, Long> {
    List<Product> findByTitleAndDescriptionAndBrandAndCategoryAndPrice(String title, String description, String brand,
            String category, double price);
}
