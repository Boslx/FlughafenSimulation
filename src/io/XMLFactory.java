package io;

import model.*;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import view.QueueViewJPanel;
import view.QueueViewText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This Factory creates Actors, Stations and their Queues from the XML Files
 */
public class XMLFactory implements IFactory {
	/**
	 * the terminals XML data file
	 */
	private static final String TERMINALS = "terminals.xml";

	/**
	 * the checkins XML data file
	 */
	private static final String CHECKINS = "checkins.xml";

	/**
	 * the consumes XML data file
	 */
	private static final String CONSUMES = "consume.xml";

	/**
	 * the securitychecks XML data file
	 */
	private static final String SECURITY_CHECKS = "securityChecks.xml";

	/**
	 * the passengers XML data file
	 */
	private static final String PASSENGERS = "passengers.xml";

	/**
	 * the boardinggates XML data file
	 */
	private static final String BOARDING_GATES = "boardingGates.xml";

	/**
	 * the startstation XML data file
	 */
	private static final String THESTARTSTATIONDATAFILE = "startstation.xml";

	/**
	 * the planes XML data file
	 */
	private static final String THEENDSTATIONDATAFILE = "planes.xml";

	/**
	 * the x position of the starting station, also position for all starting objects
	 */
	private static int XPOS_STARTSTATION;

	/**
	 * the y position of the starting station, also position for all starting objects
	 */
	private static int YPOS_STARTSTATION;

	/**
	 * create some terminal stations out of the XML file
	 */
	@Override
	public void createTerminals(String scenarioFolder) throws JDOMException, IOException {
		//read the information from the XML file into a JDOM Document
		Document theXMLDoc = new SAXBuilder().build(scenarioFolder + TERMINALS);

		//the <settings> ... </settings> node
		Element root = theXMLDoc.getRootElement();

		//get all the terminals into a List object
		List<Element> terminals = root.getChildren("terminal");

		//separate every JDOM "terminal" Element from the list and create Java Terminal objects
		for (Element terminal : terminals) {
			// data variables:
			String label = null;
			double troughPut = 0;
			int xPos = 0;
			int yPos = 0;
			String image = null;

			// read data
			label = terminal.getChildText("label");
			troughPut = Double.parseDouble(terminal.getChildText("troughput"));
			xPos = Integer.parseInt(terminal.getChildText("x_position"));
			yPos = Integer.parseInt(terminal.getChildText("y_position"));

			//the <view> ... </view> node
			Element viewGroup = terminal.getChild("view");
			// read data
			image = viewGroup.getChildText("image");

			//CREATE THE INQUEUE
			//the <inqueue> ... </inqueue> node
			Element inqueueGroup = terminal.getChild("inqueue");

			// the positions
			int xPosInQueue = Integer.parseInt(inqueueGroup.getChildText("x_position"));
			int yPosInQueue = Integer.parseInt(inqueueGroup.getChildText("y_position"));

			//create the inqueue
			SynchronizedQueue theInQueue = SynchronizedQueue.createQueue(QueueViewJPanel.class, xPosInQueue, yPosInQueue);

			//CREATE THE OUTQUEUE
			//the <outqueue> ... </outqueue> node
			Element outqueueGroup = terminal.getChild("outqueue");

			// the positions
			int xPosOutQueue = Integer.parseInt(outqueueGroup.getChildText("x_position"));
			int yPosOutQueue = Integer.parseInt(outqueueGroup.getChildText("y_position"));

			//create the outqueue
			SynchronizedQueue theOutQueue = SynchronizedQueue.createQueue(QueueViewText.class, xPosOutQueue, yPosOutQueue);


			//creating a new Terminal object
			Terminal.create(label, theInQueue, theOutQueue, troughPut, xPos, yPos, image);
		}
	}

	/**
	 * create some Check-In stations for the OnlineCheck-In and the normal Check-In out of the XML file
	 */
	@Override
	public void createCheckIns(String scenarioFolder) throws JDOMException, IOException {
		//read the information from the XML file into a JDOM Document
		Document theXMLDoc = new SAXBuilder().build(scenarioFolder + CHECKINS);

		//the <settings> ... </settings> node
		Element root = theXMLDoc.getRootElement();

		//get all the checkindesks into a List object
		List<Element> checkindesks = root.getChildren("checkindesk");

		//separate every JDOM "checkindesk" Element from the list and create Java CheckinDesk Objects
		for (Element checkindesk : checkindesks) {

			// data variables:
			String label = null;
			double troughPut = 0;
			int xPos = 0;
			int yPos = 0;
			String image = null;

			// read data
			label = checkindesk.getChildText("label");
			troughPut = Double.parseDouble(checkindesk.getChildText("troughput"));
			xPos = Integer.parseInt(checkindesk.getChildText("x_position"));
			yPos = Integer.parseInt(checkindesk.getChildText("y_position"));

			//the <view> ... </view> node
			Element viewGroup = checkindesk.getChild("view");
			// read data
			image = viewGroup.getChildText("image");

			//CREATE THE INQUEUE
			//the <inqueue> ... </inqueue> node
			Element inqueueGroup = checkindesk.getChild("inqueue");

			// the positions
			int xPosInQueue = Integer.parseInt(inqueueGroup.getChildText("x_position"));
			int yPosInQueue = Integer.parseInt(inqueueGroup.getChildText("y_position"));

			//create the inqueue
			SynchronizedQueue theInQueue = SynchronizedQueue.createQueue(QueueViewJPanel.class, xPosInQueue, yPosInQueue);

			//CREATE THE OUTQUEUE
			//the <outqueue> ... </outqueue> node
			Element outqueueGroup = checkindesk.getChild("outqueue");

			// the positions
			int xPosOutQueue = Integer.parseInt(outqueueGroup.getChildText("x_position"));
			int yPosOutQueue = Integer.parseInt(outqueueGroup.getChildText("y_position"));

			//create the outqueue
			SynchronizedQueue theOutQueue = SynchronizedQueue.createQueue(QueueViewText.class, xPosOutQueue, yPosOutQueue);


			//creating a new CheckInDesk object
			CheckInDesk.create(label, theInQueue, theOutQueue, troughPut, xPos, yPos, image);

		}

		//get all the onlinecheckins into a List object
		List<Element> onlinecheckins = root.getChildren("onlinecheckin");

		//separate every JDOM "onlinecheckin" Element from the list and create Java OnlineCheckIn Objects
		for (Element onlinecheckin : onlinecheckins) {

			// data variables:
			String label = null;
			double troughPut = 0;
			int xPos = 0;
			int yPos = 0;
			String image = null;

			// read data
			label = onlinecheckin.getChildText("label");
			troughPut = Double.parseDouble(onlinecheckin.getChildText("troughput"));
			xPos = Integer.parseInt(onlinecheckin.getChildText("x_position"));
			yPos = Integer.parseInt(onlinecheckin.getChildText("y_position"));

			//the <view> ... </view> node
			Element viewGroup = onlinecheckin.getChild("view");
			// read data
			image = viewGroup.getChildText("image");

			//CREATE THE INQUEUE
			//the <inqueue> ... </inqueue> node
			Element inqueueGroup = onlinecheckin.getChild("inqueue");

			// the positions
			int xPosInQueue = Integer.parseInt(inqueueGroup.getChildText("x_position"));
			int yPosInQueue = Integer.parseInt(inqueueGroup.getChildText("y_position"));

			//create the inqueue
			SynchronizedQueue theInQueue = SynchronizedQueue.createQueue(QueueViewJPanel.class, xPosInQueue, yPosInQueue);

			//CREATE THE OUTQUEUE
			//the <outqueue> ... </outqueue> node
			Element outqueueGroup = onlinecheckin.getChild("outqueue");

			// the positions
			int xPosOutQueue = Integer.parseInt(outqueueGroup.getChildText("x_position"));
			int yPosOutQueue = Integer.parseInt(outqueueGroup.getChildText("y_position"));

			//create the outqueue
			SynchronizedQueue theOutQueue = SynchronizedQueue.createQueue(QueueViewText.class, xPosOutQueue, yPosOutQueue);


			//creating a new OnlineCheckIn object
			OnlineCheckIn.create(label, theInQueue, theOutQueue, troughPut, xPos, yPos, image);
		}
	}


	/**
	 * create some Gastronomy and Shopping stations out of the XML file
	 */
	@Override
	public void createConsume(String scenarioFolder) throws JDOMException, IOException {
		//read the information from the XML file into a JDOM Document
		Document theXMLDoc = new SAXBuilder().build(scenarioFolder + CONSUMES);

		//the <settings> ... </settings> node
		Element root = theXMLDoc.getRootElement();

		//get all the gastronomys into a List object
		List<Element> gastronomys = root.getChildren("gastronomy");

		//separate every JDOM "gastronomy" Element from the list and create Java Gastronomy objects
		for (Element gastronomy : gastronomys) {

			// data variables:
			String label = null;
			double troughPut = 0;
			int xPos = 0;
			int yPos = 0;
			String image = null;
			boolean isDutyFree = false;
			double revenuePerCostumer = 0;

			// read data
			label = gastronomy.getChildText("label");
			troughPut = Double.parseDouble(gastronomy.getChildText("troughput"));
			xPos = Integer.parseInt(gastronomy.getChildText("x_position"));
			yPos = Integer.parseInt(gastronomy.getChildText("y_position"));
			isDutyFree = Boolean.parseBoolean(gastronomy.getChildText("dutyFree"));
			revenuePerCostumer = Double.parseDouble(gastronomy.getChildText("revenuePerCostumer"));

			//the <view> ... </view> node
			Element viewGroup = gastronomy.getChild("view");
			// read data
			image = viewGroup.getChildText("image");

			//CREATE THE INQUEUE
			//the <inqueue> ... </inqueue> node
			Element inqueueGroup = gastronomy.getChild("inqueue");

			// the positions
			int xPosInQueue = Integer.parseInt(inqueueGroup.getChildText("x_position"));
			int yPosInQueue = Integer.parseInt(inqueueGroup.getChildText("y_position"));

			//create the inqueue
			SynchronizedQueue theInQueue = SynchronizedQueue.createQueue(QueueViewJPanel.class, xPosInQueue, yPosInQueue);

			//CREATE THE OUTQUEUE
			//the <outqueue> ... </outqueue> node
			Element outqueueGroup = gastronomy.getChild("outqueue");

			// the positions
			int xPosOutQueue = Integer.parseInt(outqueueGroup.getChildText("x_position"));
			int yPosOutQueue = Integer.parseInt(outqueueGroup.getChildText("y_position"));

			//create the outqueue
			SynchronizedQueue theOutQueue = SynchronizedQueue.createQueue(QueueViewText.class, xPosOutQueue, yPosOutQueue);


			//creating a Gastronomy object
			Gastronomy.create(label, theInQueue, theOutQueue, troughPut, xPos, yPos, image, isDutyFree, revenuePerCostumer);
		}

		//get all the shops into a List object
		List<Element> shops = root.getChildren("shop");

		//separate every JDOM "shop" Element from the list and create Java Shop objects
		for (Element shop : shops) {

			// data variables:
			String label = null;
			double troughPut = 0;
			int xPos = 0;
			int yPos = 0;
			String image = null;
			boolean isDutyFree = false;
			double revenuePerCostumer;

			// read data
			label = shop.getChildText("label");
			troughPut = Double.parseDouble(shop.getChildText("troughput"));
			xPos = Integer.parseInt(shop.getChildText("x_position"));
			yPos = Integer.parseInt(shop.getChildText("y_position"));
			isDutyFree = Boolean.parseBoolean(shop.getChildText("dutyFree"));
			revenuePerCostumer = Double.parseDouble(shop.getChildText("revenuePerCostumer"));

			//the <view> ... </view> node
			Element viewGroup = shop.getChild("view");
			// read data
			image = viewGroup.getChildText("image");

			//CREATE THE INQUEUE
			//the <inqueue> ... </inqueue> node
			Element inqueueGroup = shop.getChild("inqueue");

			// the positions
			int xPosInQueue = Integer.parseInt(inqueueGroup.getChildText("x_position"));
			int yPosInQueue = Integer.parseInt(inqueueGroup.getChildText("y_position"));

			//create the inqueue
			SynchronizedQueue theInQueue = SynchronizedQueue.createQueue(QueueViewJPanel.class, xPosInQueue, yPosInQueue);

			//CREATE THE OUTQUEUE
			//the <outqueue> ... </outqueue> node
			Element outqueueGroup = shop.getChild("outqueue");

			// the positions
			int xPosOutQueue = Integer.parseInt(outqueueGroup.getChildText("x_position"));
			int yPosOutQueue = Integer.parseInt(outqueueGroup.getChildText("y_position"));

			//create the outqueue
			SynchronizedQueue theOutQueue = SynchronizedQueue.createQueue(QueueViewText.class, xPosOutQueue, yPosOutQueue);


			//creating a new Shop object
			Shop.create(label, theInQueue, theOutQueue, troughPut, xPos, yPos, image, isDutyFree, revenuePerCostumer);
		}
	}

	/**
	 * create the start station
	 */
	@Override
	public void createStartStation(String scenarioFolder) throws JDOMException, IOException {
		//read the information from the XML file into a JDOM Document
		Document theXMLDoc = new SAXBuilder().build(scenarioFolder + THESTARTSTATIONDATAFILE);

		//the <settings> ... </settings> node
		Element root = theXMLDoc.getRootElement();

		//get the start_station into a List object
		Element startStation = root.getChild("start_station");

		//get the label
		String label = startStation.getChildText("label");

		//get the position
		XPOS_STARTSTATION = Integer.parseInt(startStation.getChildText("x_position"));
		YPOS_STARTSTATION = Integer.parseInt(startStation.getChildText("y_position"));

		//the <view> ... </view> node
		Element viewGroup = startStation.getChild("view");
		// the image
		String image = viewGroup.getChildText("image");

		//CREATE THE INQUEUE
		//the <inqueue> ... </inqueue> node
		Element inqueueGroup = startStation.getChild("inqueue");

		// the positions
		int xPosInQueue = Integer.parseInt(inqueueGroup.getChildText("x_position"));
		int yPosInQueue = Integer.parseInt(inqueueGroup.getChildText("y_position"));

		//create the inqueue
		SynchronizedQueue theInQueue = SynchronizedQueue.createQueue(QueueViewText.class, xPosInQueue, yPosInQueue);

		//CREATE THE OUTQUEUE
		//the <outqueue> ... </outqueue> node
		Element outqueueGroup = startStation.getChild("outqueue");

		// the positions
		int xPosOutQueue = Integer.parseInt(outqueueGroup.getChildText("x_position"));
		int yPosOutQueue = Integer.parseInt(outqueueGroup.getChildText("y_position"));

		//create the outqueue
		SynchronizedQueue theOutQueue = SynchronizedQueue.createQueue(QueueViewText.class, xPosOutQueue, yPosOutQueue);

		//creating a new StartStation object
		StartStation.create(label, theInQueue, theOutQueue, XPOS_STARTSTATION, YPOS_STARTSTATION, image);
	}

	/**
	 * create some Passengers out of the XML file
	 */
	@Override
	public void createPassengers(String scenarioFolder) throws JDOMException, IOException {
		//read the information from the XML file into a JDOM Document
		Document theXMLDoc = new SAXBuilder().build(scenarioFolder + PASSENGERS);

		//the <settings> ... </settings> node, this is the files root Element
		Element root = theXMLDoc.getRootElement();

		//get all the passengers into a List
		List<Element> passengers = root.getChildren("passenger");

		//separate every JDOM "passenger" Element from the list and create Java Passenger objects
		for (Element passenger : passengers) {

			// data variables:
			String label = null;
			int processtime = 0;
			int speed = 0;
			String luggage_description = null;
			String flightnumber = null;
			boolean onlinecheckin = false;
			boolean isHungry = false;
			boolean wouldLikeToShop = false;
			boolean randomized = true;


			// read data
			label = passenger.getChildText("label");
			processtime = Integer.parseInt(passenger.getChildText("processtime"));
			speed = Integer.parseInt(passenger.getChildText("speed"));
			luggage_description = passenger.getChildText("luggage_description");
			flightnumber = passenger.getChildText("flightnumber");
			onlinecheckin = Boolean.parseBoolean(passenger.getChildText("onlinecheckin"));
			isHungry = Boolean.parseBoolean(passenger.getChildText("isHungry"));
			wouldLikeToShop = Boolean.parseBoolean(passenger.getChildText("wouldLikeToShop"));
			randomized = Boolean.parseBoolean((passenger.getChildText("randomized")));

			//get all the stations, where the passenger wants to go to
			//the <sequence> ... </sequence> node
			Element sequenceGroup = passenger.getChild("sequence");

			List<Element> allStations = sequenceGroup.getChildren("station");

			//get the elements into a list
			ArrayList<String> stationsToGo = new ArrayList<String>();

			for (Element theStation : allStations) {

				stationsToGo.add(theStation.getText());

			}

			//creating a new Passenger object
			Passenger.create(label, stationsToGo, processtime, speed, XPOS_STARTSTATION, YPOS_STARTSTATION, luggage_description, flightnumber, onlinecheckin, isHungry, wouldLikeToShop, randomized);
		}
	}

	/**
	 * create some Security-Check stations out of the XML file
	 */
	@Override
	public void createSecurityChecks(String scenarioFolder) throws JDOMException, IOException {
		//read the information from the XML file into a JDOM Document
		Document theXMLDoc = new SAXBuilder().build(scenarioFolder + SECURITY_CHECKS);

		//the <settings> ... </settings> node
		Element root = theXMLDoc.getRootElement();

		//get all the securitychecks into a List object
		List<Element> securityChecks = root.getChildren("securityCheck");

		//separate every JDOM "securitycheck" Element from the list and create Java Station objects
		for (Element securityCheck : securityChecks) {

			// data variables:
			String label = null;
			int maxPassengerInQueue = 0;
			double troughPut = 0;
			int xPos = 0;
			int yPos = 0;
			String image = null;

			// read data
			label = securityCheck.getChildText("label");
			troughPut = Double.parseDouble(securityCheck.getChildText("troughput"));
			xPos = Integer.parseInt(securityCheck.getChildText("x_position"));
			yPos = Integer.parseInt(securityCheck.getChildText("y_position"));

			//the <view> ... </view> node
			Element viewGroup = securityCheck.getChild("view");
			// read data
			image = viewGroup.getChildText("image");

			//CREATE THE INQUEUES

			//get all the inqueues into a List object
			List<Element> inqueues = securityCheck.getChildren("inqueue");

			//create a list of the stations inqueues
			ArrayList<SynchronizedQueue> theInqueues = new ArrayList<SynchronizedQueue>(); //ArrayList for the created inqueues

			for (Element inqueue : inqueues) {

				int xPosInQueue = Integer.parseInt(inqueue.getChildText("x_position"));
				int yPosInQueue = Integer.parseInt(inqueue.getChildText("y_position"));

				//create the actual inqueue an add it to the list
				theInqueues.add(SynchronizedQueue.createQueue(QueueViewJPanel.class, xPosInQueue, yPosInQueue));
			}

			//CREATE THE OUTQUEUES

			//get all the outqueues into a List object
			List<Element> outqueues = securityCheck.getChildren("outqueue");

			//create a list of the stations outqueues
			ArrayList<SynchronizedQueue> theOutqueues = new ArrayList<SynchronizedQueue>(); //ArrayList for the created outqueues

			for (Element outqueue : outqueues) {

				int xPosOutQueue = Integer.parseInt(outqueue.getChildText("x_position"));
				int yPosOutQueue = Integer.parseInt(outqueue.getChildText("y_position"));

				//create the actual outqueue an add it to the list
				theOutqueues.add(SynchronizedQueue.createQueue(QueueViewText.class, xPosOutQueue, yPosOutQueue));
			}

			//creating a new SecurityCheck object
			SecurityCheck.create(label, theInqueues, theOutqueues, troughPut, xPos, yPos, image);
		}
	}

	/**
	 * create some BoardingGate stations out of the XML file
	 */
	@Override
	public void createBoardingGates(String scenarioFolder) throws JDOMException, IOException {
		//read the information from the XML file into a JDOM Document
		Document theXMLDoc = new SAXBuilder().build(scenarioFolder + BOARDING_GATES);

		//the <settings> ... </settings> node
		Element root = theXMLDoc.getRootElement();

		//get all the schengenboardingsgates into a List object
		List<Element> schengenBoardingGates = root.getChildren("schengenBoardingGate");

		//separate every JDOM "schengenboardingsgate" Element from the list and create Java Station objects
		for (Element schengenBoardingGate : schengenBoardingGates) {

			// data variables:
			String label = null;
			double troughPut = 0;
			int xPos = 0;
			int yPos = 0;
			String image = null;

			// read data
			label = schengenBoardingGate.getChildText("label");
			troughPut = Double.parseDouble(schengenBoardingGate.getChildText("troughput"));
			xPos = Integer.parseInt(schengenBoardingGate.getChildText("x_position"));
			yPos = Integer.parseInt(schengenBoardingGate.getChildText("y_position"));

			//the <view> ... </view> node
			Element viewGroup = schengenBoardingGate.getChild("view");
			// read data
			image = viewGroup.getChildText("image");

			//CREATE THE INQUEUES

			//get all the inqueues into a List object
			List<Element> inqueues = schengenBoardingGate.getChildren("inqueue");

			//create a list of the stations inqueues
			ArrayList<SynchronizedQueue> theInqueues = new ArrayList<SynchronizedQueue>(); //ArrayList for the created inqueues

			for (Element inqueue : inqueues) {

				int xPosInQueue = Integer.parseInt(inqueue.getChildText("x_position"));
				int yPosInQueue = Integer.parseInt(inqueue.getChildText("y_position"));

				//create the actual inqueue an add it to the list
				theInqueues.add(SynchronizedQueue.createQueue(QueueViewJPanel.class, xPosInQueue, yPosInQueue));
			}

			//CREATE THE OUTQUEUES

			//get all the outqueues into a List object
			List<Element> outqueues = schengenBoardingGate.getChildren("outqueue");

			//create a list of the stations outqueues
			ArrayList<SynchronizedQueue> theOutqueues = new ArrayList<SynchronizedQueue>(); //ArrayList for the created outqueues

			for (Element outqueue : outqueues) {

				int xPosOutQueue = Integer.parseInt(outqueue.getChildText("x_position"));
				int yPosOutQueue = Integer.parseInt(outqueue.getChildText("y_position"));

				//create the actual outqueue an add it to the list
				theOutqueues.add(SynchronizedQueue.createQueue(QueueViewText.class, xPosOutQueue, yPosOutQueue));
			}

			//creating a new SchengenBoardingGate object
			SchengenBoardingGate.create(label, theInqueues, theOutqueues, troughPut, xPos, yPos, image);
		}

		//get all the internationalBoardingGates into a List object
		List<Element> internationalBoardingGates = root.getChildren("internationalBoardingGate");

		//separate every JDOM "internationalBoardingGate" Element from the list and create Java Station objects
		for (Element internationalBoardingGate : internationalBoardingGates) {

			// data variables:
			String label = null;
			double troughPut = 0;
			int xPos = 0;
			int yPos = 0;
			String image = null;

			// read data
			label = internationalBoardingGate.getChildText("label");
			troughPut = Double.parseDouble(internationalBoardingGate.getChildText("troughput"));
			xPos = Integer.parseInt(internationalBoardingGate.getChildText("x_position"));
			yPos = Integer.parseInt(internationalBoardingGate.getChildText("y_position"));

			//the <view> ... </view> node
			Element viewGroup = internationalBoardingGate.getChild("view");
			// read data
			image = viewGroup.getChildText("image");

			//CREATE THE INQUEUES

			//get all the inqueues into a List object
			List<Element> inqueues = internationalBoardingGate.getChildren("inqueue");

			//create a list of the stations inqueues
			ArrayList<SynchronizedQueue> theInqueues = new ArrayList<SynchronizedQueue>(); //ArrayList for the created inqueues

			for (Element inqueue : inqueues) {

				int xPosInQueue = Integer.parseInt(inqueue.getChildText("x_position"));
				int yPosInQueue = Integer.parseInt(inqueue.getChildText("y_position"));

				//create the actual inqueue an add it to the list
				theInqueues.add(SynchronizedQueue.createQueue(QueueViewJPanel.class, xPosInQueue, yPosInQueue));
			}

			//CREATE THE OUTQUEUES

			//get all the outqueues into a List object
			List<Element> outqueues = internationalBoardingGate.getChildren("outqueue");

			//create a list of the stations outqueues
			ArrayList<SynchronizedQueue> theOutqueues = new ArrayList<SynchronizedQueue>(); //ArrayList for the created outqueues

			for (Element outqueue : outqueues) {

				int xPosOutQueue = Integer.parseInt(outqueue.getChildText("x_position"));
				int yPosOutQueue = Integer.parseInt(outqueue.getChildText("y_position"));

				//create the actual outqueue an add it to the list
				theOutqueues.add(SynchronizedQueue.createQueue(QueueViewText.class, xPosOutQueue, yPosOutQueue));
			}

			//creating a new InternationalBoardingGate object
			InternationalBoardingGate.create(label, theInqueues, theOutqueues, troughPut, xPos, yPos, image);
		}
	}

	/**
	 * create the end stations (Planes)
	 */
	@Override
	public void createPlane(String scenarioFolder) throws JDOMException, IOException {
		//read the information from the XML file into a JDOM Document
		Document theXMLDoc = new SAXBuilder().build(scenarioFolder + THEENDSTATIONDATAFILE);

		//the <settings> ... </settings> node
		Element root = theXMLDoc.getRootElement();

		//get the planes into a List object
		List<Element> allPlanes = root.getChildren("plane");

		//separate every JDOM "plane" Element from the list and create Java Plane objects
		for (Element plane : allPlanes) {

			//get label
			String label = plane.getChildText("label");

			//position
			int xPos = Integer.parseInt(plane.getChildText("x_position"));
			int yPos = Integer.parseInt(plane.getChildText("y_position"));

			//the <view> ... </view> node
			Element viewGroup = plane.getChild("view");
			// the image
			String image = viewGroup.getChildText("image");

			//CREATE THE INQUEUE
			//the <inqueue> ... </inqueue> node
			Element inqueueGroup = plane.getChild("inqueue");

			// the positions
			int xPosInQueue = Integer.parseInt(inqueueGroup.getChildText("x_position"));
			int yPosInQueue = Integer.parseInt(inqueueGroup.getChildText("y_position"));

			//create the inqueue
			SynchronizedQueue theInQueue = SynchronizedQueue.createQueue(QueueViewText.class, xPosInQueue, yPosInQueue);

			//CREATE THE OUTQUEUE
			//the <outqueue> ... </outqueue> node
			Element outqueueGroup = plane.getChild("outqueue");

			// the positions
			int xPosOutQueue = Integer.parseInt(outqueueGroup.getChildText("x_position"));
			int yPosOutQueue = Integer.parseInt(outqueueGroup.getChildText("y_position"));

			//create the outqueue
			SynchronizedQueue theOutQueue = SynchronizedQueue.createQueue(QueueViewText.class, xPosOutQueue, yPosOutQueue);

			//creating a new Plane object
			Plane.create(label, theInQueue, theOutQueue, xPos, yPos, image);
		}
	}
}
