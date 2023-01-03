
public class AllInclusiveReservation extends Reservation{
	private int meals;
	
	public AllInclusiveReservation(int days, int meals)
	{
		super(days);
		this.meals = meals;
	}
	
	public int calculateCost()
	{
		return days*(120 + meals*30);
	}
}