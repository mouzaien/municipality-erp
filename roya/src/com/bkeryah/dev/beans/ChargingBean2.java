package com.bkeryah.dev.beans;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import com.bkeryah.entities.ArcUsers;
import com.bkeryah.entities.Charging;
import com.bkeryah.entities.WrkRoles;
import com.bkeryah.service.IDataAccessService;
import com.bkeryah.testssss.EmployeeForDropDown;

import utilities.Utils;

@ManagedBean
@ViewScoped
public class ChargingBean2 {

	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private java.util.List<String> xx;
	private Map<String, Integer> employeeMap;
	private Charging charging = new Charging();
	private ArcUsers chargingInUser;
	private ArcUsers chargingOutUser;
	private List<WrkRoles> roles;
	private List<Charging> charges = new ArrayList<Charging>();
	private List<Charging> filtredCharges = new ArrayList<Charging>();
	private String startDate;
	private String endDate;
	private List<EmployeeForDropDown> employeesList = new ArrayList<EmployeeForDropDown>();
	private boolean newFlag;
	private String bb;
	@PostConstruct
	public void init() {
		setEmployeeMap(dataAccessService.getEmployeeInfo());
		try {
			setRoles(dataAccessService.getallRoles());
			setCharges(dataAccessService.getallCharging());
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (Entry<String, Integer> entry : employeeMap.entrySet()) {
			if (entry.getKey() != null && entry.getValue() != null) {
				EmployeeForDropDown employee = new EmployeeForDropDown();
				employee.setName(entry.getKey());
				employee.setId(entry.getValue());
				employeesList.add(employee);
				employee = new EmployeeForDropDown();
			} else {
				continue;
			}
		}
	}

	public void newCharging(AjaxBehaviorEvent event) {

		charging = new Charging();
		newFlag = false;

	}

	public void finishCharging(AjaxBehaviorEvent event) {
		dataAccessService.finishChargingEmp(charging.getId());
		charging = new Charging();
		newFlag = false;

		try {
			charges = dataAccessService.getallCharging();

		} catch (Exception e) {
			charges = new ArrayList<>();
		}
	}

	public void showRecord(AjaxBehaviorEvent event) {

		newFlag = true;

	}

	public void updateNameAfterChoiseEmp(AjaxBehaviorEvent event) {
		String employeeOutId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("bts-ex-5");


		if (!employeeOutId.isEmpty()) {
			charging.setEmployeeOutChargingId(Integer.parseInt(employeeOutId.trim()));
			chargingOutUser = (ArcUsers) dataAccessService.findEntityById(ArcUsers.class,
					charging.getEmployeeOutChargingId());
			charging.setEmployeInChargingNameAfter(chargingOutUser.getFirstName());
			charging.setChargingEmpPrivilegeAfter(chargingOutUser.getWrkRoleId());

		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"اختر الموظف المراد تكليفه", "اختر الموظف المراد تكليفه"));
			return;
		}

	}

	
	public void addCharging(AjaxBehaviorEvent event) {
		Date gStartDate;
		Date gEndDate;
		Date todayWithZeroTime = null;

		startDate = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("startDate");
		endDate = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("endDate");
		String employeeInId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("bts-ex-4");
		try {
			gStartDate = Utils.convertHDateToGDate(startDate.trim());
			gEndDate = Utils.convertHDateToGDate(endDate.trim());

		} catch (Exception e1) {
			e1.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "خطا فى التاريخ", "خطا فى التاريخ"));
			return;
		}
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date today = new Date();
		try {
			todayWithZeroTime = formatter.parse(formatter.format(today));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (!checkFields(employeeInId, gStartDate, gEndDate, todayWithZeroTime))
			return;
		chargingOutUser = (ArcUsers) dataAccessService.findEntityById(ArcUsers.class,
				charging.getEmployeeOutChargingId());
		//charging.setEmployeInChargingId(Integer.parseInt(employeeInId.trim()));
		chargingInUser = (ArcUsers) dataAccessService.findEntityById(ArcUsers.class, charging.getEmployeInChargingId());
		charging.setChargingStratDate(startDate);
		charging.setChargingEndDate(endDate);
		charging.setEmployeInChargingNameBefore(chargingInUser.getFirstName());
		if (chargingOutUser.getWrkRoleId() == 1) {

		} else {
			charging.setChargingEmpManagerIdBefore(chargingInUser.getMgrId());
			if (chargingOutUser.getMgrId() == null) {
				charging.setChargingEmpManagerIdAfter(null);
			} else {
				charging.setChargingEmpManagerIdAfter(chargingOutUser.getMgrId());
			}
		}
		charging.setChargingEmpPrivilegeBefore(chargingInUser.getWrkRoleId());
		charging.setChargingStatus(2);
		try {
			dataAccessService.addChargingProcess(charging);
		} catch (Exception e) {
			e.printStackTrace();
		}

		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "  تم تنفيذ التكليف بنجاح ", "  تم تنفيذ التكليف بنجاح "));
		charges = dataAccessService.getallCharging();
		charging = new Charging();

	}

	/**
	 * Check fields
	 * 
	 * @param employeeInId
	 * @param startDate
	 * @param endDate
	 * @param gStartDate
	 * @param gEndDate
	 * @param todayWithZeroTime
	 * @return
	 */
	private boolean checkFields(String employeeInId, Date gStartDate, Date gEndDate, Date todayWithZeroTime) {
		boolean valid = true;

//		if (employeeInId.isEmpty()) {
//			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
//					"اختر الموظف المراد تكليفه", "اختر الموظف المراد تكليفه"));
//			valid = false;
//		}
		if (startDate.isEmpty() || endDate.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "من فضلك ادخل التاريخ", "من فضلك ادخل التاريخ"));
			valid = false;
		} else {
			if (!gEndDate.after(gStartDate) || gEndDate.before(new Date()) || gStartDate.before(todayWithZeroTime)) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "من فضلك ادخل تاريخ البداية و النهاية بشكل صحيح",
								"الشخص الذى اخترته لا يمكنت تكليفه فى الوقت الحالى"));
				valid = false;
			}
			if (!chekUserIn(charging.getEmployeInChargingId(), gStartDate, gEndDate)
					|| !chekUserOut(charging.getEmployeeOutChargingId(), gStartDate, gEndDate)) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								" لا يمكن  تنفيذ طلبك   الشخص المراد تكليفة  مكلف من قبل ",
								" لا يمكن  تنفيذ طلبك   الشخص المراد تكليفة  مكلف من قبل "));
				valid = false;
			}
		}
		return valid;
	}

	public boolean chekUserIn(Integer chargingInId, Date startDate, Date endDate) {
		boolean found = true;
		for (Charging charging : charges) {


			if (charging.getEmployeInChargingId().equals(chargingInId)) {

				try {

					if (!(Utils.convertHDateToGDate(charging.getChargingEndDate()).before(startDate)
							|| endDate.before(Utils.convertHDateToGDate(charging.getChargingStratDate())))) {
						found = false;
					}

					else {

						return found;
					}
				} catch (Exception e) {
					e.printStackTrace();
					found = true;

				}

			}
		}
		return found;
	}

	public boolean chekUserOut(Integer chargingOut, Date startDate, Date endDate) {
		boolean found = true;
		for (Charging charging : charges) {
			if (charging.getEmployeeOutChargingId().equals(chargingOut)) {

				try {

					if (!(Utils.convertHDateToGDate(charging.getChargingEndDate()).before(startDate)
							|| endDate.before(Utils.convertHDateToGDate(charging.getChargingStratDate())))) {
						found = false;
					}

					else {

						return found;
					}
				} catch (Exception e) {
					e.printStackTrace();
					found = true;

				}

			}
		}
		return found;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public Charging getCharging() {
		return charging;
	}

	public void setCharging(Charging charging) {
		this.charging = charging;
	}

	public Map<String, Integer> getEmployeeMap() {
		return employeeMap;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setEmployeeMap(Map<String, Integer> employeeMap) {
		this.employeeMap = employeeMap;
	}

	public java.util.List<String> getXx() {
		return xx;
	}

	public void setXx(java.util.List<String> xx) {
		this.xx = xx;
	}

	public ArcUsers getChargingInUser() {
		return chargingInUser;
	}

	public void setChargingInUser(ArcUsers chargingInUser) {
		this.chargingInUser = chargingInUser;
	}

	public ArcUsers getChargingOutUser() {
		return chargingOutUser;
	}

	public void setChargingOutUser(ArcUsers chargingOutUser) {
		this.chargingOutUser = chargingOutUser;
	}

	public List<WrkRoles> getRoles() {

		return roles;

	}

	public void setRoles(List<WrkRoles> roles) {
		this.roles = roles;
	}

	public List<Charging> getCharges() {
		return charges;
	}

	public boolean isNewFlag() {
		return newFlag;
	}

	public List<EmployeeForDropDown> getEmployeesList() {
		return employeesList;
	}

	public void setEmployeesList(List<EmployeeForDropDown> employeesList) {
		this.employeesList = employeesList;
	}

	public void setNewFlag(boolean newFlag) {
		this.newFlag = newFlag;
	}

	public void setCharges(List<Charging> charges) {
		this.charges = charges;
	}

	public String getBb() {
		return bb;
	}

	public void setBb(String bb) {
		this.bb = bb;
	}

	public List<Charging> getFiltredCharges() {
		return filtredCharges;
	}

	public void setFiltredCharges(List<Charging> filtredCharges) {
		this.filtredCharges = filtredCharges;
	}
}
