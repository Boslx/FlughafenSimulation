package model;

import io.Statistics;

import java.util.ArrayList;

/**
 * This class contains contains the logic and information behind the station BoardingGate
 */

public class BoardingGate extends ProcessStation {
	/**
	 * Constructor, creates a new BoardingGate
	 *
	 * @param label     of the station
	 * @param inQueues  a list of all incoming queues
	 * @param outQueues a list of all outgoing queues
	 * @param troughPut a stations parameter that affects treatment of an object
	 * @param xPos      x position of the station
	 * @param yPos      y position of the station
	 * @param image     image of the station
	 */
	BoardingGate(String label, ArrayList<SynchronizedQueue> inQueues, ArrayList<SynchronizedQueue> outQueues, double troughPut, int xPos, int yPos, String image) {
		// calls constructor of Superclass
		super(label, inQueues, outQueues, troughPut, xPos, yPos, image);
	}

	/**
	 * checks if the passenger has a boardingpass
	 */
	@Override
	protected void handleObject(Passenger passenger) {
		//checks if the passenger have a boardingpass
		if (passenger.getBoardingPass()) {
			Statistics.show(passenger.getLabel() + " has boardingpass");
			super.handleObject(passenger);
		} else {
			Statistics.show(passenger.getLabel() + " has no boardingpass and was stopped at the BoardingGate");
		}
	}
}


