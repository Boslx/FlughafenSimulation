package view;

import controller.Simulation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

/**
 * This Class is about a Settings Window to set up the Simulation
 */
public class SettingsView {
	/**
	 * creates the gui for SettingsView
	 */
	private JFrame frame;
	/**
	 * creates the container for the gui
	 */
	private Container contentPane;
	/**
	 * provides space in which an application can attach any other component.
	 */
	private JPanel options = new JPanel();
	/**
	 * decides if Json is used for the Simulation
	 */
	private boolean jsonboolean;
	/**
	 * creates the buttons for the SettingsView
	 */
	private ButtonGroup radiobuttons = new ButtonGroup();

	/**
	 * Constructer of SettingsView
	 *
	 */

	public SettingsView() {
		//creates the Frame
		createFrame();
	}

	/**
	 * Creates the Frame of the for the SettingsWindow and calls the methods to create it's layout and it's components
	 */
	private void createFrame() {
		frame = new JFrame("Settings");
		contentPane = frame.getContentPane();
		createLayout();
		addButtons();
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Creates the Layouts and adds them to the frame and the contentPane
	 */
	private void createLayout() {
		frame.setLayout(new BorderLayout());
		contentPane.add(options);
		options.setLayout(new GridLayout(2, 2, 20, 20));
	}

	/**
	 * Adds the Button and the RadioButton to the Window and their ActionListener
	 */
	private void addButtons() {
		ButtonGroup radiobuttons = new ButtonGroup();
		JButton simulationStart = new JButton("Start simulation");
		JRadioButton xml = new JRadioButton("XML");
		JRadioButton json = new JRadioButton("JSON");
		// creates the Radiobuttons
		radiobuttons.add(xml);
		radiobuttons.add(json);

		xml.setSelected(true);
		jsonboolean = false;

		/**
		 * creates ActionListener for the Startsimulation
		 */

		simulationStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				Simulation.startSimulation(jsonboolean);
				frame.setVisible(false);
			}
		});
		/**
		 * uses XML for the ActionEvent
		 */
		xml.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				jsonboolean = false;
			}
		});
		/**
		 * uses Json for the ActionEvent
		 */
		json.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				jsonboolean = true;
			}
		});
		//adds simulationStart, BorderLayout.SOUTH to the container
		contentPane.add(simulationStart, BorderLayout.SOUTH);
		// adds XML and JON to the Jpanel
		options.add(xml);
		options.add(json);
	}
}
