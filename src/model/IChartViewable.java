package model;

/**
 * Implement this Interface to make a station viewable in the ChartView Windows
 * <p>
 * Before your chart can be shown in the windows, you have to register it in {@link view.ChartView#registerChartViewable(IChartViewable, String, String)}
 */
public interface IChartViewable {
	/**
	 * @return The current value of the observed value
	 */
	double getChartObservedValue();
}
