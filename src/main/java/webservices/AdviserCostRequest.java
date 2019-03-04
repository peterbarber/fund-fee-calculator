package webservices;

import java.math.BigDecimal;
import java.util.List;

import model.Fund;

public class AdviserCostRequest {
	
	private BigDecimal initialInvestment;
	private BigDecimal yearlyInvestment;
	private Integer years;
	private List<Fund> funds;
	
	public BigDecimal getInitialInvestment() {
		return initialInvestment;
	}
	public void setInitialInvestment(BigDecimal initialInvestment) {
		this.initialInvestment = initialInvestment;
	}
	public BigDecimal getYearlyInvestment() {
		return yearlyInvestment;
	}
	public void setYearlyInvestment(BigDecimal yearlyInvestment) {
		this.yearlyInvestment = yearlyInvestment;
	}
	
	public Integer getYears() {
		return years;
	}
	public void setYears(Integer years) {
		this.years = years;
	}
	public List<Fund> getFunds() {
		return funds;
	}
	public void setFunds(List<Fund> funds) {
		this.funds = funds;
	}
	@Override
	public String toString() {
		return "AdviserCostRequest [initialInvestment=" + initialInvestment
				+ ", yearlyInvestment=" + yearlyInvestment + ", years=" + years
				+ ", funds=" + funds + "]";
	}
	

}
