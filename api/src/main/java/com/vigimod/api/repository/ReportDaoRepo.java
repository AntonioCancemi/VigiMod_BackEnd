package com.vigimod.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vigimod.api.entity.Report;

import java.time.LocalDateTime;
import java.util.List;

import com.vigimod.api.utils.AdStatus;

@Repository
public interface ReportDaoRepo extends JpaRepository<Report, Long> {
    List<Report> findByAdId(Long ad);

    List<Report> findByAdProductId(Long product);

    List<Report> findByAdProductTitleContainingIgnoreCase(String keyword);

    List<Report> findByAdProductCategory(String category);

    List<Report> findByAdProductSellerId(Long seller);

    List<Report> findByAdPublicationDateAfter(LocalDateTime date);

    List<Report> findByAdProductBrand(String key);

    List<Report> findByAdAdStatus(AdStatus valueOf);

}
