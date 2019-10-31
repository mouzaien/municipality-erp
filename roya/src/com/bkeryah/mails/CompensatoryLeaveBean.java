package com.bkeryah.mails;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.event.RowEditEvent;

import com.bkeryah.entities.ArcUsers;
import com.bkeryah.entities.HrsMasterFile;
import com.bkeryah.hr.entities.CompensatoryVacStock;
import com.bkeryah.hr.entities.VacCompensatoryDays;
import com.bkeryah.model.User;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class CompensatoryLeaveBean {
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private Integer vacHolidayType;
	private Integer vacDays;
	private Integer selectedUserId;
	private String vacHDate;
	private List<User> users = new ArrayList<User>();
	private List<User> selectedUsers = new ArrayList<User>();
	private CompensatoryVacStock emVAcSctock;
	private CompensatoryVacStock compVAcSctock = new CompensatoryVacStock();
	private Integer deptId = -1;
	private boolean disabled = true;
	private List<CompensatoryVacStock> usersCompVacStock = new ArrayList<CompensatoryVacStock>();
	private List<CompensatoryVacStock> filterCompVacStock;
	private List<CompensatoryVacStock> filterUsersCompVacStock;
	private ArcUsers currentUser = Utils.findCurrentUser();

	private List<HrsMasterFile> employers = new ArrayList<HrsMasterFile>();
	private List<Integer> years = new ArrayList<Integer>();

	private Integer empNO;
	private Integer qty;
	private Integer taken;
	private Integer empVacYear;
	private Integer empCompVacType;
	private Integer total;
	private List<CompensatoryVacStock> empCompVacStok = new ArrayList<CompensatoryVacStock>();
	private boolean higriMode;
	private Date mstartDate;
	private String startGeorDate;
	private String eName;

	@PostConstruct
	public void init() {
		fullYears();
		eName = currentUser.getFirstName();
		loadAllUserCompVacStock();
		users = dataAccessService.getAllEmployeesInDept(deptId);
		employers = dataAccessService.loadHrsMasterEmployers();
		// selectedUserId = currentUser.getEmployeeNumber();
	}

	public void loadAllUserCompVacStock() {
		usersCompVacStock = dataAccessService.getAllUsersCompVacStock();
	}

	// public void getEmployerVacStock(AjaxBehaviorEvent event) {
	public void getEmployerVacStock() {
		emVAcSctock = (CompensatoryVacStock) dataAccessService.findEntityById(CompensatoryVacStock.class,
				selectedUserId);
	}

	public void loadUsersListCompensatoryVacStockByEmpNo(AjaxBehaviorEvent event) {
		empCompVacStok = dataAccessService.getUserCompensatoryVacStockByEmpNo(selectedUserId, vacHolidayType);

		if (empCompVacStok == null) {
			MsgEntry.addErrorMessage("الموظف ليس له رصيد إجازات تعويضية لهذا العام");
			// System.out.println("الموظف ليس له رصيد");
			// FacesMessage msg = new FacesMessage("الموظف ليس له رصيد إجازات
			// تعويضية لهذا العام");
			// FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		total = 0;
		for (CompensatoryVacStock cvs : empCompVacStok) {
			total += cvs.getRemain();
		}
	}

	public String SaveVac() {
		if (empCompVacStok != null) {

			if (vacDays <= total) {
				try {
					// Date gDate = new Date();
					// vacHDate =
					// FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
					// .get("startDate");

					try {
						if (higriMode) {
							mstartDate = Utils.convertHDateToGDate(startGeorDate);
						} else {
							startGeorDate = Utils.grigDatesConvert(mstartDate);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					VacCompensatoryDays vacCompensatoryDays = new VacCompensatoryDays();
					vacCompensatoryDays.setDays(vacDays);
					vacCompensatoryDays.sethDate(startGeorDate);
					vacCompensatoryDays.setEmpno(selectedUserId);
					vacCompensatoryDays.setVacType(vacHolidayType);
					vacCompensatoryDays.setgDate(mstartDate);
					vacCompensatoryDays.setStatus(1);
					// emVAcSctock.setTaken(emVAcSctock.getTaken() + vacDays);
					// emVAcSctock.setRemain(emVAcSctock.getRemain() - vacDays);
					for (CompensatoryVacStock cvs : empCompVacStok) {
						if (vacDays <= cvs.getRemain()) {
							cvs.setTaken(cvs.getTaken() + vacDays);
							cvs.setRemain(cvs.getRemain() - vacDays);
							vacDays = 0;
						} else {
							int x = vacDays;
							int y = cvs.getRemain();
							vacDays = y;
							cvs.setTaken(cvs.getTaken() + vacDays);
							cvs.setRemain(cvs.getRemain() - vacDays);
							vacDays = x - y;
						}
						// dataAccessService.updateObject(cvs);
					}

					// dataAccessService.save(vacCompensatoryDays);
					dataAccessService.saveEmplyerVacStock(vacCompensatoryDays, empCompVacStok);

					// dataAccessService.saveEmplyerVacStock(vacCompensatoryDays,
					// empCompVacStok);
					// dataAccessService.save(vacCompensatoryDays);
					// dataAccessService.updateObject(empCompVacStok);
					;
				} catch (Exception e) {
					MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.execution"));
					e.printStackTrace();
				}
			} else {
				MsgEntry.addErrorMessage("الرصيد المتبقي لا يسمح");
			}
		} else {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.vacCompensatoryDays"));
		}
		return "";

	}

	public void saveVacaiton() {
		try {
			Calendar c = Calendar.getInstance();
			System.out.println(c.getTime());
			empCompVacStok = dataAccessService.getUserCompensatoryVacStockByEmpNo(empNO, vacHolidayType);
			if (empCompVacStok != null) {
				for (CompensatoryVacStock cvs : empCompVacStok) {
					if ((cvs.getYear()).equals(empVacYear) && (cvs.getComp_type()).equals(empCompVacType)) {
						MsgEntry.addErrorMessage(cvs.getEmpName() + " له رصيدسابق في هذا العام على نفس الإجازة  ");
						System.out.println(" رصيدسابق في هذا العام بنفس الإجازة");
					} else {
						compVAcSctock.setEmpno(empNO);
						compVAcSctock.setYear(empVacYear);
						compVAcSctock.setComp_type(empCompVacType);
						compVAcSctock.setQty(qty);
						compVAcSctock.setTaken(taken);
						compVAcSctock.setRemain(qty - taken);
						compVAcSctock.setVAC_TYPE(27);
						compVAcSctock.setComp_greg_date(c.getTime());
						compVAcSctock.setComp_higri_date(Utils.grigDatesConvert(compVAcSctock.getComp_greg_date()));
						Integer save = dataAccessService.save(compVAcSctock);

						dataAccessService.updateObject(compVAcSctock);
						MsgEntry.addInfoMessage("تم الحفظ");
						System.out.print("save number--->" + save);

					}
				}
			}

		} catch (Exception e) {
			MsgEntry.addErrorMessage("يجب تعبئة جميع الحقول");
			e.printStackTrace();
		}
		compVAcSctock = new CompensatoryVacStock();
		usersCompVacStock = dataAccessService.getAllUsersCompVacStock();
	}

	public void onRowEdit(RowEditEvent event) {
		CompensatoryVacStock selectedItem = (CompensatoryVacStock) event.getObject();
		dataAccessService.updateObject(selectedItem);
		FacesMessage msg = new FacesMessage("تم تغير أجازة الموظف رقم: " + selectedItem.getEmpName());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	//
	public void onRowCancel(RowEditEvent event) {
		CompensatoryVacStock selectedItem = (CompensatoryVacStock) event.getObject();
		FacesMessage msg = new FacesMessage(" تم إلغاء التعديل: " + selectedItem.getEmpName());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void fullYears() {
		for (int i = 0; i <= 22; i++) {
			years.add(i, 1438 + i);
		}
	}

	public void onRowSelect(AjaxBehaviorEvent event) {
		if (selectedUserId != null && selectedUsers.size() == 1) {
			selectedUserId = selectedUsers.get(0).getUserId();
			disabled = false;
		} else {
			disabled = true;
		}
	}

	public void loadEmployersByDept(AjaxBehaviorEvent event) {
		users = dataAccessService.getAllEmployeesInDept(deptId);
	}

	public Integer getVacHolidayType() {
		return vacHolidayType;
	}

	public void setVacHolidayType(Integer vacHolidayType) {
		this.vacHolidayType = vacHolidayType;
	}

	public Integer getVacDays() {
		return vacDays;
	}

	public void setVacDays(Integer vacDays) {
		this.vacDays = vacDays;
	}

	public Integer getSelectedUserId() {
		return selectedUserId;
	}

	public void setSelectedUserId(Integer selectedUserId) {
		this.selectedUserId = selectedUserId;
	}

	public String getVacHDate() {
		return vacHDate;
	}

	public void setVacHDate(String vacHDate) {
		this.vacHDate = vacHDate;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public CompensatoryVacStock getEmVAcSctock() {
		return emVAcSctock;
	}

	public void setEmVAcSctock(CompensatoryVacStock emVAcSctock) {
		this.emVAcSctock = emVAcSctock;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public List<User> getSelectedUsers() {
		return selectedUsers;
	}

	public void setSelectedUsers(List<User> selectedUsers) {
		this.selectedUsers = selectedUsers;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public List<CompensatoryVacStock> getUsersCompVacStock() {
		return usersCompVacStock;
	}

	public void setUsersCompVacStock(List<CompensatoryVacStock> usersCompVacStock) {
		this.usersCompVacStock = usersCompVacStock;
	}

	public List<CompensatoryVacStock> getFilterUsersCompVacStock() {
		return filterUsersCompVacStock;
	}

	public void setFilterUsersCompVacStock(List<CompensatoryVacStock> filterUsersCompVacStock) {
		this.filterUsersCompVacStock = filterUsersCompVacStock;
	}

	public CompensatoryVacStock getCompVAcSctock() {
		return compVAcSctock;
	}

	public void setCompVAcSctock(CompensatoryVacStock compVAcSctock) {
		this.compVAcSctock = compVAcSctock;
	}

	public List<Integer> getYears() {
		return years;
	}

	public void setYears(List<Integer> years) {
		this.years = years;
	}

	public List<HrsMasterFile> getEmployers() {
		return employers;
	}

	public void setEmployers(List<HrsMasterFile> employers) {
		this.employers = employers;
	}

	public List<CompensatoryVacStock> getFilterCompVacStock() {
		return filterCompVacStock;
	}

	public void setFilterCompVacStock(List<CompensatoryVacStock> filterCompVacStock) {
		this.filterCompVacStock = filterCompVacStock;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Integer getTaken() {
		return taken;
	}

	public void setTaken(Integer taken) {
		this.taken = taken;
	}

	public boolean isHigriMode() {
		return higriMode;
	}

	public void setHigriMode(boolean higriMode) {
		this.higriMode = higriMode;
	}

	public Date getMstartDate() {
		return mstartDate;
	}

	public void setMstartDate(Date mstartDate) {
		this.mstartDate = mstartDate;
	}

	public String getStartGeorDate() {
		return startGeorDate;
	}

	public void setStartGeorDate(String startGeorDate) {
		this.startGeorDate = startGeorDate;
	}

	public Integer getEmpVacYear() {
		return empVacYear;
	}

	public void setEmpVacYear(Integer empVacYear) {
		this.empVacYear = empVacYear;
	}

	public Integer getEmpCompVacType() {
		return empCompVacType;
	}

	public void setEmpCompVacType(Integer empCompVacType) {
		this.empCompVacType = empCompVacType;
	}

	public Integer getEmpNO() {
		return empNO;
	}

	public void setEmpNO(Integer empNO) {
		this.empNO = empNO;
	}

	public List<CompensatoryVacStock> getEmpCompVacStok() {
		return empCompVacStok;
	}

	public void setEmpCompVacStok(List<CompensatoryVacStock> empCompVacStok) {
		this.empCompVacStok = empCompVacStok;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public String geteName() {
		return eName;
	}

	public void seteName(String eName) {
		this.eName = eName;
	}

}