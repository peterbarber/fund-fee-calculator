package model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


public class AdviserData {

	private Fund zeroFeeFund;
	private Fund flatFeeFund;
	private Fund assetFeeFund;
	
	public AdviserData(Fund zeroFeeFund, Fund flatFeeFund, Fund assetFeeFund) {
		super();
		this.zeroFeeFund = zeroFeeFund;
		this.flatFeeFund = flatFeeFund;
		this.assetFeeFund = assetFeeFund;
	}
	
	public List<List> getDataTable() {
		List<List> dataTable = new ArrayList<List>();
		dataTable.add(Arrays.asList("Years", "Flat Fees", "Asset Fees"));

		Iterator<FundAccumulation> flatFeesIterator = flatFeeFund.getFundAccumulations().iterator();
		for (FundAccumulation assetFees : assetFeeFund.getFundAccumulations()) {
			List<Object> col = new ArrayList<Object>();
			col.add(Integer.valueOf(assetFees.getYear()));
			col.add(flatFeesIterator.next().getAccumulationAmount());
			col.add(assetFees.getAccumulationAmount());
			dataTable.add(col);
		}
		return dataTable;
	}


	public  BigDecimal getFlatFeeFundFeeCost() {
		return zeroFeeFund.getFinalBalance().subtract(flatFeeFund.getFinalBalance());
	}

	public BigDecimal getAssetFeeFundFeeCost() {
		return zeroFeeFund.getFinalBalance().subtract(assetFeeFund.getFinalBalance());
	}

	public Fund getAssetFeeFund() {
		return assetFeeFund;
	}

	public Fund getZeroFeeFund() {
		return zeroFeeFund;
	}

	public Fund getFlatFeeFund() {
		return flatFeeFund;
	}

	@Override
	public String toString() {
		return "AdviserData [getDataTable()=" + getDataTable()
				+ ", getFlatFeeFundFeeCost()=" + getFlatFeeFundFeeCost()
				+ ", getAssetFeeFundFeeCost()=" + getAssetFeeFundFeeCost()
				+ ", getAssetFeeFund()=" + getAssetFeeFund()
				+ ", getZeroFeeFund()=" + getZeroFeeFund()
				+ ", getFlatFeeFund()=" + getFlatFeeFund() + "]";
	}
	
	

}
