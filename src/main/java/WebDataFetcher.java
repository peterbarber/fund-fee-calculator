import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class WebDataFetcher {

	public Document getData() throws IOException {
		Document doc = Jsoup.connect("http://www.superratings.com.au/ratemysuper/caresuper?js=submitform").
				timeout(20000).
				cookie("sessid", "979ee9ed57fb4712bdf1770b802dec89").
				cookie("__utmt", "1").
				cookie("__utma", "88439321.453572266.1403691609.1418722373.1421834320.10").
				cookie("__utmb", "88439321.1.10.1421834320; __utmc=88439321").
				cookie("__utmz", "88439321.1417322710.5.3.utmcsr=google|utmccn=(organic)|utmcmd=organic|utmctr=(not%20provided)").
				data("prefix", "dom_54bf787f4eeaf").
				data("challenge", "1a71f2d1442625b440a1b6c53d44e378d39741ca").
				data("default_product", "611").
				post();
		
		
//		doc = Jsoup.connect("http://www.superratings.com.au/ratemysuper/caresuper?call2action=logStats&s=fees&a=false").
//				timeout(20000).
//				cookie("sessid", "979ee9ed57fb4712bdf1770b802dec89").
//				cookie("__utmt", "1").
//				cookie("__utma", "88439321.453572266.1403691609.1418722373.1421834320.10").
//				cookie("__utmb", "88439321.1.10.1421834320; __utmc=88439321").
//				cookie("__utmz", "88439321.1417322710.5.3.utmcsr=google|utmccn=(organic)|utmcmd=organic|utmctr=(not%20provided)").
//				get();
		return doc;
	}

}
