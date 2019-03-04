package model;

import java.math.BigDecimal;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class InputValidation {
	
	public InputValidation(BigDecimal initialAmount, BigDecimal contributionAmount, Integer years, BigDecimal fees, BigDecimal yield) {
		super();
		this.initialAmount = initialAmount;
		this.contributionAmount = contributionAmount;
		this.years = years;
		this.fees = fees;
		this.yield = yield;
	}
	@NotNull
	@Min(0)
	@Max(1000000000)
	private BigDecimal initialAmount;
	
	@NotNull
	@Min(0)
	@Max(1000000000)
	private BigDecimal contributionAmount;
	
	@NotNull
	@Min(0)
	@Max(100)
	private Integer years;
	
	@NotNull
	@Min(0)
	@Max(10)
	private BigDecimal fees;
	
	@NotNull
	@Min(0)
	@Max(30)
	private BigDecimal yield;

	public BigDecimal getInitialAmount() {
		return initialAmount;
	}

	public void setInitialAmount(BigDecimal initialAmount) {
		this.initialAmount = initialAmount;
	}

	public BigDecimal getContributionAmount() {
		return contributionAmount;
	}

	public void setContributionAmount(BigDecimal contributionAmount) {
		this.contributionAmount = contributionAmount;
	}

	public Integer getYears() {
		return years;
	}

	public void setYears(Integer years) {
		this.years = years;
	}

	public BigDecimal getFees() {
		return fees;
	}

	public void setFees(BigDecimal fees) {
		this.fees = fees;
	}

	public BigDecimal getYield() {
		return yield;
	}

	public void setYield(BigDecimal yield) {
		this.yield = yield;
	}
	
}
