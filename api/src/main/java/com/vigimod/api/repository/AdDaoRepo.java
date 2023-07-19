package com.vigimod.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vigimod.api.entity.Ad;
import com.vigimod.api.entity.Product;
import com.vigimod.api.utils.AdStatus;

import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;

@Repository
public interface AdDaoRepo extends JpaRepository<Ad, Long> {

    @Query("SELECT COUNT(a) FROM Ad a WHERE a.adStatus = 'PENDING'")
    long countPendingAds();

    List<Ad> findByProductAndLocationAndAndPublicationDate(Product product, String location,
            LocalDateTime publicationDate);

    List<Ad> findByAdStatus(AdStatus adStatus);

    List<Ad> findByLocationAllIgnoreCase(String location);

    List<Ad> findByPublicationDateAfter(LocalDateTime date);

    List<Ad> findByProductCategoryAllIgnoreCase(String category);

    List<Ad> findByProductSellerId(Long seller);

    List<Ad> findByProductSellerUsername(String username);

    List<Ad> findByProductBrandAllIgnoreCase(String brand);

    List<Ad> findByProductTitleContainingAllIgnoreCase(String keyword);

    // Filtraggio degli annunci per prezzo inferiore a un valore
    List<Ad> findByProductPriceLessThan(double price);

    // Filtraggio degli annunci per valutazione del prodotto superiore a un valore

    // Filtraggio degli annunci per disponibilità di stock
    List<Ad> findByProductStockGreaterThan(int stock);

    @Query("SELECT ad FROM Ad ad JOIN FETCH ad.product JOIN FETCH ad.product.seller GROUP BY seller.id")
    List<Ad> findAllGropSeller();

    @Query("SELECT seller.id, ad FROM Ad ad JOIN FETCH ad.product JOIN FETCH ad.product.seller seller")
    Map<Long, List<Ad>> findAllAdsGropedByProductSellerId();

}
