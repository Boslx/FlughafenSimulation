package view;

import controller.Simulation;
import model.StartStation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * A simple JButton class for a start button
 */
@SuppressWarnings("serial")
class StartButton extends JButton implements ActionListener {
	/**
	 * constructor for the StartButton class
	 */
	public StartButton() {
		super("START");
		this.addActionListener(this);
	}

	/**
	 * Sets the simulation on and lets it run
	 * @param event the action to be performed
	 */
	@Override
	public void actionPerformed(ActionEvent event) {

		//set the simulation on
		Simulation.setRunning(true);

		//wake up the start station -> lets the simulation run
		StartStation.getStartStation().wakeUp();
	}
}
