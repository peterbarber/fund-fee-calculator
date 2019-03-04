package webservices;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import model.AdviserData;
import service.AdviserCostCalculator;

@Path("/advisercostcalculator")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdviserCostCalculatorWebService {
	@Context
	private UriInfo context;

	private AdviserCostCalculator adviserCostCalculator = new AdviserCostCalculator();

	public AdviserCostCalculatorWebService() {
	}

	@POST
	@Path("/compare")
	public AdviserData compare(AdviserCostRequest request) {

		System.out.println(request);

		return adviserCostCalculator.getData(request.getInitialInvestment(),
				request.getYearlyInvestment(), request.getYears(),
				request.getFunds());
	}
}
