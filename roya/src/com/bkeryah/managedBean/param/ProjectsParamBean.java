package com.bkeryah.managedBean.param;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.bkeryah.entities.Project;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class ProjectsParamBean {
	private static Logger logger = Logger.getLogger(ProjectsParamBean.class);
	private boolean addMode;
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private List<Project> projectList;
	private List<Project> filteredProjects;
	private Project project = new Project();
	private boolean isNewProject;

	@PostConstruct
	public void init() {
		projectList = dataAccessService.getAllProjects();
	}

	// ========================== CRUD Operations =========================
	public void addNewProject() {
		project = new Project();
		addMode = true;
	}

	public void loadProjects(Project ProjectItem) {
		project = (Project) dataAccessService.findEntityById(Project.class, ProjectItem.getId());
		logger.info("Projectname is?= " + project);
	}

	public void loadSelectedProject() {
		addMode = false;
	}

	public void saveProject() {
		try {
			if (project == null && project.getName().isEmpty() || project.getNumber().isEmpty()) {
				MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));

			} else {
				dataAccessService.saveObject(project);
				projectList.add(project);
				project = new Project();
				logger.info("add Project: id: " + project.getId());
				System.out.println(
						"Project Added With Id: " + project.getId() + " Is Fetched Successfully From Database");
				MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			}

		} catch (Exception e) {

		}
	}

	public void updateProject() {
		try {
			if (project != null) {
				dataAccessService.updateObject(project);
				project = new Project();
				logger.info("Update Project: id: " + project.getId());
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
		ProjectsParamBean.logger = logger;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public List<Project> getProjectList() {
		return projectList;
	}

	public void setProjectList(List<Project> projectList) {
		this.projectList = projectList;
	}

	public List<Project> getFilteredProjects() {
		return filteredProjects;
	}

	public void setFilteredProjects(List<Project> filteredProjects) {
		this.filteredProjects = filteredProjects;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public boolean isNewProject() {
		return isNewProject;
	}

	public void setNewProject(boolean isNewProject) {
		this.isNewProject = isNewProject;
	}

	public boolean isAddMode() {
		return addMode;
	}

	public void setAddMode(boolean addMode) {
		this.addMode = addMode;
	}

}
