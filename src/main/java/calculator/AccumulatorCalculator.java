package calculator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import model.FundAccumulation;
import model.FundFeeData;

public class AccumulatorCalculator {

	static public List<FundAccumulation> calculateCompoundGrowthWithFees(
			BigDecimal initialAmount, BigDecimal contributionAmount,
			Integer years, BigDecimal fees, BigDecimal yield) {
		BigDecimal actualYield = yield.subtract(fees);
		return calculateCompoundGrowth(initialAmount, contributionAmount,
				years, actualYield);
	}

	static public List<FundAccumulation> calculateWithFixedAndPercentageFees(
			BigDecimal initialAmount, BigDecimal contributionAmount,
			Integer years, BigDecimal fixedFee, BigDecimal percentageFee,
			BigDecimal yield) {
		BigDecimal actualYield = yield.subtract(percentageFee);
		return calculateFixedFeeCompoundGrowth(initialAmount, contributionAmount,
				years, actualYield, fixedFee);
	}

	static public List<FundAccumulation> calculateCompoundGrowth(
			BigDecimal initialAmount, BigDecimal contributionAmount,
			Integer years, BigDecimal yield) {
		return calculateFixedFeeCompoundGrowth(initialAmount, contributionAmount,
				years, yield, BigDecimal.ZERO);
	}

	static private List<FundAccumulation> calculateFixedFeeCompoundGrowth(
			BigDecimal initialAmount, BigDecimal contributionAmount,
			Integer years, BigDecimal yield, BigDecimal fixedFee) {

		List<FundAccumulation> accumulationByYear = new ArrayList<FundAccumulation>();
		accumulationByYear.add(new FundAccumulation(0, initialAmount));
		BigDecimal accumulatedAmount = initialAmount;
		for (int y = 1; y < years + 1; y++) {
			accumulatedAmount = (accumulatedAmount.multiply(yield.divide(
					BigDecimal.valueOf(100), FundFeeData.SCALE,
					FundFeeData.ROUNDING_MODE))).add(accumulatedAmount);
			accumulatedAmount = accumulatedAmount.add(contributionAmount);
			accumulatedAmount = accumulatedAmount.subtract(fixedFee);

			accumulationByYear.add(new FundAccumulation(y, accumulatedAmount));
		}
		return accumulationByYear;
	}

}
