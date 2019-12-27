package io;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;
import view.QueueViewJPanel;
import view.QueueViewText;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * This Factory creates Actors, Stations and their Queues from the JSON Files
 */

public class JSONFactory implements IFactory {
	/**
	 * the terminals JSON data file
	 */
	private static final String TERMINALS = "terminals.json";

	/**
	 * the checkins JSON data file
	 */
	private static final String CHECKINS = "checkins.json";

	/**
	 * the consumes JSON data file
	 */
	private static final String CONSUMES = "consume.json";

	/**
	 * the securitychecks JSON data file
	 */
	private static final String SECURITY_CHECKS = "securityChecks.json";

	/**
	 * the objects JSON data file
	 */
	private static final String PASSENGERS = "passengers.json";

	/**
	 * the boardinggates JSON data file
	 */
	private static final String BOARDING_GATES = "boardingGates.json";

	/**
	 * the start station JSON data file
	 */
	private static final String THESTARTSTATIONDATAFILE = "startstation.json";

	/**
	 * the end station JSON data file
	 */
	private static final String THEENDSTATIONDATAFILE = "planes.json";

	/**
	 * the x position of the starting station, also position for all starting objects
	 */
	private static int XPOS_STARTSTATION;

	/**
	 * the y position of the starting station, also position for all starting objects
	 */
	private static int YPOS_STARTSTATION;

	@Override
	public void createStartStation(String scenarioFolder) throws IOException {
		//read the information from the JSON file
		String content = new String(Files.readAllBytes(Paths.get(scenarioFolder + THESTARTSTATIONDATAFILE)));
		JSONArray startStations = new JSONObject(content).getJSONObject("settings").getJSONArray("start_station");

		//separate every JDOM "terminal" Element from the list and create Java Station objects
		for (int i = 0; i < startStations.length(); i++) {
			// data variables:
			String label = null;
			String image = null;

			// read the data from the json object
			label = startStations.getJSONObject(i).getString("label");
			XPOS_STARTSTATION = startStations.getJSONObject(i).getInt("x_position");
			YPOS_STARTSTATION = startStations.getJSONObject(i).getInt("y_position");

			//the <view> ... </view> node
			JSONObject viewGroup = startStations.getJSONObject(i).getJSONObject("view");
			// read data
			image = viewGroup.getString("image");

			//CREATE THE INQUEUE
			//the <inqueue> ... </inqueue> node
			JSONObject inqueueGroup = startStations.getJSONObject(i).getJSONObject("inqueue");

			// the positions
			int xPosInQueue = inqueueGroup.getInt("x_position");
			int yPosInQueue = inqueueGroup.getInt("y_position");

			//create the inqueue
			SynchronizedQueue theInQueue = SynchronizedQueue.createQueue(QueueViewJPanel.class, xPosInQueue, yPosInQueue);

			//CREATE THE OUTQUEUE
			//the <outqueue> ... </outqueue> node
			JSONObject outqueueGroup = startStations.getJSONObject(i).getJSONObject("outqueue");

			// the positions
			int xPosOutQueue = outqueueGroup.getInt("x_position");
			int yPosOutQueue = outqueueGroup.getInt("y_position");

			//create the outqueue
			SynchronizedQueue theOutQueue = SynchronizedQueue.createQueue(QueueViewText.class, xPosOutQueue, yPosOutQueue);


			//creating a new Terminal object
			StartStation.create(label, theInQueue, theOutQueue, XPOS_STARTSTATION, YPOS_STARTSTATION, image);
		}
	}

	@Override
	public void createPassengers(String scenarioFolder) throws IOException {
		//read the information from the JSON file into a JDOM Document
		String content = new String(Files.readAllBytes(Paths.get(scenarioFolder + PASSENGERS)));
		JSONArray passengers = new JSONObject(content).getJSONObject("settings").getJSONArray("passenger");

		//separate every JDOM "terminal" Element from the list and create Java Station objects
		for (int i = 0; i < passengers.length(); i++) {

			// data variables:
			String label = null;
			int processtime = 0;
			int speed = 0;
			String luggage_description = null;
			String flightnumber = null;
			boolean onlinecheckin = false;
			boolean isHungry = false;
			boolean wouldLikeToShop = false;
			boolean randomized = false;


			// read data
			label = passengers.getJSONObject(i).getString("label");
			processtime = passengers.getJSONObject(i).getInt("processtime");
			speed = passengers.getJSONObject(i).getInt("speed");
			luggage_description = passengers.getJSONObject(i).getString("luggage_description");
			flightnumber = passengers.getJSONObject(i).getString("flightnumber");
			onlinecheckin = passengers.getJSONObject(i).getBoolean("onlinecheckin");
			isHungry = passengers.getJSONObject(i).getBoolean("isHungry");
			wouldLikeToShop = passengers.getJSONObject(i).getBoolean("wouldLikeToShop");
			randomized = passengers.getJSONObject(i).getBoolean("randomized");

			//get all the stations, where the passenger wants to go to
			//the <sequence> ... </sequence> node
			JSONArray sequenceGroup = passengers.getJSONObject(i).getJSONArray("sequence");

			//get the elements into a list
			ArrayList<String> stationsToGo = new ArrayList<String>();

			for (int x = 0; x < sequenceGroup.length(); x++) {
				stationsToGo.add(sequenceGroup.getString(x));
			}

			//creating a new Passenger object
			Passenger.create(label, stationsToGo, processtime, speed, XPOS_STARTSTATION, YPOS_STARTSTATION,
					luggage_description, flightnumber, onlinecheckin, isHungry, wouldLikeToShop, randomized);
		}
	}

	@Override
	public void createBoardingGates(String scenarioFolder) throws IOException {
		//read the information from the JSON file into a JDOM Document
		String content = new String(Files.readAllBytes(Paths.get(scenarioFolder + BOARDING_GATES)));
		JSONArray schengenBoardingGate = new JSONObject(content).getJSONObject("settings").getJSONArray("schengenBoardingGate");

		//separate every JDOM "terminal" Element from the list and create Java Station objects
		for (int i = 0; i < schengenBoardingGate.length(); i++) {
			// data variables:
			String label = null;
			double troughPut = 0;
			int xPos = 0;
			int yPos = 0;
			String image = null;

			// read the data from the json object
			label = schengenBoardingGate.getJSONObject(i).getString("label");
			troughPut = schengenBoardingGate.getJSONObject(i).getDouble("troughput");
			xPos = schengenBoardingGate.getJSONObject(i).getInt("x_position");
			yPos = schengenBoardingGate.getJSONObject(i).getInt("y_position");

			//the <view> ... </view> node
			JSONObject viewGroup = schengenBoardingGate.getJSONObject(i).getJSONObject("view");
			// read data
			image = viewGroup.getString("image");

			//CREATE THE INQUEUES

			//get all the inqueues into a JSONArray
			JSONArray inqueues = schengenBoardingGate.getJSONObject(i).getJSONArray("inqueue");

			//create a list of the stations inqueues
			ArrayList<SynchronizedQueue> theInqueues = new ArrayList<SynchronizedQueue>(); //ArrayList for the created inqueues

			for (int x = 0; x < inqueues.length(); x++) {

				int xPosInQueue = inqueues.getJSONObject(x).getInt("x_position");
				int yPosInQueue = inqueues.getJSONObject(x).getInt("y_position");

				//create the actual inqueue an add it to the list
				theInqueues.add(SynchronizedQueue.createQueue(QueueViewJPanel.class, xPosInQueue, yPosInQueue));
			}

			//CREATE THE OUTQUEUES

			//get all the outqueues into a List object
			JSONArray outqueues = schengenBoardingGate.getJSONObject(i).getJSONArray("outqueue");

			//create a list of the stations outqueues
			ArrayList<SynchronizedQueue> theOutqueues = new ArrayList<SynchronizedQueue>(); //ArrayList for the created outqueues

			for (int x = 0; x < outqueues.length(); x++) {

				int xPosInQueue = outqueues.getJSONObject(x).getInt("x_position");
				int yPosInQueue = outqueues.getJSONObject(x).getInt("y_position");

				//create the actual inqueue an add it to the list
				theOutqueues.add(SynchronizedQueue.createQueue(QueueViewJPanel.class, xPosInQueue, yPosInQueue));
			}

			//creating a new SchengenBoardingGate object
			SchengenBoardingGate.create(label, theInqueues, theOutqueues, troughPut, xPos, yPos, image);
		}

		JSONArray internationalBoardingGate = new JSONObject(content).getJSONObject("settings").getJSONArray("internationalBoardingGate");

		//separate every JDOM "terminal" Element from the list and create Java Station objects
		for (int i = 0; i < internationalBoardingGate.length(); i++) {
			// data variables:
			String label = null;
			double troughPut = 0;
			int xPos = 0;
			int yPos = 0;
			String image = null;

			// read the data from the json object
			label = internationalBoardingGate.getJSONObject(i).getString("label");
			troughPut = internationalBoardingGate.getJSONObject(i).getDouble("troughput");
			xPos = internationalBoardingGate.getJSONObject(i).getInt("x_position");
			yPos = internationalBoardingGate.getJSONObject(i).getInt("y_position");

			//the <view> ... </view> node
			JSONObject viewGroup = internationalBoardingGate.getJSONObject(i).getJSONObject("view");
			// read data
			image = viewGroup.getString("image");

			//CREATE THE INQUEUES

			//get all the inqueues into a JSONArray
			JSONArray inqueues = internationalBoardingGate.getJSONObject(i).getJSONArray("inqueue");

			//create a list of the stations inqueues
			ArrayList<SynchronizedQueue> theInqueues = new ArrayList<SynchronizedQueue>(); //ArrayList for the created inqueues

			for (int x = 0; x < inqueues.length(); x++) {

				int xPosInQueue = inqueues.getJSONObject(x).getInt("x_position");
				int yPosInQueue = inqueues.getJSONObject(x).getInt("y_position");

				//create the actual inqueue an add it to the list
				theInqueues.add(SynchronizedQueue.createQueue(QueueViewJPanel.class, xPosInQueue, yPosInQueue));
			}

			//CREATE THE OUTQUEUES

			//get all the outqueues into a List object
			JSONArray outqueues = internationalBoardingGate.getJSONObject(i).getJSONArray("outqueue");

			//create a list of the stations outqueues
			ArrayList<SynchronizedQueue> theOutqueues = new ArrayList<SynchronizedQueue>(); //ArrayList for the created outqueues

			for (int x = 0; x < outqueues.length(); x++) {

				int xPosOutQueue = outqueues.getJSONObject(i).getInt("x_position");
				int yPosOutQueue = outqueues.getJSONObject(i).getInt("y_position");

				//create the actual inqueue an add it to the list
				theOutqueues.add(SynchronizedQueue.createQueue(QueueViewJPanel.class, xPosOutQueue, yPosOutQueue));
			}

			//creating a new InternationalBoardingGate object
			InternationalBoardingGate.create(label, theInqueues, theOutqueues, troughPut, xPos, yPos, image);
		}
	}

	@Override
	public void createConsume(String scenarioFolder) throws IOException {
		//read the information from the JSON file
		String content = new String(Files.readAllBytes(Paths.get(scenarioFolder + CONSUMES)));
		JSONArray restaurants = new JSONObject(content).getJSONObject("settings").getJSONArray("gastronomy");

		//separate every JDOM "terminal" Element from the list and create Java Station objects
		for (int i = 0; i < restaurants.length(); i++) {
			// data variables:
			String label = null;
			double troughPut = 0;
			int xPos = 0;
			int yPos = 0;
			String image = null;
			boolean isDutyFree = false;
			double revenuePerCostumer = 0;

			// read the data from the json object
			label = restaurants.getJSONObject(i).getString("label");
			troughPut = restaurants.getJSONObject(i).getDouble("troughput");
			xPos = restaurants.getJSONObject(i).getInt("x_position");
			yPos = restaurants.getJSONObject(i).getInt("y_position");
			isDutyFree = restaurants.getJSONObject(i).getBoolean("dutyFree");
			revenuePerCostumer = restaurants.getJSONObject(i).getDouble("revenuePerCostumer");

			//the <view> ... </view> node
			JSONObject viewGroup = restaurants.getJSONObject(i).getJSONObject("view");
			// read data
			image = viewGroup.getString("image");

			//CREATE THE INQUEUE
			//the <inqueue> ... </inqueue> node
			JSONObject inqueueGroup = restaurants.getJSONObject(i).getJSONObject("inqueue");

			// the positions
			int xPosInQueue = inqueueGroup.getInt("x_position");
			int yPosInQueue = inqueueGroup.getInt("y_position");

			//create the inqueue
			SynchronizedQueue theInQueue = SynchronizedQueue.createQueue(QueueViewJPanel.class, xPosInQueue, yPosInQueue);

			//CREATE THE OUTQUEUE
			//the <outqueue> ... </outqueue> node
			JSONObject outqueueGroup = restaurants.getJSONObject(i).getJSONObject("outqueue");

			// the positions
			int xPosOutQueue = outqueueGroup.getInt("x_position");
			int yPosOutQueue = outqueueGroup.getInt("y_position");

			//create the outqueue
			SynchronizedQueue theOutQueue = SynchronizedQueue.createQueue(QueueViewText.class, xPosOutQueue, yPosOutQueue);


			//creating a Gastronomy object
			Gastronomy.create(label, theInQueue, theOutQueue, troughPut, xPos, yPos, image, isDutyFree, revenuePerCostumer);
		}
		JSONArray shops = new JSONObject(content).getJSONObject("settings").getJSONArray("shop");

		//separate every JDOM "terminal" Element from the list and create Java Station objects
		for (int i = 0; i < shops.length(); i++) {
			// data variables:
			String label = null;
			double troughPut = 0;
			int xPos = 0;
			int yPos = 0;
			String image = null;
			boolean isDutyFree = false;
			double revenuePerCostumer = 0;

			// read the data from the json object
			label = shops.getJSONObject(i).getString("label");
			troughPut = shops.getJSONObject(i).getDouble("troughput");
			xPos = shops.getJSONObject(i).getInt("x_position");
			yPos = shops.getJSONObject(i).getInt("y_position");
			isDutyFree = shops.getJSONObject(i).getBoolean("dutyFree");
			revenuePerCostumer = shops.getJSONObject(i).getDouble("revenuePerCostumer");

			//the <view> ... </view> node
			JSONObject viewGroup = shops.getJSONObject(i).getJSONObject("view");
			// read data
			image = viewGroup.getString("image");

			//CREATE THE INQUEUE
			//the <inqueue> ... </inqueue> node
			JSONObject inqueueGroup = shops.getJSONObject(i).getJSONObject("inqueue");

			// the positions
			int xPosInQueue = inqueueGroup.getInt("x_position");
			int yPosInQueue = inqueueGroup.getInt("y_position");

			//create the inqueue
			SynchronizedQueue theInQueue = SynchronizedQueue.createQueue(QueueViewJPanel.class, xPosInQueue, yPosInQueue);

			//CREATE THE OUTQUEUE
			//the <outqueue> ... </outqueue> node
			JSONObject outqueueGroup = shops.getJSONObject(i).getJSONObject("outqueue");

			// the positions
			int xPosOutQueue = outqueueGroup.getInt("x_position");
			int yPosOutQueue = outqueueGroup.getInt("y_position");

			//create the outqueue
			SynchronizedQueue theOutQueue = SynchronizedQueue.createQueue(QueueViewText.class, xPosOutQueue, yPosOutQueue);


			//creating a Gastronomy object
			Shop.create(label, theInQueue, theOutQueue, troughPut, xPos, yPos, image, isDutyFree, revenuePerCostumer);
		}
	}

	@Override
	public void createTerminals(String scenarioFolder) throws IOException {
		//read the information from the JSON file
		String content = new String(Files.readAllBytes(Paths.get(scenarioFolder + TERMINALS)));
		JSONArray terminalObjects = new JSONObject(content).getJSONObject("settings").getJSONArray("terminal");

		//separate every JDOM "terminal" Element from the list and create Java Station objects
		for (int i = 0; i < terminalObjects.length(); i++) {
			// data variables:
			String label = null;
			double troughPut = 0;
			int xPos = 0;
			int yPos = 0;
			String image = null;

			// read the data from the json object
			label = terminalObjects.getJSONObject(i).getString("label");
			troughPut = terminalObjects.getJSONObject(i).getDouble("troughput");
			xPos = terminalObjects.getJSONObject(i).getInt("x_position");
			yPos = terminalObjects.getJSONObject(i).getInt("y_position");

			//the <view> ... </view> node
			JSONObject viewGroup = terminalObjects.getJSONObject(i).getJSONObject("view");
			// read data
			image = viewGroup.getString("image");

			//CREATE THE INQUEUE
			//the <inqueue> ... </inqueue> node
			JSONObject inqueueGroup = terminalObjects.getJSONObject(i).getJSONObject("inqueue");

			// the positions
			int xPosInQueue = inqueueGroup.getInt("x_position");
			int yPosInQueue = inqueueGroup.getInt("y_position");

			//create the inqueue
			SynchronizedQueue theInQueue = SynchronizedQueue.createQueue(QueueViewJPanel.class, xPosInQueue, yPosInQueue);

			//CREATE THE OUTQUEUE
			//the <outqueue> ... </outqueue> node
			JSONObject outqueueGroup = terminalObjects.getJSONObject(i).getJSONObject("outqueue");

			// the positions
			int xPosOutQueue = outqueueGroup.getInt("x_position");
			int yPosOutQueue = outqueueGroup.getInt("y_position");

			//create the outqueue
			SynchronizedQueue theOutQueue = SynchronizedQueue.createQueue(QueueViewText.class, xPosOutQueue, yPosOutQueue);


			//creating a new Terminal object
			Terminal.create(label, theInQueue, theOutQueue, troughPut, xPos, yPos, image);
		}
	}

	@Override
	public void createCheckIns(String scenarioFolder) throws IOException {
		//read the information from the JSON
		String content = new String(Files.readAllBytes(Paths.get(scenarioFolder + CHECKINS)));
		JSONArray checkindesks = new JSONObject(content).getJSONObject("settings").getJSONArray("checkindesk");

		//separate every JDOM "terminal" Element from the list and create Java Station objects
		for (int i = 0; i < checkindesks.length(); i++) {
			// data variables:
			String label = null;
			double troughPut = 0;
			int xPos = 0;
			int yPos = 0;
			String image = null;

			// read the data from the json object
			label = checkindesks.getJSONObject(i).getString("label");
			troughPut = checkindesks.getJSONObject(i).getDouble("troughput");
			xPos = checkindesks.getJSONObject(i).getInt("x_position");
			yPos = checkindesks.getJSONObject(i).getInt("y_position");

			//the <view> ... </view> node
			JSONObject viewGroup = checkindesks.getJSONObject(i).getJSONObject("view");
			// read data
			image = viewGroup.getString("image");

			//CREATE THE INQUEUE
			//the <inqueue> ... </inqueue> node
			JSONObject inqueueGroup = checkindesks.getJSONObject(i).getJSONObject("inqueue");

			// the positions
			int xPosInQueue = inqueueGroup.getInt("x_position");
			int yPosInQueue = inqueueGroup.getInt("y_position");

			//create the inqueue
			SynchronizedQueue theInQueue = SynchronizedQueue.createQueue(QueueViewJPanel.class, xPosInQueue, yPosInQueue);

			//CREATE THE OUTQUEUE
			//the <outqueue> ... </outqueue> node
			JSONObject outqueueGroup = checkindesks.getJSONObject(i).getJSONObject("outqueue");

			// the positions
			int xPosOutQueue = outqueueGroup.getInt("x_position");
			int yPosOutQueue = outqueueGroup.getInt("y_position");

			//create the outqueue
			SynchronizedQueue theOutQueue = SynchronizedQueue.createQueue(QueueViewText.class, xPosOutQueue, yPosOutQueue);


			//creating a Gastronomy object
			CheckInDesk.create(label, theInQueue, theOutQueue, troughPut, xPos, yPos, image);
		}
		JSONArray onlinecheckins = new JSONObject(content).getJSONObject("settings").getJSONArray("onlinecheckin");

		//separate every JDOM "terminal" Element from the list and create Java Station objects
		for (int i = 0; i < onlinecheckins.length(); i++) {
			// data variables:
			String label = null;
			double troughPut = 0;
			int xPos = 0;
			int yPos = 0;
			String image = null;

			// read the data from the json object
			label = onlinecheckins.getJSONObject(i).getString("label");
			troughPut = onlinecheckins.getJSONObject(i).getDouble("troughput");
			xPos = onlinecheckins.getJSONObject(i).getInt("x_position");
			yPos = onlinecheckins.getJSONObject(i).getInt("y_position");

			//the <view> ... </view> node
			JSONObject viewGroup = onlinecheckins.getJSONObject(i).getJSONObject("view");
			// read data
			image = viewGroup.getString("image");

			//CREATE THE INQUEUE
			//the <inqueue> ... </inqueue> node
			JSONObject inqueueGroup = onlinecheckins.getJSONObject(i).getJSONObject("inqueue");

			// the positions
			int xPosInQueue = inqueueGroup.getInt("x_position");
			int yPosInQueue = inqueueGroup.getInt("y_position");

			//create the inqueue
			SynchronizedQueue theInQueue = SynchronizedQueue.createQueue(QueueViewJPanel.class, xPosInQueue, yPosInQueue);

			//CREATE THE OUTQUEUE
			//the <outqueue> ... </outqueue> node
			JSONObject outqueueGroup = onlinecheckins.getJSONObject(i).getJSONObject("outqueue");

			// the positions
			int xPosOutQueue = outqueueGroup.getInt("x_position");
			int yPosOutQueue = outqueueGroup.getInt("y_position");

			//create the outqueue
			SynchronizedQueue theOutQueue = SynchronizedQueue.createQueue(QueueViewText.class, xPosOutQueue, yPosOutQueue);


			//creating a Gastronomy object
			OnlineCheckIn.create(label, theInQueue, theOutQueue, troughPut, xPos, yPos, image);
		}
	}

	@Override
	public void createSecurityChecks(String scenarioFolder) throws IOException {
		//read the information from the JSON
		String content = new String(Files.readAllBytes(Paths.get(scenarioFolder + SECURITY_CHECKS)));
		JSONArray securityChecks = new JSONObject(content).getJSONObject("settings").getJSONArray("securityCheck");

		//separate every JDOM "terminal" Element from the list and create Java Station objects
		for (int i = 0; i < securityChecks.length(); i++) {
			// data variables:
			String label = null;
			double troughPut = 0;
			int xPos = 0;
			int yPos = 0;
			String image = null;

			// read the data from the json object
			label = securityChecks.getJSONObject(i).getString("label");
			troughPut = securityChecks.getJSONObject(i).getDouble("troughput");
			xPos = securityChecks.getJSONObject(i).getInt("x_position");
			yPos = securityChecks.getJSONObject(i).getInt("y_position");

			//the <view> ... </view> node
			JSONObject viewGroup = securityChecks.getJSONObject(i).getJSONObject("view");
			// read data
			image = viewGroup.getString("image");

			//CREATE THE INQUEUES

			//get all the inqueues into a JSONArray
			JSONArray inqueues = securityChecks.getJSONObject(i).getJSONArray("inqueue");

			//create a list of the stations inqueues
			ArrayList<SynchronizedQueue> theInqueues = new ArrayList<SynchronizedQueue>(); //ArrayList for the created inqueues

			for (int x = 0; x < inqueues.length(); x++) {

				int xPosInQueue = inqueues.getJSONObject(x).getInt("x_position");
				int yPosInQueue = inqueues.getJSONObject(x).getInt("y_position");

				//create the actual inqueue an add it to the list
				theInqueues.add(SynchronizedQueue.createQueue(QueueViewJPanel.class, xPosInQueue, yPosInQueue));
			}

			//CREATE THE OUTQUEUES

			//get all the outqueues into a List object
			JSONArray outqueues = securityChecks.getJSONObject(i).getJSONArray("outqueue");

			//create a list of the stations outqueues
			ArrayList<SynchronizedQueue> theOutqueues = new ArrayList<SynchronizedQueue>(); //ArrayList for the created outqueues

			for (int x = 0; x < outqueues.length(); x++) {

				int xPosInQueue = outqueues.getJSONObject(x).getInt("x_position");
				int yPosInQueue = outqueues.getJSONObject(x).getInt("y_position");

				//create the actual inqueue an add it to the list
				theOutqueues.add(SynchronizedQueue.createQueue(QueueViewJPanel.class, xPosInQueue, yPosInQueue));
			}

			//creating a new SchengenBoardingGate object
			SecurityCheck.create(label, theInqueues, theOutqueues, troughPut, xPos, yPos, image);
		}
	}

	@Override
	public void createPlane(String scenarioFolder) throws IOException {
		//read the information from the JSON
		String content = new String(Files.readAllBytes(Paths.get(scenarioFolder + THEENDSTATIONDATAFILE)));
		JSONArray planes = new JSONObject(content).getJSONObject("settings").getJSONArray("plane");

		//separate every JDOM "terminal" Element from the list and create Java Station objects
		for (int i = 0; i < planes.length(); i++) {
			// data variables:
			String label = null;
			int xPos = 0;
			int yPos = 0;
			String image = null;

			// read the data from the json object
			label = planes.getJSONObject(i).getString("label");
			xPos = planes.getJSONObject(i).getInt("x_position");
			yPos = planes.getJSONObject(i).getInt("y_position");

			//the <view> ... </view> node
			JSONObject viewGroup = planes.getJSONObject(i).getJSONObject("view");
			// read data
			image = viewGroup.getString("image");

			//CREATE THE INQUEUE
			//the <inqueue> ... </inqueue> node
			JSONObject inqueueGroup = planes.getJSONObject(i).getJSONObject("inqueue");

			// the positions
			int xPosInQueue = inqueueGroup.getInt("x_position");
			int yPosInQueue = inqueueGroup.getInt("y_position");

			//create the inqueue
			SynchronizedQueue theInQueue = SynchronizedQueue.createQueue(QueueViewJPanel.class, xPosInQueue, yPosInQueue);

			//CREATE THE OUTQUEUE
			//the <outqueue> ... </outqueue> node
			JSONObject outqueueGroup = planes.getJSONObject(i).getJSONObject("outqueue");

			// the positions
			int xPosOutQueue = outqueueGroup.getInt("x_position");
			int yPosOutQueue = outqueueGroup.getInt("y_position");

			//create the outqueue
			SynchronizedQueue theOutQueue = SynchronizedQueue.createQueue(QueueViewText.class, xPosOutQueue, yPosOutQueue);


			//creating a Gastronomy object
			Plane.create(label, theInQueue, theOutQueue, xPos, yPos, image);
		}
	}
}
