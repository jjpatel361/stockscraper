package app;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class launcher {

	public static void main(String[] args) throws IOException {
		double multipler = CurrencyConverter.getExchangeRate(ExchangeCurrency.USD, ExchangeCurrency.INR);
		System.out.println(multipler);
	}

}
