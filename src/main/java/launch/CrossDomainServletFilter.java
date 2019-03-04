package launch;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CrossDomainServletFilter implements Filter {

	private static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
	private static final String ORIGIN = "Origin";
	private static final List<String> originWhiteList = Arrays.asList(
			"^https?://(www.)?spoonfedinvestor.com",
			"^https?://(www.)?mensmoneyhealth.com.au",
			"^https?://(www.)?moneyweapons.com",
			"^http://fundfeecalculator.herokuapp.com");

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		handle((HttpServletRequest) request, (HttpServletResponse) response,
				chain);
	}

	private void handle(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String origin = request.getHeader(ORIGIN);
		if (origin == null) { // allow same origin (origin header not sent)
			response.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, "");
		}
//		if (matchUrls(originWhiteList, origin)) {
//			response.gethHeaders().add(ACCESS_CONTROL_ALLOW_ORIGIN, origin); // allowed
//		} else {
//			cres.getHttpHeaders().add(ACCESS_CONTROL_ALLOW_ORIGIN, ""); // not
//																		// allowed
//		}
//		cres.getHttpHeaders().add("Access-Control-Allow-Headers",
//				"origin, content-type, accept, authorization");
//		cres.getHttpHeaders().add("Access-Control-Allow-Credentials", "true");
//		cres.getHttpHeaders().add("Access-Control-Allow-Methods",
//				"GET, POST, PUT, DELETE, OPTIONS, HEAD");
//		cres.getHttpHeaders().add("Access-Control-Max-Age", "1209600");
//		return cres;

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

	private Boolean matchUrls(List<String> whitelistUrls, String origin) {
		for (String whitelistUrl : whitelistUrls) {
			if (matchUrl(whitelistUrl, origin)) {
				return true;
			}
		}
		return false;
	}

	private Boolean matchUrl(String regex, String url) {
		return Pattern.matches(regex, url);
	}

}
