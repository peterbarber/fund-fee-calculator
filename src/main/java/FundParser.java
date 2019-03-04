import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class FundParser {

	private Logger log = Logger.getLogger(this.getClass().getName());

	public List<Fund> parse() throws IOException {

		File in = new File("src/test/resources/careSuperFundsData.txt");
		FileInputStream fis = new FileInputStream(in);
		String fileContent = IOUtils.toString(fis, "UTF-8");

		String decoded = java.net.URLDecoder.decode(fileContent, "UTF-8");
		decoded = java.net.URLDecoder.decode(decoded, "UTF-8");
		System.out.println(decoded);
		// log.log(Level.FINE, doc.toString());
		Document doc = Jsoup.parse(decoded);

		List<Fund> funds = new ArrayList<Fund>();
		
		for (int i = 0; i < 3; i++) {
			Fund fund = new Fund();
			fund.setName(getFundName(doc, i));
			fund.setMemberFee(getMemberFee(doc, i));
			fund.setAdminFee(getAdminFee(doc, i));
			fund.setInvestmentFee(getInvestmentFee(doc, i));
			funds.add(fund);
		}
		return funds;
	}

	private String getFundName(Document doc, int i) {
		return doc.select("h2").get(i).text();
	}

	private String getInvestmentFee(Document doc, int index) {
		return doc.select("th:contains(Investment Fee) + td").get(index).text();
	}

	private String getAdminFee(Document doc, int index) {
		return doc.select("th:contains(Member Fee) + td").get(index).text();
	}

	private String getMemberFee(Document doc, int index) {
		return doc.select("th:contains(Admin Fee) + td").get(index).text();
	}

}
