package webservices;

import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import model.Fund;

@XmlRootElement
public class FundReturnCalculatorRequest {
	BigDecimal initialInvestmentAmount;
	BigDecimal salary;
	BigDecimal contribution;
	Integer startAge;
	Integer endAge;
	BigDecimal taxPercent;
	
	List<Fund> funds;

	public BigDecimal getInitialInvestmentAmount() {
		return initialInvestmentAmount;
	}
	public void setInitialInvestmentAmount(BigDecimal initialInvestmentAmount) {
		this.initialInvestmentAmount = initialInvestmentAmount;
	}
	public BigDecimal getSalary() {
		return salary;
	}
	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}
	public BigDecimal getContribution() {
		return contribution;
	}
	public void setContribution(BigDecimal contribution) {
		this.contribution = contribution;
	}
	public Integer getStartAge() {
		return startAge;
	}
	public void setStartAge(Integer startAge) {
		this.startAge = startAge;
	}
	public Integer getEndAge() {
		return endAge;
	}
	public void setEndAge(Integer endAge) {
		this.endAge = endAge;
	}
	public List<Fund> getFunds() {
		return funds;
	}
	public void setFunds(List<Fund> funds) {
		this.funds = funds;
	}
	
	public BigDecimal getTaxPercent() {
		return taxPercent;
	}
	public void setTaxPercent(BigDecimal taxPercent) {
		this.taxPercent = taxPercent;
	}
	@Override
	public String toString() {
		return "FundReturnCalculatorRequest [initialInvestmentAmount="
				+ initialInvestmentAmount + ", salary=" + salary
				+ ", contribution=" + contribution + ", startAge=" + startAge
				+ ", endAge=" + endAge + ", taxPercent=" + taxPercent
				+ ", funds=" + funds + "]";
	}
	
	
}