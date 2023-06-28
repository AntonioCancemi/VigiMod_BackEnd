package com.vigimod.api.service;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vigimod.api.entity.Ad;
import com.vigimod.api.repository.AdDaoRepo;
import com.vigimod.api.utils.AdStatus;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class AdService {
    @Autowired
    AdDaoRepo repo;

    // CRUD
    // get all
    public List<Ad> getAll() {
        // DB cehck
        if (repo.findAll().isEmpty()) {
            throw new EntityExistsException("NO Ads OUND!!!");
        }
        return repo.findAll();
    }

    // get By ID
    public Ad getByID(Long id) {
        // DB check
        if (!repo.existsById(id)) {
            throw new EntityExistsException("Ad NOT FOUND!!!");
        }
        return repo.findById(id).get();
    }

    // create
    public Ad create(Ad ad) {
        // DB check
        if (!repo.findByProductAndLocationAndAndPublicationDate(ad.getProduct(), ad.getLocation(),
                ad.getPublicationDate()).isEmpty()) {
            throw new EntityExistsException("Ad Already exists!!!");
        }
        return repo.save(ad);
    }

    // update
    public Ad update(Long id, Ad ad) {
        // DB check
        if (!repo.existsById(id)) {
            throw new EntityExistsException("Ad ID NOT FOUND!!!");
        }

        return repo.save(ad);
    }

    // delete
    public String remove(Long id) {
        // DB check
        if (!repo.existsById(id)) {
            throw new EntityExistsException("Ad ID NOT FOUND!!!");
        }
        repo.deleteById(id);
        return "Ad eliminato!!!";
    }

    // Other..
    // Filter chain
    public List<Ad> getAdsByFilter(String filter, String key) {
        if (repo.findAll().isEmpty()) {
            throw new EntityNotFoundException("NO Ads OUND!!!");
        }
        switch (filter) {
            case "title":
                // DB check
                if (repo.findByProductTitleContainingAllIgnoreCase(key).isEmpty()) {
                    throw new EntityNotFoundException("Ad:title:[" + key + "] NOT FOUND!!!");
                }
                return repo.findByProductTitleContainingAllIgnoreCase(key);
            case "seller":
                // DB check
                if (repo.findByProductCategoryAllIgnoreCase(key).isEmpty()) {
                    throw new EntityNotFoundException("Ad:seller:[" + key + "] NOT FOUND!!!");
                }
                return repo.findByProductCategoryAllIgnoreCase(key);
            case "brand":
                // DB check
                if (repo.findByProductBrandAllIgnoreCase(key).isEmpty()) {
                    throw new EntityNotFoundException("Ad brand:[" + key + "] NOT FOUND!!!");
                }
                return repo.findByProductBrandAllIgnoreCase(key);
            case "date":
                // key check
                try {
                    LocalDateTime.parse(key);
                } catch (DateTimeParseException e) {
                    throw new DateTimeException("parse [" + key + "] to LocalDateTime Faild ");
                }
                // DB check
                if (repo.findByPublicationDateAfter(LocalDateTime.parse(key)).isEmpty()) {
                    throw new EntityNotFoundException("Ad after:[" + key + "] NOT FOUND!!!");
                }
                return repo.findByPublicationDateAfter(LocalDateTime.parse(key));
            case "price":
                // key check
                try {
                    Double.parseDouble(key);
                } catch (NumberFormatException nfe) {
                    throw new NumberFormatException("Expected Double instead:[" + key + "]");
                }
                // DB check
                if (repo.findByProductPriceLessThan(Double.parseDouble(key)).isEmpty()) {
                    throw new EntityNotFoundException("Ad ProductPriceLessThan:[" + key + "] NOT FOUND!!!");
                }
                return repo.findByProductPriceLessThan(Double.parseDouble(key));
            case "stock":
                // key check
                try {
                    Integer.parseInt(key);

                } catch (NumberFormatException nfe) {
                    throw new NumberFormatException("Expected Integer instead:[" + key + "]");
                }
                // DB check
                if (repo.findByProductStockGreaterThan(Integer.parseInt(key)).isEmpty()) {
                    throw new EntityNotFoundException("Ad ProductStockGreaterThan:[" + key + "] NOT FOUND!!!");
                }
                return repo.findByProductStockGreaterThan(Integer.parseInt(key));
            case "status":
                // key check
                List<String> adStatus = Arrays.asList(AdStatus.values().toString());
                if (!adStatus.contains(key.toUpperCase())) {
                    throw new EnumConstantNotPresentException(AdStatus.class, key);
                }
                // DB check
                if (repo.findByAdStatus(AdStatus.valueOf(key.toUpperCase())).isEmpty()) {
                    throw new EntityNotFoundException("Ad status:[" + key + "] NOT FOUND!!!");
                }
                return repo.findByAdStatus(AdStatus.valueOf(key.toUpperCase()));
            default:
                return repo.findAll();
        }
    }

}