package model;

/**
 * This class extends the Consume with more attributes and handles the shop objects.
 */
public class Shop extends Consume {
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
	 * @param isDutyFree         tells, if you have to pay taxes there
	 * @param revenuePerCostumer The revenue generate by each costumer
	 */
	private Shop(String label, SynchronizedQueue inQueue, SynchronizedQueue outQueue, double troughPut, int xPos,
	             int yPos, String image, boolean isDutyFree, double revenuePerCostumer) {
		//calls Superclass of Shop
		super(label, inQueue, outQueue, troughPut, xPos, yPos, image, isDutyFree, revenuePerCostumer);
	}

	/**
	 * Creates a new Shop Station
	 *
	 * @param label              of the station
	 * @param inQueue            the incoming queue
	 * @param outQueue           the outgoing queue
	 * @param troughPut          a stations parameter that affects treatment of an object
	 * @param xPos               x position of the station
	 * @param yPos               y position of the station
	 * @param image              image of the station
	 * @param isDutyFree         tells, if you have to pay taxes there
	 * @param revenuePerCostumer the revenue generate by each costumer
	 */
	public static void create(String label, SynchronizedQueue inQueue, SynchronizedQueue outQueue, double troughPut,
	                          int xPos, int yPos, String image, boolean isDutyFree, double revenuePerCostumer) {
		// creates new Shop
		new Shop(label, inQueue, outQueue, troughPut, xPos, yPos, image, isDutyFree, revenuePerCostumer);
	}

	/**
	 * The passenger goes into the shop, buys something and does no longer want to shop afterwards.
	 *
	 * @param passenger The passenger who buys something.
	 */
	@Override
	protected void handleObject(Passenger passenger) {
		//moves Passanger
		super.handleObject(passenger);
		//passenger looses interest in shop
		passenger.setWouldLikeToShop(false);
		//adds Revenue for each costumer
		super.addRevenue(this.getRevenuePerCostumer());
	}
}
