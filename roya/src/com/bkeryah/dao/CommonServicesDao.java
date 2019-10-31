package com.bkeryah.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.bkeryah.entities.ArcRecords;
import com.bkeryah.entities.ArcUsers;
import com.bkeryah.entities.WhsWarehouses;

import utilities.HijriCalendarUtil;
import utilities.Utils;

public abstract class CommonServicesDao extends HibernateTemplate {
	public abstract Integer save(Object myObject);

	public String readQuery(String QueryId) {
		Properties prop = new Properties();
		InputStream input = null;
		try {
			String filename = "commonQuery.properties";
			input = Utils.class.getClassLoader().getResourceAsStream(filename);
			prop.load(input);
			return prop.getProperty(QueryId);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return "";
	}

	public ArcRecords getNewArcRecord(Integer arcType, String recTitle, Integer letterto) {
		ArcRecords arcRecord = new ArcRecords();
		arcRecord.setApplicationType(arcType);
		arcRecord.setRecTitle(recTitle);
		ArcUsers user = Utils.findCurrentUser();
		Integer fromId = user.getUserId();
		String CurrentHijriDate = HijriCalendarUtil.findCurrentHijriDate();
		Date sysDate = new Date();
		arcRecord.setRecGDate(sysDate);
		arcRecord.setRecHDate(CurrentHijriDate);
		arcRecord.setCreatedIn(sysDate);
		arcRecord.setRecordIsImportant(0);
		arcRecord.setCreatedBy(fromId);
		arcRecord.setRecGDate(sysDate);
		arcRecord.setRecHDate(CurrentHijriDate);
		arcRecord.setCreatedIn(sysDate);
		arcRecord.setLetterFrom(user.getDeptId());
		arcRecord.setLetterTo(letterto);
		arcRecord.setIncomeYear(Integer.parseInt(HijriCalendarUtil.findCurrentYear()));
		return arcRecord;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Object findEntityById( Class entityClass, int EntityId) {
		return get(entityClass, EntityId);
	}
	
	

	public abstract Integer createNewArcRecord(Integer arcType, String recTitle, boolean withIncomeNumber,
			Integer toId);

	public abstract Integer findUserSection(int userId);

	public abstract String createInocmeNUmber();

	public List<WhsWarehouses> getStoreDeanWharehouses(Integer storeDeanId) {
		// TODO Auto-generated method stub
		return null;
	}
	


	
}
