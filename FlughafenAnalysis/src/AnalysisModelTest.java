import org.junit.jupiter.api.Assertions;

import java.io.IOException;

/**
 * Tests whether the analysis methods function correctly
 *
 * @author Team 5
 * @version 2019-11-28
 */
class AnalysisModelTest {

	@org.junit.jupiter.api.Test
	void parseCSV() throws IOException {
		AnalysisModel test = new AnalysisModel();
		test.parseCSV("TestingCSV/Testing.csv");
		Assertions.assertEquals(10, test.getXDATA().size());
	}

	@org.junit.jupiter.api.Test
	void getMaxValue() throws IOException {
		AnalysisModel test = new AnalysisModel();
		test.parseCSV("TestingCSV/Testing.csv");
		Assertions.assertEquals(10, test.getMaxValue());
	}

	@org.junit.jupiter.api.Test
	void getAverage() throws IOException {
		AnalysisModel test = new AnalysisModel();
		test.parseCSV("TestingCSV/Testing.csv");
		Assertions.assertEquals(5.5, test.getAverage());
	}
}