package com.vigimod.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vigimod.api.entity.Image;
import com.vigimod.api.repository.ImageDaoRepo;
import com.vigimod.api.repository.ProductDaoRepo;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/image")
class resourceNameController {

    @Autowired
    ImageDaoRepo repo;
    @Autowired
    ProductDaoRepo productRepo;

    @GetMapping
    public ResponseEntity<List<Image>> getAll() {
        try {
            List<Image> items = new ArrayList<Image>();

            repo.findAll().forEach(items::add);

            if (items.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Image> getById(@PathVariable("id") Long id) {
        Optional<Image> existingItemOptional = repo.findById(id);

        if (existingItemOptional.isPresent()) {
            return new ResponseEntity<>(existingItemOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<List<Image>> getByProduct(@PathVariable("id") Long id) {
        List<Image> existingItemOptional = repo.findByProductId(id);

        if (!existingItemOptional.isEmpty()) {
            return new ResponseEntity<>(existingItemOptional, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Image> create(@RequestBody Image item) {
        try {
            if (!productRepo.existsById(item.getProductId())) {
                throw new EntityNotFoundException("Product id:" + item.getProductId() + " Not Found");
            }
            Image savedItem = repo.save(item);
            return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Image> update(@PathVariable("id") Long id, @RequestBody Image item) {
        Optional<Image> existingItemOptional = repo.findById(id);
        if (existingItemOptional.isPresent()) {
            Image existingItem = existingItemOptional.get();
            System.out
                    .println("TODO for developer - update logic is unique to entity and must be implemented manually.");
            // existingItem.setSomeField(item.getSomeField());
            return new ResponseEntity<>(repo.save(existingItem), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        try {
            repo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
}
