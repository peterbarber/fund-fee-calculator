package model;

import java.math.BigDecimal;

public class FundAccumulation {
	
	private Integer year;
	private BigDecimal accumulationAmount;

	public FundAccumulation(Integer year, BigDecimal accumulationAmount) {
		super();
		this.year = year;
		this.accumulationAmount = accumulationAmount;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public BigDecimal getAccumulationAmount() {
		// round to 2 decimal places - do not use this in calculations!
		return accumulationAmount.setScale(2, FundFeeData.ROUNDING_MODE);
	}

	public void setAccumulationAmount(BigDecimal accumulationAmount) {
		this.accumulationAmount = accumulationAmount;
	}


}
