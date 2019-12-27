import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The model of the analysis application
 *
 * @author Team 5
 * @version 2019-11-28
 */
class AnalysisModel {

	private final ArrayList<Long> XDATA = new ArrayList<Long>();
	private final ArrayList<Double> YDATA = new ArrayList<Double>();

	/**
	 * @return Gets the xData Values
	 */
	public ArrayList<Long> getXDATA() {
		return XDATA;
	}

	/**
	 * @return Gets the yData Values
	 */
	public ArrayList<Double> getYDATA() {
		return YDATA;
	}

	/**
	 * Reads the values of a CSV File
	 *
	 * @param csvFile The path of the CSV File
	 * @throws IOException           Problems with the File creation
	 * @throws NumberFormatException Problems with parsing the numbers
	 */
	void parseCSV(String csvFile) throws IOException, NumberFormatException {
		BufferedReader br = null;
		String line = "";

		// Create new BufferedReader for the selected CSV-File. We use BufferedReader because it is the easiest to use.
		br = new BufferedReader(new FileReader(csvFile));

		// Skip first header line
		br.readLine();
		while ((line = br.readLine()) != null) {

			// seperate each line
			String[] country = line.split(";");
			XDATA.add(Long.parseLong(country[0]));
			YDATA.add(Double.parseDouble(country[1]));
		}
	}

	/**
	 * Finds the max Value in a ArrayList of values
	 *
	 * @return the highest value found
	 */
	Double getMaxValue() {
		// The maxValue found at a certain iteration
		Double maxValue = 0d;

		for (int i = 0; i < YDATA.size(); i++) {
			if (YDATA.get(i) > maxValue)
				maxValue = YDATA.get(i);
		}

		return maxValue;
	}

	/**
	 * Calculates the average value in the data
	 *
	 * @return The calculated average value
	 */
	Double getAverage() {
		ArrayList<Long> cleanedTimes = new ArrayList<Long>();
		ArrayList<Double> cleanedValues = new ArrayList<Double>();
		Long lastTime = -1L;

		// Create CleanedLists without duplicates
		for (int i = 0; i < XDATA.size(); i++) {
			// If the nextTime does not equal the lastTime, add to cleaned Lists
			if (!lastTime.equals(XDATA.get(i))) {
				cleanedTimes.add(XDATA.get(i));
				cleanedValues.add(YDATA.get(i));

				lastTime = XDATA.get(i);
			}
		}

		// Get the sum of all values in cleanedValues
		double sum = 0d;
		for (Double cleanValue : cleanedValues) {
			sum += cleanValue;
		}

		// Divide the sum of all cleaned values by their size
		return sum / cleanedValues.size();
	}
}
