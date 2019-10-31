/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkeryah.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.CheckboxTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.UploadedFile;

import com.bkeryah.bean.AttachmentFileClass;
import com.bkeryah.hr.managedBeans.Employer;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

/**
 *
 * @author IbrahimDarwiesh
 */
@ManagedBean
@ViewScoped
public class GeneralizationBean implements Serializable {

	private List<Employer> employersList;
	private Map<String, List<Employer>> managersMap;
	private TreeNode root;
	private TreeNode[] selectedNodes;
	private com.bkeryah.hr.managedBeans.Employer president;
	private boolean disabled;
	private String directionId;
	private Map<String,String> directionsMap ;
	private String subject;
	private String details;
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ManagedProperty(value="#{dataAccessService}")
	private IDataAccessService dataAccessService;
    private List<Employer> selectedEmployersList;    
    private List<Employer> checkedEmployersList;
    private UploadedFile uploadedFile;
    private List<AttachmentFileClass> files;
    private List<UploadedFile> attachmentsList;
    
    
    public GeneralizationBean() {
    	
    }
    
    public void addEmployersAction(){
    	if((selectedNodes != null) && (selectedNodes.length != 0)){
			selectedEmployersList = new ArrayList<>();
			for(TreeNode nd : selectedNodes){
				selectedEmployersList.add((Employer) nd.getData());
//				removeElemetOfTreeNode(root, nd);
			}
		}
    }
    
    public boolean removeElemetOfTreeNode(TreeNode rootNode, TreeNode nodeToDelete) {
        if (rootNode.getChildren().remove(nodeToDelete)) {
            return true;
        } else {
            for (TreeNode childNode : rootNode.getChildren()) {
                if (childNode.getChildCount() > 0) {
                    return removeElemetOfTreeNode(childNode, nodeToDelete);
                }

            }
            return false;
        }
    }
    
    public void addAllEmployersAction(){
		selectedEmployersList = new ArrayList<>(employersList);
	}
    
    public void addHeadmasterEmployersAction(){
    	if(selectedEmployersList == null)
    		selectedEmployersList = new ArrayList<>();
    	List<String> headmastersList = new ArrayList<>();
    	if((selectedNodes != null) && (selectedNodes.length != 0)){
			selectedEmployersList = new ArrayList<>();
			for(TreeNode nd : selectedNodes){
				Employer empl = (Employer) nd.getData();
				if(empl.getLevel() == 2){
					headmastersList.add(""+empl.getId());
					selectedEmployersList.add(empl);
				}
			}
			for(int i=3;i<=managersMap.keySet().size();i++){
				for(Employer emp : managersMap.get(""+i)){
					if(headmastersList.contains(""+emp.getParentId())){
						selectedEmployersList.add(emp);
						headmastersList.add(""+emp.getId());
					}
					
				}
			}
		}
    }
    
    public void deleteEmployersAction(){
    	selectedEmployersList.removeAll(checkedEmployersList);
    }

    public String loadAllEmployers(){
    	if (!FacesContext.getCurrentInstance().isPostback()) {
    		employersList = dataAccessService.loadAllEmployers();	
    		populateEmployersMapByLevel();
    		root = createTree();
    		loadDirections();
    		
    	}
    	return null;
    	
    }

    private void loadDirections() {
		directionsMap = dataAccessService.loadDirections();
		
	}

//	private void populateEmployersMap() {
//		managersMap = new LinkedHashMap<>();
//		List<Employer> empList = null;
//		for(int i = 0; i<employersList.size(); i++){
//			Employer manager = employersList.get(i);
//	      	if(manager.getParentId() == 0){
//	      		president = manager;
//	      		continue;
//	      	}
//	      	
//	      	empList = new ArrayList<>();
//	      	for(int j = 0; j<employersList.size(); j++){
//	      		Employer emp = employersList.get(j);
//	      		if(emp.getParentId() == manager.getId())
//	      			empList.add(emp);
//	      	}
//	      	if((empList != null) && (!empList.isEmpty()))
//      			managersMap.put(""+manager.getId(), new EmployersOrganigram(manager, empList));
//	      }
//
//	}
	
	private void populateEmployersMapByLevel() {
		managersMap = new LinkedHashMap<>();
//		int cursor=0;
		List<Employer> empList = new ArrayList<>();
		int level = 1;
		for(int i = 0; i<employersList.size(); i++){	      	
      		Employer emp = employersList.get(i);
      		if(emp.getLevel() == level)
      			empList.add(emp);
      		else{
      			managersMap.put(""+level, empList);
      			empList = new ArrayList<>();
      			empList.add(emp);
      			level = emp.getLevel();
      		}	      	
		}
	}
	
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	public TreeNode createTree() {
//		TreeNode root = new CheckboxTreeNode(new Employer(0, "", 0, 0), null);
//      TreeNode presNode = new CheckboxTreeNode(president, root);
//      TreeNode principal = presNode;
//      int mgrId = president.getId();
//      List<String> keyList = new ArrayList(managersMap.keySet());
//		for(String managerId : managersMap.keySet()){
//			TreeNode trNode = new CheckboxTreeNode(managersMap.get(managerId).getManager(), principal);
//			for(Employer empl : managersMap.get(managerId).getEmployersList()){
//				TreeNode childNode = new CheckboxTreeNode(empl, trNode);
//				// check if employers are also managers
//				if(keyList.contains(""+empl.getId())){
//					
//				}
//			}
//			if(mgrId != managersMap.get(managerId).getManager().getParentId())
//				principal = trNode;
//		}
//       
//      
//       
//      return root;
//  }
	
	public TreeNode createTree() {
	//Employer employer = new Employer(0, "", 0, 0);
	Employer employer = new Employer();
	TreeNode root = new CheckboxTreeNode(employer, null);
      List<TreeNode> nodesList = new ArrayList<>();
		for(String level : managersMap.keySet()){
			for(Employer empl : managersMap.get(level)){
				TreeNode trNode = new CheckboxTreeNode(empl, (Integer.parseInt(level)==1)?root:getParentNode(empl.getParentId(), nodesList));
				nodesList.add(trNode);				
			}			
		}
     
       
      
       
      return root;
	}
	
	private TreeNode getParentNode(int parentId, List<TreeNode> nodesList){
		TreeNode tn = null;
		for(TreeNode nd : nodesList){
			Employer emp = (Employer) nd.getData();
			if(emp.getId() == parentId)
				return nd;
		}
		return tn;
	}
	
	public void sendGeneralization(){
		for(Employer emp : selectedEmployersList){
			this.dataAccessService.SendNewRecord(MsgEntry.GENERALIZATION+" : "+subject, 0, Utils.findCurrentUser().getUserId(), emp.getId(), details, Integer.parseInt(directionId), 0, null);
		}
		MsgEntry.addInfoMessage(MsgEntry.SUCCESS_SEND_GENERALIZATION);
	}	
	
	public void uploadFiles(FileUploadEvent fileUploadEvent) {  
            if(attachmentsList == null)
            	attachmentsList = new ArrayList<>();
            attachmentsList.add(fileUploadEvent.getFile());
            
    }

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public List<Employer> getEmployersList() {
		return employersList;
	}

	public void setEmployersList(List<Employer> employersList) {
		this.employersList = employersList;
	}

	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public TreeNode[] getSelectedNodes() {
		return selectedNodes;
	}

	public void setSelectedNodes(TreeNode[] selectedNodes) {
		this.selectedNodes = selectedNodes;
	}

	public Map<String, List<Employer>> getManagersMap() {
		return managersMap;
	}

	public void setManagersMap(Map<String, List<Employer>> managersMap) {
		this.managersMap = managersMap;
	}

	public Employer getPresident() {
		return president;
	}

	public void setPresident(Employer president) {
		this.president = president;
	}

	public List<Employer> getSelectedEmployersList() {
		return selectedEmployersList;
	}

	public void setSelectedEmployersList(List<Employer> selectedEmployersList) {
		this.selectedEmployersList = selectedEmployersList;
	}

	public List<Employer> getCheckedEmployersList() {
		return checkedEmployersList;
	}

	public void setCheckedEmployersList(List<Employer> checkedEmployersList) {
		this.checkedEmployersList = checkedEmployersList;
	}

	public boolean isDisabled() {
		return ((selectedEmployersList == null) || (selectedEmployersList.isEmpty()));
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public String getDirectionId() {
		return directionId;
	}

	public void setDirectionId(String directionId) {
		this.directionId = directionId;
	}

	public Map<String,String> getDirectionsMap() {
		return directionsMap;
	}

	public void setDirectionsMap(Map<String,String> directionsMap) {
		this.directionsMap = directionsMap;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public List<AttachmentFileClass> getFiles() {
		return this.dataAccessService.getAllFilesbyUser(Utils.findCurrentUser().getUserId());
	}

	public void setFiles(List<AttachmentFileClass> files) {
		this.files = files;
	}

	public List<UploadedFile> getAttachmentsList() {
		return attachmentsList;
	}

	public void setAttachmentsList(List<UploadedFile> attachmentsList) {
		this.attachmentsList = attachmentsList;
	}
	
}
