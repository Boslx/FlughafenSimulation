package model;

import java.util.ArrayList;

/**
 * The International Boarding-Gate should handle all the Objects that enter a International Plane.
 * It extends the BoardingGate class.
 */
public class InternationalBoardingGate extends BoardingGate {
	/**
	 * (private!) Constructor, creates a new InternationalBoardingGate
	 *
	 * @param label     of the station
	 * @param inQueues  a list of all incoming queues
	 * @param outQueues a list of all outgoing queues
	 * @param troughPut a stations parameter that affects treatment of an object
	 * @param xPos      x position of the station
	 * @param yPos      y position of the station
	 * @param image     image of the station
	 */
	private InternationalBoardingGate(String label, ArrayList<SynchronizedQueue> inQueues, ArrayList<SynchronizedQueue> outQueues, double troughPut, int xPos, int yPos, String image) {
		// calls constructor of Superclass
		super(label, inQueues, outQueues, troughPut, xPos, yPos, image);
	}

	/**
	 * Create a new InternationalBoardingGate
	 *
	 * @param label     of the station
	 * @param inQueues  a list of all incoming queues
	 * @param outQueues a list of all outgoing queues
	 * @param troughPut a stations parameter that affects treatment of an object
	 * @param xPos      x position of the station
	 * @param yPos      y position of the station
	 * @param image     image of the station
	 */
	public static void create(String label, ArrayList<SynchronizedQueue> inQueues, ArrayList<SynchronizedQueue> outQueues, double troughPut, int xPos, int yPos, String image) {
		// creates InternationalBoardingGate
		new InternationalBoardingGate(label, inQueues, outQueues, troughPut, xPos, yPos, image);
	}
}
