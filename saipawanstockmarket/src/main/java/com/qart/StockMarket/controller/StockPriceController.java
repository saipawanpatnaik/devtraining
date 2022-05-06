package com.qart.StockMarket.controller;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.sql.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qart.StockMarket.dto.CompanyStockDetailsDTO;
import com.qart.StockMarket.dto.StockPriceDetailsDTO;
import com.qart.StockMarket.dto.StockPriceIndexDTO;
import com.qart.StockMarket.exception.CompanyNotFoundException;
import com.qart.StockMarket.exception.InvalidCompanyException;
import com.qart.StockMarket.exception.InvalidStockException;
import com.qart.StockMarket.exception.StockNotFoundException;
import com.qart.StockMarket.services.StockMarketService;

@RestController
@RequestMapping (value = "/stock")
public class StockPriceController {

	@Autowired
	private StockMarketService stockMarketService;
	
	@PostMapping(value="/add-stock")
	public ResponseEntity<StockPriceDetailsDTO> addStockDetails(@Valid @RequestBody  StockPriceDetailsDTO stockPriceDetailsDTO,
            BindingResult bindingResult)throws InvalidCompanyException,CompanyNotFoundException{
		if(bindingResult.hasErrors()) {
			throw new InvalidStockException("Stock Details not valid");
		}
		stockMarketService.saveStockPriceDetails(stockPriceDetailsDTO);
		return ResponseEntity.ok(stockPriceDetailsDTO);
		
	}
	
	@GetMapping(value = "/getStockByCompanyCode/{companyCode}")
    public ResponseEntity<?> getStockByCompanyCode(@PathVariable("companyCode") final Long companyCode)throws CompanyNotFoundException,StockNotFoundException {
		CompanyStockDetailsDTO stockPriceDetailsDTO = stockMarketService.getAllStocksDetailsByCompanyCode(companyCode); 
		//List<StockPriceDetailsDTO> stockPriceDetailsDTO = stockMarketService.getAllStocksByCompanyCode(companyCode);
		//return ResponseEntity.ok(stockMarketService.getStockByCode(companyCode));
		//return ResponseEntity.ok(stockMarketService.getAllStocksDetailsByCompanyCode(companyCode));
		if(stockPriceDetailsDTO ==null)
	        throw new StockNotFoundException("Invalid Company Code!!- "+companyCode+" Please enter valid companyCode...");
	       
		else
	            return new ResponseEntity<CompanyStockDetailsDTO>(stockPriceDetailsDTO, HttpStatus.OK);
		
    }
	
	@GetMapping(value = "/getStockPriceIndex/{companyCode}/{startDate}/{endDate}")
    public ResponseEntity<StockPriceIndexDTO> displayStockPriceIndex(@PathVariable final Long companyCode,
            @PathVariable final Date startDate, @PathVariable final Date endDate) throws CompanyNotFoundException, StockNotFoundException {
		LocalDate start= Instant.ofEpochMilli(startDate.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	    LocalDate end= Instant.ofEpochMilli(endDate.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	    StockPriceIndexDTO stockPriceIndexDTO =  stockMarketService.getStockPriceIndex(companyCode, start, end);
		return ResponseEntity.ok(stockPriceIndexDTO);
	}

 }
