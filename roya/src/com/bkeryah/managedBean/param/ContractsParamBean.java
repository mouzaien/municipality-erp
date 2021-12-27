package com.bkeryah.managedBean.param;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.logging.log4j.*;

import com.bkeryah.entities.Project;
import com.bkeryah.entities.ProjectContract;
import com.bkeryah.entities.WrkSection;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class ContractsParamBean {
	String TAG = this.getClass().getSimpleName();

	private static Logger logger = LogManager.getLogger(ContractsParamBean.class);

	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private List<ProjectContract> contractsList;
	private List<ProjectContract> filteredContracts;
	private ProjectContract projectContract = new ProjectContract();
	private List<Project> projectsList;

	private boolean isNewContract;

	@PostConstruct
	public void init() {
		contractsList = dataAccessService.getallProjectContract();
	}

	// ========================= My Methods =========================
	// ========================== CRUD Operations =========================
	public void addNewContract() {
		projectContract = new ProjectContract();
		projectsList = this.dataAccessService.getallProjects();
//		contractsList = this.dataAccessService.getallProjectContract();

		Utils.openDialog("mydialog");
		System.out.println(" sNewProjectContract = " + isNewContract + " ");

	}

	public void loadContract(ProjectContract ProjectContractItem) {
		projectContract = (ProjectContract) dataAccessService.findEntityById(ProjectContract.class,
				ProjectContractItem.getId());

		logger.info("ProjectContractname is?= " + projectContract);
	}

	public void loadSelectedProjectContract() {
		Utils.openDialog("mydialog");

//		jobsList = this.dataAccessService.loadJobsBySectionId(ProjectContract.getWrkSectionId());
		projectsList = this.dataAccessService.getallProjects();

//		RequestContext currentInstance = RequestContext.getCurrentInstance();
//		currentInstance.execute("PF('mydialog').show();");

		System.out.println(" sNewProjectContract = " + isNewContract + " ");

	}

	public void saveProjectContract() {
		try {
//			ProjectContract = new ProjectContract();
			if (projectContract == null && projectContract.getOwner().isEmpty()
					|| projectContract.getAssignDate().isEmpty()) { // TODO add more ?
				MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));

			} else {
				dataAccessService.saveObject(projectContract);
				contractsList.add(projectContract);
				projectContract = new ProjectContract();

				logger.info("add ProjectContract: Date: " + projectContract.getAssignDate());
				System.out.println("ProjectContract Added With Id: " + projectContract.getId()
						+ " Is Fetched Successfully From Database");
				MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			}

		} catch (Exception e) {

		}
	}

	public void updateProjectContract() {
		try {
			if (projectContract != null) {
				dataAccessService.updateObject(projectContract);
				projectContract = new ProjectContract();
				logger.info("Update Project: id: " + projectContract.getId());
			}
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
		}
	}

	// =========== Getters and Setters ============= \\
	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		ContractsParamBean.logger = logger;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public List<ProjectContract> getContractsList() {
		return contractsList;
	}

	public void setContractsList(List<ProjectContract> contractsList) {
		this.contractsList = contractsList;
	}

	public List<ProjectContract> getFilteredContracts() {
		return filteredContracts;
	}

	public void setFilteredContracts(List<ProjectContract> filteredContracts) {
		this.filteredContracts = filteredContracts;
	}

	public ProjectContract getProjectContract() {
		return projectContract;
	}

	public void setProjectContract(ProjectContract projectContract) {
		this.projectContract = projectContract;
	}

	public List<Project> getProjectsList() {
		return projectsList;
	}

	public void setProjectsList(List<Project> projectsList) {
		this.projectsList = projectsList;
	}

	public boolean isNewContract() {
		return isNewContract;
	}

	public void setNewContract(boolean isNewContract) {
		this.isNewContract = isNewContract;
	}

}
