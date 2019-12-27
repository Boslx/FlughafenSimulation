package model;

import java.util.ArrayList;

/**
 * This class extends the SimpleProcessStation class with more attributes and implements an ArrayList for objects Luggage.
 */
class CheckIn extends SimpleProcessStation {

	/**
	 * Luggage handed in at the check-in
	 */
	protected ArrayList<Luggage> droppedLuggage = new ArrayList<Luggage>();

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
	CheckIn(String label, SynchronizedQueue inQueue, SynchronizedQueue outQueue, double troughPut, int xPos, int yPos, String image) {
		// calls constructor of Superclass
		super(label, inQueue, outQueue, troughPut, xPos, yPos, image);
	}

	/**
	 * Gets the luggage handed in at the check-in
	 *
	 * @return droppedLuggage
	 */
	public ArrayList<Luggage> getDroppedLuggage() {
		return droppedLuggage;
	}
}
