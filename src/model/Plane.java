package model;

import controller.Simulation;
import io.Statistics;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Class for the plane. This is the Station where the passenger will go in the end of the station sequence
 */
public class Plane extends SimpleStation implements IChartViewable {

	/**
	 * instance of the start station
	 */
	private static ArrayList<Plane> thePlanes = new ArrayList<>();

	/**
	 * (private!) Constructor, creates a new end station
	 *
	 * @param label    of the station
	 * @param inQueue  the incoming queue
	 * @param outQueue the outgoing queue
	 * @param xPos     x position of the station
	 * @param yPos     y position of the station
	 * @param image    image of the station
	 */
	private Plane(String label, SynchronizedQueue inQueue, SynchronizedQueue outQueue, int xPos, int yPos, String image) {
		// calls constructor of Superclass
		super(label, inQueue, outQueue, xPos, yPos, image);
		view.ChartView.registerChartViewable(this, "Passenger-units in " + this.label, "Passenger-units");
	}

	/**
	 * creates a new end station
	 *
	 * @param label    of the station
	 * @param inQueue  the incoming queue
	 * @param outQueue the outgoing queue
	 * @param xPos     x position of the station
	 * @param yPos     y position of the station
	 * @param image    image of the station
	 */
	public static void create(String label, SynchronizedQueue inQueue, SynchronizedQueue outQueue, int xPos, int yPos, String image) {
			thePlanes.add(new Plane(label, inQueue, outQueue, xPos, yPos, image));
	}

	/**
	 * @return when there are no passengers in the inQueue, this returns false
	 */
	@Override
	protected boolean work() {

		//let the thread wait only if there are no objects in the incoming queue
		if (numberOfInQueueObjects() == 0) return false;

		//If there is an inqueue object found, handle it
		if (numberOfInQueueObjects() > 0) this.handleObject(this.getNextInQueueObject());

		//maybe there is more work to do
		return true;

	}

	@Override
	protected void handleObject(Passenger passenger) {
		// the object chooses the outgoing queue and enter it
		passenger.enterOutQueue(this);

		//  this is a just for fun action, the object gets a new location, but CAUTION !!!! magic numbers :-( !!!!!!
		// passenger.theView.setLocation((this.getXPos() - 100) + 18 * numberOfOutQueueObjects(), this.getYPos() + 120);

		//End the simulation if the condition is met
		endSimulation();
	}

	/**
	 * Gets the number of passengers on all planes.
	 *
	 * @return the number of passengers on all planes
	 */
	private int getAllPassengersAtEnd() {
		int allPassengersAtEnd = 0;
		for (Plane plane : thePlanes)
			allPassengersAtEnd += plane.numberOfOutQueueObjects();

		return allPassengersAtEnd;
	}

	/**
	 * End the simulation if the condition is met
	 */
	private void endSimulation() {

		// Are all objects in the stations outgoing queue, then we are finish
		if (Passenger.getAllPassengers().size() == getAllPassengersAtEnd()) {

			Statistics.show("\n--- Simulation beendet ----");

			//show some station statistics

			//the process stations
			for (ProcessStation station : ProcessStation.getAllProcessStations()) {
				station.printStatistics();
			}

			//the simple process stations
			for (SimpleProcessStation station : SimpleProcessStation.getAllSimpleProcessStations()) {
				station.printStatistics();
			}

			//show some objects statistics
			for (Object object : this.outGoingQueue) {
				((Passenger) object).printStatistics();
			}

			Simulation.setRunning(false);
			// end simulation
			// System.exit(0);

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


	/**
	 * Tells the ChartObserver the numberOfOutQueueObjects
	 *
	 * @return the number of Outqueueobjects
	 */
	@Override
	public double getChartObservedValue() {
		return this.numberOfOutQueueObjects();
	}
}
