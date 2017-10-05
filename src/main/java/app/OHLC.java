package app;

import java.util.Date;

public class OHLC {

	public double open;
	public double high;
	public double low;
	public double close;
	public double volume;
	public Date dt;
	
	public OHLC(double open, double high, double low, double close) {
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
	}

	public OHLC() {
		
	}
	
	
	@Override
	public String toString() {
		
		return  "Open : " +  this.open
				+ "\nHigh : " + this.high
				+ "\nLow : " + this.low
				+ "\nClose : " + this.close
				+ "\nVolume : " + this.volume
				+ "\nDate : " + this.dt;
	}
}
