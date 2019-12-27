package model;

import controller.Simulation;
import io.Statistics;
import view.PassengerView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class for the passengers that should move from the StartStation through a sequence of Stations and should end up in
 * a plane station (EndStation)
 */

public class Passenger extends Actor {
	/**
	 * The Standard Passenger Image jpg file
	 */
	private final static String ACTORBLUE = "symbols/actor/50x50/ActorBlue.png";
	/**
	 * The Passenger with luggage Image jpg file
	 */
	private final static String ACTORBLUELUGGAGE = "symbols/actor/50x50/ActorBlueLuggage.png";
	/**
	 * The Passenger with Ticket Image jpg file
	 */
	private final static String ACTORBLUETICKET = "symbols/actor/50x50/ActorBlueTicket.png";
	/**
	 * The Passenger with Ticket and luggage image jpg file
	 */
	private final static String ACTORBLUELUGGAGETICKET = "symbols/actor/50x50/ActorBlueLuggageTicket.png";
	/**
	 * list of all passengers
	 */
	private static ArrayList<Passenger> allPassengers = new ArrayList<Passenger>();
	/**
	 * the instance of our static inner Measurement class
	 */
	Measurement measurement = new Measurement();
	/**
	 * the view of the passenger
	 */
	private PassengerView theView;
	/**
	 * Image String for the filepath
	 */
	private String image = ACTORBLUE;
	/**
	 * A new Random object to create Random booleans and Integers
	 */
	private Random random = new Random();
	/**
	 * The index of the SecurityCheck in the stationsToGo ArrayList of each passenger
	 */
	private int indexSecurityCheck = 0;
	/**
	 * The boolean which is used to make the Scenario random or not
	 */
	private boolean randomized;
	/**
	 * the process time of the passenger
	 */
	private int processTime;
	/**
	 * the speed of the passenger, the higher the lower
	 */
	private int mySpeed;
	/**
	 * the ticket of the passenger
	 */
	private AirlineTicket airlineTicket;
	/**
	 * the luggage of the passenger
	 */
	private Luggage luggage;
	/**
	 * Tells, if the passenger is hungry
	 */
	private boolean isHungry;
	/**
	 * Tells, if the passenger wants to shop at the airport
	 */
	private boolean wouldLikeToShop;
	/**
	 * information about the boardingpass
	 */
	private boolean boardingpass;
	/**
	 * all the station (labels) where the passenger have to go to
	 */
	private ArrayList<String> stationsToGo = new ArrayList<String>();
	/**
	 * a pointer to the actual position of the stationsToGo list, start position is 0
	 */
	private int stationListPointer = 0;
	/**
	 * the actual station where this passenger is in, null if it's not in a station or a stations queue
	 */
	private Station actualStation = null;

	/**
	 * (private!) Constructor, creates a new passenger and send it to the start station
	 *
	 * @param label               of the passenger
	 * @param stationsToGo        the stations to go
	 * @param processtime         the processing time of the passenger, affects treatment by a station
	 * @param speed               the moving speed of the passenger
	 * @param xPos                x position of the passenger
	 * @param yPos                y position of the passenger
	 * @param luggage_description description of the luggage from the Passenger
	 * @param flightnumber        the flightnumber of the target plane
	 * @param onlinecheckin       information if the passenger used the onlinecheckin
	 * @param isHungry			  information if the passenger is hungry
	 * @param wouldLikeToShop	  information if the passenger wants to shop
	 * @param randomized		  information if some of the stations should be put random into the scenario
	 */
	private Passenger(String label, ArrayList<String> stationsToGo, int processtime, int speed, int xPos, int yPos,
	                  String luggage_description, String flightnumber, boolean onlinecheckin,
	                  boolean isHungry, boolean wouldLikeToShop, boolean randomized) {
		//calls the Constructor from Actor
		super(label, xPos, yPos);
		//create a Luggage object with description and owner for this Passenger
		luggage = new Luggage(luggage_description, this);
		//create a AirLineTicket with the label and the flightnumber for this Passenger
		airlineTicket = new AirlineTicket(label, flightnumber);
		//give the Passenger a boardingpass if he did the online Check-In
		this.boardingpass = onlinecheckin;
		//set the correct Image for the Passenger depending on if he has a boardingpass because he did an online Check-In
		if (boardingpass) {
			image = ACTORBLUELUGGAGETICKET;
		} else {
			image = ACTORBLUELUGGAGE;
		}
		//create the view
		this.theView = PassengerView.create(label, image, xPos, yPos);
		//add passenger to the static list
		Passenger.allPassengers.add(this);

		this.stationsToGo = stationsToGo;
		this.processTime = processtime;
		this.mySpeed = speed;
		this.isHungry = isHungry;
		this.wouldLikeToShop = wouldLikeToShop;
		this.randomized = randomized;
		//adds dynamic stations based on if the Scenario should be randomized or not
		if(randomized) {
			addDynamicStations();
		}

		//the first station to go to is the start station
		Station station = this.getNextStation();

		//enter the in queue of the start station
		this.enterInQueue(station);

	}

	/**
	 * Create a new passenger
	 *
	 * @param label               of the passenger
	 * @param stationsToGo        the stations to go
	 * @param processtime         the processing time of the passenger, affects treatment by a station
	 * @param speed               the moving speed of the passenger
	 * @param xPos                x position of the passenger
	 * @param yPos                y position of the passenger
	 * @param luggage_description description of the luggage from the Passenger
	 * @param flightnumber        the flightnumber of the target plane
	 * @param onlinecheckin       information if the passenger used the onlinecheckin
	 * @param isHungry			  information if the passenger is hungry
	 * @param wouldLikeToShop	  information if the passenger wants to shop
	 * @param randomized		  information if some of the stations should be put random into the scenario
	 */
	public static void create(String label, ArrayList<String> stationsToGo, int processtime, int speed, int xPos,
	                          int yPos, String luggage_description, String flightnumber,
	                          boolean onlinecheckin, boolean isHungry, boolean wouldLikeToShop, boolean randomized) {

		new Passenger(label, stationsToGo, processtime, speed, xPos, yPos, luggage_description, flightnumber,
				onlinecheckin, isHungry, wouldLikeToShop, randomized);
	}

	/**
	 * Get all passengers
	 *
	 * @return a list of all passengers
	 */
	public static ArrayList<Passenger> getAllPassengers() {
		return allPassengers;
	}

	public boolean isHungry() {
		return isHungry;
	}

	void setHungry(boolean hungry) {
		isHungry = hungry;
	}

	public boolean getWouldLikeToShop() {
		return wouldLikeToShop;
	}

	void setWouldLikeToShop(boolean wouldLikeToShop) {
		this.wouldLikeToShop = wouldLikeToShop;
	}

	public boolean getBoardingPass() {
		return boardingpass;
	}

	/**
	 * Chose the next station to go to
	 *
	 * @param pass is the boarding pass okay?
	 */
	public void setBoardingPass(boolean pass) {

		boardingpass = pass;
	}

	/**
	 * Get the next Station to which the Passenger goes. If the Station is the Security-Check the next Station will be
	 * the Security-Check with the lowest amount of Passengers in the Inqueue
	 *
	 * @return The next Station the Passenger will go
	 */
	private Station getNextStation() {

		//we are at the end of the list
		if (this.stationsToGo.size() < stationListPointer) return null;

		//get the label of the next station from the list and increase the list pointer
		String stationLabel = this.stationsToGo.get(stationListPointer++);

        //Check if the station label starts with "{" to identify multiple option stations
		if (stationLabel.startsWith("{")) {
			String stationLabelCleaned = stationLabel;
			//removing "{" and "}" from the String
			stationLabelCleaned = stationLabelCleaned.replace("{", "");
			stationLabelCleaned = stationLabelCleaned.replace("}", "");

			//Split the existing stations in different Strings and add them in an Array
			String[] stationLabels = stationLabelCleaned.split(", ");
			ArrayList<Station> possibleStations = new ArrayList<>();
			//Get the Stations for the labels in the ArrayList possibleStations
			for (Station station : Station.getAllStations())
				for (String labelStation : stationLabels) {
					if (station.label.equals(labelStation))
						possibleStations.add(station);
				}

			// Get station with the lowest amount of the Inqueue passengers and save it in lowestQueue
			Station lowestQueue = null;
			for (Station possibleStation : possibleStations) {
				if (lowestQueue == null || possibleStation.numberOfInQueueObjects() < lowestQueue.numberOfInQueueObjects()) {
					lowestQueue = possibleStation;
				}
			}
			//return the Station with the lowest amount of Inqueue passengers if there is one
			if (lowestQueue != null)
				return lowestQueue;
		}

		//looking for the matching station and return it
		for (Station station : Station.getAllStations()) {
			if (stationLabel.equals(station.getLabel())) return station;

		}

		return null; //the matching station isn't found
	}

	/**
	 * Chooses a suited incoming queue of the given station and enter it
	 *
	 * @param station the station from where the queue should be chosen
	 */
	private void enterInQueue(Station station) {

		//get the stations incoming queues
		ArrayList<SynchronizedQueue> inQueues = station.getAllInQueues();

		//there is just one queue, enter it
		if (inQueues.size() == 1) inQueues.get(0).offer(this);

			//Do we have more than one incoming queue?
			//We have to make a decision which queue we choose -> your turn
		else {
			//get the first queue and it's size
			SynchronizedQueue queueBuffer = inQueues.get(0);
			int queueSize = queueBuffer.size();

			//Looking for the shortest queue (in a simple way)
			for (SynchronizedQueue inQueue : inQueues) {

				if (inQueue.size() < queueSize) {
					queueBuffer = inQueue;
					queueSize = inQueue.size();
				}
			}

			//enter the queue
			queueBuffer.offer(this);

		}

		//set actual station to the just entered station
		this.actualStation = station;

	}

	/**
	 * Adds a alternative Station as the next station of the passenger
	 *
	 * @param label the label of the Station to go
	 */
	void addAlternativeStationNext(String label) {
		this.stationsToGo.add(stationListPointer, label);
	}

	/**
	 * Chooses a suited outgoing queue of the given station and enter it
	 *
	 * @param station the station from where the queue should be chosen
	 */
	void enterOutQueue(Station station) {

		//get the stations outgoing queues
		ArrayList<SynchronizedQueue> outQueues = station.getAllOutQueues();


		//there is just one queue, enter it
		if (outQueues.size() == 1) outQueues.get(0).offer(this);

			//Do we have more than one outgoing queue?
			//We have to make a decision which queue we choose -> your turn
		else {

			//get the first queue and it's size
			SynchronizedQueue queueBuffer = outQueues.get(0);
			int queueSize = queueBuffer.size();

			//Looking for the shortest queue (in a simple way)
			for (SynchronizedQueue inQueue : outQueues) {

				if (inQueue.size() < queueSize) {
					queueBuffer = inQueue;
					queueSize = inQueue.size();
				}
			}

			//enter the queue
			queueBuffer.offer(this);

		}

	}

	/**
	 * Working method of the passenger
	 *
	 * @return false if the passenger has no more work to do for the moment,
	 * so the thread can fall into the wait() mode
	 */
	@Override
	protected boolean work() {

		//the passenger is leaving the station -> set actual station to null
		this.actualStation = null;

		//choose the next station to go to
		Station station = this.getNextStation();

		//only move if there is a next station found
		if (station == null) return false;

		//let the passenger move to the chosen station

		Statistics.show(this.getLabel() + " geht zur " + station.getLabel());

		//while target is not achieved
		while (!(station.getXPos() == this.xPos && station.getYPos() == this.yPos)) {

			//move to the station
			if (station.getXPos() > this.xPos) this.xPos++;
			if (station.getYPos() > this.yPos) this.yPos++;

			if (station.getXPos() < this.xPos) this.xPos--;
			if (station.getYPos() < this.yPos) this.yPos--;

			//set our view to the new position
			theView.setLocation(this.xPos, this.yPos);

			//let the thread sleep for the sequence time
			try {
				Thread.sleep(Simulation.SPEEDFACTOR * mySpeed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

		Statistics.show(this.getLabel() + " erreicht " + station.getLabel());

		//the passenger has reached the station, now the passenger chooses an incoming queue and enter it
		this.enterInQueue(station);

		//wake up the station
		station.wakeUp();

		//work is done
		return false;


	}

	/**
	 * Print some statistics
	 */
	public void printStatistics() {

		String theString = "\nObjekt: " + this.label;
		theString = theString + "\nZeit zum Behandeln des Objekts: " + measurement.myTreatmentTime;

		Statistics.show(theString);

	}

	/**
	 * Get the actual station where this passenger is in
	 *
	 * @return the actual station where this passenger is in, null if it's not in a station or a stations queue
	 */
	public Station getActualStation() {
		return actualStation;
	}

	/**
	 * Get the passengers processing time
	 *
	 * @return the processing time
	 */
	public int getProcessTime() {
		return processTime;
	}

	/**
	 * Adds Stations (Gastronomy, Shopping and Check-In's) for every individual Passenger based on onlinecheckin,
	 * isHungry and wouldLikeToShop booleans
	 *
	 * @throws IndexOutOfBoundsException there is no security check in the stations list
	 */
	private void addDynamicStations() throws IndexOutOfBoundsException {
		//Try to decide randomly if the Passenger decides to go Shopping first or visit the Gastronomy
		try {
			if (random.nextBoolean()) {
				//add the Stations (first Shops then Gastronomys)
				addDynamicShopStations();
				addDynamicGastronomyStations();
			} else {
				//add the Stations (first Gastronomys then Shops)
				addDynamicGastronomyStations();
				addDynamicShopStations();
			}
		}
		//Catches the IndexOutOfBoundsException if the stationsToGo doesn't contain SecurityChecks
		catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
			Statistics.show(this.getLabel() + " have to walk through a Security-Check but doesn't (according to XML File)");
		}
		addDynamicCheckInStation();
	}

	/**
	 * Randomly adds the CheckInDesk or the OnlineCheckIn as a station for the passenger somewhere before he passes the
	 * Security-Check
	 */
	private void addDynamicCheckInStation() {
		//gets the index of the Security-Check Station in indexSecurityCheck
		for (String station : stationsToGo) {
			if (station.contains("Security-Check")) {
				indexSecurityCheck = stationsToGo.indexOf(station);
			}
		}
		//gets a new Integer for the Position that is smaller than and excludes the index of the Security-Check
		int checkinPosition = random.nextInt(indexSecurityCheck);
		//Checks if the Position for the Check-In is now 0 and put it to 1 to prevent to be the starting Station
		if (checkinPosition == 0) {
			checkinPosition++;
		}
		//adds the correct Check-In Method to the stationsToGo based on if the passenger has a boardingpass
		if (boardingpass) {
			stationsToGo.add(checkinPosition, "OnlineCheckIn");
			stationsToGo.add(checkinPosition, "PublicTerminal");
		} else {
			stationsToGo.add(checkinPosition, "Check-InDesk");
			stationsToGo.add(checkinPosition, "PublicTerminal");
		}
	}

	/**
	 * Randomly adds the Shopping station as a station for the passenger somewhere before he passes the
	 * Security-Check or the DutyFreeShop behind the Security-Check if the passenger is willing to shop
	 */
	private void addDynamicShopStations() {
		//Checks if the Passenger wants to Shop (set in XML)
		if (wouldLikeToShop) {
			//Iterate over the stationsToGo to get the index of the SecurityCheck
			for (String station : stationsToGo) {
				if (station.contains("Security-Check")) {
					indexSecurityCheck = stationsToGo.indexOf(station);
				}
			}
			// Decides randomly if the Passenger decides to go Shopping at the PublicTerminal (Shopping)
			// or at the AirTerminal (DutyFreeShopping)
			if (random.nextBoolean()) {
				stationsToGo.add((indexSecurityCheck - 1), "Shopping");
				stationsToGo.add((indexSecurityCheck - 1), "PublicTerminal");
			} else {
				stationsToGo.add((indexSecurityCheck + 1), "DutyFreeShopping");
				stationsToGo.add((indexSecurityCheck + 1), "AirTerminal");
			}
		}
	}

	/**
	 * Randomly adds the Gastronomy as a station for the passenger somewhere before he passes the
	 * Security-Check behind the Security-Check if the passenger is hungry
	 */
	private void addDynamicGastronomyStations() {
		//Checks if the Passenger wants to visit the Gastronomy (set in XML)
		if (isHungry) {
			//Iterate over the stationsToGo to get the index of the SecurityCheck
			for (String station : stationsToGo) {
				if (station.contains("Security-Check")) {
					indexSecurityCheck = stationsToGo.indexOf(station);
				}
			}
			//Decides randomly if the Passenger decides to go to the Gastronomy at the PublicTerminal (Gastronomy_1)
			//or at the AirTerminal (Gastronomy_2)
			if (random.nextBoolean()) {
				stationsToGo.add((indexSecurityCheck - 1), "Gastronomy_1");
				stationsToGo.add((indexSecurityCheck - 1), "PublicTerminal");
			} else {
				stationsToGo.add((indexSecurityCheck + 1), "Gastronomy_2");
				stationsToGo.add((indexSecurityCheck + 1), "AirTerminal");
			}
		}
	}

	/**
	 * Sets the Luggage of the passenger
	 *
	 * @param luggage the luggage to be set
	 */
	public void setLuggage(Luggage luggage) {
		this.luggage = luggage;
	}

	/**
	 * @return theView The View of the passenger
	 */
	public PassengerView getTheView() {
		return theView;
	}

	/**
	 * Sets the View of the passenger
	 *
	 * @param theView the View to be set
	 */
	public void setTheView(PassengerView theView) {
		this.theView = theView;
	}

	/**
	 * A (static) inner class for measurement jobs. The class records specific values of the passenger during the simulation.
	 * These values can be used for statistic evaluation.
	 */
	static class Measurement {

		/**
		 * the treated time by all processing stations, in seconds
		 */
		int myTreatmentTime = 0;

	}

	/**
	 * @return the luggage of the passenger
	 */
	public Luggage getLuggage() {
		return luggage;
	}
}
	
