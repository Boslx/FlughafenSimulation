package model;

/**
 * This class represents a Terminal Object
 */
public class Terminal extends SimpleProcessStation {
	/**
	 * Constructor, creates a new process station
	 *
	 * @param label     of the station
	 * @param inQueue   the incoming queue
	 * @param outQueue  the outgoing queue
	 * @param troughPut a stations parameter that affects treatment of an object
	 * @param xPos      x position of the station
	 * @param yPos      y position of the station
	 * @param image     image of the station
	 */
	private Terminal(String label, SynchronizedQueue inQueue, SynchronizedQueue outQueue, double troughPut, int xPos, int yPos, String image) {
		// calls Superclass of Terminal
		super(label, inQueue, outQueue, troughPut, xPos, yPos, image);
	}

	/**
	 * @param label of the station
	 * @param inQueue a list of all incoming queues
	 * @param outQueue a list of all outgoing queues
	 * @param troughPut a stations parameter that affects treatment of an object
	 * @param xPos x position of the station
	 * @param yPos y position of the station
	 * @param image image of the station
	 */
	public static void create(String label, SynchronizedQueue inQueue, SynchronizedQueue outQueue, double troughPut, int xPos, int yPos, String image) {
		//creates new Terminal
		new Terminal(label, inQueue, outQueue, troughPut, xPos, yPos, image);
	}

	/**
	 * Handle the given object.
	 *
	 * @param passenger of the Object
	 */
	@Override
	protected void handleObject(Passenger passenger) {
		passenger.enterOutQueue(this);
	}
}
