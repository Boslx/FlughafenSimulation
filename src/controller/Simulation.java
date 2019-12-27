package controller;

import io.Factory;
import io.Statistics;
import model.Actor;
import view.ChartView;
import view.SettingsView;
import view.SimulationView;

import javax.swing.*;
import java.io.File;
import java.util.Observable;
import java.util.concurrent.atomic.AtomicLong;


/**
 * The main class, controls the flow of the simulation
 */
public class Simulation {

	/**
	 * a speed factor for the clock to vary the speed of the clock in a simple way
	 */
	public static int SPEEDFACTOR = 1;
	/**
	 * the beat or speed of the clock, e.g. 300 means one beat every 300 milli seconds
	 */
	public static final int CLOCKBEAT = 300 * SPEEDFACTOR;
	/**
	 * is the simulation running
	 */
	private static boolean isRunning = false;

	/**
	 * true:  the Project will be loaded from JSON Files
	 * false: the Project will be loaded from XML Files
	 */
	private static boolean useJSON = false;
	/**
	 * the global clock
	 */
	//the clock must be thread safe -> AtomicLong. The primitive type long isn't, even if synchronized
	private static AtomicLong clock = new AtomicLong(0);

	/** creates an Observable for the ChartView so it can be Observed
	 *
	 */
	private static ChartViewObservable chartViewObservable = new ChartViewObservable();

	/**
	 * The folder, in which the scenario files are
	 */
	private static String scenarioFolder;

	/**
	 * Shows us if the Simulation is running
	 * @return isRunning indicates, whether the simulation is running
	 */
	public static boolean isRunning() {
		return isRunning;
	}

	/**
	 * Lets the Simulation start
	 * @param isRunning starts or stops the simulation
	 */
	public static void setRunning(boolean isRunning) {
		Simulation.isRunning = isRunning;
		chartViewObservable.notify(isRunning);
	}

	/**
	 * Start of the Simulation
	 *
	 * @param args Arguments. Not in use.
	 */
	public static void main(String[] args) {
		// Open a FileChooser Dialog to select the Scenario Folder
		JFileChooser f = new JFileChooser();
		f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		f.setDialogTitle("Open Scenario folder");
		f.setCurrentDirectory(new File(System.getProperty("user.dir") + "/scenarios"));

		// Only if the JFileChooser was successful, continue
		if (f.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			// creates the scenariofolder with filename,pathname and two slashes
			scenarioFolder = f.getSelectedFile().getAbsolutePath() + "\\";
			// creates the Window
			SettingsView settingswindow = new SettingsView();


		} else {
			// Prints Message if the user selected the wrong folder
			JOptionPane.showMessageDialog(null, "Please select the folder, in which the config files are");
		}
	}

	/**
	 * Starts the actual simulation after all settings have been configured.
	 *
	 * @param json for the use of json
	 */
	public static void startSimulation(boolean json) {
		useJSON = json;
		//a new simulation
		Simulation theSimulation = new Simulation();
		theSimulation.init();
		chartViewObservable.init();
	}


	/**
	 * Get the global time
	 *
	 * @return the global time
	 */
	public static long getGlobalTime() {
		return clock.get();
	}

	/**
	 * initialize the simulation
	 */
	private void init() {

		//create all stations and objects for the starting scenario out of XML
		Factory.createStartScenario(useJSON, scenarioFolder);
		ChartView.initChartViewers();

		//the view of our simulation
		new SimulationView();

		// set up the the heartbeat (clock) of the simulation
		new HeartBeat().start();

		Statistics.show("---- Simulation gestartet ---\n");

		// start all the actor threads
		for (Actor actor : Actor.getAllActors()) {
			actor.start();

		}

		/*
		 * Hinweis: wenn nicht über den Startbutton gestartet werden soll oder die Simulation ohne View laufen soll,
		 * den auskommentierten Code unten verwenden
		 */

		/*
		//Zeitpuffer vor Start -> sonst läuft der letzte manchmal nicht los
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}

		//wake up the start station -> lets the simulation run
		StartStation.getStartStation().wakeUp();

		*/

	}

	/**
	 * This class is responsible to notify ChartViews
	 */
	private static class ChartViewObservable extends Observable {
		/**
		 * Add all existing chartView Instances as Observers
		 */
		void init() {
			// let all ChartViews be Observed
			for (ChartView chartView : ChartView.getAllChartViews())
				this.addObserver(chartView);
		}

		/**
		 * Notify all ChartViews
		 *
		 * @param info true:  The simulation has been started
		 *             false: The simulation is over
		 */
		void notify(boolean info) {
			setChanged();
			notifyObservers(info);
		}
	}

	/**
	 * The heartbeat (the pulse) of the simulation, controls the clock.
	 */
	private class HeartBeat extends Thread {

		@Override
		public void run() {

			while (true) {

				try {

					Thread.sleep(CLOCKBEAT);

					//Increase the global clock
					clock.incrementAndGet();


				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}

}
