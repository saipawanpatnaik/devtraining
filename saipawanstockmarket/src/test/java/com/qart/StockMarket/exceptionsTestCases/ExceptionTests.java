package com.qart.StockMarket.exceptionsTestCases;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.qart.StockMarket.controller.CompanyDetailsController;
import com.qart.StockMarket.controller.StockPriceController;
import com.qart.StockMarket.dto.CompanyDetailsDTO;
import com.qart.StockMarket.dto.StockPriceDetailsDTO;
import com.qart.StockMarket.exception.CompanyNotFoundException;
import com.qart.StockMarket.exception.ExceptionResponse;
import com.qart.StockMarket.services.CompanyDetailsService;
import com.qart.StockMarket.services.StockMarketService;
import com.qart.StockMarket.utilTestClass.MasterData;

import static com.qart.StockMarket.utilTestClass.TestUtils.exceptionTestFile;
import static com.qart.StockMarket.utilTestClass.TestUtils.currentTest;
import static com.qart.StockMarket.utilTestClass.TestUtils.testAssert;
import static org.mockito.Mockito.when;

import java.io.Serializable;

@WebMvcTest({CompanyDetailsController.class, StockPriceController.class})
@AutoConfigureMockMvc
public class ExceptionTests{

	//private static final long serialVersionUID = 5383694840817307616L;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CompanyDetailsService companyInfoService;

	@MockBean
	private StockMarketService stockMarketService;

	//====================================================================================================================
	//			1. Exceptions tests regarding Company Operations - Add and Delete
	//====================================================================================================================	
	@Test
	public void testCompanyForInvalidDataException() throws Exception
	{
		CompanyDetailsDTO companyDto = MasterData.getCompanyDetailsDTO();
		companyDto.setCompanyName("SE");

		Mockito.when(companyInfoService.saveCompanyDetails(companyDto)).thenReturn(companyDto);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/company/add-company")
				.content(MasterData.asJsonString(companyDto))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		testAssert(currentTest(), (result.getResponse().getStatus() == HttpStatus.BAD_REQUEST.value() ? true : false), exceptionTestFile);
	}
	//--------------------------------------------------------------------------------------------
	@Test
	public void testCompanyForExceptionUponAddingCompanyWithNullValue() throws Exception
	{
		CompanyDetailsDTO companyDto = MasterData.getCompanyDetailsDTO();
		companyDto.setStockExchange(null);

		Mockito.when(companyInfoService.saveCompanyDetails(companyDto)).thenReturn(companyDto);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/company/add-company")
				.content(MasterData.asJsonString(companyDto))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		testAssert(currentTest(), result.getResponse().getStatus() == 400 ? true : false, exceptionTestFile);		
	}
	//--------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------
//	@Test
//	public void testCompanyForExceptionUponDeletingCompanyByNullValue() throws Exception
//	{
//		Mockito.when(companyInfoService.deleteCompany(2L)).thenReturn(null);
//
//		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/company/deleteCompany/2")
//				.contentType(MediaType.APPLICATION_JSON)
//				.accept(MediaType.APPLICATION_JSON);
//
//		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//
//
//		testAssert(currentTest(), result.getResponse().getStatus() == 404 ? true : false, exceptionTestFile);		
//	}
	@Test
	public void testCompanyNotFoundExceptions() throws Exception
	{
		ExceptionResponse exResponse = new ExceptionResponse("Company with Id - 2 not Found!",
				System.currentTimeMillis(), HttpStatus.NOT_FOUND.value());

		Mockito.when(this.companyInfoService.deleteCompany(2L))
		.thenThrow(new CompanyNotFoundException("Company with Id - 2 not Found!"));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/company/deleteCompany/2")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		testAssert(currentTest(),
				(result.getResponse().getContentAsString().contains(exResponse.getMessage()) ? "true" : "false"),
				exceptionTestFile);
	}	

	//====================================================================================================================
	//			2. Exceptions tests regarding Stock Operations
	//====================================================================================================================	

	//--------------------------------------------------------------------------------------------

//	@Test
//	public void testCompanyForInvalidStockException() throws Exception
//	{
//		StockPriceDetailsDTO stockDto = MasterData.getStockPriceDetailsDTO();
//		stockDto.setCurrentStockPrice(12.0);
//
//		Mockito.when(stockMarketService.saveStockPriceDetails(stockDto)).thenReturn(stockDto);
//
//		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/stock/add-stock")
//				.content(MasterData.asJsonString(stockDto))
//				.contentType(MediaType.APPLICATION_JSON)
//				.accept(MediaType.APPLICATION_JSON);
//
//		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//
//		testAssert(currentTest(), (result.getResponse().getStatus() == HttpStatus.BAD_REQUEST.value() ? true : false), exceptionTestFile);
//	}
//
//	@Test
//	public void testStockForExceptionUponAddingStockWithNullValue() throws Exception
//	{
//		StockPriceDetailsDTO stockDto = MasterData.getStockPriceDetailsDTO();
//		stockDto.setCurrentStockPrice(null);
//
//		Mockito.when(stockMarketService.saveStockPriceDetails(stockDto)).thenReturn(stockDto);
//
//		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/stock/add-stock")
//				.content(MasterData.asJsonString(stockDto))
//				.contentType(MediaType.APPLICATION_JSON)
//				.accept(MediaType.APPLICATION_JSON);
//
//		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//
//
//		testAssert(currentTest(), result.getResponse().getStatus() == 400 ? true : false, exceptionTestFile);		
//	}

//	@Test
//	public void testStockForExceptionUponFetchingStockDetailsByNullValue() throws Exception
//	{
//		Mockito.when(stockMarketService.getStockByCode(2L)).thenReturn(null);
//
//		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/stock/getStockByCompanyCode/2")
//				.contentType(MediaType.APPLICATION_JSON)
//				.accept(MediaType.APPLICATION_JSON);
//
//		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//
//
//		testAssert(currentTest(), result.getResponse().getStatus() == 404 ? true : false, exceptionTestFile);		
//	}
}