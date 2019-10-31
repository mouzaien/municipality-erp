package com.bkeryah.hr.managedBeans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.bkeryah.hr.entities.HrsGovJobSeries;
import com.bkeryah.hr.entities.HrsGovJobType;
import com.bkeryah.service.IDataAccessService;

@ViewScoped
@ManagedBean
public class PostsClassBean {

	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private TreeNode root;
	private List<HrsGovJobSeries> hrsJobs;
	private TreeNode selectedNode;
	private TreeNode OldSelectedNode;
	private HrsGovJobSeries hrsGovJobSeriesElem;
	private HrsGovJobSeries hrsGovJobSpec;
	private HrsGovJobSeries hrsGovJobCatSeries;
	private HrsGovJobSeries hrsGovJobSeries;
	private Integer jobOld;
	private Integer jobSpecId;
	private Integer jobCatId;
	private TreeNode hrsGovJobGNode;
	private TreeNode hrsGovJobSpecNode;
	private TreeNode hrsGovJobCatSeriesNode;
	private TreeNode hrsGovJobSeriesNode;
	private List<HrsGovJobType> hrsgovjobTypes;
	private List<FloorModel> hrsCommpactFloors;
	private boolean nodeVisible;
	private TreeNode parentNode;
	private String serieId;
	private String title;
	private Integer jobTypeId;

	public String getSerieId() {
		return serieId;
	}

	public void setSerieId(String serieId) {
		this.serieId = serieId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getJobTypeId() {
		return jobTypeId;
	}

	public void setJobTypeId(Integer jobTypeId) {
		this.jobTypeId = jobTypeId;
	}

	public boolean isNodeVisible() {
		return nodeVisible;
	}

	public void setNodeVisible(boolean nodeVisible) {
		this.nodeVisible = nodeVisible;
	}

	public List<FloorModel> getHrsCommpactFloors() {
		return hrsCommpactFloors;
	}

	public void setHrsCommpactFloors(List<FloorModel> hrsCommpactFloors) {
		this.hrsCommpactFloors = hrsCommpactFloors;
	}

	public List<HrsGovJobType> getHrsgovjobTypes() {
		return hrsgovjobTypes;
	}

	public void setHrsgovjobTypes(List<HrsGovJobType> hrsgovjobTypes) {
		this.hrsgovjobTypes = hrsgovjobTypes;
	}

	@PostConstruct
	private void init() {
		hrsJobs = dataAccessService.getHrsJobs();
		hrsgovjobTypes = dataAccessService.getAllHrsGovJobTypes();
	}

	public void loadData() {
		// job g
		root = new DefaultTreeNode("Root", null);
		jobOld = hrsJobs.get(0).getJobG();
		Integer jobsIndex = 0;
		initNodes(jobsIndex);
		jobsIndex++;
		while (jobsIndex < hrsJobs.size()) {

			if (hrsJobs.get(jobsIndex).getJobG().equals(jobOld)) {

				if (hrsJobs.get(jobsIndex).getJobSpec().equals(jobSpecId)) {

					if (hrsJobs.get(jobsIndex).getJobCatseries().equals(jobCatId)) {
						// series
						GetNewJobsSeriesNode(jobsIndex);
					} else {
						// cat
						addCatNode(jobsIndex);
					}
					jobSpecId = hrsGovJobSpec.getJobSpec();

				} else {
					// spec
					addSpecNode(jobsIndex);
				}
			} else {
				initNodes(jobsIndex);
			}
			jobsIndex++;
		}

	}

	public void save(ActionEvent e) {
		HrsGovJobSeries selectedJob = (HrsGovJobSeries) selectedNode.getData();
		Integer saveNode = dataAccessService.save(selectedJob);

	}

	public void addJob(ActionEvent e) {
		HrsGovJobSeries jovJobSeries = new HrsGovJobSeries((HrsGovJobSeries) selectedNode.getData());
		jovJobSeries.setSerieId(serieId);
		jovJobSeries.setTitle(title);
		jovJobSeries.setType(jobTypeId);
		DefaultTreeNode newHrsGovJobSeriesNode = new DefaultTreeNode(jovJobSeries, null);
		selectedNode.getChildren().add(newHrsGovJobSeriesNode);
		Integer saveNode = dataAccessService.save(jovJobSeries);
		serieId = null;
		title = null;
		jobTypeId = null;
	}

	private void addCatNode(Integer jobsIndex) {
		GetNewJobsCatSeriesNode(jobsIndex);
		// series
		GetNewJobsSeriesNode(jobsIndex);
	}

	private void addSpecNode(Integer jobsIndex) {
		GetNewJobsSpecNode(jobsIndex);
		addCatNode(jobsIndex);
	}

	private void initNodes(Integer jobsIndex) {
		addJobGNode(jobsIndex);
	}

	private void addJobGNode(Integer jobsIndex) {
		GetNewJobsGNode(jobsIndex, root);
		addSpecNode(jobsIndex);
	}

	private void GetNewJobsGNode(Integer jobsIndex, TreeNode parent) {
		jobOld = hrsJobs.get(jobsIndex).getJobG();
		hrsGovJobSeriesElem = new HrsGovJobSeries(hrsJobs.get(jobsIndex));
		hrsGovJobSeriesElem.setTitle(hrsGovJobSeriesElem.getJobGroup().getTitle());
		hrsGovJobGNode = new DefaultTreeNode(hrsGovJobSeriesElem, root);
	}

	private void GetNewJobsSpecNode(Integer jobsIndex) {
		hrsGovJobSpec = new HrsGovJobSeries(hrsJobs.get(jobsIndex));
		hrsGovJobSpec.setTitle(hrsGovJobSpec.getJobSpecific().getTitle());
		jobSpecId = hrsGovJobSpec.getJobSpec();
		hrsGovJobSpecNode = new DefaultTreeNode(hrsGovJobSpec, hrsGovJobGNode);
		hrsGovJobGNode.getChildren().add(hrsGovJobSpecNode);
	}

	private void GetNewJobsCatSeriesNode(Integer jobsIndex) {
		hrsGovJobCatSeries = new HrsGovJobSeries(hrsJobs.get(jobsIndex));
		hrsGovJobCatSeries.setTitle(hrsGovJobCatSeries.getJobCategories().getTitle());
		hrsGovJobCatSeries.setType(2);
		jobCatId = hrsGovJobCatSeries.getJobCatseries();
		hrsGovJobCatSeriesNode = new DefaultTreeNode(hrsGovJobCatSeries, hrsGovJobSpecNode);
		hrsGovJobSpecNode.getChildren().add(hrsGovJobCatSeriesNode);
	}

	private void GetNewJobsSeriesNode(Integer jobsIndex) {
		hrsGovJobSeries = new HrsGovJobSeries(hrsJobs.get(jobsIndex));
		hrsGovJobSeries.setType(1);
		hrsGovJobSeriesNode = new DefaultTreeNode(hrsGovJobSeries, hrsGovJobCatSeriesNode);
		hrsGovJobSpecNode.getChildren().add(hrsGovJobCatSeriesNode);
	}

	public void onNodeSelect(NodeSelectEvent event) {
		selectedNode = event.getTreeNode();


		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", event.getTreeNode().toString());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

}
