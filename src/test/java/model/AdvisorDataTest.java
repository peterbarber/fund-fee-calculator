package model;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class AdvisorDataTest {

	@Mock private Fund zeroFeeFund;
	@Mock private Fund flatFeeFund;
	@Mock private Fund assetFeeFund;

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldGetFeeCost() {
		Mockito.when(zeroFeeFund.getFinalBalance()).thenReturn(new BigDecimal("110.00"));
		Mockito.when(flatFeeFund.getFinalBalance()).thenReturn(new BigDecimal("108.00"));
		Mockito.when(assetFeeFund.getFinalBalance()).thenReturn(new BigDecimal("105.00"));
				
		 AdviserData ad = new AdviserData(zeroFeeFund, flatFeeFund, assetFeeFund);
		 
		 assertEquals(new BigDecimal("2.00"), ad.getFlatFeeFundFeeCost());
		 assertEquals(new BigDecimal("5.00"), ad.getAssetFeeFundFeeCost());
		
	}


}
