package com.vigimod.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vigimod.api.entity.Seller;
import com.vigimod.api.repository.SellerDaoRepo;

import jakarta.persistence.EntityExistsException;

@Service
public class SellerService {
    @Autowired
    SellerDaoRepo repo;

    public List<Seller> getAll() {
        return repo.findAll();
    }

    public Seller getByID(Long id) {
        if (!repo.existsById(id)) {
            throw new EntityExistsException("Seller NOT FOUND!!!");
        }
        return repo.findById(id).get();
    }

    public Seller create(Seller p) {
        if (repo.existsByUsernameAndEmailAndPhoneNumber(p.getUsername(), p.getEmail(), p.getPhoneNumber())) {
            throw new EntityExistsException("Seller Already exists!!!");
        }
        return repo.save(p);
    }

    public Seller update(Long id, Seller p) {
        if (!repo.existsById(id)) {
            throw new EntityExistsException("Seller ID NOT FOUND!!!");
        }

        return repo.save(p);
    }

    public String remove(Long id) {
        if (!repo.existsById(id)) {
            throw new EntityExistsException("Seller ID NOT FOUND!!!");
        }
        repo.deleteById(id);
        return "Seller eliminato!!!";
    }
}