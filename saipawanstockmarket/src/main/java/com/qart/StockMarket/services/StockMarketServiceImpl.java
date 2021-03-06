package com.qart.StockMarket.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.qart.StockMarket.dto.CompanyDetailsDTO;
import com.qart.StockMarket.dto.CompanyStockDetailsDTO;
import com.qart.StockMarket.dto.StockPriceDetailsDTO;
import com.qart.StockMarket.dto.StockPriceIndexDTO;
import com.qart.StockMarket.exception.CompanyNotFoundException;
import com.qart.StockMarket.exception.GeneralException;
import com.qart.StockMarket.exception.InvalidDateException;
import com.qart.StockMarket.exception.StockNotFoundException;
import com.qart.StockMarket.model.CompanyDetails;
import com.qart.StockMarket.model.StockPriceDetails;
import com.qart.StockMarket.repository.CompanyDetailsRepository;
import com.qart.StockMarket.repository.StockPriceRepository;
import com.qart.StockMarket.utils.StockMarketUtility;

@Service
public class StockMarketServiceImpl implements StockMarketService {

	@Autowired
	private StockPriceRepository stockPriceRepository;

	@Autowired
	private CompanyDetailsRepository companyRepository;

	@Override
	public StockPriceDetailsDTO saveStockPriceDetails(StockPriceDetailsDTO stockPriceDetailsDTO) {
		Optional<CompanyDetails> companyDetails = companyRepository.findById(stockPriceDetailsDTO.getCompanyCode());
		if (companyDetails.isPresent()) {
			//			StockPriceDetails StockPriceDetails = new StockPriceDetails();
			//			StockPriceDetailsDTO savedStockDto = new StockPriceDetailsDTO();
			//			BeanUtils.copyProperties(stockPriceDetailsDTO,StockPriceDetails);
			if (stockPriceDetailsDTO.getStockPriceDate().compareTo(java.time.LocalDate.now()) < 0) {
				StockPriceDetails savedStock = stockPriceRepository
						.save(StockMarketUtility.convertToStockPriceDetails(stockPriceDetailsDTO));
				if (savedStock != null) {
					// BeanUtils.copyProperties(savedStock,savedStockDto);
					return StockMarketUtility.convertToStockPriceDetailsDTO(savedStock);
					// return savedStockDto;
				} else {
					throw new GeneralException("Database write failed");
				}
			} else {
				throw new InvalidDateException("Stock Price Date must be current or past date");
			}

		} else {
			throw new CompanyNotFoundException(
					"Company with Code " + stockPriceDetailsDTO.getCompanyCode() + " not found");
		}
	}

	@Override
	public StockPriceIndexDTO getStockPriceIndex(Long companyCode, LocalDate startDate, LocalDate endDate) {
		Optional<StockPriceDetails> StockDetails = stockPriceRepository.findById(companyCode);
		if (StockDetails.isPresent()) {
			List<StockPriceDetails> stockPriceDetailList = stockPriceRepository
					.findStockByCompanyCodeBetweendates(companyCode, startDate, endDate);
			StockPriceIndexDTO stockPriceIndexDto = new StockPriceIndexDTO();
			stockPriceIndexDto.setAvgStockPrice(getAvgStockPrice(companyCode, startDate, endDate));
			stockPriceIndexDto.setMaxStockPrice(getMaxStockPrice(companyCode, startDate, endDate));
			stockPriceIndexDto.setMinStockPrice(getMinStockPrice(companyCode, startDate, endDate));
			stockPriceIndexDto
			.setStockPriceList(StockMarketUtility.convertToStockPriceDetailsDtoList(stockPriceDetailList));
			return stockPriceIndexDto;
		} else {
			throw new StockNotFoundException("Stock with Company Code " + companyCode + " not found from " + startDate
					+ " to " + endDate + " time frame");
		}
	}

	public Double getMaxStockPrice(Long companyCode, LocalDate startDate, LocalDate endDate) {
		return stockPriceRepository.findMaxStockPrice(companyCode, startDate, endDate);
	};

	public Double getAvgStockPrice(Long companyCode, LocalDate startDate, LocalDate endDate) {
		return stockPriceRepository.findAvgStockPrice(companyCode, startDate, endDate);
	};

	public Double getMinStockPrice(Long companyCode, LocalDate startDate, LocalDate endDate) {
		return stockPriceRepository.findMinStockPrice(companyCode, startDate, endDate);
	}

	@Override
	public List<StockPriceDetailsDTO> getAllStocksByCompanyCode(Long companyCode) {
		// TODO Auto-generated method stub

		List<StockPriceDetails> stocks = stockPriceRepository.findByCompanyCode(companyCode);
		System.out.println(stocks);
		List<StockPriceDetailsDTO> stockDtos = new ArrayList<>();
		CompanyDetailsDTO companyDto = new CompanyDetailsDTO();
		for (StockPriceDetails stock : stocks) {
			StockPriceDetailsDTO stockDto = new StockPriceDetailsDTO();
			BeanUtils.copyProperties(stock, stockDto);
			stockDtos.add(stockDto);
		}

		return stockDtos;
	}

	public List<StockPriceDetailsDTO> getStockByCode(Long companyCode) {

		List<StockPriceDetails> stockDetails = stockPriceRepository.findStockByCompanyCode(companyCode);

		if (CollectionUtils.isEmpty(stockDetails))
			return null;
		else
			return stockDetails.stream().map(StockMarketUtility::convertToStockPriceDetailsDTO)
					.collect(Collectors.toList());
	};

	public CompanyStockDetailsDTO getAllStocksDetailsByCompanyCode(Long companyCode) {

		CompanyStockDetailsDTO CompanyStockDto = new CompanyStockDetailsDTO();
		Optional<CompanyDetails> companyDetails = companyRepository.findById(companyCode);
		if(companyDetails.isPresent()) {
			CompanyDetailsDTO companyDetailsDTO = new CompanyDetailsDTO();
			BeanUtils.copyProperties(companyDetails.get(), companyDetailsDTO);
			CompanyStockDto.setCompanyDto(companyDetailsDTO);
			CompanyStockDto.setStockPriceDTO(getStockByCode(companyCode));
			return CompanyStockDto;
		}
		else
			return null;
	}


}
