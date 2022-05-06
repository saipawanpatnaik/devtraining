package com.qart.StockMarket.services;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qart.StockMarket.dto.CompanyDetailsDTO;
import com.qart.StockMarket.exception.CompanyNotFoundException;
import com.qart.StockMarket.exception.GeneralException;
import com.qart.StockMarket.model.CompanyDetails;
import com.qart.StockMarket.repository.CompanyDetailsRepository;
import com.qart.StockMarket.utils.StockMarketUtility;

@Service
public class CompanyDetailsServiceImpl implements CompanyDetailsService{

	@Autowired
	private CompanyDetailsRepository companyDetailsRepository;

	@Override
	public CompanyDetailsDTO saveCompanyDetails(CompanyDetailsDTO companyDetailsDTO) {
		CompanyDetails saved = companyDetailsRepository.save(StockMarketUtility.convertToCompanyDetails(companyDetailsDTO));
		CompanyDetailsDTO savedDto = StockMarketUtility.convertToCompanyDetailsDTO(saved);
		if(saved==null) {
			throw new GeneralException("Database write failed");
		}
		return savedDto;
	}

	public CompanyDetailsDTO deleteCompany(Long companyCode) {
		//Optional<CompanyDetails> company = companyDetailsRepository.findById(companyCode);
		CompanyDetailsDTO company = getCompanyInfoById(companyCode);		
		Integer value;
		if(company == null)
			return null;
		else
			value= companyDetailsRepository.deleteByCompanyCode(companyCode);
		if(value!=null)
			return company;
		else
			return null;

	}

	public CompanyDetailsDTO getCompanyInfoById(Long companyCode) {
	Optional<CompanyDetails> company =companyDetailsRepository.findById(companyCode);
		if(company.isPresent())
			return StockMarketUtility.convertToCompanyDetailsDTO(company.get());
		else
			return null;
	};

}
