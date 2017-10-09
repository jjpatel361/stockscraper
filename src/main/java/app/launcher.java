package app;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class launcher {

	public static void main(String[] args) throws IOException {
		/*
		 * Currency Converter Example Usage
		 * */
		double multipler = CurrencyConverter.getExchangeRate(ExchangeCurrency.USD, ExchangeCurrency.INR);
		System.out.println(multipler);
		
		/*
		 * Get Stock Price for today 
		 * */
		StockSelector selector = new StockSelector();
		Stock s = selector.getStock("AAPL");
		System.out.println(s.toString());
		System.out.println("********************");
		
		/*
		 * Get Stock Price for Historical date
		 * 
		 * returns OHLC object
		 * */
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONTH, Calendar.SEPTEMBER);
		c.set(Calendar.YEAR, 2017);
		c.set(Calendar.DATE, 01);
		
		Date dt = c.getTime();
		OHLC ohlc = selector.getHistoricalOHLC("AAPL", dt);
		System.out.println(ohlc.toString());
		System.out.println("********************");
	}

}
