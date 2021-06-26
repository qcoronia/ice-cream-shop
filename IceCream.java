//Paul Albrick Coronia
//
//Ice Cream Class

public class IceCream
{	
	private String 	brand;
	private String 	variety;
	private String	flavor;
	private double 	price;
	
	public IceCream()
	{
		brand = "";
		variety = "";
		flavor = "";
		price = 0.0;
	}
	
	public IceCream( String b, String v, String f, double p)
	{
		brand = b;
		variety = v;
		flavor = f;
		price = p;
	}
	
	public String getBrand()
	{
		return brand;
	}
	
	public String getVariety()
	{
		return variety;
	}
	
	public String getFlavor()
	{
		return flavor;
	}
	
	public double getPrice()
	{
		return price;
	}
	
	public void setPrice( double p)
	{
		price = p;
	}
	
	public String toString()
	{
		String s = "" + brand + " " + variety + " " + flavor + " " + price;
		return s;
	}
}