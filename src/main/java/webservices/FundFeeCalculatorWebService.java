package webservices;

import java.math.BigDecimal;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import model.FundFeeData;
import service.FundFeeCalculatorService;
import service.FundFeeCalculatorServiceImpl;

/**
 * Root resource (exposed at "fundfeecalculator" path)
 */
@Path("/fundfeecalculator")
@Produces(MediaType.APPLICATION_JSON)
public class FundFeeCalculatorWebService {
	@Context
	private UriInfo context;
	
	private FundFeeCalculatorService fundFeeCalculatorService = new FundFeeCalculatorServiceImpl();

	public FundFeeCalculatorWebService() {}

	@GET
	public FundFeeData getData(@QueryParam("initial") BigDecimal initialInvestmentAmount,
			@QueryParam("contribution") BigDecimal contribution,
			@QueryParam("years") Integer years,
			@QueryParam("fees") BigDecimal fees,
			@QueryParam("yield") BigDecimal yield) {
		
		System.out.println(initialInvestmentAmount);
		System.out.println(contribution);
		System.out.println(years);
		System.out.println(fees);
		System.out.println(yield);
		
		return fundFeeCalculatorService.calculate(initialInvestmentAmount, contribution, years, fees, yield);
	}
	
	@GET
	@Path("/byAge")
	public FundFeeData getDataByAge(@QueryParam("initial") BigDecimal initialInvestmentAmount,
			@QueryParam("contribution") BigDecimal contribution,
			@QueryParam("startAge") Integer startAge,
			@QueryParam("endAge") Integer endAge,
			@QueryParam("fees") BigDecimal fees,
			@QueryParam("yield") BigDecimal yield) {
		
		System.out.println(initialInvestmentAmount);
		System.out.println(contribution);
		System.out.println(startAge);
		System.out.println(endAge);
		System.out.println(fees);
		System.out.println(yield);
		
		return fundFeeCalculatorService.calculateFromAge(initialInvestmentAmount, contribution, startAge, endAge, fees, yield);
	}
}
