package com.bkeryah.shared.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

import utilities.HijriCalendarUtil;
import utilities.HijriCalendarUtil.YearClass;

@ManagedBean(name = "endDateTimeBean")
@RequestScoped
public class EndDateTimeBean {
	private List<Integer> years = new ArrayList<>();
	private Integer currentMont = Integer.parseInt(HijriCalendarUtil.findCurrentMonth());
	private Integer currentDay = Integer.parseInt(HijriCalendarUtil.findCurrentDay());
	private Integer currentYear = Integer.parseInt(HijriCalendarUtil.findCurrentYear());
	private String curentDate;
	private List<YearClass> lstYears = new ArrayList<>();
	private HashMap<Integer, YearClass> lstYearsMap = new HashMap<Integer, YearClass>();
	private List<HijriMonth> lstMonths = new ArrayList<>();
	private List<HijriDay> lstDays = new ArrayList<>();
	private List<HijriDayRow> hijriDaysRows = new ArrayList<>();

	public String getCurentDate() {
		return curentDate;
	}

	public void setCurentDate(String curentDate) {
		this.curentDate = curentDate;
	}

	public List<HijriDayRow> getHijriDaysRows() {
		return hijriDaysRows;
	}

	public void setHijriDaysRows(List<HijriDayRow> hijriDaysRows) {
		this.hijriDaysRows = hijriDaysRows;
	}

	public List<HijriDay> getLstDays() {
		return lstDays;
	}

	public void setLstDays(List<HijriDay> lstDays) {
		this.lstDays = lstDays;
	}

	public List<Integer> getYears() {
		return years;
	}

	public List<HijriMonth> getLstMonths() {
		return lstMonths;
	}

	public void setLstMonths(List<HijriMonth> lstMonths) {
		this.lstMonths = lstMonths;
	}

	public void setYears(List<Integer> years) {
		this.years = years;
	}

	public List<HijriDay> getRowHiriDays(HijriDayRow SelectHijriRow) {
		for (HijriDayRow hijriDayRow : hijriDaysRows) {
			if (hijriDayRow.getId() == SelectHijriRow.getId())
				return hijriDayRow.getLstDays();
		}
		return null;
	}

	@PostConstruct
	public void init() {
		getAllYears();
		for (YearClass CurrentYear : lstYears) {
			lstYearsMap.put(CurrentYear.getYear(), CurrentYear);
		}
		Map<String, String> allHijriMonths = HijriCalendarUtil.findAllHijriMonths();
		for (Map.Entry<String, String> hijriMonth : allHijriMonths.entrySet()) {
			HijriMonth month = new HijriMonth();
			month.setId(Integer.parseInt(hijriMonth.getValue()));
			month.setName(hijriMonth.getKey());
			lstMonths.add(month);
		}
		initCalendar();
	}

	public void updateEndDays(int from) {
		if (from == 100) {
			if (currentMont < 12) {
				currentMont++;
				currentDay = 1;
			} else
				currentMont = 1;
		} else if (from == 200) {
			if (currentMont > 1) {
				currentMont--;
				currentDay = 1;
			}
		} else
			currentDay = from;
		initCalendar();

	}

	public void initCalendar() {
		int monthLength = HijriCalendarUtil.findMonthLength(currentYear, currentMont);
		hijriDaysRows.clear();
		curentDate = String.format("%1$02d/%2$02d/%3$04d", currentDay, currentMont, currentYear);
		int firstDay = HijriCalendarUtil.findFirstMonthDay(curentDate) - 1;
		int nbrRow = 1;
		{
			List<HijriDay> hijriDays = new ArrayList<>();
			for (int i = 1; i <= 7; i++) {
				HijriDay hijriDay = new HijriDay();
				if (i >= firstDay) {
					hijriDay.setId(i - firstDay + 1);
					hijriDay.setName(lstMonths.get(i).getName());
				}
				hijriDays.add(hijriDay);
			}
			HijriDayRow hijriRow = new HijriDayRow();
			hijriRow.setId(nbrRow);
			hijriRow.setLstDays(hijriDays);
			hijriDaysRows.add(hijriRow);
		}
		int lastIndex = 0;
		for (int j = 0; j < 3; j++) {
			nbrRow++;
			List<HijriDay> hijriDayss = new ArrayList<>();
			for (int i = 1; i <= 7; i++) {
				HijriDay hijriDay = new HijriDay();
				lastIndex = j * 7 + i + (7 - firstDay + 1);
				hijriDay.setId(j * 7 + i + (7 - firstDay + 1));
				hijriDay.setName(lstMonths.get(i).getName());
				hijriDayss.add(hijriDay);
			}
			HijriDayRow hijriRow = new HijriDayRow();
			hijriRow.setId(nbrRow);
			hijriRow.setLstDays(hijriDayss);
			hijriDaysRows.add(hijriRow);
		}
		{
			nbrRow++;
			List<HijriDay> hijriDays = new ArrayList<>();
			for (int i = lastIndex; i <= monthLength + (35 - monthLength - firstDay); i++) {
				HijriDay hijriDay = new HijriDay();
				if (lastIndex < monthLength) {
					hijriDay.setId(i + 1);
					hijriDay.setName(lstMonths.get(i - lastIndex + 1).getName());
				}
				lastIndex++;
				hijriDays.add(hijriDay);
			}
			HijriDayRow hijriRow = new HijriDayRow();
			hijriRow.setId(nbrRow);
			hijriRow.setLstDays(hijriDays);
			hijriDaysRows.add(hijriRow);
		}
	}

	public void updateDay(int day) {
		curentDate = String.format("%1$02d/%2$02d/%3$04d", currentDay, currentMont, currentYear);
	}

	protected List<Integer> getAllYears() {
		lstYears = HijriCalendarUtil.loadYearsData();
		return years;
	}

	public List<YearClass> getLstYears() {
		return lstYears;
	}

	public void setLstYears(List<YearClass> lstYears) {
		this.lstYears = lstYears;
	}

	public Integer getCurrentMont() {
		return currentMont;
	}

	public void setCurrentMont(Integer currentMont) {
		this.currentMont = currentMont;
	}

	public Integer getCurrentDay() {
		return currentDay;
	}

	public void setCurrentDay(Integer currentDay) {
		this.currentDay = currentDay;
	}

	public Integer getCurrentYear() {
		return currentYear;
	}

	public void setCurrentYear(Integer currentYear) {
		this.currentYear = currentYear;
	}

}
