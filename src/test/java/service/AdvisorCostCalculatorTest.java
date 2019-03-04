package service;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import model.AdviserData;
import model.Fund;

import org.junit.Before;
import org.junit.Test;

public class AdvisorCostCalculatorTest {

	private AdviserCostCalculator advisorCostCalculator;
	private List<Fund> funds;
	private BigDecimal initialInvestment;
	private Integer years;
	private BigDecimal yearlyContribution;

	@Before
	public void setUp() {
		advisorCostCalculator = new AdviserCostCalculator();
		funds = new ArrayList<Fund>();
	}

	@Test
	public void shouldGetTotalBalance() {
		initialInvestment = new BigDecimal(100);
		yearlyContribution = BigDecimal.ZERO;
		years = 1;
		Fund fund1 = new Fund("f", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.TEN);
		funds.add(fund1);

		List<Fund> resultFunds = advisorCostCalculator.compare(
				initialInvestment, yearlyContribution, years, funds);

		assertEquals(new BigDecimal("110.00"), resultFunds.get(0).getFinalBalance());
	}

	@Test
	public void shouldTotalBalancesFor3Funds() {
		//1 no fees
		Fund fund1 = new Fund("no fees", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.TEN);
		funds.add(fund1);

		//2 flat fee advisor
		Fund fund2 = new Fund("flat fees", BigDecimal.ZERO, BigDecimal.TEN, BigDecimal.TEN);
		funds.add(fund2);
		
		//3 asset based advisor
		Fund fund3 = new Fund("asset based fees", BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.TEN);
		funds.add(fund3);
		
		initialInvestment = new BigDecimal(100);
		yearlyContribution = BigDecimal.ZERO;
		years = 1;

		List<Fund> resultFunds = advisorCostCalculator.compare(
				initialInvestment, yearlyContribution, years, funds);

		assertEquals(new BigDecimal("110.00"), resultFunds.get(0).getFinalBalance());
		assertEquals(new BigDecimal("100.00"), resultFunds.get(1).getFinalBalance());
		assertEquals(new BigDecimal("109.00"), resultFunds.get(2).getFinalBalance());
	}
	
	@Test
	public void shouldGetFeeCost() {
		//1 no fees
		Fund fund1 = new Fund("no fees", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.TEN);
		funds.add(fund1);

		//2 flat fee advisor
		Fund fund2 = new Fund("flat fees", BigDecimal.ZERO, BigDecimal.TEN, BigDecimal.TEN);
		funds.add(fund2);
		
		//3 asset based advisor
		Fund fund3 = new Fund("asset based fees", BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.TEN);
		funds.add(fund3);
		
		initialInvestment = new BigDecimal(100);
		yearlyContribution = BigDecimal.ZERO;
		years = 1;
		
		 AdviserData ad = advisorCostCalculator.getData(initialInvestment, yearlyContribution, years, funds);
		 
		 assertEquals(new BigDecimal("10.00"), ad.getFlatFeeFundFeeCost());
		 assertEquals(new BigDecimal("1.00"), ad.getAssetFeeFundFeeCost());
		
	}

}
