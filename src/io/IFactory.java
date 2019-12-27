package io;

import org.jdom2.JDOMException;

import java.io.IOException;

/**
 * An Interface to implement in the Factorys to create all Actors, Stations and their Queues to make sure everything is
 * created correct no matter which Factory is used.
 */
interface IFactory {
	/**
	 * Creates the StartStations
	 *
	 * @param scenarioFolder The folder, in which the scenario file is
	 * @throws IOException   something is wrong with file
	 * @throws JDOMException we have trouble creating the station in the view
	 */
	void createStartStation(String scenarioFolder) throws IOException, JDOMException;

	/**
	 * Creates the Passengers
	 *
	 * @param scenarioFolder The folder, in which the scenario file is
	 * @throws IOException   something is wrong with file
	 * @throws JDOMException we have trouble creating the station in the view
	 */
	void createPassengers(String scenarioFolder) throws IOException, JDOMException;

	/**
	 * Creates the BoardingGates
	 *
	 * @param scenarioFolder The folder, in which the scenario file is
	 * @throws IOException   something is wrong with file
	 * @throws JDOMException we have trouble creating the station in the view
	 */
	void createBoardingGates(String scenarioFolder) throws IOException, JDOMException;

	/**
	 * Creates the Shops and Gastronomy
	 *
	 * @param scenarioFolder The folder, in which the scenario file is
	 * @throws IOException   something is wrong with file
	 * @throws JDOMException we have trouble creating the station in the view
	 */
	void createConsume(String scenarioFolder) throws IOException, JDOMException;

	/**
	 * creates the Terminals
	 *
	 * @param scenarioFolder The folder, in which the scenario file is
	 * @throws IOException   something is wrong with file
	 * @throws JDOMException we have trouble creating the station in the view
	 */
	void createTerminals(String scenarioFolder) throws IOException, JDOMException;

	/**
	 * Creates the CheckIns
	 *
	 * @param scenarioFolder The folder, in which the scenario file is
	 * @throws IOException   something is wrong with file
	 * @throws JDOMException we have trouble creating the station in the view
	 */
	void createCheckIns(String scenarioFolder) throws IOException, JDOMException;

	/**
	 * Creates the SecurityChecks
	 *
	 * @param scenarioFolder The folder, in which the scenario file is
	 * @throws IOException   something is wrong with file
	 * @throws JDOMException we have trouble creating the station in the view
	 */
	void createSecurityChecks(String scenarioFolder) throws IOException, JDOMException;

	/**
	 * Creates the Planes
	 *
	 * @param scenarioFolder The folder, in which the scenario file is
	 * @throws IOException   something is wrong with file
	 * @throws JDOMException we have trouble creating the station in the view
	 */
	void createPlane(String scenarioFolder) throws IOException, JDOMException;
}