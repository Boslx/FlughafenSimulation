package model;

import controller.Simulation;
import io.Statistics;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Class for a simple process station (with just one incoming and one outgoing queue)
 */
public class SimpleProcessStation extends SimpleStation {

	/**
	 * the instance of our static inner Measurement class
	 */
	private Measurement measurement = new Measurement();
	/**
	 * a parameter that affects the speed of the treatment for an object
	 */
	private double throughPut;

	/**
	 * Constructor, creates a new process station
	 *
	 * @param label      of the station
	 * @param inQueue    the incoming queue
	 * @param outQueue   the outgoing queue
	 * @param throughPut the rate of how many passengers can be handled in a certain time
	 * @param xPos       x position of the station
	 * @param yPos       y position of the station
	 * @param image      image of the station
	 */
	SimpleProcessStation(String label, SynchronizedQueue inQueue, SynchronizedQueue outQueue, double throughPut, int xPos, int yPos, String image) {
		//calls SuperClass
		super(label, inQueue, outQueue, xPos, yPos, image);

		//the troughPut parameter 
		this.throughPut = throughPut;
	}

	/** create a new simple process station and add it to the station list
	 *
	 * @param label of the station 
	 * @param inQueue the incoming queue
	 * @param outQueue the outgoing queue
	 * @param troughPut a stations parameter that affects treatment of an object
	 * @param xPos x position of the station
	 * @param yPos y position of the station
	 * @param image image of the station 
	 */

	/**
	 * Get all simple process stations
	 *
	 * @return the allSimpleProcessStations
	 */
	public static ArrayList<SimpleProcessStation> getAllSimpleProcessStations() {

		// the simple process station list
		ArrayList<SimpleProcessStation> allSimpleProcessStations = new ArrayList<SimpleProcessStation>();

		//filter the simple process stations out of the station list
		for (Station station : Station.getAllStations()) {

			if (station instanceof SimpleProcessStation) allSimpleProcessStations.add((SimpleProcessStation) station);

		}

		return allSimpleProcessStations;
	}

	/**
	 * public static void create(String label,  SynchronizedQueue inQueue, SynchronizedQueue outQueue, double troughPut, int xPos, int yPos, String image){
	 * <p>
	 * new SimpleProcessStation(label, inQueue, outQueue, troughPut, xPos, yPos, image);
	 * <p>
	 * }
	 */

	/*
	protected void handleObject(TheObject theObject){

		//the object chooses an outgoing queue and enter it
		theObject.enterOutQueue(this);

		//let the next objects start with a little delay
		try {
			Thread.sleep(Simulation.CLOCKBEAT);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	*/

	/**
	 * @param passenger the passenger to be handled
	 */
	@Override
	protected void handleObject(Passenger passenger) {

		//count all the visiting objects
		measurement.numbOfVisitedObjects++;

		Statistics.show(this.getLabel() + " behandelt: " + passenger.getLabel());

		//the processing time of the object
		int processTime = passenger.getProcessTime();

		//the time to handle the object
		int theObjectsTreatingTime = (int) (processTime / this.throughPut);

		//get the starting time of the treatment
		long startTime = Simulation.getGlobalTime();

		//the elapsed time of the treatment
		int elapsedTime = 0;

		//while treating time is not reached
		while (!(theObjectsTreatingTime <= elapsedTime)) {

			//the elapsed time since the start of the treatment
			elapsedTime = (int) (Simulation.getGlobalTime() - startTime);

			//let the thread sleep for the adjusted clock beat
			//This is just needed to notice the different treatment duration in the view
			try {
				Thread.sleep(Simulation.CLOCKBEAT);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

		//increase the time the object was treated
		passenger.measurement.myTreatmentTime = passenger.measurement.myTreatmentTime + elapsedTime;

		//increase the stations in use time
		measurement.inUseTime = measurement.inUseTime + elapsedTime;

		//the treatment is over, now the object chooses the outgoing queue and enter it
		passenger.enterOutQueue(this);

		//just to see the view of the outgoing queue works
		try {
			Thread.sleep(500);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	/**
	 * get and print some statistics out of the Measurement class
	 */
	public void printStatistics() {

		String theString = "\nStation Typ: " + this.label;
		theString = theString + "\nAnzahl der behandelten Objekte: " + measurement.numbOfVisitedObjects;
		theString = theString + "\nZeit zum Behandeln aller Objekte: " + measurement.inUseTime;
		theString = theString + "\nDurchnittliche Behandlungsdauer: " + measurement.avgTreatmentTime();

		Statistics.show(theString);

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

	/**
	 * A (static) inner class for measurement jobs. The class records specific values of the station during the simulation.
	 * These values can be used for statistic evaluation.
	 */
	private static class Measurement {

		/**
		 * the total time the station is in use
		 */
		private int inUseTime = 0;

		/**
		 * the number of all objects that visited this station
		 */
		private int numbOfVisitedObjects = 0;


		/**
		 * Get the average time for treatment
		 *
		 * @return the average time for treatment
		 */
		private int avgTreatmentTime() {
			//in case that a station wasn't visited
			if (numbOfVisitedObjects == 0) return 0;
			else
				return inUseTime / numbOfVisitedObjects;

		}

	}

}
