package app;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/*
 * @author jay.patel
 * @param
 * 
 * */
public class StockSelector {
	
	
	private static final String API_ENDPOINT = "https://finance.google.com/finance?q=";
	private static final String HISTORICAL_API_ENDPOINT = "http://finance.google.com/finance/historical?";
	private Document htmlDocument;
	
		public StockSelector() {
		
	}
	
	
	public Stock getStock(String stockTicker) {
		Stock company = new Stock();
		String url = getEndpointURL(stockTicker);
		htmlDocument = WebConnector.fetchHTMLDocument(url);
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
	
	private double getActualPrice() {
		List<Element> elems = htmlDocument.select(CssSelectors.STOCK_ACTUAL_PRICE);
		if(!elems.isEmpty()) {
			String price_str = elems.get(0).text().replaceAll(",", "");
			double price = Double.parseDouble(price_str);
			return price;
		}
		return -1;
	}
	
	private double getChangeValue() throws Exception {
		List<Element> elems = htmlDocument.select(CssSelectors.STOCK_PRICE_CHANGE_VALUE);
		if(!elems.isEmpty()) {
			String price_str = elems.get(0).text().replaceAll(",", "");
			double price = Double.parseDouble(price_str);
			return price;
		}else {
			throw new Exception("Selector for price change invalid");
		}
	}
	
	private double getChangePercent() throws Exception {
		List<Element> elems = htmlDocument.select(CssSelectors.STOCK_PRICE_CHANGE_PERCENT);
		if(!elems.isEmpty()) {
			String price_str = elems.get(0).text().replace("(", "").replace(")","").replace("%","");
			double price = Double.parseDouble(price_str);
			return price;
		}else {
			throw new Exception("Selector for price change invalid");
		}
	}
	
	private String getStockName() {
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


	@SuppressWarnings("unused")
	private String getCurrency() {
		return "";
	}


	public OHLC getHistoricalOHLC(String symbol, Date dt) {
		String url;
		try {
			url = getHistoricalURL(symbol, dt);
			Document doc = Jsoup.connect(url)
					.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:55.0) Gecko/20100101 Firefox/55.0")
					.get();
			
			HashMap<String, String> contents = getTableContents(doc);
			
			OHLC parsedData = getOHLC(contents);
			
			return parsedData;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	private OHLC getOHLC(HashMap<String, String> contents) {
		OHLC stockData = new OHLC();
		
		stockData.open = sanitizeNumber(contents.get("open"));
		stockData.high = sanitizeNumber(contents.get("high"));
		stockData.low = sanitizeNumber(contents.get("low"));
		stockData.close = sanitizeNumber(contents.get("close"));
		stockData.volume = sanitizeNumber(contents.get("volume"));
		stockData.dt = sanitizeDate(contents.get("date"));
		
		return stockData;
	}


	/**
	 * @param strDate : MMM DD, YYYY format date e.g. Sep 01, 2017
	 * @return Date dt java.util.Date object
	 */
	private Date sanitizeDate(String strDate) {
		System.out.println(strDate);
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("MMM DD, YYYY");
			Date dt = sdf.parse(strDate);
			return dt;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}


	private double sanitizeNumber(String doubleValueAsString) {
		double d = Double.parseDouble(doubleValueAsString.replace(",", ""));
		return d;
	}


	private HashMap<String, String> getTableContents(Document doc) {
		 HashMap<String, String> ohlcValues = new HashMap<String, String>();
		 Elements el = doc.select(CssSelectors.STOCK_HISTORICAL_TABLE);
		 for (Element element : el) {
			 // Ignore the table header and table footer
			if(!element.hasClass("bb") || element.hasClass("tptr")) {
				
				Elements tdElements = element.getElementsByTag("td");
				String dt = tdElements.get(0).text();
				String open = tdElements.get(1).text();
				String high = tdElements.get(2).text();
				String low = tdElements.get(3).text();
				String close = tdElements.get(4).text();
				String volume = tdElements.get(5).text();
				
				ohlcValues.put("open", open);
				ohlcValues.put("high", high);
				ohlcValues.put("low", low);
				ohlcValues.put("close", close);
				ohlcValues.put("volume", volume);
				ohlcValues.put("date", dt);
			};
		}
		return ohlcValues;
	}


	public String getHistoricalURL(String symbol, Date dt) throws UnsupportedEncodingException{
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
		String formated_date = sdf.format(dt);
		String queryparams = "q=" + symbol + "&";
		queryparams += "startdate=" + URLEncoder.encode(formated_date,"UTF-8") +"&";
		queryparams += "enddate=" + URLEncoder.encode(formated_date,"UTF-8");
		return HISTORICAL_API_ENDPOINT + queryparams.replace("+", "%20");
	}

}
