//Paul Albrick Coronia
//
//Ice Cream Detail Class

public class IceCreamDetail
{
	private	IceCream	iceCream;
	private	int			quantity;
	private	double		subTotal;
	
	public IceCreamDetail()
	{
		iceCream = new IceCream();
		quantity = 0;
		subTotal = 0.0;
	}
	
	public IceCreamDetail( IceCream ic, int qty)
	{
		iceCream = ic;
		quantity = qty;
		subTotal = getSubTotal();
	}
	
	public IceCream getIceCream()
	{
		return iceCream;
	}
	
	public int getQuantity()
	{
		return quantity;
	}
	
	public double getSubTotal()
	{
		return ((iceCream.getPrice()) * quantity);
	}
	
	public void increaseQuantityBy( int increment)
	{
		quantity += increment;
	}
	
	public void decreaseQuantityBy( int decrement)
	{
		quantity -= decrement;
	}
	
	public String toString()
	{
		String s = "" + iceCream.toString() + " " + quantity+ " " + subTotal;
		return s;
	}
}