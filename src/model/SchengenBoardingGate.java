package model;

import java.util.ArrayList;

/**
 * This class extends the BoardingGate with more attributes and handles the SchengenBoardingGate objects.
 */
public class SchengenBoardingGate extends BoardingGate {
	/**
	 * (private!) Constructor, creates a new process station
	 *
	 * @param label     of the station
	 * @param inQueues  a list of all incoming queues
	 * @param outQueues a list of all outgoing queues
	 * @param troughPut a stations parameter that affects treatment of an object
	 * @param xPos      x position of the station
	 * @param yPos      y position of the station
	 * @param image     image of the station
	 */
	private SchengenBoardingGate(String label, ArrayList<SynchronizedQueue> inQueues, ArrayList<SynchronizedQueue> outQueues, double troughPut, int xPos, int yPos, String image) {
		// creates Superclass
		super(label, inQueues, outQueues, troughPut, xPos, yPos, image);
	}

	/**
	 * creates new SchengenBoardingGate
	 *
	 * @param label     of the station
	 * @param inQueues  the incoming queue
	 * @param outQueues the outgoing queue
	 * @param troughPut a stations parameter that affects treatment of an object
	 * @param xPos      x position of the station
	 * @param yPos      y position of the station
	 * @param image     image of the station
	 */
	public static void create(String label, ArrayList<SynchronizedQueue> inQueues, ArrayList<SynchronizedQueue> outQueues, double troughPut, int xPos, int yPos, String image) {
		// creates new SchengenBoardingGate
		new SchengenBoardingGate(label, inQueues, outQueues, troughPut, xPos, yPos, image);
	}
}
