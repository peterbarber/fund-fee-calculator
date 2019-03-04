package service;

import java.math.BigDecimal;

import calculator.AccumulatorCalculator;
import model.FundFeeData;

public class FundFeeCalculatorServiceImpl implements FundFeeCalculatorService {

	@Override
	public FundFeeData calculate(BigDecimal initialAmount,
			BigDecimal contributionAmount, Integer years, BigDecimal fees,
			BigDecimal yield) {
		validateInput(initialAmount, contributionAmount, years, fees, yield);
		FundFeeData fundFeeData = new FundFeeData(AccumulatorCalculator.calculateCompoundGrowthWithFees(
				initialAmount, contributionAmount, years, fees, yield),
				AccumulatorCalculator.calculateCompoundGrowth(initialAmount, contributionAmount, years,
						yield));
		return fundFeeData;
	}

	@Override
	public FundFeeData calculateFromAge(BigDecimal initialAmount,
			BigDecimal contributionAmount, Integer startAge, Integer endAge,
			BigDecimal fees, BigDecimal yield) {
		Integer years = endAge - startAge;
		validateInput(initialAmount, contributionAmount, years, fees, yield);
		FundFeeData fundFeeData = new FundFeeData(AccumulatorCalculator.calculateCompoundGrowthWithFees(
				initialAmount, contributionAmount, years, fees, yield),
				AccumulatorCalculator.calculateCompoundGrowth(initialAmount, contributionAmount, years,
						yield));
		fundFeeData.setStartAge(startAge);
		fundFeeData.setEndAge(endAge);
		fundFeeData.setFee(fees);
		return fundFeeData;
	}

	
	private void validateInput(BigDecimal initialAmount,
			BigDecimal contributionAmount, Integer years, BigDecimal fees,
			BigDecimal yield) {
		// BigDecimal oneBillion = new BigDecimal(1000000000);
		// if(initialAmount.compareTo(oneBillion) > 0) {
		// throw new IllegalArgumentException("> 1 billion");
		// }

	}

}
