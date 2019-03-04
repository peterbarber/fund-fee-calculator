package service;

import java.math.BigDecimal;

import model.FundFeeData;

public interface FundFeeCalculatorService {

	FundFeeData calculateFromAge(BigDecimal initialAmount, BigDecimal contributionAmount, Integer startAge, Integer endAge, BigDecimal fees,
			BigDecimal yield);

	FundFeeData calculate(BigDecimal initialAmount, BigDecimal contributionAmount, 
			Integer years, BigDecimal fees, BigDecimal yield);

}
