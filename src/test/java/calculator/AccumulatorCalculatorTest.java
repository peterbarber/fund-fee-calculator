package calculator;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.List;

import model.FundAccumulation;

import org.junit.Test;

public class AccumulatorCalculatorTest {

	private BigDecimal twenty = BigDecimal.valueOf(20);
	private BigDecimal five = BigDecimal.valueOf(5);
	private BigDecimal zero = BigDecimal.ZERO;

	@Test
	public void shouldIgnoreAllZeros() {
		List<FundAccumulation> data = AccumulatorCalculator
				.calculateCompoundGrowthWithFees(zero, zero, 0, zero, zero);
		assertEquals(1, data.size());
		assertEquals(new BigDecimal("0.00"), data.get(0)
				.getAccumulationAmount());
	}

	@Test
	public void shouldDoZeroYears() {
		List<FundAccumulation> data = AccumulatorCalculator
				.calculateCompoundGrowthWithFees(twenty, zero, 0, zero, zero);
		assertEquals(1, data.size());
		assertEquals(new BigDecimal("20.00"), data.get(0)
				.getAccumulationAmount());
	}

	@Test
	public void shouldDoOneYearZeroFees() {
		List<FundAccumulation> data = AccumulatorCalculator
				.calculateCompoundGrowthWithFees(twenty, zero, 1, zero, zero);
		assertEquals(2, data.size());
		assertEquals(new BigDecimal("20.00"), data.get(1)
				.getAccumulationAmount());
	}

	@Test
	public void shouldDoOneYearContribution() {
		List<FundAccumulation> data = AccumulatorCalculator
				.calculateCompoundGrowthWithFees(twenty, BigDecimal.valueOf(5),
						1, zero, zero);
		assertEquals(2, data.size());
		assertEquals(new BigDecimal("25.00"), data.get(1)
				.getAccumulationAmount());
	}

	@Test
	public void shouldDoTwoYearsContribution() {
		List<FundAccumulation> data = AccumulatorCalculator
				.calculateCompoundGrowthWithFees(twenty, BigDecimal.valueOf(5),
						2, zero, zero);
		assertEquals(3, data.size());
		assertEquals(new BigDecimal("30.00"), data.get(2)
				.getAccumulationAmount());
	}

	@Test
	public void shouldDoOneYearInterest() {
		List<FundAccumulation> data = AccumulatorCalculator
				.calculateCompoundGrowthWithFees(twenty, zero, 1, zero,
						BigDecimal.valueOf(5));
		assertEquals(2, data.size());
		assertEquals(new BigDecimal("21.00"), data.get(1)
				.getAccumulationAmount());
	}

	@Test
	public void shouldDoOneYearMinusFixedFee() {
		List<FundAccumulation> data = AccumulatorCalculator
				.calculateWithFixedAndPercentageFees(twenty, zero, 1, five, zero,
						five);
		assertEquals(2, data.size());
		assertEquals(new BigDecimal("16.00"), data.get(1)
				.getAccumulationAmount());
	}
	
	@Test
	public void shouldDoOneYearMinusPercentFee() {
		List<FundAccumulation> data = AccumulatorCalculator
				.calculateWithFixedAndPercentageFees(twenty, zero, 1, zero, new BigDecimal(2),
						five);
		assertEquals(2, data.size());
		assertEquals(new BigDecimal("20.60"), data.get(1)
				.getAccumulationAmount());
	}
	
	@Test
	public void shouldDoOneYearMinusPercentAndFixedFee() {
		List<FundAccumulation> data = AccumulatorCalculator
				.calculateWithFixedAndPercentageFees(twenty, zero, 1, five, new BigDecimal(2),
						five);
		assertEquals(2, data.size());
		assertEquals(new BigDecimal("15.60"), data.get(1)
				.getAccumulationAmount());
	}

	@Test
	public void shouldDoTwoYearsInterest() {
		List<FundAccumulation> data = AccumulatorCalculator
				.calculateCompoundGrowthWithFees(twenty, zero, 2, zero,
						BigDecimal.valueOf(5));
		assertEquals(3, data.size());
		assertEquals(new BigDecimal("22.05"), data.get(2)
				.getAccumulationAmount());
	}

	@Test
	public void shouldDoOneYearInterestMinusFees() {
		List<FundAccumulation> data = AccumulatorCalculator
				.calculateCompoundGrowthWithFees(BigDecimal.valueOf(20),
						BigDecimal.ZERO, 1, BigDecimal.valueOf(3), five);
		assertEquals(2, data.size());
		assertEquals(new BigDecimal("20.40"), data.get(1)
				.getAccumulationAmount());
	}

	@Test
	public void shouldDoAll() {
		List<FundAccumulation> data = AccumulatorCalculator
				.calculateCompoundGrowthWithFees(BigDecimal.valueOf(100000),
						BigDecimal.valueOf(2000), 3, BigDecimal.valueOf(1.5),
						BigDecimal.valueOf(8));
		assertEquals(4, data.size());
		assertEquals(new BigDecimal("108500.00"), data.get(1)
				.getAccumulationAmount());
		assertEquals(new BigDecimal("117552.50"), data.get(2)
				.getAccumulationAmount());
		assertEquals(new BigDecimal("127193.41"), data.get(3)
				.getAccumulationAmount());
	}

}
