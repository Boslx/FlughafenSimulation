package model;

import controller.Simulation;
import io.Statistics;

import java.util.ArrayList;

/**
 * Superclass for all Actors
 */
public abstract class Actor extends Thread {

	/**
	 * all the Actor objects
	 */
	private static ArrayList<Actor> allActors = new ArrayList<Actor>();
	/**
	 * x position of the actor
	 */
	int xPos;
	/**
	 * y position of the actor
	 */
	int yPos;
	/**
	 * label of the actor
	 */
	String label;


	/**
	 * Constructor for all actors
	 *
	 * @param label of the actor
	 * @param xPos  x position of the actor
	 * @param yPos  y position of the actor
	 */
	Actor(String label, int xPos, int yPos) {

		allActors.add(this);

		//type of the actor
		this.label = label;

		//position of the actor
		this.xPos = xPos;
		this.yPos = yPos;

	}

	/**
	 * Get all Actors
	 *
	 * @return the allActors
	 */
	public static ArrayList<Actor> getAllActors() {
		return allActors;
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {

		//run the actor
		while (true) {

			try {

				//let the thread sleep for a little time
				//without that we've got a running problem
				Actor.sleep(Simulation.CLOCKBEAT);

				act();


			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Acting method for all actors
	 * Waits until the wakeUp() method notifies the thread, then the work() method is called
	 * notify
	 */
	private synchronized void act() {

		/*
		 * Let the thread wait only, if the simulation is still not running or,
		 * more important, if there is no more work to do for the moment
		 */

		if ((!Simulation.isRunning()) || (!work())) {

			//wait until a wake up (notify) instruction comes in
			try {
				Statistics.show(this.getLabel() + " wait()");
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Working method of the actor
	 *
	 * @return true if the actor has more work to do, and
	 * false if the actor has no more work to do for the moment, so the thread can fall into the wait() mode
	 */
	protected abstract boolean work();

	/**
	 * Wakes up the actor thread. This is the method where the notify() call must be placed
	 */
	public synchronized void wakeUp() {
		Statistics.show(this.getLabel() + " notify()");
		notify();
	}

	/**
	 * Get the actors label
	 *
	 * @return the label
	 */
	String getLabel() {
		return label;
	}

	/**
	 * get the actors x position
	 *
	 * @return the xPos
	 */
	int getXPos() {
		return xPos;
	}

	/**
	 * get the actors y position
	 *
	 * @return the xPos
	 */
	int getYPos() {
		return yPos;
	}
}
