package model;

import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import model.FundAccumulation;

@XmlRootElement
public class Fund {
	
	public static int ROUNDING_MODE = BigDecimal.ROUND_HALF_EVEN;
	public static int SCALE = 5;
	
	private String name;
	private BigDecimal percentageFeePa = BigDecimal.ZERO;
	private BigDecimal fixedFeePa = BigDecimal.ZERO;
	private BigDecimal yieldPa = BigDecimal.ZERO;
	
	List<FundAccumulation> fundAccumulations;
	
	public Fund() {
		super();
	}
	
	public Fund(String name, BigDecimal percentageFeePa, BigDecimal fixedFeePa,
			BigDecimal yieldPa) {
		super();
		this.name = name;
		this.percentageFeePa = percentageFeePa;
		this.fixedFeePa = fixedFeePa;
		this.yieldPa = yieldPa;
	}
	
	public BigDecimal getFinalBalance() {
		return this.fundAccumulations.get(this.fundAccumulations.size()-1).getAccumulationAmount();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<FundAccumulation> getFundAccumulations() {
		return fundAccumulations;
	}
	public void setFundAccumulations(List<FundAccumulation> fundAccumulations) {
		this.fundAccumulations = fundAccumulations;
	}
	public BigDecimal getPercentageFeePa() {
		return percentageFeePa;
	}
	public void setPercentageFeePa(BigDecimal percentageFeePa) {
		this.percentageFeePa = percentageFeePa;
	}
	public BigDecimal getFixedFeePa() {
		return fixedFeePa;
	}
	public void setFixedFeePa(BigDecimal fixedFeePa) {
		this.fixedFeePa = fixedFeePa;
	}
	public BigDecimal getYieldPa() {
		return yieldPa;
	}
	public void setYieldPa(BigDecimal yieldPa) {
		this.yieldPa = yieldPa;
	}

	@Override
	public String toString() {
		return "Fund [name=" + name + ", percentageFeePa=" + percentageFeePa
				+ ", fixedFeePa=" + fixedFeePa + ", yieldPa=" + yieldPa
				+ ", fundAccumulations=" + fundAccumulations + "]";
	}
	
	

}
