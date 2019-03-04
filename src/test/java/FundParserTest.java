import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class FundParserTest {

	private FundParser fundParser;
	
	@Before	
	public void before() {
		fundParser = new FundParser();
	}
	
	@Test
	public void testSize() throws IOException {
		List<Fund>funds = fundParser.parse();
		assertEquals(3,funds.size());
	}
	
	@Test
	public void testParse() throws IOException {
		List<Fund>funds = fundParser.parse();
		assertEquals("$78",funds.get(0).getAdminFee());
	}
	
}
