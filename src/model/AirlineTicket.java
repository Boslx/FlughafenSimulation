package model;

/**
 * This class contains all the information we need from a AirlineTicket
 */
class AirlineTicket {
	/** The name of the passenger */
	private String name;
	/** The flightnumer in the ticket */
	private String flightNumber;

	/**
	 * Constructer of AirlineTicket
	 *
	 * @param name         the name of the passenger
	 * @param flightNumber the flight number of the passenger
	 */
	public AirlineTicket(String name, String flightNumber) {
		this.name = name;
		this.flightNumber = flightNumber;
	}

	/**
	 * @return the name of the passenger
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the flightnumber
	 */
	public String getFlightNumber() {
		return flightNumber;
	}
}
