package com.qart.StockMarket.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qart.StockMarket.dto.CompanyDetailsDTO;
import com.qart.StockMarket.exception.CompanyNotFoundException;
import com.qart.StockMarket.exception.InvalidCompanyException;
import com.qart.StockMarket.services.CompanyDetailsService;

@RestController
@RequestMapping (value = "/company")
public class CompanyDetailsController {
	
	@Autowired
	private CompanyDetailsService companyDetailsService;
	
	@PostMapping(value="/add-company")
	public ResponseEntity<CompanyDetailsDTO> addCompanyDetails( @Valid @RequestBody  CompanyDetailsDTO companyDetailsDTO,
            BindingResult bindingResult) throws InvalidCompanyException {
		if(bindingResult.hasErrors()) {
			throw new InvalidCompanyException("Company Details not valid");		
		}
		companyDetailsService.saveCompanyDetails(companyDetailsDTO);
		return ResponseEntity.ok(companyDetailsDTO);
	}
	
	@DeleteMapping(value = "/deleteCompany/{companyCode}")
	public ResponseEntity<CompanyDetailsDTO> deleteCompanyDetails(@PathVariable("companyCode")  Long companyCode) throws CompanyNotFoundException{
		CompanyDetailsDTO companyDetailsDTO = companyDetailsService.deleteCompany(companyCode);
        if (companyDetailsDTO == null)
            throw new CompanyNotFoundException("Invalid Company Code!!- "+companyCode+" Please enter valid companyCode...");
        else
            return new ResponseEntity<CompanyDetailsDTO>(companyDetailsDTO, HttpStatus.OK);
	}
}
