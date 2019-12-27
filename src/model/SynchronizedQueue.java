/**
 *
 */
package model;

import view.QueueView;
import view.QueueViewJPanel;
import view.QueueViewText;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Class for a synchronized thread safe queue
 */
public class SynchronizedQueue implements Queue<Object> {

	/** the instance of the queue */
	private static SynchronizedQueue theQueue;
	/** the view of the queue */
	private QueueView theView;
	/** the internal list */
	private LinkedList<Object> myList;


	/** creates a new queue
	 *
	 */
	private SynchronizedQueue() {
		myList = new LinkedList<Object>();
	}


	/** Creates a queue inclusive view
	 *
	 * @param queueView the class of the required queue view
	 * @param xPos x position of the queues view
	 * @param yPos y position of the queues view
	 *
	 * @return the created queue
	 */
	public static SynchronizedQueue createQueue(Class<?> queueView, int xPos, int yPos) {

		theQueue = new SynchronizedQueue();

		//check if the given "queueView" class is assignable to the wanted view class
		//if successful create the wanted QueueView object
		if (queueView.isAssignableFrom(QueueViewText.class))
			theQueue.theView = QueueViewText.create(theQueue.size(), xPos, yPos);
		if (queueView.isAssignableFrom(QueueViewJPanel.class))
			theQueue.theView = QueueViewJPanel.create(theQueue.size(), xPos, yPos);

		return theQueue;

	}


	/** updates the value of the view
	 *
	 */
	private void updateView() {
		theView.updateValue(this.size());
	}


	/** Wrapper for add,
	 * if there's a view it will be updated
	 *
	 * @see java.util.Queue#add(java.lang.Object)
	 */
	public synchronized boolean add(Object e) {

		boolean success = myList.add(e);

		//is there a view -> update it
		if (success && theView != null) updateView();

		return success;

	}

	/** Wrapper for offer,
	 * if there's a view it will be updated
	 *
	 * @see java.util.Queue#offer(java.lang.Object)
	 */
	@Override
	public synchronized boolean offer(Object e) {

		boolean success = myList.offer(e);

		//is there a view -> update it
		if (success && theView != null) updateView();

		return success;
	}

	/** Wrapper for poll,
	 * if there's a view it will be updated
	 *
	 * @see java.util.Queue#poll()
	 */
	@Override
	public synchronized Object poll() {

		Object head = myList.poll();

		//is there a view -> update it
		if (head != null && theView != null) updateView();

		return head;

	}


	/** Wrapper for remove,
	 * if there's a view it will be updated
	 *
	 * @see java.util.Queue#remove()
	 */
	@Override
	public synchronized Object remove() {

		Object head = myList.remove();

		//is there a view -> update it
		if (head != null && theView != null) updateView();

		return head;
	}

	/** Wrapper for remove(o),
	 * if there's a view it will be updated
	 *
	 * @see java.util.Queue#remove(java.lang.Object)
	 */
	@Override
	public synchronized boolean remove(Object o) {

		boolean success = myList.remove(o);

		//is there a view -> update it
		if (success && theView != null) updateView();

		return success;
	}

	/** Wrapper for clear,
	 * if there's a view it will be updated
	 *
	 * @see java.util.Queue#clear()
	 */
	@Override
	public synchronized void clear() {
		myList.clear();
		if (theView != null) updateView();
	}

	/**
	 *
	 * @return myList.peek
	 */
	@Override
	public synchronized Object peek() {
		return myList.peek();
	}

	/**
	 *
	 * @return myList.element
	 */
	@Override
	public synchronized Object element() {
		return myList.element();
	}

	/**
	 *
	 * @param c
	 * @return myList.addAll
	 */
	@Override
	public synchronized boolean addAll(Collection<?> c) {
		return myList.addAll(c);
	}

	/**
	 *
	 * @param o
	 * @return myList.contains
	 */
	@Override
	public synchronized boolean contains(Object o) {
		return myList.contains(o);
	}

	/**
	 *
	 * @param c
	 * @return myList.containsALl
	 */
	@Override
	public synchronized boolean containsAll(Collection<?> c) {
		return myList.containsAll(c);
	}

	/**
	 *
	 * @return myList.isEmpty
	 */
	@Override
	public synchronized boolean isEmpty() {
		return myList.isEmpty();
	}

	/**
	 *
	 * @return myList.iterator
	 */
	@Override
	public synchronized Iterator<Object> iterator() {
		return myList.iterator();
	}

	/**
	 *
	 * @param c
	 * @return myList.removeAll
	 */
	@Override
	public synchronized boolean removeAll(Collection<?> c) {
		return myList.removeAll(c);
	}

	/**
	 *
	 * @param c
	 * @return myList.retainAll
	 */
	@Override
	public synchronized boolean retainAll(Collection<?> c) {
		return myList.retainAll(c);
	}

	/**
	 *
	 * @return myList.size
	 */
	@Override
	public synchronized int size() {
		return myList.size();
	}

	/**
	 *
	 * @return myList.toArray
	 */
	@Override
	public synchronized Object[] toArray() {
		return myList.toArray();
	}

	@SuppressWarnings("unchecked")
	@Override
	public synchronized Object[] toArray(Object[] a) {
		return myList.toArray();
	}
}
