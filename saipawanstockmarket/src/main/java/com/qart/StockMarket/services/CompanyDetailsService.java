package com.qart.StockMarket.services;

import com.qart.StockMarket.dto.CompanyDetailsDTO;

public interface CompanyDetailsService {
	
	public CompanyDetailsDTO saveCompanyDetails(CompanyDetailsDTO companyDetailsDTO);
	public CompanyDetailsDTO deleteCompany(Long companyCode);
	public CompanyDetailsDTO getCompanyInfoById(Long companyCode);
}
