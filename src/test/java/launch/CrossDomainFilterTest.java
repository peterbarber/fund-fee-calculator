package launch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponse;

public class CrossDomainFilterTest {

	private static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
	private static final String HTTP_WWW_SPOONFEDINVESTOR_COM = "http://www.spoonfedinvestor.com";
	private static final String HTTP_WWW_MMH_COM = "http://www.mensmoneyhealth.com.au";
	private static final String HTTPS_WWW_MMH_COM = "https://www.mensmoneyhealth.com.au";
	private static final String HTTP_MMH_COM = "http://mensmoneyhealth.com.au";
	private static final String HTTP_HEROKU_COM = "http://fundfeecalculator.herokuapp.com";
	private static final String HTTP_MONEY_WEAPONS_COM = "http://moneyweapons.com";

	private static final String ORIGIN = "Origin";
	private CrossDomainFilter crossDomainFilter;
	@Mock
	private ContainerRequest creq;
	private ContainerResponse cres;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		crossDomainFilter = new CrossDomainFilter();
		cres = new ContainerResponse(null, null, null);
	}
	
	@Test
	public void testNotAllowedOrigin() {
		Mockito.when(creq.getHeaderValue(ORIGIN)).thenReturn("http://not allowed!");
		
		cres = crossDomainFilter.filter(creq, cres);
		
		String access = (String) cres.getHttpHeaders().get(ACCESS_CONTROL_ALLOW_ORIGIN).get(0);
		assertEquals("", access);
	}
	
	@Test
	public void shouldAllowSpoonfed() {
		Mockito.when(creq.getHeaderValue(ORIGIN)).thenReturn(HTTP_WWW_SPOONFEDINVESTOR_COM);
		
		cres = crossDomainFilter.filter(creq, cres);
		
		String access = (String) cres.getHttpHeaders().get(ACCESS_CONTROL_ALLOW_ORIGIN).get(0);
		assertEquals(HTTP_WWW_SPOONFEDINVESTOR_COM, access);
	}
	
	@Test
	public void shouldAllowMensMoneyHealth() {
		Mockito.when(creq.getHeaderValue(ORIGIN)).thenReturn(HTTP_WWW_MMH_COM);
		
		cres = crossDomainFilter.filter(creq, cres);
		
		String access = (String) cres.getHttpHeaders().get(ACCESS_CONTROL_ALLOW_ORIGIN).get(0);
		assertEquals(HTTP_WWW_MMH_COM, access);
	}
	
	@Test
	public void shouldAllowMensMoneyHealthSecure() {
		Mockito.when(creq.getHeaderValue(ORIGIN)).thenReturn(HTTPS_WWW_MMH_COM);
		
		cres = crossDomainFilter.filter(creq, cres);
		
		String access = (String) cres.getHttpHeaders().get(ACCESS_CONTROL_ALLOW_ORIGIN).get(0);
		assertEquals(HTTPS_WWW_MMH_COM, access);
	}
	
	@Test
	public void shouldAllowMensMoneyHealthNakedDomain() {
		Mockito.when(creq.getHeaderValue(ORIGIN)).thenReturn(HTTP_MMH_COM);
		
		cres = crossDomainFilter.filter(creq, cres);
		
		String access = (String) cres.getHttpHeaders().get(ACCESS_CONTROL_ALLOW_ORIGIN).get(0);
		assertEquals(HTTP_MMH_COM, access);
	}

	@Test
	public void shouldAllowHerokuDomain() {
		Mockito.when(creq.getHeaderValue(ORIGIN)).thenReturn(HTTP_HEROKU_COM);
		
		cres = crossDomainFilter.filter(creq, cres);
		
		String access = (String) cres.getHttpHeaders().get(ACCESS_CONTROL_ALLOW_ORIGIN).get(0);
		assertEquals(HTTP_HEROKU_COM, access);
	}

	@Test
	public void shouldAllowMoneyWeaponsDomain() {
		Mockito.when(creq.getHeaderValue(ORIGIN)).thenReturn(HTTP_MONEY_WEAPONS_COM);
		
		cres = crossDomainFilter.filter(creq, cres);
		
		String access = (String) cres.getHttpHeaders().get(ACCESS_CONTROL_ALLOW_ORIGIN).get(0);
		assertEquals(HTTP_MONEY_WEAPONS_COM, access);
	}

	@Test
	public void shouldSameOriginWhenNoOriginHeaderSent() {
		Mockito.when(creq.getHeaderValue(ORIGIN)).thenReturn(null);
		
		cres = crossDomainFilter.filter(creq, cres);
		
		assertNull(cres.getHttpHeaders().get(ACCESS_CONTROL_ALLOW_ORIGIN));
	}

}
