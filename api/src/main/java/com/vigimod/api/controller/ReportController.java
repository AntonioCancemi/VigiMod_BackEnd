package com.vigimod.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vigimod.api.entity.Report;
import com.vigimod.api.service.ReportService;

@RestController
@RequestMapping("/api/report")
class ReportController {

    @Autowired
    ReportService service;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getByID(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Report item) {
        return ResponseEntity.ok(service.create(item));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,
            @RequestBody Report item) {
        return ResponseEntity.ok(service.update(id, item));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.remove(id));
    }

    @GetMapping("/list")
    public ResponseEntity<List<Report>> getAdsByFilter(
            @RequestParam(required = false) String filter,
            @RequestParam(required = false) String key) {
        return ResponseEntity.ok(service.getReportsByFilter(filter, key));
    }

}