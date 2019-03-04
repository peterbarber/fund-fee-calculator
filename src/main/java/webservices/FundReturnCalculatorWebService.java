package webservices;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import model.Fund;
import service.SuperAnnuationCalculator;

/**
 * Root resource (exposed at "fundreturncalculator" path)
 * 
 * 
 * DISABLED as conflicts with FundFeeCalculatorWebService!
 * 
 */
@Path("/fundreturncalculator")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FundReturnCalculatorWebService {
	@Context
	private UriInfo context;
	
	private SuperAnnuationCalculator superAnnuationCalculator = new SuperAnnuationCalculator();

	public FundReturnCalculatorWebService() {}
	
	@POST
	@Path("/compareSuperannuationFunds")
	public List<Fund> compareSuperannuationFunds(FundReturnCalculatorRequest request) {

		System.out.println(request);
		
		return superAnnuationCalculator.compare(request.initialInvestmentAmount, request.contribution, request.salary, 
				request.startAge, request.endAge, request.taxPercent, request.funds);
	}
}
