package app;

/**
 * @author Jay Patel
 *	
 */
public class Stock {

	public double actualPrice;
	public String name;
	public double changePercent;
	public double changeValue;
	
	@Override
	public String toString() {
		
		return "Name: " + this.name 
				+ "\nActual Price : " + this.actualPrice 
				+ "\nChange Percent : " + this.changePercent;
	}
	
	
		
}
