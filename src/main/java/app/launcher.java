package app;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class launcher {

	public static void main(String[] args) throws IOException {
		
		/*
		String url = "https://finance.google.com/finance?q=";
		String quote = "NSE:ACC";
		String css_pricelement = "#market-data-div>#price-panel .pr";
		String css_price_change_value = ".id-price-change span.ch.bld>span:first-child";
		String css_price_change_percent = ".id-price-change span.ch.bld>span:last-child";
		String css_currency = ".mdata-dis>div";
		Document doc = Jsoup.connect(url+quote).get();
		List<Element> elems = doc.getElementsByClass("appbar-snippet-primary");
		
		if(!elems.isEmpty()) {
			System.out.println("Stock Name : "  + elems.get(0).text());
		}else {
			System.err.println("No element found");
		}
		
		List<Element> priceEles = doc.select(css_price_change_value);
		
		if(!priceEles.isEmpty()) {
			System.out.println("Price Change "  + priceEles.get(0).text());
		}
		priceEles = doc.select(css_price_change_percent);
		
		if(!priceEles.isEmpty()) {
			System.out.println("Price Change Value : " + priceEles.get(0).text());
		}
		
		priceEles = doc.select(css_pricelement);
		
		if(!priceEles.isEmpty()) {
			System.out.println("Current Price "  + priceEles.get(0).text());
		}
		
		priceEles = doc.select(css_currency);
		
		if(!priceEles.isEmpty()) {
			System.out.println("Current Currency "  + priceEles.get(0).text());
		}*/
		
	}

}
