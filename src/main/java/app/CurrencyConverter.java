package app;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author Jay Patel
 * 
 * CurrencyConverter is used to convert prices between two currencies.
 * Uses Google Exchange Rate Converter
 * https://finance.google.com/finance/converter?a=[qty]&from=[from]&to=[to]
 *
 */
public class CurrencyConverter {

	private static final String CURRENCY_API_ENDPOINT = "https://finance.google.com/finance/converter?";
	/**
	 * Use to convert currency. Returns the multiplier. 
	 * E.g. convert("USD", "INR") returns 67.9 as 1 USD  = 67.9 INR  
	 * @param from : Currency from
	 * @param to : Currency to 
	 * @return multiplier 
	 * @throws IOException 
	 * 
	 */
	public static double getExchangeRate(String from, String to) throws IOException {
		String url = generateURL(from, to);
		Document d = WebConnector.fetchHTMLDocument(url);
		double mul = getMultiplier(d);
		return mul;
	}
	private static double getMultiplier(Document d) {
		Elements eleList = d.select(CssSelectors.CURRENCY_CONVERTER);
		String multiplier_str  = eleList.get(0).text().replace(",", "");
		String cleaned_str = multiplier_str.replaceAll("[^\\d.]", "");
		return Double.parseDouble(cleaned_str);
	}
	private static String generateURL(String from, String to) {
		String queryParameters = "a=1";
		queryParameters += "&from="+from;
		queryParameters += "&to=" + to;
		return CURRENCY_API_ENDPOINT + queryParameters;
	}

}
