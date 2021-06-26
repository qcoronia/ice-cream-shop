//Paul Albrick Coronia
//
//Transaction class

import java.util.*;

public class Transaction
{
	private int transNumber;
	private String transType;
	private String date;
	private String time;
	private Vector<IceCreamDetail> icDetails;
	private double totalPrice;
	
	public Transaction()
	{
		transNumber = 0;
		date = "00/00/0000";
		time = "00:00:00";
		icDetails = new Vector<IceCreamDetail>();
		totalPrice = 0.0;
	}
	
	public Transaction( int tN, String tT, String d, String t, Vector<IceCreamDetail> iCD)
	{
		transNumber = tN;
		transType = tT;
		date = d;
		time = t;
		icDetails = iCD;
		totalPrice = getTotalPrice();
	}
	
	public int getTransactionNumber()
	{
		return transNumber;
	}
	
	public String getTransactionType()
	{
		return transType;
	}
	
	public String getDate()
	{
		return date;
	}
	
	public String getTime()
	{
		return time;
	}
	
	public Vector<IceCreamDetail> getIceCreamDetail()
	{
		return icDetails;
	}
	
	public IceCreamDetail getIceCreamDetail( int i)
	{
		return (IceCreamDetail)icDetails.elementAt(i);
	}
	
	public double getTotalPrice()
	{
		double sum = 0.0;
		
		for( IceCreamDetail icd : icDetails)
		{
			sum += (icd.getSubTotal());
		}
		
		return ( sum / icDetails.size());
	}
	
	public String toString()
	{
		String s = "" + transNumber + " " + transType + " " + date + " " + time	 + " " + icDetails.size() + "\n";
		for( IceCreamDetail icd : icDetails)
		{
			s += icd.toString() + "\n";
		}
		return s;
	}
}