import static org.junit.Assert.*;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;


public class WebDataFetcherTest {

	private WebDataFetcher webDataFetcher; 
	
	@Before
	public void before() {
		webDataFetcher = new WebDataFetcher();
	}
	
	@Test
	public void testGetSomeData() throws IOException {
		Document doc = webDataFetcher.getData();
		assertNotNull(doc);
	}
	
//	@Test
//	public void testGetSuperAnnuationData() throws IOException {
//		Document doc = webDataFetcher.getData();
//		assertEquals("", doc.select(""));
//	}

}
