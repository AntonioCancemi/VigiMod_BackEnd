package com.vigimod.api.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vigimod.api.entity.Ad;
import com.vigimod.api.entity.Report;
import com.vigimod.api.repository.ReportDaoRepo;
import com.vigimod.api.utils.AdStatus;

import jakarta.persistence.EntityExistsException;

@Service
public class ReportService {
    @Autowired
    ReportDaoRepo repo;

    public List<Report> getAll() {
        if (repo.findAll().isEmpty()) {
            throw new EntityExistsException("NO Report FOUND!!!");
        }
        return repo.findAll();
    }

    public Report getByID(Long id) {
        if (!repo.existsById(id)) {
            throw new EntityExistsException("Report NOT FOUND!!!");
        }
        return repo.findById(id).get();
    }

    public Report create(Report r) {
        r.setCreatedAt(LocalDateTime.now());
        return repo.save(r);
    }

    public Report update(Long id, Report r) {
        // logica per il versioning
        if (!repo.existsById(id)) {
            throw new EntityExistsException("Report ID NOT FOUND!!!");
        }

        return repo.save(r);
    }

    public String remove(Long id) {
        if (!repo.existsById(id)) {
            throw new EntityExistsException("Report ID NOT FOUND!!!");
        }
        repo.deleteById(id);
        return "Report eliminato!!!";
    }

    public List<Report> getReportsByFilter(String filter, String key) {
        if (repo.findAll().isEmpty()) {
            throw new EntityExistsException("NO Report FOUND!!!");
        }
        switch (filter) {
            case "title":
                return repo.findByAdProductTitleContaining(key);
            case "category":
                return repo.findByAdProductCategory(key);
            case "date":
                return repo.findByAdPublicationDateAfter(LocalDateTime.parse(key));
            case "brand":
                return repo.findByAdProductBrand(key);
            case "ad":
                return repo.findByAdId(Long.parseLong(key));
            case "product":
                return repo.findByAdProductId(Long.parseLong(key));
            case "status":
                return repo.findByAdAdStatus(AdStatus.valueOf(key.toUpperCase()));
            default:
                return repo.findAll();
        }
    }
}