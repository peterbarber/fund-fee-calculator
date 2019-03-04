package launch;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;

/**
 * Allow the system to serve xhr level 2 from all cross domain site
 */
public class CrossDomainFilter implements ContainerResponseFilter {
    private static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
	private static final String ORIGIN = "Origin";
    private static final List<String> originWhiteList = Arrays.asList("^https?://(www.)?spoonfedinvestor.com",
    		"^https?://(www.)?mensmoneyhealth.com.au", "^https?://(www.)?moneyweapons.com", "^http://fundfeecalculator.herokuapp.com");
    
	/**
     * Add the cross domain data to the output if needed
     *
     * @param creq The container request (input)
     * @param cres The container request (output)
     * @return The output request with cross domain if needed
     */
    @Override
    public ContainerResponse filter(ContainerRequest creq, ContainerResponse cres) {
    	String origin = creq.getHeaderValue(ORIGIN);
	if(origin == null) { // allow same origin (origin header not sent)
		return cres;
	}
    	if(matchUrls(originWhiteList, origin)) {
    		cres.getHttpHeaders().add(ACCESS_CONTROL_ALLOW_ORIGIN, origin); //allowed
    	} else {
    		cres.getHttpHeaders().add(ACCESS_CONTROL_ALLOW_ORIGIN, ""); //not allowed
    	}
        cres.getHttpHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
        cres.getHttpHeaders().add("Access-Control-Allow-Credentials", "true");
        cres.getHttpHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        cres.getHttpHeaders().add("Access-Control-Max-Age", "1209600");
        return cres;
    }
    
    private Boolean matchUrls(List<String> whitelistUrls, String origin) {
    	for (String whitelistUrl : whitelistUrls) {
			if(matchUrl(whitelistUrl, origin)) {
				return true;
			}
		}
    	return false;
    }
    
    private Boolean matchUrl(String regex, String url) {
    	return Pattern.matches(regex, url);
    }
}
