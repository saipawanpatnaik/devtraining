package com.qart.StockMarket.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.qart.StockMarket.dto.CompanyDetailsDTO;
import com.qart.StockMarket.dto.StockPriceDetailsDTO;
import com.qart.StockMarket.model.CompanyDetails;
import com.qart.StockMarket.model.StockPriceDetails;

public class StockMarketUtility {
	
		public static StockPriceDetails convertToStockPriceDetails(StockPriceDetailsDTO stockPriceDetailsDTO)	{
				
				StockPriceDetails newStock = new StockPriceDetails();
				BeanUtils.copyProperties(stockPriceDetailsDTO,newStock);
		        return newStock;	
		};
		
		public static StockPriceDetailsDTO convertToStockPriceDetailsDTO(StockPriceDetails stockPriceDetails)	{
			
			StockPriceDetailsDTO newStock = new StockPriceDetailsDTO();
			BeanUtils.copyProperties(stockPriceDetails,newStock);
	        return newStock;		
		};
		
		//=================================================================================================================================
		//				2. Company Details Conversion : Model to DTO - AND - DTO to Model
		//=================================================================================================================================
		public static CompanyDetailsDTO convertToCompanyDetailsDTO(CompanyDetails companyDto)	{
			
			CompanyDetailsDTO newCompanyDTO = new CompanyDetailsDTO();
			BeanUtils.copyProperties(companyDto,newCompanyDTO);			
			return newCompanyDTO;
		};	
		//---------------------------------------------------------------------------------------------------------------------------------
		public static CompanyDetails convertToCompanyDetails(CompanyDetailsDTO companyDetailsDTO)	{
			
			CompanyDetails newCompany = new CompanyDetails();
			BeanUtils.copyProperties(companyDetailsDTO,newCompany);			
			return newCompany;
		};	
		//---------------------------------------------------------------------------------------------------------------------------------

	    
	    public static List<CompanyDetailsDTO> convertToCompanyDetailsDtoList(List<CompanyDetails> companyDetailsList) {
	    	
	        List<CompanyDetailsDTO> list = new ArrayList<CompanyDetailsDTO>();
	        
	        for(CompanyDetails companyDto: companyDetailsList) {
	            list.add(convertToCompanyDetailsDTO(companyDto));
	        }       
	        return list;
	    }
		//---------------------------------------------------------------------------------------------------------------------------------
	    public static List<StockPriceDetailsDTO> convertToStockPriceDetailsDtoList(List<StockPriceDetails> stockPriceDetailsList) {
	    	
			List<StockPriceDetailsDTO> list = new ArrayList<StockPriceDetailsDTO>();
			
			for(StockPriceDetails stockDto : stockPriceDetailsList) {
				list.add(convertToStockPriceDetailsDTO(stockDto));
			}
			return list;
	    }

}
