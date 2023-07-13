package com.vigimod.api.service;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vigimod.api.entity.Report;
import com.vigimod.api.repository.ReportDaoRepo;
import com.vigimod.api.utils.AdStatus;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

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
                // DB check
                if (repo.findByAdProductTitleContainingIgnoreCase(key).isEmpty()) {
                    throw new EntityNotFoundException("Ad title:[" + key + "] NOT FOUND!!!");
                }
                return repo.findByAdProductTitleContainingIgnoreCase(key);
            case "category":
                if (repo.findByAdProductBrand(key).isEmpty()) {
                    throw new EntityNotFoundException("Report with Ad Product category:[" + key + "] NOT FOUND!!!");
                }
                return repo.findByAdProductCategory(key);
            case "brand":
                // DB check
                if (repo.findByAdProductBrand(key).isEmpty()) {
                    throw new EntityNotFoundException("Report with Ad Product  brand:[" + key + "] NOT FOUND!!!");
                }
                return repo.findByAdProductBrand(key);
            case "seller":
                // key check
                try {
                    Long.parseLong(key);

                } catch (NumberFormatException nfe) {
                    throw new NumberFormatException("Expected number(Long) instead:[" + key + "]");
                }
                // DB check
                if (repo.findByAdProductSellerId(Long.parseLong(key)).isEmpty()) {
                    throw new EntityNotFoundException("Report with SellerId:[" + key + "] NOT FOUND!!!");
                }
                return repo.findByAdProductSellerId(Long.parseLong(key));
            case "date":
                // key check
                try {
                    LocalDateTime.parse(key);
                } catch (DateTimeParseException e) {
                    throw new DateTimeException("parse [" + key + "] to LocalDateTime Faild ");
                }
                // DB check
                if (repo.findByAdPublicationDateAfter(LocalDateTime.parse(key)).isEmpty()) {
                    throw new EntityNotFoundException("Report with Ad after:[" + key + "] NOT FOUND!!!");
                }
                return repo.findByAdPublicationDateAfter(LocalDateTime.parse(key));
            case "ad":
                // key check
                try {
                    Long.parseLong(key);

                } catch (NumberFormatException nfe) {
                    throw new NumberFormatException("Expected number(Long) instead:[" + key + "]");
                }
                // DB check
                if (repo.findByAdId(Long.parseLong(key)).isEmpty()) {
                    throw new EntityNotFoundException("Report with AdId:[" + key + "] NOT FOUND!!!");
                }
                return repo.findByAdId(Long.parseLong(key));
            case "product":
                // key check
                try {
                    Long.parseLong(key);

                } catch (NumberFormatException nfe) {
                    throw new NumberFormatException("Expected number(Long) instead:[" + key + "]");
                }
                // DB check
                if (repo.findByAdProductId(Long.parseLong(key)).isEmpty()) {
                    throw new EntityNotFoundException("Report with Ad Product Id:[" + key + "] NOT FOUND!!!");
                }
                return repo.findByAdProductId(Long.parseLong(key));
            case "status":
                // key check
                List<String> adStatus = Arrays.asList(AdStatus.values().toString());
                if (!adStatus.contains(key.toUpperCase())) {
                    throw new EnumConstantNotPresentException(AdStatus.class, key);
                }
                // DB check
                if (repo.findByAdAdStatus(AdStatus.valueOf(key.toUpperCase())).isEmpty()) {
                    throw new EntityNotFoundException("Report with Ad status:[" + key + "] NOT FOUND!!!");
                }
                return repo.findByAdAdStatus(AdStatus.valueOf(key.toUpperCase()));
            default:
                return repo.findAll();
        }
    }
}