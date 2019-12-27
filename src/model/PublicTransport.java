package model;

/**
 * This class extends the StartStation and brings Passenger to the Terminal.
 */
class PublicTransport extends StartStation {
	/**
	 * Constructor, creates a new start station
	 *
	 * @param label    of the station
	 * @param inQueue  the incoming queue
	 * @param outQueue the outgoing queue
	 * @param xPos     x position of the station
	 * @param yPos     y position of the station
	 * @param image    image of the station
	 */
	protected PublicTransport(String label, SynchronizedQueue inQueue, SynchronizedQueue outQueue, int xPos, int yPos, String image) {
		//calls Superclass
		super(label, inQueue, outQueue, xPos, yPos, image);
	}
}
