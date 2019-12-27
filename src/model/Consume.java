package model;

/**
 * This class extends the SimpleProcessStation class with more attributes and calculates the revenue.
 */
public class Consume extends SimpleProcessStation implements IChartViewable {

	/* The revenue that all shops achieve */
	private static double totalRevenue = 0;
	/* Has a chart view already been created? */
	private static Boolean chartViewCreated = false;
	/* Are the purchases tax-free? */
	private Boolean isDutyFree;
	/* How much do we earn per Costumer? */
	private double revenuePerCostumer;

	/**
	 * Constructor, creates a new process station
	 *
	 * @param label              of the station
	 * @param inQueue            the incoming queue
	 * @param outQueue           the outgoing queue
	 * @param troughPut          a stations parameter that affects treatment of an object
	 * @param xPos               x position of the station
	 * @param yPos               y position of the station
	 * @param image              image of the station
	 * @param isDutyFree         tells, if you have pay to pay taxes there
	 * @param revenuePerCostumer the revenue generate by each costumer
	 */
	Consume(String label, SynchronizedQueue inQueue, SynchronizedQueue outQueue, double troughPut, int xPos,
	        int yPos, String image, boolean isDutyFree, double revenuePerCostumer) {
		// calls constructor of Superclass
		super(label, inQueue, outQueue, troughPut, xPos, yPos, image);
		this.isDutyFree = isDutyFree;
		this.revenuePerCostumer = revenuePerCostumer;

		// Has a chart view already been created?
		if (!chartViewCreated) {
			view.ChartView.registerChartViewable(this, "Revenue over time", "Euro");
			chartViewCreated = true;
		}
	}

	/**
	 * @return The revenue generated per Costumer
	 */
	public double getRevenuePerCostumer() {
		return revenuePerCostumer;
	}

	/**
	 * @return isDutyFree the taxes
	 */
	private Boolean getDutyFree() {
		return isDutyFree;
	}

	/**
	 * @return The Revenue of all business, excluding taxes
	 */
	private double getTotalRevenue() {
		return totalRevenue;
	}

	/**
	 * Adds revenue to the total Revenue
	 *
	 * @param revenue The revenue of the business
	 */
	void addRevenue(double revenue) {
		if (getDutyFree())
			totalRevenue += revenue;
		else
			//We calculate the Revenue without the tax of 19%
			totalRevenue += revenue / 1.19;
	}

	/**
	 * @return the total revenue for the chartview
	 */
	@Override
	public double getChartObservedValue() {
		return getTotalRevenue();
	}
}
