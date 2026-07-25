package com.cognizant.ormlearn.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.ormlearn.model.Stock;

/**
 * Doc 2 - Hands on 2: Write queries on stock table using Query Methods.
 */
@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {

    // All stock details of a given code between two dates (e.g. Facebook, Sept 2019)
    List<Stock> findByCodeAndDateBetween(String code, LocalDate startDate, LocalDate endDate);

    // All stock details for a code where the closing price was greater than a value
    List<Stock> findByCodeAndCloseGreaterThan(String code, BigDecimal close);

    // Top 3 dates with the highest volume of transactions, across all codes
    List<Stock> findTop3ByOrderByVolumeDesc();

    // Top 3 dates when a given code's stock closed at its lowest price
    List<Stock> findTop3ByCodeOrderByCloseAsc(String code);
}
