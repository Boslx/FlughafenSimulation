import org.knowm.xchart.QuickChart;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * The View of the Analysis application
 *
 * @author Team 5
 * @version 2019-11-28
 */
class AnalysisView extends JFrame {

	/**
	 * Chart Analysis window width
	 */
	private final static int WIDTH = 500;
	/**
	 * Chart Analysis window height
	 */
	private final static int HEIGHT = 400;
	/**
	 * Chart Analysis window title
	 */
	private final static String TITLE = "Chart Analysis";
	private final AnalysisModel analysisModel;

	/**
	 * Constructor of the Analysis View
	 */
	private AnalysisView() {
		// Create new Analysis
		this.analysisModel = new AnalysisModel();

		// Create a new OpenFile Dialog
		JFileChooser fileChooser = new JFileChooser();

		// Set the working directory (project folder) as the shown folder
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

		// Make sure the User can only select csv Files
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Recordings", "csv");
		fileChooser.setFileFilter(filter);

		// Get the result of the openFileDialog and look if it was successful
		if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			System.out.println("Selected file: " + selectedFile.getAbsolutePath());

			// Parse the CSV File and close the project in case of an problem
			try {
				analysisModel.parseCSV(selectedFile.getAbsolutePath());
			} catch (IOException | NumberFormatException e) {
				// Bad files or bad values
				e.printStackTrace();
				System.exit(0);
			}


			XYChart chart = QuickChart.getChart("", "", "", "graph", analysisModel.getXDATA(), analysisModel.getYDATA());
			XChartPanel<XYChart> chartPanel = new XChartPanel<XYChart>(chart);
			this.setContentPane(chartPanel);

			this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			this.setTitle(TITLE);
			this.setSize(WIDTH, HEIGHT);

		} else {
			// If the selection was not successful, close the application.
			System.exit(0);
		}
	}

	/**
	 * The starting point of the analysis application
	 * @param args arguments (not in use)
	 */
	public static void main(String[] args) {
		AnalysisView view = new AnalysisView();
		view.setVisible(true);

		// Show a Messagebox with the AnalysisReport
		JOptionPane.showMessageDialog(view, view.getAnalysisReport());
	}

	/**
	 * Generates a Report of the Data
	 *
	 * @return The report as a String
	 */
	private String getAnalysisReport() {
		return "The MaxValue: " + analysisModel.getMaxValue() + "\n" +
				"The AverageValue: " + analysisModel.getAverage();
	}
}
