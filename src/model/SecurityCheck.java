package model;

import java.util.ArrayList;

/**
 * This Class symbolizes and works as an SecurityCheck
 */
public class SecurityCheck extends ProcessStation {

	/**
	 * (private!) Constructor, creates a new SecurityCheck
	 *
	 * @param label               of the station
	 * @param inQueues            a list of all incoming queues
	 * @param outQueues           a list of all outgoing queues
	 * @param troughPut           a stations parameter that affects treatment of an object
	 * @param xPos                x position of the station
	 * @param yPos                y position of the station
	 * @param image               image of the station
	 */
	private SecurityCheck(String label, ArrayList<SynchronizedQueue> inQueues, ArrayList<SynchronizedQueue> outQueues,
	                      double troughPut, int xPos, int yPos, String image) {
		// calls SupperClass
		super(label, inQueues, outQueues, troughPut, xPos, yPos, image);
	}

	/**
	 * @param label               of the station
	 * @param inQueues            a list of all incoming queues
	 * @param outQueues           a list of all outgoing queues
	 * @param troughPut           a stations parameter that affects treatment of an object
	 * @param xPos                x position of the station
	 * @param yPos                y position of the station
	 * @param image               image of the station
	 */
	public static void create(String label, ArrayList<SynchronizedQueue> inQueues, ArrayList<SynchronizedQueue> outQueues,
	                          double troughPut, int xPos, int yPos, String image) {
		//creates new SecurityCheck
		new SecurityCheck(label, inQueues, outQueues, troughPut, xPos, yPos, image);
	}
}
