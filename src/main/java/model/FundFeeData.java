package model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FundFeeData {

	public static int ROUNDING_MODE = BigDecimal.ROUND_HALF_EVEN;
	public static int SCALE = 5;

	private List<FundAccumulation> accumulationWithFees;
	private List<FundAccumulation> accumulationWithoutFees;
	private BigDecimal finalAccumulationAmountWithFees;
	private BigDecimal finalAccumulationAmountWithoutFees;
	private BigDecimal totalFeesAmount;
	private BigDecimal totalFeesPercent;
	private Integer startAge = 0;
	private Integer endAge = 0;
	private BigDecimal fee;

	public FundFeeData(List<FundAccumulation> accumulationWithFees, List<FundAccumulation> accumulationWithoutFees) {
		this.accumulationWithFees = accumulationWithFees;
		this.accumulationWithoutFees = accumulationWithoutFees;

		finalAccumulationAmountWithFees = getFinalAccumulationWithFees();
		finalAccumulationAmountWithoutFees = getFinalAccumulationWithoutFees();

		totalFeesAmount = getFinalAccumulationWithoutFees().subtract(getFinalAccumulationWithFees());
		if (totalFeesAmount.compareTo(BigDecimal.ZERO) > 0) {
			totalFeesPercent = totalFeesAmount.divide(getFinalAccumulationWithoutFees(), SCALE, ROUNDING_MODE).multiply(BigDecimal.valueOf(100));
		} else {
			totalFeesPercent = BigDecimal.ZERO;
		}
	}

	public List<FundAccumulation> getAccumulationByYearWithFees() {
		return accumulationWithFees;
	}

	public List<FundAccumulation> getAccumulationByYearWithoutFees() {
		return accumulationWithoutFees;
	}

	private BigDecimal getFinalAccumulationWithFees() {
		return this.accumulationWithFees.get(accumulationWithFees.size() - 1).getAccumulationAmount();
	}

	private BigDecimal getFinalAccumulationWithoutFees() {
		return this.accumulationWithoutFees.get(accumulationWithoutFees.size() - 1).getAccumulationAmount();
	}

	public BigDecimal getTotalFeesAmount() {
		return totalFeesAmount;
	}

	/*
	 * var data = google.visualization.arrayToDataTable([ [ 'Years', 'No Fees',
	 * 'With Fees' ], [ '5', 10000, 10000 ], [ '10', 20000, 15000 ], [ '15',
	 * 30000, 25000 ], [ '20', 60000, 35000 + x ], [ '25', 100000, 70000 + x ],
	 * [ '30', 150000, 122000 + x ] ]);
	 */
	public List<List> getDataTable() {
		List<List> dataTable = new ArrayList<List>();
		dataTable.add(Arrays.asList("Years", "No Fees", "With Fees"));

		Iterator<FundAccumulation> withFeesIterator = accumulationWithFees.iterator();
		for (FundAccumulation noFees : accumulationWithoutFees) {
			List<Object> col = new ArrayList<Object>();
			col.add(Integer.valueOf(noFees.getYear()));
			col.add(noFees.getAccumulationAmount());
			col.add(withFeesIterator.next().getAccumulationAmount());
			dataTable.add(col);
		}
		return dataTable;
	}

	public List<List> getDataTableByAge() {
		List<List> dataTable = new ArrayList<List>();
		dataTable.add(Arrays.asList("Age", "No Fees", "With Fees"));

		Iterator<FundAccumulation> withFeesIterator = accumulationWithFees.iterator();
		for (FundAccumulation noFees : accumulationWithoutFees) {
			List<Object> col = new ArrayList<Object>();
			col.add(Integer.valueOf(noFees.getYear() + startAge));
			col.add(noFees.getAccumulationAmount());
			col.add(withFeesIterator.next().getAccumulationAmount());
			dataTable.add(col);
		}
		return dataTable;
	}

	public BigDecimal getTotalFeesPercent() {
		return totalFeesPercent.setScale(2, FundFeeData.ROUNDING_MODE);
	}

	public void setTotalFeesPercent(BigDecimal totalFeesPercent) {
		this.totalFeesPercent = totalFeesPercent;
	}

	public BigDecimal getFinalAccumulationAmountWithFees() {
		return finalAccumulationAmountWithFees.setScale(2, FundFeeData.ROUNDING_MODE);
	}

	public BigDecimal getFinalAccumulationAmountWithoutFees() {
		return finalAccumulationAmountWithoutFees.setScale(2, FundFeeData.ROUNDING_MODE);
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

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public BigDecimal getFee() {
		return fee;
	}

}
