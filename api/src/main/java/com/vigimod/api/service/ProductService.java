package com.vigimod.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vigimod.api.entity.Product;
import com.vigimod.api.repository.ProductDaoRepo;

import jakarta.persistence.EntityExistsException;

@Service
public class ProductService {
    @Autowired
    ProductDaoRepo repo;

    public List<Product> getAll() {
        return repo.findAll();
    }

    public Product getByID(Long id) {
        if (!repo.existsById(id)) {
            throw new EntityExistsException("Product NOT FOUND!!!");
        }
        return repo.findById(id).get();
    }

    public Product create(Product p) {
        if (repo.findByTitleAndDescriptionAndBrandAndCategoryAndPrice(p.getTitle(), p.getDescription(), p.getBrand(),
                p.getCategory(), p.getPrice()).isEmpty()) {
            throw new EntityExistsException("Product Already exists!!!");
        }
        return repo.save(p);
    }

    public Product update(Long id, Product p) {
        if (!repo.existsById(id)) {
            throw new EntityExistsException("Product ID NOT FOUND!!!");
        }

        return repo.save(p);
    }

    public String remove(Long id) {
        if (!repo.existsById(id)) {
            throw new EntityExistsException("Product ID NOT FOUND!!!");
        }
        repo.deleteById(id);
        return "Product eliminato!!!";
    }
}