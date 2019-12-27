package io;

import org.jdom2.JDOMException;

import javax.swing.*;
import java.io.IOException;

/**
 * This is an factory that creates all Stations for the Simulation.
 * It differentiates between XML and JSON files.
 */
public class
Factory {

	/**
	 * create the actors for the starting scenario
	 *
	 * @param useJSON        use for the JSONFactory
	 * @param scenarioFolder the Folder, in which the scenario is
	 */
	public static void createStartScenario(boolean useJSON, String scenarioFolder) {
		// This factory will be set in the next step
		IFactory factory;

		// Set the right Factory
		if (useJSON)
			factory = new JSONFactory();
		else
			factory = new XMLFactory();

		try {
			factory.createStartStation(scenarioFolder);
			factory.createPassengers(scenarioFolder);
			factory.createBoardingGates(scenarioFolder);
			factory.createConsume(scenarioFolder);
			factory.createTerminals(scenarioFolder);
			factory.createCheckIns(scenarioFolder);
			factory.createSecurityChecks(scenarioFolder);
			factory.createPlane(scenarioFolder);
		} catch (IOException | JDOMException e) {
			// Show a Error Message and close the Application with Exit Code 10 (ERROR)
			JOptionPane.showMessageDialog(null, e.getMessage(), "Loading Error!", JOptionPane.ERROR_MESSAGE);
			System.exit(10);
		}
	}
}
