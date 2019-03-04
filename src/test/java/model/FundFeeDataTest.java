package model;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Work out fees based on total without fees. 
 * As really you would have earned interest on the money taken out for the fee!
 *
 */
public class FundFeeDataTest {

	private FundFeeData fundFeeData;
	private List<FundAccumulation> accumulationWithFees;
	private List<FundAccumulation> accumulationWithoutFees;
	
	@Before
	public void before() {
		accumulationWithFees = new ArrayList<FundAccumulation>();
		accumulationWithoutFees = new ArrayList<FundAccumulation>();
	}
	
	@Test
	public void shouldCalculateFeePercentage() {
		accumulationWithFees.add(new FundAccumulation(1, BigDecimal.valueOf(80)));
		accumulationWithoutFees.add(new FundAccumulation(1, BigDecimal.valueOf(100)));
		fundFeeData = new FundFeeData(accumulationWithFees, accumulationWithoutFees);

		assertEquals(new BigDecimal("20.00"),fundFeeData.getTotalFeesPercent());
	}
	
	@Test
	public void shouldCalculateFinalTotals() {
		accumulationWithFees.add(new FundAccumulation(1, BigDecimal.valueOf(80)));
		accumulationWithoutFees.add(new FundAccumulation(1, BigDecimal.valueOf(100)));
		fundFeeData = new FundFeeData(accumulationWithFees, accumulationWithoutFees);

		assertEquals(new BigDecimal("80.00"),fundFeeData.getFinalAccumulationAmountWithFees());
		assertEquals(new BigDecimal("100.00"),fundFeeData.getFinalAccumulationAmountWithoutFees());
	}

	@Test
	public void shouldCalculateFeesAmount() {
		accumulationWithFees.add(new FundAccumulation(1, BigDecimal.valueOf(80)));
		accumulationWithoutFees.add(new FundAccumulation(1, BigDecimal.valueOf(100)));
		fundFeeData = new FundFeeData(accumulationWithFees, accumulationWithoutFees);

		assertEquals(new BigDecimal("20.00"),fundFeeData.getTotalFeesAmount());
	}
}
