package com.vigimod.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vigimod.api.entity.Ad;
import com.vigimod.api.service.AdService;

@RestController

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/ad")
class AdController {

    @Autowired
    AdService service;

    @GetMapping
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<Ad>> getAdsByFilter(
            @RequestParam(required = false) String filter,
            @RequestParam(required = false) String key) {
        return ResponseEntity.ok(service.getAdsByFilter(filter, key));
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getByID(id));
    }

    @PostMapping
    @PreAuthorize(" hasRole('ADMIN')")
    public ResponseEntity<?> create(@RequestBody Ad item) {
        return ResponseEntity.ok(service.create(item));
    }

    @PutMapping("{id}")
    @PreAuthorize(" hasRole('ADMIN')")
    public ResponseEntity<?> update(@PathVariable("id") Long id,
            @RequestBody Ad item) {
        return ResponseEntity.ok(service.update(id, item));
    }

    @DeleteMapping("{id}")
    @PreAuthorize(" hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.remove(id));
    }
}