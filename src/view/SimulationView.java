package view;

import javax.swing.*;
import java.awt.*;

/**
 * Class for our main window
 */

@SuppressWarnings("serial")
public class SimulationView extends JFrame {

	/**
	 * main window width
	 */
	private final static int WIDTH = 1200;

	/**
	 * main window height
	 */
	private final static int HEIGHT = 700;

	/**
	 * main window title
	 */
	private final static String TITLE = "Prototyp: allgemeine Objekt/Queue/Station Simulation";

	/**
	 * a panel where the views of our actors can run
	 */
	private static SimulationPanel simulationPanel = new SimulationPanel();


	/**
	 * Creates a JFrame main window for our simulation
	 */
	public SimulationView() {
		this.init();

	}

	/**
	 * Add an actor view to the simulation view
	 *
	 * @param theView the actor view
	 */
	public static void addActorView(Component theView) {

		//adds the view to the panel
		simulationPanel.addActorView(theView);

	}

	/**
	 * initialize the main window
	 */
	private void init() {

		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setSize(WIDTH, HEIGHT);
		this.setTitle(TITLE);

		//create a start button
		StartButton startButton = new StartButton();

		//put the simulation panel into our JFrame
		this.getContentPane().add(simulationPanel, BorderLayout.CENTER);
		//put the start button into our JFrame
		this.getContentPane().add(startButton, BorderLayout.PAGE_END);

		this.setVisible(true);

	}

	/**
	 * Inner JPanel class where the simulation runs
	 */
	private static class SimulationPanel extends JPanel {

		/**
		 * Constructor initializes the panel
		 */
		SimulationPanel() {

			this.setBackground(Color.WHITE);

			// this gives us a layout which is controllable by coordinates,
			this.setLayout(null);

		}

		/**
		 * Adds a new actor view to the panel
		 *
		 * @param theView the actor view
		 */
		private void addActorView(Component theView) {
			this.add(theView);
			this.repaint();
		}

	}

}
