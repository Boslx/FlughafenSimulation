package model;

import controller.Simulation;

import java.util.Collection;

/**
 * Class for the beginning station, this is where all objects start
 */
public class StartStation extends SimpleStation {

	/**
	 * instance of the start station
	 */
	private static StartStation theStartStation;

	/**
	 * (private!) Constructor, creates a new start station
	 *
	 * @param label    of the station
	 * @param inQueue  the incoming queue
	 * @param outQueue the outgoing queue
	 * @param xPos     x position of the station
	 * @param yPos     y position of the station
	 * @param image    image of the station
	 */
	StartStation(String label, SynchronizedQueue inQueue, SynchronizedQueue outQueue, int xPos, int yPos, String image) {

		super(label, inQueue, outQueue, xPos, yPos, image);
	}


	/**
	 * creates a new start station
	 *
	 * @param label    of the station
	 * @param inQueue  the incoming queue
	 * @param outQueue the outgoing queue
	 * @param xPos     x position of the station
	 * @param yPos     y position of the station
	 * @param image    image of the station
	 */
	public static void create(String label, SynchronizedQueue inQueue, SynchronizedQueue outQueue, int xPos, int yPos, String image) {

		theStartStation = new StartStation(label, inQueue, outQueue, xPos, yPos, image);

	}

	/**
	 * Get the start station
	 *
	 * @return theStartStation
	 */
	public static StartStation getStartStation() {
		return theStartStation;
	}

	/**
	 * @param passenger the object that should be treated
	 */
	@Override
	protected void handleObject(Passenger passenger) {

		//the object chooses an outgoing queue and enter it
		passenger.enterOutQueue(this);

		//let the next objects start with a little delay
		try {
			Thread.sleep(Simulation.CLOCKBEAT);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @return null
	 */
	@Override
	protected Collection<Passenger> getNextInQueueObjects() {
		return null;
	}

	/**
	 * @return null
	 */
	@Override
	protected Collection<Passenger> getNextOutQueueObjects() {
		return null;
	}


}
