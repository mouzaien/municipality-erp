package com.bkeryah.hr.managedBeans;

import java.io.Serializable;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.BarChartSeries;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import com.bkeryah.entities.ArcUsers;
import com.bkeryah.model.DashbordModel;
import com.bkeryah.service.IDataAccessService;

import utilities.Utils;

/**
 * This class contain data of dashboard screen.
 * 
 * @author Ghoyouth
 *
 */
@ManagedBean
@ViewScoped
public class ChartView implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private ArcUsers currentUser;
	private Integer archivedMails = 0;
	private Integer unreadMails;
	private Integer normalVacationCount;
	private Integer authorizationMonthCount;
	private String unreadMailsString;
	private LineChartModel lineChart =new LineChartModel();
	private BarChartModel barChart =new BarChartModel();

	/**
	 * Load params to create dashboard data
	 */
	@PostConstruct
	public void init() {
		currentUser = Utils.findCurrentUser();
		DashbordModel dashbordModel = dataAccessService.loadDasbordParams(currentUser.getUserId());
		if (dashbordModel != null) {
			archivedMails = dashbordModel.getArchNb();
			unreadMails = dashbordModel.getUnreadNb();
			authorizationMonthCount = dashbordModel.getAuthorizationNb();
			normalVacationCount = dashbordModel.getVacationNb();
		}
		createMonthlyTransactionsBarChart();
		createMonthlyTransactionsPercentLineChart();
	}

	/**
	 * Create bar chart for number of transactions for current user
	 */
	private void createMonthlyTransactionsBarChart() {
		barChart = initTransactionsChartData();
		barChart.setTitle(Utils.loadMessagesFromFile("monthly.transactions"));
		barChart.setAnimate(true);
		barChart.setLegendPosition("ne");
	}

	/**
	 * Load bar chart data and populate them
	 * 
	 * @return
	 */
	private BarChartModel initTransactionsChartData() {
		barChart = new BarChartModel();
		BarChartSeries series1 = new BarChartSeries();
		series1.setLabel(Utils.loadMessagesFromFile("transactions.NB"));
		Map<Object, Number> itemsMap = dataAccessService.loadArcRecordsNBForCurrentYear(currentUser.getUserId(), null);
		populateChart(series1, itemsMap);
		barChart.addSeries(series1);
		Axis yAxis = barChart.getAxis(AxisType.Y);
		yAxis.setMin(0);
		if (itemsMap != null && itemsMap.size() > 0)
			yAxis.setMax(Collections
					.max(itemsMap.entrySet(),
							(entry1, entry2) -> entry1.getValue().intValue() - entry2.getValue().intValue())
					.getValue().intValue() + 10);
		return barChart;
	}

	/**
	 * Create line chart for number of transactions for current user comparing
	 * it to number of transactions in his section
	 */
	private void createMonthlyTransactionsPercentLineChart() {
		lineChart = initTransactionsLineChartData();
		lineChart.setTitle(Utils.loadMessagesFromFile("monthly.transactions"));
		lineChart.setAnimate(true);
		lineChart.setLegendPosition("ne");
		CategoryAxis xAxis = new CategoryAxis();
		lineChart.getAxes().put(AxisType.X, xAxis);

	}

	/**
	 * Load line chart data and populate them
	 * 
	 * @return
	 */
	private LineChartModel initTransactionsLineChartData() {
		lineChart = new LineChartModel();
		LineChartSeries series1 = new LineChartSeries();
		series1.setLabel(Utils.loadMessagesFromFile("your.transactions"));
		Map<Object, Number> itemsMap = dataAccessService.loadArcRecordsNBForCurrentYear(currentUser.getUserId(), null);
		if (itemsMap.size() > 0) {
			populateChart(series1, itemsMap);
			lineChart.addSeries(series1);

			LineChartSeries series2 = new LineChartSeries();
			series2.setLabel(Utils.loadMessagesFromFile("section.transactions"));
			Map<Object, Number> itemsTotMap = dataAccessService.loadArcRecordsNBForCurrentYear(currentUser.getUserId(),
					currentUser.getDeptId());
			populateChart(series2, itemsTotMap);
			lineChart.addSeries(series2);

			Axis yAxis = lineChart.getAxis(AxisType.Y);
			yAxis.setMin(0);
			yAxis.setMax(Collections
					.max(itemsTotMap.entrySet(),
							(entry1, entry2) -> entry1.getValue().intValue() - entry2.getValue().intValue())
					.getValue().intValue() + 10);

		}
		return lineChart;
	}

	/**
	 * Convert map to chart series data
	 * 
	 * @param series1
	 * @param itemsMap
	 */
	private void populateChart(ChartSeries series1, Map<Object, Number> itemsMap) {
		int count = 1;
		while (count <= 12) {
			if (!itemsMap.containsKey(count))
				series1.set(Month.of(count).getDisplayName(TextStyle.FULL, new Locale("ar")), 0);
			else
				series1.set(Month.of(count).getDisplayName(TextStyle.FULL, new Locale("ar")), itemsMap.get(count));
			count++;
		}
	}

	/**
	 * Getters and setters
	 */

	public ArcUsers getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(ArcUsers currentUser) {
		this.currentUser = currentUser;
	}

	public Integer getArchivedMails() {
		return archivedMails;
	}

	public void setArchivedMails(Integer archivedMails) {
		this.archivedMails = archivedMails;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public Integer getUnreadMails() {
		return unreadMails;
	}

	public void setUnreadMails(Integer unreadMails) {
		this.unreadMails = unreadMails;
	}

	// public Long getNormalVacationCount() {
	// return normalVacationCount;
	// }
	//
	// public void setNormalVacationCount(Long normalVacationCount) {
	// this.normalVacationCount = normalVacationCount;
	// }
	//
	// public Long getExcuseVacationCount() {
	// return excuseVacationCount;
	// }
	//
	// public void setExcuseVacationCount(Long excuseVacationCount) {
	// this.excuseVacationCount = excuseVacationCount;
	// }

	public String getUnreadMailsString() {
		return unreadMailsString;
	}

	public void setUnreadMailsString(String unreadMailsString) {
		this.unreadMailsString = unreadMailsString;
	}

	public LineChartModel getLineChart() {
		return lineChart;
	}

	public void setLineChart(LineChartModel lineChart) {
		this.lineChart = lineChart;
	}

	public BarChartModel getBarChart() {
		return barChart;
	}

	public void setBarChart(BarChartModel barChart) {
		this.barChart = barChart;
	}

	public Integer getNormalVacationCount() {
		return normalVacationCount;
	}

	public void setNormalVacationCount(Integer normalVacationCount) {
		this.normalVacationCount = normalVacationCount;
	}

	public Integer getAuthorizationMonthCount() {
		return authorizationMonthCount;
	}

	public void setAuthorizationMonthCount(Integer authorizationMonthCount) {
		this.authorizationMonthCount = authorizationMonthCount;
	}
}
