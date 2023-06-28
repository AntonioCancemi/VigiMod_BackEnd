package com.vigimod.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vigimod.api.entity.Ad;
import com.vigimod.api.entity.Product;
import com.vigimod.api.entity.Seller;
import com.vigimod.api.utils.AdStatus;

import java.util.List;
import java.time.LocalDateTime;

@Repository
public interface AdDaoRepo extends JpaRepository<Ad, Long> {
    List<Ad> findByProductAndLocationAndAndPublicationDate(Product product, String location,
            LocalDateTime publicationDate);

    List<Ad> findByAdStatus(AdStatus adStatus);

    List<Ad> findByLocation(String location);

    List<Ad> findByPublicationDateAfter(LocalDateTime date);
    
    List<Ad> findByProductCategory(String category);

    List<Ad> findByProductSellerId(Long seller);

    List<Ad> findByProductSellerUsername(String username);

    List<Ad> findByProductBrand(String brand);

    List<Ad> findByProductTitleContaining(String keyword);

    // Filtraggio degli annunci per prezzo inferiore a un valore
    List<Ad> findByProductPriceLessThan(double price);

    // Filtraggio degli annunci per valutazione del prodotto superiore a un valore
    List<Ad> findByProductRatingGreaterThan(double rating);

    // Filtraggio degli annunci per disponibilità di stock
    List<Ad> findByProductStockGreaterThan(int stock);

}
