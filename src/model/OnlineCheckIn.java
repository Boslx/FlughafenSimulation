package model;

import view.PassengerView;

/**
 * This class extends the CheckIn class with more attributes and handles the Passenger objects.
 */
public class OnlineCheckIn extends CheckIn {
	private static OnlineCheckIn single_instance = null;

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
	private OnlineCheckIn(String label, SynchronizedQueue inQueue, SynchronizedQueue outQueue, double troughPut, int xPos, int yPos, String image) {
		// calls constructor of Superclass
		super(label, inQueue, outQueue, troughPut, xPos, yPos, image);
	}

	/**
	 * creates one OnlineCheckIn (Singleton)
	 * prevents more than one instance from being generated
	 *
	 * @param label		of the station
	 * @param inQueue	the incoming queue
	 * @param outQueue	the outgoing queue
	 * @param troughPut	the troughput of the station
	 * @param xPos		x position of the station
	 * @param yPos		y position of the station
	 * @param image		image of the station
	 * @return the created instance of OnlineCheckIn
	 */
	public static OnlineCheckIn create(String label, SynchronizedQueue inQueue, SynchronizedQueue outQueue, double troughPut, int xPos, int yPos, String image) {
		//creates OnlineCheckIn
		if (single_instance == null)
			single_instance = new OnlineCheckIn(label, inQueue, outQueue, troughPut, xPos, yPos, image);

		return single_instance;
	}

	/**
	 * The passenger goes to the OnlineCheckIn and deposits the luggage
	 *
	 * @param passenger the passenger to be handled
	 */
	@Override
	protected void handleObject(Passenger passenger) {
		//calls Superclass
		super.handleObject(passenger);
		//add the Luggage to the Check-In Luggage List
		droppedLuggage.add(passenger.getLuggage());
		//Set the Passengers Luggage null
		passenger.setLuggage(null);
		//Change the Icon of the Passenger
		PassengerView thisView = passenger.getTheView();
		thisView.setSize(0, 0);
		passenger.setTheView(PassengerView.create(passenger.getLabel(), "symbols/actor/50x50/ActorBlueTicket.png", passenger.getXPos(), passenger.getYPos()));
	}
}
