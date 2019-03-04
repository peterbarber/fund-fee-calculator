package service;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import model.FundFeeData;

import org.junit.Before;
import org.junit.Test;

public class FundFeeCalculatorServiceImplTest {

	FundFeeCalculatorService fundFeeCalculatorService;
	private BigDecimal twenty = BigDecimal.valueOf(20);
	private BigDecimal five = BigDecimal.valueOf(5);
	private BigDecimal zero = BigDecimal.ZERO;

	@Before
	public void setup() {
		fundFeeCalculatorService = new FundFeeCalculatorServiceImpl();
	}

	@Test(expected = NullPointerException.class)
	public void shouldThrowErrorOnNullArguments() {
		fundFeeCalculatorService.calculate(null, null, null, null, null);
	}

	@Test
	public void shouldIgnoreAllZeros() {
		FundFeeData data = fundFeeCalculatorService.calculate(zero, zero, 0,
				zero, zero);
		assertEquals(1, data.getAccumulationByYearWithFees().size());
		assertEquals(1, data.getAccumulationByYearWithoutFees().size());
		assertEquals(new BigDecimal("0.00"), data
				.getAccumulationByYearWithFees().get(0).getAccumulationAmount());
	}

	@Test
	public void shouldDoZeroYears() {
		FundFeeData data = fundFeeCalculatorService.calculate(twenty, zero, 0,
				zero, zero);
		assertEquals(1, data.getAccumulationByYearWithFees().size());
		assertEquals(1, data.getAccumulationByYearWithoutFees().size());
		assertEquals(new BigDecimal("20.00"), data
				.getAccumulationByYearWithFees().get(0).getAccumulationAmount());
	}

	@Test
	public void shouldSetFinalAccumulationAmount() {
		FundFeeData data = fundFeeCalculatorService.calculate(
				BigDecimal.valueOf(20), five, 1, zero, zero);
		assertEquals(new BigDecimal("25.00"),
				data.getFinalAccumulationAmountWithFees());
	}

	@Test
	public void shouldSetTotalFees() {
		FundFeeData data = fundFeeCalculatorService.calculate(
				BigDecimal.valueOf(20), five, 1, five, BigDecimal.TEN);
		assertEquals(new BigDecimal("1.00"), data.getTotalFeesAmount());
	}

	@Test
	public void shouldSetDataTable() {
		FundFeeData data = fundFeeCalculatorService.calculate(
				BigDecimal.valueOf(20), five, 1, five, BigDecimal.TEN);
		assertNotNull(data.getDataTable());
		assertNotNull(data.getDataTable().get(0));
		assertNotNull(data.getDataTable().get(1));
		assertNotNull(data.getDataTable().get(2));
	}

	@Test
	public void shouldSetDataTableByAge() {
		FundFeeData data = fundFeeCalculatorService.calculate(
				BigDecimal.valueOf(2), five, 1, five, BigDecimal.ONE);
		assertNotNull(data.getDataTableByAge());
		assertNotNull(data.getDataTable().get(0));
		assertNotNull(data.getDataTable().get(1));
		assertNotNull(data.getDataTable().get(2));
	}

	@Test
	public void shouldCalculateWithAge() {
		FundFeeData data = fundFeeCalculatorService.calculateFromAge(twenty,
				zero, 49, 50, zero, five);
		assertEquals(2, data.getAccumulationByYearWithFees().size());
		assertEquals(new BigDecimal("21.00"), data
				.getAccumulationByYearWithFees().get(1).getAccumulationAmount());
	}

	@Test
	public void shouldSetFee() {
		BigDecimal fee = new BigDecimal("1.91");
		FundFeeData data = fundFeeCalculatorService.calculateFromAge(twenty,
				zero, 49, 50, fee, five);
		assertEquals(fee, data.getFee());
	}

	@Test
	public void shouldSetEndAge() {
		Integer endAge = 50;
		FundFeeData data = fundFeeCalculatorService.calculateFromAge(twenty,
				zero, 49, endAge, zero, five);
		assertEquals(endAge, data.getEndAge());
	}

	@Test
	public void shouldSetFeesPercentage() {
		BigDecimal fees = new BigDecimal("1");
		FundFeeData data = fundFeeCalculatorService.calculateFromAge(
				new BigDecimal(100), zero, 49, 50, fees, BigDecimal.TEN);
		assertEquals(new BigDecimal("0.91"), data.getTotalFeesPercent());
	}

	// TODO negative yield

}
