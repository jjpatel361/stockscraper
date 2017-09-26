package app;

import java.nio.channels.IllegalSelectorException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/*
 * @author jay.patel
 * @param
 * 
 * */
public class StockSelector {
	
	private String _stockTicker;
	private static final String API_ENDPOINT = "https://finance.google.com/finance?q=";
	public Document htmlDocument;
	
	
	public StockSelector() {
		
	}
	
	
	public Stock getStock(String stockTicker) {
		_stockTicker = stockTicker;
		Stock company = new Stock();
		String url = getEndpointURL(stockTicker);
		htmlDocument = fetchHTMLDocument(url);
		company.name = getStockName();
		company.actualPrice = getActualPrice();
		try {
			company.changePercent = getChangePercent();
			company.changeValue = getChangeValue();
		} catch (Exception e) {
			System.err.println("Error in fetching price change"+ e.getMessage());
			e.printStackTrace();
		}
		return company;
	}
	
	public double getActualPrice() {
		List<Element> elems = htmlDocument.select(CssSelectors.STOCK_ACTUAL_PRICE);
		if(!elems.isEmpty()) {
			String price_str = elems.get(0).text().replaceAll(",", "");
			double price = Double.parseDouble(price_str);
			return price;
		}
		return -1;
	}
	
	public double getChangeValue() throws Exception {
		List<Element> elems = htmlDocument.select(CssSelectors.STOCK_PRICE_CHANGE_VALUE);
		if(!elems.isEmpty()) {
			String price_str = elems.get(0).text().replaceAll(",", "");
			double price = Double.parseDouble(price_str);
			return price;
		}else {
			throw new Exception("Selector for price change invalid");
		}
	}
	
	public double getChangePercent() throws Exception {
		List<Element> elems = htmlDocument.select(CssSelectors.STOCK_PRICE_CHANGE_PERCENT);
		if(!elems.isEmpty()) {
			String price_str = elems.get(0).text().replace("(", "").replace(")","").replace("%","");
			double price = Double.parseDouble(price_str);
			return price;
		}else {
			throw new Exception("Selector for price change invalid");
		}
	}
	
	public String getStockName() {
		List<Element> elems = htmlDocument.getElementsByClass(CssSelectors.STOCK_NAME);
		if(!elems.isEmpty()) {
			String name = elems.get(0).text();
			return name;
		}
		return null;
	}


	private Document fetchHTMLDocument(String url) {
		try {
			Document doc = Jsoup.connect(url).get();
			return doc;
		} catch (Exception e) {
			System.err.println("Exception in fetching document");
			e.printStackTrace();
		}
		return null;
	}


	private String getEndpointURL(String stockTicker) {
		return API_ENDPOINT+stockTicker;
	}


	private String getCurrency() {
		return "";
	}
	

}
