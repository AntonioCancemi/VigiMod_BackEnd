package com.vigimod.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vigimod.api.entity.Seller;

@Repository
public interface SellerDaoRepo extends JpaRepository<Seller, Long> {
    boolean existsByUsernameAndEmailAndPhoneNumber(String username, String email, String phoneNumber);
}
