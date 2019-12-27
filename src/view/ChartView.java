package view;

import controller.Simulation;
import model.IChartViewable;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.UUID;

/**
 * This class contains the logic behind a ChartView window.
 * The private nested class ChartViewObservable in {@link controller.Simulation} notify this class about the start and the end of the simulation
 */
public class ChartView extends JFrame implements Observer {
	/**
	 * ChartView window width
	 */
	private final static int WIDTH = 400;
	/**
	 * ChartView window height
	 */
	private final static int HEIGHT = 300;
	/**
	 * ChartView window title
	 */
	private final static String TITLE = "Chart View";
	/**
	 * A list of all chartView instances.
	 * We need this list of all instances to add the chartView as a Observer in ChartViewObserverable in Controller.Simulation in a later step
	 */
	private static ArrayList<ChartView> chartViews = new ArrayList<ChartView>();
	/**
	 * A list of all registered chartView windows.
	 * All registeredChartViewElements will create a chartView at {@link ChartView#initChartViewers()}
	 */
	private static ArrayList<ChartViewElement> registeredChartViewElements = new ArrayList<>();
	/**
	 * The thread, in which the graph will be updated by runnable graphUpdaterRunner in the constructor
	 * It will be started and interrupted in {@link view.ChartView#update(Observable, Object)}
	 */
	private Thread graphUpdater;
	/**
	 * All times of the observed value
	 */
	private ArrayList<Long> xData = new ArrayList<Long>();
	/**
	 * All values the observed value
	 */
	private ArrayList<Double> yData = new ArrayList<Double>();
	/**
	 * The graph itself
	 */
	private XYChart chart;
	/**
	 * A SWING View of the Graph, which can be easily added to the SWING GUI
	 */
	private XChartPanel<XYChart> chartPanel;

	/**
	 * Constructor of the ChartView
	 *
	 * @param chartViewElement Metadata to create the view
	 */
	private ChartView(ChartViewElement chartViewElement) {
		// Adds the ChartView to a collection of all ChartViews.
		// We need a collection of all Chart Windows so we can add them as Observer for ChartViewObservable in Controller.Simulation
		chartViews.add(this);

		// This is a stupid workaround because XCharts QuickChart only accepts lists with at least one value.
		xData.add((long) 0);
		yData.add((double) 0);

		// We create a new chart using xCharts getChart() method
		chart = QuickChart.getChart(chartViewElement.getChartTitle(), chartViewElement.getXTITLE(),
				chartViewElement.getyTitle(), chartViewElement.getSeriesName(), xData, yData);

		// Clear the values of the workaround.
		xData.clear();
		yData.clear();

		// Create a panel from the graph that can easily be added to the Swing GUI
		chartPanel = new XChartPanel<XYChart>(chart);

		// This anonymouse class runnable regularly collects new values and displays them in the graph.
		// We choose to use a anonymouse instead of a nested class, because it's much easier to read.
		Runnable graphUpdaterRunner = new Runnable() {
			@Override
			public void run() {
				// Set the current time as the startTime and subtract it from future times to get the time since the start of the recording
				long startTime = Simulation.getGlobalTime();

				// While the simulation is running, collect values and update the graph
				while (true) {
					// Save in yData the current value of the observedValue
					yData.add(chartViewElement.getChartViewable().getChartObservedValue());
					// Save in xData the current Simulation time
					// Subtract the start time so that we get the actual time since the beginning of the simulation.
					xData.add(Simulation.getGlobalTime() - startTime);

					// Update the graph with the newly collected values.
					chart.updateXYSeries(chartViewElement.getSeriesName(), xData, yData, null);
					// In order for the updated graph to be displayed in Swing, we have to repaint it.
					chartPanel.repaint();

					try {
						// Let the thread sleep 250ms before getting the next value
						Thread.sleep(250);
					} catch (InterruptedException e) {
						// End loop if the thread is interrupted
						break;
					}
				}

				saveAsCsv(chartViewElement);
			}
		};

		// Assign the runner to the graphUpdater
		graphUpdater = new Thread(graphUpdaterRunner);

		// Add the graph to the window
		this.getContentPane().add(chartPanel, BorderLayout.CENTER);

		this.setSize(WIDTH, HEIGHT);
		this.setTitle(TITLE);

		this.setVisible(true);
	}

	/**
	 * Gets all ChartViews created
	 *
	 * @return The Chartviews created since the start of the application
	 */
	public static ArrayList<ChartView> getAllChartViews() {
		return chartViews;
	}

	/**
	 * Any chart viewable that wants to be displayed in a window must be registered here
	 *
	 * @param chartViewable The instance of the chartViewable, where the values will be collected an shown
	 * @param chartTitle    The title of the shown graph
	 * @param yTitle        The description of the Y - Axis
	 */
	public static void registerChartViewable(IChartViewable chartViewable, String chartTitle, String yTitle) {
		registeredChartViewElements.add(new ChartViewElement(chartViewable, chartTitle, yTitle));
	}

	/**
	 * Creates a ChartViewer window from all registered ChartViewables
	 */
	public static void initChartViewers() {
		for (ChartViewElement chartViewable : registeredChartViewElements) {
			new ChartView(chartViewable);
		}
	}

	/**
	 * Exports the xData and yData as a Csv-File
	 *
	 * @param element Metadata about the exported CSV - File
	 */
	private void saveAsCsv(ChartViewElement element) {
		/*
		  How the CSV should look like:

		  XTITLE,yTitle
		  1;4
		  2;5
		  3;5
		  4;6
		  ...
		 */
		try (PrintWriter writer = new PrintWriter(new File(element.getChartTitle() + ".csv"))) {

			// We use StringBuilder instead of String, because StringBuilder can append Text simpler and more efficient
			StringBuilder sb = new StringBuilder();

			// At first, write the headers
			sb.append(element.getXTITLE());
			sb.append(";");
			sb.append(element.getyTitle());
			sb.append("\r\n");

			// Write all values in the file
			for (int i = 0; i < xData.size(); i++) {
				sb.append(xData.get(i));
				sb.append(";");
				sb.append(yData.get(i));
				sb.append("\r\n");
			}

			// Save everything in the file
			writer.write(sb.toString());
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Starts or interrupts the graphUpdater Thread
	 *
	 * @param observable the Observable who's calling
	 * @param o          true: the graphUpdater Thread starts
	 *                   false: the graphUpdater Thread will be interrupted
	 */
	@Override
	public void update(Observable observable, Object o) {
		if ((Boolean) o)
			graphUpdater.start();
		else
			graphUpdater.interrupt();
	}

	/**
	 * This class contains all the information needed to create a GraphViewer.
	 */
	private static class ChartViewElement {
		/**
		 * The Title of the x - Axis. In our simulations it's always the time
		 */
		private final String XTITLE = "Time";
		/**
		 * A reference to the Station, where we retrieve our observed value
		 */
		private IChartViewable chartViewable;
		/**
		 * The title of the chart and the name of saved CSV File
		 */
		private String chartTitle;
		/**
		 * The name of the y - Axis
		 */
		private String yTitle;
		/**
		 * The name of the series. This must be unique value!
		 *
		 */
		private String seriesName;

		/**
		 * This class contains all information required to build a ChartView
		 *
		 * @param chartViewable A reference to the chartViewable, where the values will be collected
		 * @param chartTitle    The title of the shown graph and the name of the exported CSV - File
		 * @param yTitle        The description of the Y - Axis
		 */
		ChartViewElement(IChartViewable chartViewable, String chartTitle, String yTitle) {
			this.chartViewable = chartViewable;
			this.chartTitle = chartTitle;
			this.yTitle = yTitle;

			// A randomly generated string will be used for the seriesName
			this.seriesName = UUID.randomUUID().toString();
		}

		/**
		 * @return The ChartViewable (The registerd instance)
		 */
		IChartViewable getChartViewable() {
			return chartViewable;
		}

		/**
		 * @return The title of the chart
		 */
		String getChartTitle() {
			return chartTitle;
		}

		/**
		 * @return The title of the x - Axis
		 */
		String getXTITLE() {
			return XTITLE;
		}

		/**
		 * @return The title of the y - Axis
		 */
		String getyTitle() {
			return yTitle;
		}

		/**
		 * @return The unique name the graph value series
		 */
		String getSeriesName() {
			return seriesName;
		}
	}
}
