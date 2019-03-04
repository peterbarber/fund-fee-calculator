package service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import model.Fund;
import model.FundAccumulation;
import model.FundFeeData;

public class SuperAnnuationCalculator {
	
	private static final BigDecimal CONTRIBUTION_TAX_RATE = new BigDecimal("0.08");
	private final BigDecimal SALARY_SUPER_PERCENTAGE = new BigDecimal("0.095");

	public List<Fund> compare(BigDecimal initialAmount, BigDecimal additionalContrib, BigDecimal salary,Integer startAge,
			Integer endAge, BigDecimal taxPercent, List<Fund> funds) {
		return compare(initialAmount, salary, startAge, endAge, taxPercent, funds);
	}
	
	public List<Fund> compare(BigDecimal initialAmount, BigDecimal salary,Integer startAge,
			Integer endAge, BigDecimal taxPercent, List<Fund> funds) {

		for (Fund fund : funds) {
			List<FundAccumulation> fundAccumulations = new ArrayList<FundAccumulation>();

			fundAccumulations.add(new FundAccumulation(startAge, initialAmount));
			BigDecimal yearlyAccumulatedAmount = initialAmount;
			for (int y = startAge+1; y <= endAge; y++) {
				yearlyAccumulatedAmount = addHalfSalaryContrib(salary, yearlyAccumulatedAmount);
				BigDecimal amountAfterFees = subtractFees(yearlyAccumulatedAmount, fund.getFixedFeePa(), fund.getPercentageFeePa());
				BigDecimal amountAfterGrowth = addGrowth(fund, amountAfterFees);
				yearlyAccumulatedAmount = minuxTax(taxPercent, amountAfterGrowth, amountAfterFees);
				//yearlyAccumulatedAmount = addContribution(yearlyContributionAmount,yearlyAccumulatedAmount);
				yearlyAccumulatedAmount = addHalfSalaryContrib(salary, yearlyAccumulatedAmount);
				fundAccumulations.add(new FundAccumulation(y, yearlyAccumulatedAmount));
			}
			fund.setFundAccumulations(fundAccumulations);
		}
		return funds;
	}

	private BigDecimal addHalfSalaryContrib(BigDecimal salary,
			BigDecimal yearlyAccumulatedAmount) {
		BigDecimal contrib = calculateSuperContribsFromSalary(salary);
		contrib = deductContributionTax(salary, contrib);
		return yearlyAccumulatedAmount.add(contrib.divide(BigDecimal.valueOf(2), FundFeeData.SCALE,
				FundFeeData.ROUNDING_MODE));
	}

	private BigDecimal deductContributionTax(BigDecimal salary,
			BigDecimal contrib) {
		if(salary.compareTo(new BigDecimal("37000")) > 0) {
			contrib = contrib.subtract(contrib.multiply(CONTRIBUTION_TAX_RATE));
		}
		return contrib;
	}

	private BigDecimal minuxTax(BigDecimal taxPercent,
			BigDecimal amountAfterGrowth, BigDecimal amountAfterFees) {
		if(taxPercent==null || taxPercent.compareTo(BigDecimal.ZERO) <= 0) {
			return amountAfterGrowth;
		}
		
		taxPercent = taxPercent.divide(BigDecimal.valueOf(100), FundFeeData.SCALE,
				FundFeeData.ROUNDING_MODE);
		BigDecimal taxableAmount = amountAfterGrowth.subtract(amountAfterFees);
		BigDecimal tax = taxableAmount.multiply(taxPercent);
		return amountAfterGrowth.subtract(tax);
	}
	
	private BigDecimal addGrowth(Fund fund, BigDecimal yearlyAccumulatedAmount) {
		return (yearlyAccumulatedAmount.multiply(fund.getYieldPa()
				.divide(BigDecimal.valueOf(100), FundFeeData.SCALE,
						FundFeeData.ROUNDING_MODE))).add(yearlyAccumulatedAmount);
	}

	private BigDecimal addContribution(BigDecimal yearlyContributionAmount,
			BigDecimal yearlyAccumulatedAmount) {
		return yearlyContributionAmount.add(yearlyAccumulatedAmount);
	}
	
	private BigDecimal subtractFees(BigDecimal yearlyAccumulatedAmount, BigDecimal fixedFee, BigDecimal percentageFee) {
		yearlyAccumulatedAmount = yearlyAccumulatedAmount.subtract(fixedFee);
		BigDecimal feeAfterTaxRefund = percentageFee.multiply(new BigDecimal("0.85")); // reduce by 15%
		yearlyAccumulatedAmount = yearlyAccumulatedAmount.subtract(yearlyAccumulatedAmount.multiply(feeAfterTaxRefund.divide(BigDecimal.valueOf(100), FundFeeData.SCALE,
				FundFeeData.ROUNDING_MODE)));
		return yearlyAccumulatedAmount;
	}

	private BigDecimal calculateSuperContribsFromSalary(BigDecimal salary) {
		return salary.multiply(SALARY_SUPER_PERCENTAGE);
	}
}
