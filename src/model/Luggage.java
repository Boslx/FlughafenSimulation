package model;

/**
 * The Luggage Class describes the Luggage that the Passengers carry with them on the Airport. It has a description
 * and an Owner. The Luggage should get taken away at the CheckInDesk or the OnlineCheckIn Stations before entering
 * the SecurityCheck section.
 */
class Luggage {
	/**
	 * Luggage description
	 */
	private String description;
	/**
	 * Owner of Luggage
	 */
	private Passenger owner;

	/**
	 * public constructor of Luggage
	 *
	 * @param description sets description
	 * @param owner       sets owner
	 */

	public Luggage(String description, Passenger owner) {
		this.description = description;
		this.owner = owner;
	}

	/**
	 * gets Description of Luggage
	 *
	 * @return description descripes Luggage
	 */

	public String getDescription() {
		return description;
	}

	/**
	 * gets Owner of Luggage
	 *
	 * @return owner of the Luggage
	 */
	public Passenger getOwner() {
		return owner;
	}
}
