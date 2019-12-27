package model;

/**
 * This class extends the Consume class with more attributes and handles the Passenger objects.
 */
public class Gastronomy extends Consume {
	/**
	 * Constructor, creates a new process station
	 *
	 * @param label              of the station
	 * @param inQueue            the incoming queue
	 * @param outQueue           the outgoing queue
	 * @param troughPut          is the troughPut of the station
	 * @param xPos               x position of the station
	 * @param yPos               y position of the station
	 * @param image              image of the station
	 * @param isDutyFree         if the gastronomy DutyFree
	 * @param revenuePerCostumer the revenue generate by each costumer
	 */
	private Gastronomy(String label, SynchronizedQueue inQueue, SynchronizedQueue outQueue, double troughPut, int xPos,
	                   int yPos, String image, boolean isDutyFree, double revenuePerCostumer) {
		// calls constructor of Superclass
		super(label, inQueue, outQueue, troughPut, xPos, yPos, image, isDutyFree, revenuePerCostumer);
	}

	/**
	 * Creates a new Gastronomy Station
	 *
	 * @param label              of the station
	 * @param inQueue            the incoming queue
	 * @param outQueue           the outgoing queue
	 * @param troughPut          is the troughPut of the station
	 * @param xPos               x position of the station
	 * @param yPos               y position of the station
	 * @param image              image of the station
	 * @param isDutyFree         if the gastronomy DutyFree
	 * @param revenuePerCostumer the revenue generate by each costumer
	 */
	public static void create(String label, SynchronizedQueue inQueue, SynchronizedQueue outQueue, double troughPut,
	                          int xPos, int yPos, String image, boolean isDutyFree, double revenuePerCostumer) {
		// creates new Gastronomy
		new Gastronomy(label, inQueue, outQueue, troughPut, xPos, yPos, image, isDutyFree, revenuePerCostumer);
	}

	/**
	 * The passenger goes into the restaurant, eats something and is no longer hungry afterwards.
	 *
	 * @param passenger The passenger who buys something.
	 */
	@Override
	protected void handleObject(Passenger passenger) {
		// calls Superclass
		super.handleObject(passenger);
		passenger.setHungry(false);
		super.addRevenue(this.getRevenuePerCostumer());
	}
}
