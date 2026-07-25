package com.cognizant.ormlearn.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cognizant.ormlearn.model.Stock;
import com.cognizant.ormlearn.repository.StockRepository;

/** Doc 2 - Hands on 2: Write queries on stock table using Query Methods. */
@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    // All FB stock details in September 2019
    @Transactional
    public List<Stock> getStockByCodeAndDateRange(String code, LocalDate start, LocalDate end) {
        return stockRepository.findByCodeAndDateBetween(code, start, end);
    }

    // All GOOGL stock details where the closing price was greater than a value
    @Transactional
    public List<Stock> getStockByCodeAboveClose(String code, BigDecimal close) {
        return stockRepository.findByCodeAndCloseGreaterThan(code, close);
    }

    // Top 3 dates with the highest volume of transactions
    @Transactional
    public List<Stock> getTop3ByVolume() {
        return stockRepository.findTop3ByOrderByVolumeDesc();
    }

    // Top 3 dates when a given stock code closed at its lowest
    @Transactional
    public List<Stock> getTop3LowestClose(String code) {
        return stockRepository.findTop3ByCodeOrderByCloseAsc(code);
    }
}
