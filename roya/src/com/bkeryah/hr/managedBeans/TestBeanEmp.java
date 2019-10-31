package com.bkeryah.hr.managedBeans;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.HibernateException;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.bkeryah.dao.DataAccessImpl;
import com.bkeryah.dao.ICommonDao;
import com.bkeryah.entities.ArcAttach;
import com.bkeryah.entities.ArcRecords;
import com.bkeryah.entities.EmployeeInitiation;
import com.bkeryah.entities.HrEmployeeVacation;
import com.bkeryah.entities.WrkApplication;
import com.bkeryah.entities.WrkComment;
import com.bkeryah.entities.WrkCommentType;
import com.bkeryah.entities.WrkPurpose;
import com.bkeryah.service.IDataAccessService;

import customeExceptions.VacationAndInitException;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class TestBeanEmp {
	private String x;
	@ManagedProperty(value="#{dataAccessService}")
	private IDataAccessService dataAccessService;
	@ManagedProperty(value="#{commonDao}")
	private ICommonDao commonDao;

	public void test() {
		EmployeeInitiation employeeInitiation = new EmployeeInitiation();

		employeeInitiation.setIntaionDay("fff");
		employeeInitiation.setVacationId(9978778);

		try {
			dataAccessService.saveInitaionAftrWorl(employeeInitiation);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "yyyyyyyyy ", "yyyyyyyyyy "));

		} catch (VacationAndInitException e3) {


			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e3.getMessage(), e3.getMessage()));
			// } catch (Exception e2) {
			// FacesContext.getCurrentInstance().addMessage(null,
			// new FacesMessage(FacesMessage.SEVERITY_ERROR, e2.getMessage(),
			// e2.getMessage()));
		}

	}

	public void load() {
		WrkApplication application = dataAccessService.getWrkApplicationRecordById(227377);


		// HrEmployeeVacation employeeVacation =
		// dataAccessService.getVacationById(22508);
		//
		// Integer lastvacationId =
		// dataAccessService.getLastVacationForCurrentId(4281051);

		// HrEmployeeVacation employeeVacation =
		// dataAccessService.getVacationById(lastvacationId);

	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public ICommonDao getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(ICommonDao commonDao) {
		this.commonDao = commonDao;
	}
}
