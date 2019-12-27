package model;

import java.util.ArrayList;

/**
 * Superclass for simple stations with just one incoming and one outgoing queue
 */

public abstract class SimpleStation extends Station {

	/**
	 * the outgoing queue for already handled objects
	 */
	SynchronizedQueue outGoingQueue;
	/**
	 * the incoming queue for still to handle objects
	 */
	private SynchronizedQueue inComingQueue;


	/**
	 * Constructor for simple stations
	 *
	 * @param label    of the station
	 * @param inQueue  the incoming queue
	 * @param outQueue the outgoing queue
	 * @param xPos     x position of the station
	 * @param yPos     y position of the station
	 * @param image    image of the station
	 */
	SimpleStation(String label, SynchronizedQueue inQueue, SynchronizedQueue outQueue, int xPos, int yPos, String image) {
		//calls SuperClass
		super(label, xPos, yPos, image);

		//the stations queues
		this.inComingQueue = inQueue;
		this.outGoingQueue = outQueue;

	}

	/**
	 * @return this.inComingQueue.size
	 */
	@Override
	protected int numberOfInQueueObjects() {

		return this.inComingQueue.size();

	}

	/**
	 * @return this.outGoingQueue.size
	 */
	@Override
	protected int numberOfOutQueueObjects() {

		return this.outGoingQueue.size();
	}

	/**
	 * @param passenger the object that should be treated
	 */
	@Override
	protected abstract void handleObject(Passenger passenger);

	/**
	 * @return (Passenger) this.inComingQueue.poll
	 */
	@Override
	protected Passenger getNextInQueueObject() {

		//return simply the first object
		return (Passenger) this.inComingQueue.poll();
	}

	/**
	 * @return (Passenger) this.outGoingQueue.poll
	 */
	@Override
	protected Passenger getNextOutQueueObject() {

		//return simply the first object
		return (Passenger) this.outGoingQueue.poll();
	}

	/**
	 * @return inQueues
	 */
	@Override
	public ArrayList<SynchronizedQueue> getAllInQueues() {

		// we have just one incoming queue
		ArrayList<SynchronizedQueue> inQueues = new ArrayList<SynchronizedQueue>();
		inQueues.add(inComingQueue);
		return inQueues;
	}

	/**
	 * @return outQueues
	 */
	@Override
	public ArrayList<SynchronizedQueue> getAllOutQueues() {

		// we have just one outgoing queue
		ArrayList<SynchronizedQueue> outQueues = new ArrayList<SynchronizedQueue>();
		outQueues.add(outGoingQueue);
		return outQueues;

	}

}
