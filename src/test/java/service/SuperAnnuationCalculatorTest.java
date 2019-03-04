package service;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import model.Fund;

import org.junit.Before;
import org.junit.Test;

public class SuperAnnuationCalculatorTest {

	private BigDecimal ONE = BigDecimal.valueOf(1);
	private BigDecimal TWENTY = BigDecimal.valueOf(20);
	private BigDecimal FIVE = BigDecimal.valueOf(5);
	private BigDecimal ZERO = BigDecimal.ZERO.setScale(2, Fund.ROUNDING_MODE);
	private BigDecimal ONE_HUNDRED_THOUSAND = BigDecimal.valueOf(100000);
	
	private SuperAnnuationCalculator superCalculator;
	private BigDecimal currentBalance;
	private BigDecimal additionalContributions;
	private Fund fund;
	private List<Fund> fundsIn;
	private BigDecimal salary;
	private Integer startAge;
	private Integer endAge;
	private BigDecimal earningTaxPercent;
	
	@Before
	public void before() {
		currentBalance = ZERO;
		additionalContributions = ZERO;
		salary = ZERO;
		startAge = 0;
		endAge = 0;
		superCalculator = new SuperAnnuationCalculator();
		fund = new Fund();
		fundsIn = new ArrayList<Fund>();
		fundsIn.add(fund);
	}
	
//	@Test(expected=NoFundSpecifiedException.class)
//	public void testNoFunds() {
//		fund = null;
//		superCalculator.compare(currentBalance, salary, additionalContributions, startAge, endAge, fund);
//	}
	
	@Test
	public void testSingleFundSingleYear() {
		startAge = 0;
		endAge = 0;
		List<Fund> funds = superCalculator.compare(currentBalance, salary, additionalContributions, startAge, endAge, earningTaxPercent, fundsIn);
		assertEquals(1, funds.size());
	}
	
	@Test
	public void testSingleFundOneYear() {
		startAge = 1;
		endAge = 2;
		List<Fund> funds = superCalculator.compare(currentBalance, salary, additionalContributions, startAge, endAge, earningTaxPercent, fundsIn);
		assertEquals(1, funds.size());
		assertEquals(2, funds.get(0).getFundAccumulations().size());
	}
	
	@Test
	public void shouldDoOneYearZeroFees() {
		List<Fund> funds = superCalculator.compare(currentBalance, salary, additionalContributions, 0, 0, earningTaxPercent, fundsIn);
		assertEquals(1, funds.get(0).getFundAccumulations().size());
		assertEquals(Integer.valueOf(0), funds.get(0).getFundAccumulations().get(0).getYear());
		assertEquals(ZERO, funds.get(0).getFundAccumulations().get(0).getAccumulationAmount());
	}
	
	@Test
	public void shouldSetCurrentAgeAndRetirementAge() {
		
		List<Fund> funds = superCalculator.compare(currentBalance, salary, additionalContributions, 30, 31, earningTaxPercent, fundsIn);
		
		assertEquals(new Integer(30), funds.get(0).getFundAccumulations().get(0).getYear());
		assertEquals(new Integer(31), funds.get(0).getFundAccumulations().get(1).getYear());
	}
	
	@Test
	public void shouldDo2YearsFixedFeeTakenStart2ndYear() {
		fund.setFixedFeePa(TWENTY);
		
		List<Fund> funds = superCalculator.compare(currentBalance, salary, additionalContributions, 0, 1, earningTaxPercent, fundsIn);
		
		assertEquals(2, funds.get(0).getFundAccumulations().size());
		assertEquals(new BigDecimal("-20.00"), funds.get(0).getFundAccumulations().get(1).getAccumulationAmount());
	}
	
	
	
//	@Test
//	public void shouldDoOneYearContribution() {
//		
//		List<Fund> data = superCalculator.compare(TWENTY, ZERO, FIVE, 0, 1, earningTaxPercent, fundsIn);
//		Fund fund = data.get(0);
//		assertEquals(2,fund.getFundAccumulations().size());
//		assertEquals(new BigDecimal("25.00"), fund.getFundAccumulations().get(1).getAccumulationAmount());
//	}
	
//	@Test
//	public void shouldDoTwoYearsContribution() {
//		List<Fund> data = superCalculator.compare(TWENTY, ZERO, FIVE, 0, 2, earningTaxPercent, fundsIn);
//		Fund fund = data.get(0);
//		assertEquals(3,fund.getFundAccumulations().size());
//		assertEquals(new BigDecimal("30.00"), fund.getFundAccumulations().get(2).getAccumulationAmount());
//	}
	
	@Test
	public void shouldDoOneYearInterest() {
		fund.setPercentageFeePa(ZERO);
		fund.setYieldPa(FIVE);
		List<Fund> data = superCalculator.compare(TWENTY, salary, additionalContributions, 0, 1, earningTaxPercent, fundsIn);
		Fund fund = data.get(0);
		assertEquals(2,fund.getFundAccumulations().size());
		assertEquals(new BigDecimal("21.00"), fund.getFundAccumulations().get(1).getAccumulationAmount());
	}
	
	@Test
	public void shouldDoTwoYearsInterest() {
		fund.setPercentageFeePa(ZERO);
		fund.setYieldPa(FIVE);
		List<Fund> data = superCalculator.compare(TWENTY,salary, additionalContributions, 0, 2, earningTaxPercent, fundsIn);
		Fund fund = data.get(0);
		assertEquals(3,fund.getFundAccumulations().size());
		assertEquals(new BigDecimal("22.05"), fund.getFundAccumulations().get(2).getAccumulationAmount());
	}
	
	/**
	 * bal: 100,000
	 * growth: 5%
	 * 2 years
	 * verified by moneysmart
	 */
	@Test
	public void shouldDoTwoYearsInteres100Thou() {
		fund.setPercentageFeePa(ZERO);
		fund.setYieldPa(FIVE);
		List<Fund> data = superCalculator.compare(ONE_HUNDRED_THOUSAND,salary, additionalContributions, 0, 2, earningTaxPercent, fundsIn);
		Fund fund = data.get(0);
		assertEquals(3,fund.getFundAccumulations().size());
		assertEquals(new BigDecimal("110250.00"), fund.getFundAccumulations().get(2).getAccumulationAmount());
	}
	
	/**
	 * verified by moneysmart
	 */
	@Test
public void shouldDoOneYearInterestMinusFees() {
		fund.setPercentageFeePa(BigDecimal.valueOf(3));
		fund.setYieldPa(FIVE);
		List<Fund> data = superCalculator.compare(ONE_HUNDRED_THOUSAND, salary, additionalContributions, 0, 1, earningTaxPercent, fundsIn);
		Fund fund = data.get(0);
		assertEquals(2,fund.getFundAccumulations().size());
		assertEquals(new BigDecimal("102322.50"), fund.getFundAccumulations().get(1).getAccumulationAmount());
	}
	
	/**
	 * 100,000 - 0.85%f + 5%g 
	 * 100,00 - 850 = 99150 + 5% 
	 * 100,000 = 99150 + 1.05 = 104107.5
	 * verified by moneysmart
	 */
	@Test
	public void testSingleYearFees() {
		startAge = 0;
		endAge = 1;
		fund.setPercentageFeePa(ONE);
		fund.setYieldPa(FIVE);
		List<Fund> funds = superCalculator.compare(ONE_HUNDRED_THOUSAND, salary, additionalContributions, startAge, endAge, earningTaxPercent, fundsIn);
		assertEquals(1, funds.size());
		assertEquals(new BigDecimal("104107.50"), funds.get(0).getFundAccumulations().get(1).getAccumulationAmount());
	}
	
	/**
	 * verified by moneysmart
	 */
	@Test
	public void testSingleYearFeesAndTax() {
		startAge = 0;
		endAge = 1;
		earningTaxPercent = new BigDecimal("8");
		fund.setPercentageFeePa(ONE);
		fund.setYieldPa(FIVE);
		List<Fund> funds = superCalculator.compare(ONE_HUNDRED_THOUSAND, salary, additionalContributions, startAge, endAge, earningTaxPercent, fundsIn);
		assertEquals(1, funds.size());
		assertEquals(new BigDecimal("103710.90"), funds.get(0).getFundAccumulations().get(1).getAccumulationAmount());
	}
	
	/**
	 * 100,000 +(9.5% of 10000 = 950) 
	 * = 100,950
	 * verified by moneysmart
	 */
	@Test
	public void testSingleYearWithSalary() {
		BigDecimal salary = BigDecimal.valueOf(10000);
		currentBalance = ONE_HUNDRED_THOUSAND;
		List<Fund> data = superCalculator.compare(currentBalance, salary, 0, 1, earningTaxPercent, fundsIn);
		Fund fund = data.get(0);
		assertEquals(new BigDecimal("100950.00"), fund.getFundAccumulations().get(1).getAccumulationAmount());
	}
	
	/**
	 * 100,000 +(9.5% of 10000 - 0%tax) 
	 * = 100,950
	 * no tax on super under salary of $37,000
	 * verified by moneysmart
	 */
	@Test
	public void testSingleYearWithSalaryAndTax() {
		BigDecimal salary = BigDecimal.valueOf(10000);
		currentBalance = ONE_HUNDRED_THOUSAND;
		List<Fund> data = superCalculator.compare(currentBalance, salary, 0, 1, earningTaxPercent, fundsIn);
		Fund fund = data.get(0);
		assertEquals(new BigDecimal("100950.00"), fund.getFundAccumulations().get(1).getAccumulationAmount());
	}
	
	/**
	 * 100,000 +(9.5% of 100000 - 15%tax = 9500-760=) 
	 * = 108,740
	 * verified by moneysmart
	 * 
	 */
	@Test
	public void testSingleYearWithSalaryHundredGrandAndTax() {
		BigDecimal salary = ONE_HUNDRED_THOUSAND;
		currentBalance = ONE_HUNDRED_THOUSAND;
		List<Fund> data = superCalculator.compare(currentBalance, salary, 0, 1, earningTaxPercent, fundsIn);
		Fund fund = data.get(0);
		assertEquals(new BigDecimal("108740.00"), fund.getFundAccumulations().get(1).getAccumulationAmount());
	}
	
	/**
	 * Half salary goes in current year, then other half end of year
	 * salary: (9.5% of 100000 - 8%tax = 9500-760=8740)
	 * growth: 100,000+4370 - 0.85%f +5%g = 
	 * 			= 103482.855 +5% = 108656.99775 
	 * total:	108656.99775 + 4370
	 * 
	 * verified by moneysmart
	 */
	@Test
	public void singleYearFeesWithSalary() {
		fund.setPercentageFeePa(ONE);
		fund.setYieldPa(FIVE);
		salary = ONE_HUNDRED_THOUSAND;
		currentBalance = ONE_HUNDRED_THOUSAND;
		List<Fund> data = superCalculator.compare(currentBalance, salary, 0, 1, earningTaxPercent, fundsIn);
		Fund fund = data.get(0);
		assertEquals(2,fund.getFundAccumulations().size());
		assertEquals(new BigDecimal("113027.00"), fund.getFundAccumulations().get(1).getAccumulationAmount());
	}
	
	/**
	 * Half contribution goes in current year, then other half end of year
	 * contribution: 2000
	 * growth: 100,000+1000 -1.275%f +8%g = 
	 * 			=  99712.25 + 8% = 107689.23
	 * total:	107689.23+1000 = 108,689.23
	 * 
	 */
//	@Test
//	public void shouldCalcAccumulateWithoutSalary() {
//		fund.setPercentageFeePa(BigDecimal.valueOf(1.5));
//		fund.setYieldPa(BigDecimal.valueOf(8));
//		List<Fund> data = superCalculator.compare(ONE_HUNDRED_THOUSAND,ZERO, BigDecimal.valueOf(2000),
//				0, 3, earningTaxPercent, fundsIn);
//		Fund fund = data.get(0);
//		assertEquals(4,fund.getFundAccumulations().size());
//		assertEquals(new BigDecimal("108500.00"), fund.getFundAccumulations().get(1).getAccumulationAmount());
//		assertEquals(new BigDecimal("117552.50"), fund.getFundAccumulations().get(2).getAccumulationAmount());
//		assertEquals(new BigDecimal("127193.41"), fund.getFundAccumulations().get(3).getAccumulationAmount());
//	}
	
	/**
	 * 100,000 - 20 = 99,980
	 * 
	 * verified by moneysmart
	 */
	@Test
	public void testFixedFee() {
		fund.setFixedFeePa(TWENTY);
		currentBalance = ONE_HUNDRED_THOUSAND;
		List<Fund> data = superCalculator.compare(currentBalance,salary, additionalContributions,
				0, 1, earningTaxPercent, fundsIn);
		Fund fund = data.get(0);
		assertEquals(new BigDecimal("99980.00"), fund.getFundAccumulations().get(1).getAccumulationAmount());
	}
	
	/**
	 * 100,000-20-(1.5%-15%) = 98480.3
	 * 99980 * 1.275% = 98705.255
	 * 
	 * verified by moneysmart
	 */
	@Test
	public void testFixedAndPercentageFee() {
		fund.setFixedFeePa(TWENTY);
		fund.setPercentageFeePa(BigDecimal.valueOf(1.5));
		currentBalance = ONE_HUNDRED_THOUSAND;
		List<Fund> data = superCalculator.compare(currentBalance,salary, additionalContributions,
				0, 1, earningTaxPercent, fundsIn);
		Fund fund = data.get(0);
		assertEquals(new BigDecimal("98705.26"), fund.getFundAccumulations().get(1).getAccumulationAmount());
	}
	
	/**
	 * 100,000-20-(1.5%-15%) + 8%g = 98480.3
	 * 99980 * 1.275% + 8%g = 98705.255 + 8%g = 106601.6754 
	 * 
	 * 106601.6754-20-1.275% + 8%g = 113640.58
	 * 
	 * 113640.58-20-1.275% + 8%g = 121145.67
	 * 
	 * verified by moneysmart
	 */
	@Test
	public void shouldCalcAccumulateWithFixedFee() {
		fund.setPercentageFeePa(BigDecimal.valueOf(1.5));
		fund.setYieldPa(BigDecimal.valueOf(8));
		fund.setFixedFeePa(TWENTY);
		currentBalance = ONE_HUNDRED_THOUSAND;
		List<Fund> data = superCalculator.compare(currentBalance,salary, additionalContributions,
				0, 3, earningTaxPercent, fundsIn);
		Fund fund = data.get(0);
		assertEquals(4,fund.getFundAccumulations().size());
		assertEquals(new BigDecimal("106601.68"), fund.getFundAccumulations().get(1).getAccumulationAmount());
		assertEquals(new BigDecimal("113640.58"), fund.getFundAccumulations().get(2).getAccumulationAmount());
		assertEquals(new BigDecimal("121145.67"), fund.getFundAccumulations().get(3).getAccumulationAmount());
	}
	
	@Test
	public void shouldDoOneYearByAge() {
		fund.setPercentageFeePa(ZERO);
		fund.setYieldPa(FIVE);
		currentBalance = TWENTY;
		List<Fund> data = superCalculator.compare(currentBalance,salary,additionalContributions,49,50,earningTaxPercent, fundsIn);
		Fund fund = data.get(0);
		assertEquals(2,fund.getFundAccumulations().size());
		assertEquals(new BigDecimal("21.00"), fund.getFundAccumulations().get(1).getAccumulationAmount());
	}
	
	@Test
	public void shouldSetFee() {
		BigDecimal fee = new BigDecimal("1.91");
		fund.setPercentageFeePa(fee);
		fund.setYieldPa(FIVE);
		currentBalance = TWENTY;
		List<Fund> data = superCalculator.compare(currentBalance,salary, additionalContributions,49,50,earningTaxPercent, fundsIn);
		Fund fund = data.get(0);
		assertEquals(fee,fund.getPercentageFeePa());
	}

}
