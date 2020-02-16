package com.bkeryah.hr.managedBeans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.event.RowEditEvent;

import com.bkeryah.entities.HrsEmpHistorical;
import com.bkeryah.entities.HrsGovJob4;
import com.bkeryah.entities.HrsMasterFile;
import com.bkeryah.entities.Nationality;
import com.bkeryah.entities.WrkDept;
import com.bkeryah.entities.HrsSalaryScale;
import com.bkeryah.entities.HrsSalaryScaleDgrs;
import com.bkeryah.hr.entities.Sys035;
import com.bkeryah.service.IDataAccessService;

@ManagedBean(name = "empHistoryBean")
@ViewScoped
public class EmpHistoryBean {

	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private List<HrsEmpHistorical> empHistoryList = new ArrayList<HrsEmpHistorical>();
	private HrsEmpHistorical empHistory;
	private List<Nationality> sysList = new ArrayList<Nationality>();
	private List<HrsGovJob4> jobCreationList = new ArrayList<HrsGovJob4>();
	private List<WrkDept> deptList = new ArrayList<WrkDept>();
	private List<HrsSalaryScale> rankList = new ArrayList<HrsSalaryScale>();
	private List<HrsSalaryScaleDgrs> ranNumList = new ArrayList<HrsSalaryScaleDgrs>();
	private List<HrsSalaryScaleDgrs> ranNumListOut = new ArrayList<HrsSalaryScaleDgrs>();
	private Nationality sys;
	private List<Sys035> recoList = new ArrayList<Sys035>();
	private String empNo;
	private Integer RecordType;

	private List<HrsMasterFile> usrsList;
	private String CATegoryName;
	private String transferSalary;

	@PostConstruct
	public void init() {
		usrsList = dataAccessService.getAllEmployeesList();
		sysList = dataAccessService.getallNationalities();
		recoList = dataAccessService.loadAllJobRec();
		jobCreationList = dataAccessService.findAllJobCreat();
		deptList = dataAccessService.findAllDepartments();
		rankList = dataAccessService.loadAllJobRanks(); // المرتبة >> classNumber
		ranNumListOut=dataAccessService.loadJobRaNum();
	}

	public void onRowEdit(RowEditEvent event) {

		HrsEmpHistorical selectedItem = (HrsEmpHistorical) event.getObject();
		FacesMessage msg = new FacesMessage("تم حفظ التعديل");
		dataAccessService.updateObject(selectedItem);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("تم إلغاء التعديل");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void loadAllEmpsHis(AjaxBehaviorEvent E) {
		try {
			System.out.println("empNo >>>>>> " + empNo);
			if ("-1".equalsIgnoreCase(empNo)) {
				empHistoryList = dataAccessService.findAllHrsEmpHistorical();
			} else {
				if (empNo != null && !empNo.isEmpty() && empNo.length() < 10) {
					empHistoryList = dataAccessService.findEmpHistoricalByEmpNo(Integer.parseInt(empNo));
				} else {
					empHistoryList = new ArrayList<>();
				}
			}
		} catch (Exception e) {

		}
	}

	public void loadAllScaleDgr(Integer rankNum) {
		if(rankNum != null)
		ranNumList = dataAccessService.loadScaleDgree(rankNum); // الدرجة >> RankNumber
	}
	
	public String movementTitle(Integer id) {
		if(id != null) {
		Sys035 move=(Sys035)dataAccessService.findEntityById(Sys035.class, id);
		return move.getName();
		}
		return null;
		
	}
//	public String jobName(String id) {
//		if(id != null) {
//			HrsGovJob4 job=(HrsGovJob4)dataAccessService.findEntityById(HrsGovJob4.class, id);
//		return job.getJobName();
//		}
//		return null;
//		
//	}
	
	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public List<HrsEmpHistorical> getEmpHistoryList() {
		return empHistoryList;
	}

	public void setEmpHistoryList(List<HrsEmpHistorical> empHistoryList) {
		this.empHistoryList = empHistoryList;
	}

	public HrsEmpHistorical getEmpHistory() {
		return empHistory;
	}

	public void setEmpHistory(HrsEmpHistorical empHistory) {
		this.empHistory = empHistory;
	}

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public List<HrsMasterFile> getUsrsList() {
		return usrsList;
	}

	public void setUsrsList(List<HrsMasterFile> usrsList) {
		this.usrsList = usrsList;
	}

	public List<Nationality> getSysList() {
		return sysList;
	}

	public void setSysList(List<Nationality> sysList) {
		this.sysList = sysList;
	}

	public Nationality getSys() {
		return sys;
	}

	public void setSys(Nationality sys) {
		this.sys = sys;
	}

	public String getCATegoryName() {
		return CATegoryName;
	}

	public void setCATegoryName(String cATegoryName) {
		CATegoryName = cATegoryName;
	}

	public Integer getRecordType() {
		return RecordType;
	}

	public void setRecordType(Integer recordType) {
		RecordType = recordType;
	}

	public List<Sys035> getRecoList() {
		return recoList;
	}

	public void setRecoList(List<Sys035> recoList) {
		this.recoList = recoList;
	}

	public List<HrsGovJob4> getJobCreationList() {
		return jobCreationList;
	}

	public void setJobCreationList(List<HrsGovJob4> jobCreationList) {
		this.jobCreationList = jobCreationList;
	}

	public List<WrkDept> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<WrkDept> deptList) {
		this.deptList = deptList;
	}

	public List<HrsSalaryScale> getRankList() {
		return rankList;
	}

	public void setRankList(List<HrsSalaryScale> rankList) {
		this.rankList = rankList;
	}

	public List<HrsSalaryScaleDgrs> getRanNumList() {
		return ranNumList;
	}

	public void setRanNumList(List<HrsSalaryScaleDgrs> ranNumList) {
		this.ranNumList = ranNumList;
	}
 
	public String getTransferSalary() {
		return transferSalary;
	}

	public void setTransferSalary(String transferSalary) {
		this.transferSalary = transferSalary;
	}

	public List<HrsSalaryScaleDgrs> getRanNumListOut() {
		return ranNumListOut;
	}

	public void setRanNumListOut(List<HrsSalaryScaleDgrs> ranNumListOut) {
		this.ranNumListOut = ranNumListOut;
	}

}
