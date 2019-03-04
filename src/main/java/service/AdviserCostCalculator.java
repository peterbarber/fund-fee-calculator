package service;

import java.math.BigDecimal;
import java.util.List;

import calculator.AccumulatorCalculator;
import model.AdviserData;
import model.Fund;
import model.FundAccumulation;

public class AdviserCostCalculator {

	List<Fund> compare(BigDecimal initialAmount,
			BigDecimal additionalContrib, Integer years, List<Fund> funds) {

		for (Fund fund : funds) {
			List<FundAccumulation> accumulations = AccumulatorCalculator
					.calculateWithFixedAndPercentageFees(initialAmount,
							additionalContrib, years, fund.getFixedFeePa(), 
							fund.getPercentageFeePa(), fund.getYieldPa());
			fund.setFundAccumulations(accumulations);
		}
		return funds;
	}

	public AdviserData getData(BigDecimal initialInvestment,
			BigDecimal yearlyContribution, Integer years, List<Fund> funds) {
		List<Fund> retFunds = compare(initialInvestment, yearlyContribution, years, funds);
		
		AdviserData ad = new AdviserData(retFunds.get(0), retFunds.get(1), retFunds.get(2));
		return ad;
	}

}
