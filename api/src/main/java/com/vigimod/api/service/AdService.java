package com.vigimod.api.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vigimod.api.entity.Ad;
import com.vigimod.api.repository.AdDaoRepo;
import com.vigimod.api.utils.AdStatus;

import jakarta.persistence.EntityExistsException;

@Service
public class AdService {
    @Autowired
    AdDaoRepo repo;

    public List<Ad> getAll() {
        return repo.findAll();
    }

    public Ad getByID(Long id) {
        if (!repo.existsById(id)) {
            throw new EntityExistsException("Ad NOT FOUND!!!");
        }
        return repo.findById(id).get();
    }

    public Ad create(Ad ad) {
        if (!repo.findByProductAndLocationAndAndPublicationDate(ad.getProduct(), ad.getLocation(),
                ad.getPublicationDate()).isEmpty()) {
            throw new EntityExistsException("Ad Already exists!!!");
        }
        return repo.save(ad);
    }

    public Ad update(Long id, Ad ad) {
        if (!repo.existsById(id)) {
            throw new EntityExistsException("Ad ID NOT FOUND!!!");
        }

        return repo.save(ad);
    }

    public String remove(Long id) {
        if (!repo.existsById(id)) {
            throw new EntityExistsException("Ad ID NOT FOUND!!!");
        }
        repo.deleteById(id);
        return "Ad eliminato!!!";
    }

    public List<Ad> getAdsByFilter(String filter, String key) {
        if (repo.findAll().isEmpty()) {
            throw new EntityExistsException("NO Ads OUND!!!");
        }
        switch (filter) {
            case "title":
                return repo.findByProductTitleContaining(key);
            case "seller":
                return repo.findByProductCategory(key);
            case "date":
                return repo.findByPublicationDateAfter(LocalDateTime.parse(key));
            case "brand":
                return repo.findByProductBrand(key);
            case "price":
                return repo.findByProductPriceLessThan(Double.parseDouble(key));
            case "stock":
                return repo.findByProductStockGreaterThan(Integer.parseInt(key));
            case "status":
                return repo.findByAdStatus(AdStatus.valueOf(key.toUpperCase()));
            default:
                return repo.findAll();
        }
    }

}