package webservices;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import model.FundFeeData;


public class FundFeeCalculatorWebServiceTest {

	private FundFeeCalculatorWebService fundFeeCalculatorWebService;
	
	@Before
	public void setUp() {
		fundFeeCalculatorWebService = new FundFeeCalculatorWebService();
	}
	
	@Test
	public void shouldReturnJSONData() {
		FundFeeData data = fundFeeCalculatorWebService.getData(BigDecimal.ZERO,BigDecimal.ZERO,0,BigDecimal.ZERO,BigDecimal.ZERO);
		assertNotNull(data);
	}

}