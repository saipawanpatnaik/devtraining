package com.qart.StockMarket.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.qart.StockMarket.model.CompanyDetails;
import com.qart.StockMarket.model.StockPriceDetails;

@Repository
public interface StockPriceRepository extends JpaRepository<StockPriceDetails, Long>{
	
	 List<StockPriceDetails> findByCompanyCode(Long companyCode);
	 
	 @Query("select cd FROM StockPriceDetails cd WHERE cd.companyCode=?1")
	 public List<StockPriceDetails> findStockByCompanyCode(Long companyCode);
	 
	 @Query("SELECT MAX(currentStockPrice) FROM StockPriceDetails sp WHERE sp.companyCode=?1 AND sp.stockPriceDate BETWEEN ?2 AND ?3")
	 public Double findMaxStockPrice(Long companyCode, LocalDate from, LocalDate to);

	 @Query("SELECT AVG(currentStockPrice) FROM StockPriceDetails sp WHERE sp.companyCode=?1 AND sp.stockPriceDate BETWEEN ?2 AND ?3")
	 public Double findAvgStockPrice(Long companyCode, LocalDate from, LocalDate to);

	 @Query("SELECT MIN(currentStockPrice) FROM StockPriceDetails sp WHERE sp.companyCode=?1 AND sp.stockPriceDate BETWEEN ?2 AND ?3")
	 public Double findMinStockPrice(Long companyCode, LocalDate from, LocalDate to);
	    
	 @Query("select cd FROM StockPriceDetails cd WHERE cd.stockPriceDate BETWEEN ?2 AND ?3 AND cd.companyCode=?1")
	 public List<StockPriceDetails> findStockByCompanyCodeBetweendates(Long companyCode,LocalDate startDate,LocalDate endDate);

}
