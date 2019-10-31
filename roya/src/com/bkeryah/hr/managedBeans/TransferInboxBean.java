package com.bkeryah.hr.managedBeans;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.model.CheckboxTreeNode;
import org.primefaces.model.TreeNode;

import com.bkeryah.entities.ArcUsers;
import com.bkeryah.service.IDataAccessService;

import utilities.Utils;

@ManagedBean (name = "transferInboxBean" )
@ViewScoped
public class TransferInboxBean  {


//	private WrkApplication wrkApplication = new WrkApplication();
//	private List<WrkPurpose> wrkPurposes;
//	private ArcRecords arcRecord = new ArcRecords();
//	private byte[] applicationUserCommentBytes;
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private List<ArcUsers> allEmployeesTree = new ArrayList<ArcUsers>();
	private TreeNode[] selectedNodes;
	private List<Integer> selectedStructIdList = new ArrayList<>();
	private Map<ArcUsers, List<ArcUsers>> empsMap;
	private TreeNode root;
//	private List<ArcUsers> allEmployeesInDept = new ArrayList<ArcUsers>();
//	private List<ArcUsers> allEmployees = new ArrayList<ArcUsers>();
//	private List<WrkRefrentionalSetting> allManagers = new ArrayList<WrkRefrentionalSetting>();
//	// private List<Integer> copyIds = new ArrayList<Integer>();
//	private List<String> copyIds = new ArrayList<String>();
//	private List<UploadedFile> files = new ArrayList<UploadedFile>();
//	private List<ArcAttach> attachs = new ArrayList<ArcAttach>();
//	private Map<Integer, String> employeeMap = new HashMap<Integer, String>();
//	private Map<String, Integer> employeeMap2 = new HashMap<String, Integer>();
//	private Map<String, Integer> employeeMapInDept = new HashMap<String, Integer>();
//	private String applicationPurpose;
//	private int sendOriginalMemoToId;
//	private boolean manager;
//	private int wrkReciever;
//	private boolean generalManager;
//	private String copyToManagerId;
//	private Integer arcRecId;
//	private boolean sendTo;
//	private Integer panelFlag;
//	private boolean panelShowFlag;
//	// private boolean allEmpsPanelFlag;
//	// private boolean departmentEmpsFlag;
//	private boolean sendToUserNameFlag;
//	private String sendToUserName;
//	private String sendToUserLastName;
//	private boolean checkAllDeptEmpsFlag;
//	private boolean checkAllDeptEmpsFlagMngr;
//	private Map<String, Integer> genericMap = new HashMap<String, Integer>();
//	private List<String> WrkCopyEmpRecievers = new ArrayList<String>();
//	private List<String> WrkCopyMngRecievers = new ArrayList<String>();

//	public Map<String, String> getEmpsMapInDept() {
//		return empsMapInDept;
//	}
//
//	public Map<String, String> getMgrMap() {
//		return mgrMap;
//	}
//
//	public void setEmpsMapInDept(Map<String, String> empsMapInDept) {
//		this.empsMapInDept = empsMapInDept;
//	}
//
//	public void setMgrMap(Map<String, String> mgrMap) {
//		this.mgrMap = mgrMap;
//	}
//
//	private Map<String, Integer> allManagersMap = new HashMap<String, Integer>();
//	private List<EmployeeForDropDown> toCopyList = new ArrayList<>();
//	private boolean sendCopyToUserNameFlag;
//	private AttachmentModel selectedItem;
//	private Integer toIdCopy;
//	private Map<String, String> empsMapInDept = new LinkedHashMap<String, String>();
//	private Map<String, String> mgrMap = new LinkedHashMap<String, String>();
//
//	// private EmployeeForDropDown emp;
//
//	private ArcUsers currentUser;
//	private boolean managerFlag;

	 @PostConstruct
	public void init() {
		 allEmployeesTree = dataAccessService.employeersTree();
//		 populateTree();
//		 root = createTree();
//				 
}
		
//	 private void populateTree() {
//		 empsMap = new LinkedHashMap<>();
//			for (int i = 0; i < allEmployeesTree.size(); i++) {
//				ArcUsers arcuser = allEmployeesTree.get(i);
//				// System.out.println("****parent:
//				// "+section.getParentId()+section.getParent().getStructDirectory()+"***struct:**
//				// "+section.getStructId()+"***"+section.getStructDirectory());
//				if (empsMap.containsKey(arcuser.getParent())) {
//					empsMap.get(arcuser.getParent()).add(arcuser);
//				} else {
//					List<ArcUsers> arcUserList = new ArrayList<>();
//					arcUserList.add(arcuser);
//					empsMap.put(arcuser, arcUserList);
////					empsMap.put(arcuser.getParent(), arcUserList);
//				}
//			}
//		}
	 
//	 public TreeNode createTree() {
//			Map<ArcDocumentStruct, List<ArcDocumentStruct>> myUserMap = null;
//
//			if ((Utils.findCurrentUser().getWrkRoleId() == 1) || (Utils.findCurrentUser().getDeptId() == 1))
//				myUserMap = new LinkedHashMap<>(sectionsMap);
//			else if (Utils.findCurrentUser().getWrkRoleId() == 2) {
//				myUserMap = new LinkedHashMap<>();
//				myUserMap.put(getUserSection(Utils.findCurrentUser().getWrkSection().getDefaultStruct()),
//						sectionsMap.get(getUserSection(Utils.findCurrentUser().getWrkSection().getDefaultStruct())));
//			}
//
//			ArcDocumentStruct section = new ArcDocumentStruct();
//			TreeNode root = new CheckboxTreeNode(section, null);
//			List<TreeNode> nodesList = new ArrayList<>();
//			if (Utils.findCurrentUser().getWrkRoleId() == 2) {
//				for (ArcDocumentStruct parent : myUserMap.keySet()) {
//					TreeNode trNode = new CheckboxTreeNode(parent, root);
//					for (ArcDocumentStruct sec : myUserMap.get(parent)) {
//						TreeNode trChildNode = new CheckboxTreeNode(sec, trNode);
//					}
//				}
//			} else {
//				if (myUserMap != null)
//					for (ArcDocumentStruct parent : myUserMap.keySet()) {
//						for (ArcDocumentStruct sec : myUserMap.get(parent)) {
//							TreeNode trNode = new CheckboxTreeNode(sec,
//									(sec.getParentId() == null) ? root : getParentNode(sec.getParentId(), nodesList));
//							nodesList.add(trNode);
//						}
//					}
//			}
//			return root;
//		}
	 
//		setCurrentUser(Utils.findCurrentUser());
//		if (currentUser.getWrkRoleId() < 4)
//			managerFlag = true;
//
//		setWrkPurposes(dataAccessService.getAllPurposes());
//		setEmployeeMapInDept(dataAccessService.getEmployeeInDeptInfo());
//		// setAllEmployees(dataAccessService.getAllEmployees());
//		setEmployeeMap2(dataAccessService.getEmployeeInfo());
//		Iterator<Entry<String, Integer>> entries = employeeMap2.entrySet().iterator();
//		while (entries.hasNext()) {
//			Entry<String, Integer> entry = entries.next();
//			String key = entry.getKey();
//			Integer value = entry.getValue();
//
//			// ...
//		}
//		setAllManagers(dataAccessService.getAllManagersFromWFS());
//		int currentUserId = Utils.findCurrentUser().getUserId();
//		for (WrkRefrentionalSetting manager : allManagers) {
//			if (currentUserId == manager.getManagerId()) {
//				setManager(true);
//			}
//			allManagersMap.put(manager.getDeptName(), manager.getManagerId());
//		}
//		List<UserContactClass> listOfEmpsInDept = dataAccessService
//				.findAllEmployeesInDept(Utils.findCurrentUser().getUserId());
//
//		for (UserContactClass item : listOfEmpsInDept) {
//
//			empsMapInDept.put(item.getUserName(), String.valueOf(item.getUserId()));
//
//		}
//
//		List<UserContactClass> listOfManagers = dataAccessService.findAllManagers(Utils.findCurrentUser().getUserId());
//		// Map<String, String> specialEmpsMap = new HashMap<String, String>();
//		List<EmployeeForDropDown> specialEMpList = dataAccessService.getSpecialEmployeeList();
//		for (EmployeeForDropDown emp : specialEMpList) {
//			if (employeeMapInDept.containsValue(emp.getId()))
//
//			else {
//				empsMapInDept.put(emp.getName(), String.valueOf(emp.getId()));
//			}
//		}
//		for (UserContactClass item : listOfManagers) {
//			mgrMap.put(item.getUserName(), String.valueOf(item.getUserId()));
//		}
//
//		if (currentUserId == 250) // general Manager Id
//			setGeneralManager(true);

//	}
//	 public void onNodeSelect(NodeSelectEvent event) {
//			selectedStructIdList.add(((ArcDocumentStruct) event.getTreeNode().getData()).getStructId());
//			recordsList = null;
//		}
//
//		public void onNodeUnselect(NodeUnselectEvent event) {
//			selectedStructIdList.remove(new Integer(((ArcDocumentStruct) event.getTreeNode().getData()).getStructId()));
//			recordsList = null;
//		}
//	public List<ArcUsers> getAllEmployeesTree() {
//		return allEmployeesTree;
//	}
//
//	public void setAllEmployeesTree(List<ArcUsers> allEmployeesTree) {
//		this.allEmployeesTree = allEmployeesTree;
//	}
//
//	public IDataAccessService getDataAccessService() {
//		return dataAccessService;
//	}
//
//	public void setDataAccessService(IDataAccessService dataAccessService) {
//		this.dataAccessService = dataAccessService;
//	}
//
//	public TreeNode[] getSelectedNodes() {
//		return selectedNodes;
//	}
//
//	public void setSelectedNodes(TreeNode[] selectedNodes) {
//		this.selectedNodes = selectedNodes;
//	}
//
//	public List<Integer> getSelectedStructIdList() {
//		return selectedStructIdList;
//	}
//
//	public void setSelectedStructIdList(List<Integer> selectedStructIdList) {
//		this.selectedStructIdList = selectedStructIdList;
//	}
//
//	public TreeNode getRoot() {
//		return root;
//	}
//
//	public void setRoot(TreeNode root) {
//		this.root = root;
//	}
//
//	public Map<ArcUsers, List<ArcUsers>> getEmpsMap() {
//		return empsMap;
//	}
//
//	public void setEmpsMap(Map<ArcUsers, List<ArcUsers>> empsMap) {
//		this.empsMap = empsMap;
//	}

//	public void checkAllListnerForManagers() {
//		if (checkAllDeptEmpsFlagMngr == true) {
//
//			Iterator<Entry<String, String>> entries = mgrMap.entrySet().iterator();
//			while (entries.hasNext()) {
//				Entry<String, String> entry = entries.next();
//				String key = entry.getKey();
//				String value = entry.getValue();
//				WrkCopyMngRecievers.add(value);
//
//			}
//
//		} else {
//
//			WrkCopyMngRecievers.clear();
//
//		}
//	}
//
//	public void onChangeEventCheckBox() {
//
//
//	}
//
//	public void onChangeEventCheckBoxAllMngr() {
//
//
//
//	}
//
//	public void checkAllListner() {
//		if (checkAllDeptEmpsFlag == true) {
//
//			Iterator<Entry<String, String>> entries = empsMapInDept.entrySet().iterator();
//			while (entries.hasNext()) {
//				Entry<String, String> entry = entries.next();
//				String key = entry.getKey();
//				String value = entry.getValue();
//				WrkCopyEmpRecievers.add(value);
//
//			}
//
//		} else {
//
//			WrkCopyEmpRecievers.clear();
//
//		}
//	}
//
//	public void updateChoisePanel(AjaxBehaviorEvent event) {
//		switch (panelFlag) {
//		case 1:
//			genericMap = employeeMapInDept;
//			break;
//		case 2:
//			genericMap = allManagersMap;
//			break;
//		// case 3:
//		// genericMap = employeeMap2;
//		// break;
//		default:
//			genericMap = new HashMap<>();
//		}
//
//		setPanelShowFlag(true);
//		setSendToUserNameFlag(false);
//	}
//
//	public void saveSendTo(AjaxBehaviorEvent event) {
//
//		try {
//
//			ArcUsers x = dataAccessService.getUserNameById(wrkApplication.getToUserId());
//			setSendToUserName(x.getFirstName());
//			setSendToUserLastName(x.getLastName());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		setPanelShowFlag(false);
//		setSendToUserNameFlag(true);
//
//	}
//
//	public void saveSendCopyTo(AjaxBehaviorEvent event2) {
//		try {
//
//			ArcUsers x = dataAccessService.getUserNameById(wrkApplication.getToCopyId());
//
//			EmployeeForDropDown emp = new EmployeeForDropDown();
//			emp.setId(wrkApplication.getToCopyId());
//
//			emp.setName(x.getFirstName());
//			toCopyList.add(emp);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		setPanelShowFlag(false);
//		setSendCopyToUserNameFlag(true);
//	}
//
//	public void clearMngRadioList(ValueChangeEvent valueChangeEvent) {
//
//		wrkApplication.setToUserId(wrkReciever);
//	}
//
//	public void clearEmpRadioList(ValueChangeEvent valueChangeEvent) {
//
//		wrkApplication.setToUserId(wrkReciever);
//
//	}
//
//	public int getWrkReciever() {
//		return wrkReciever;
//	}
//
//	public void setWrkReciever(int wrkReciever) {
//		this.wrkReciever = wrkReciever;
//	}
//
//	public void cancel() {
//
//		setPanelShowFlag(false);
//		setSendToUserNameFlag(true);
//
//	}
//
//	public void updateCopyList(EmployeeForDropDown emp) {
//
//		toCopyList.remove(emp);
//
//	}
	
//	public String saveMemoupdate() {
//
//
//		if (wrkApplication.getToUserId() != 0 && wrkApplication.getToUserId() != null) {
//			setSendTo(false);
//			attachs = Utils.SaveAttachementsToFtpServer(attachList);// SaveAttachementsInServer(attachList);
//			wrkApplication.setApplicationPurpose(
//					(applicationPurpose != null) ? Integer.parseInt(applicationPurpose.trim()) : null);
//			try {
//
//				List<String> copyListInteger = new ArrayList<String>();
//
//				copyListInteger.addAll(WrkCopyEmpRecievers);
//				copyListInteger.addAll(WrkCopyMngRecievers);
//
//
//				dataAccessService.saveMemo(wrkApplication, arcRecord, attachs, copyListInteger);
//				logger.info("save success");
//			} catch (Exception e) {
//				logger.error("saveMemo" + arcRecord.getId() + "   " + e.toString());
//				e.printStackTrace();
//				return null;
//			} finally {
//				wrkApplication = new WrkApplication();
//				arcRecord = new ArcRecords();
//				applicationPurpose = "";
//				copyIds = new ArrayList<>();
//			}
//
//			return "mails";
//
//		} else {
//
//			setSendTo(true);
//			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
//					"اختر جهة ارسال المعاملة ", " اختر جهة ارسال المعاملة"));
//			return null;
//
//		}
//	}
//	public String saveMemo() {
//
//
//		if (wrkApplication.getToUserId() != 0 && wrkApplication.getToUserId() != null) {
//			setSendTo(false);
//			attachs = Utils.SaveAttachementsToFtpServer(attachList);// SaveAttachementsInServer(attachList);
//			wrkApplication.setApplicationPurpose(
//					(applicationPurpose != null) ? Integer.parseInt(applicationPurpose.trim()) : null);
//			try {
//
//				List<String> copyListInteger = new ArrayList<String>();
//
//				copyListInteger.addAll(WrkCopyEmpRecievers);
//				copyListInteger.addAll(WrkCopyMngRecievers);
//
//
//				dataAccessService.saveMemo(wrkApplication, arcRecord, attachs, copyListInteger);
//				logger.info("save success");
//			} catch (Exception e) {
//				logger.error("saveMemo" + arcRecord.getId() + "   " + e.toString());
//				e.printStackTrace();
//				return null;
//			} finally {
//				wrkApplication = new WrkApplication();
//				arcRecord = new ArcRecords();
//				applicationPurpose = "";
//				copyIds = new ArrayList<>();
//			}
//
//			return "mails";
//
//		} else {
//
//			setSendTo(true);
//			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
//					"اختر جهة ارسال المعاملة ", " اختر جهة ارسال المعاملة"));
//			return null;
//
//		}
//	}

	/**
	 * 
	 */
//	public List<ArcAttach> SaveAttachementsInServer(List<AttachmentModel> attachList) {
//		List<ArcAttach> myAttachs = new ArrayList<ArcAttach>();
//		for (AttachmentModel att : attachList) {
//			ArcAttach attach = new ArcAttach();
//
//			String name = Utils.generateRandomName() + "." + att.getAttachExt();
//			attach.setAttachName(name);
//			try {
//
//				Utils.saveAttachments(att.getAttachStream(), name);
//
//				attach.setAttachOwner(Utils.findCurrentUser().getUserId());
//				attach.setAttachDate(new Date());
//
//				attach.setAttachSize((double) att.getAttachSize());
//				attach.setAttachType(1);
//
//				attach.setAttachCategory("FILE");
//				myAttachs.add(attach);
//			} catch (Exception e) {
//
//				e.printStackTrace();
//				myAttachs.clear();
//			}
//		}
//		return myAttachs;
//	}
//
//	public void NewRecordupload(FileUploadEvent event) {
//
//		try {
//			AttachmentModel attach = new AttachmentModel();
//			attach.setAttachRealName(event.getFile().getFileName());
//			attach.setAttachSize(event.getFile().getSize());
//			attach.setAttachStream(event.getFile().getInputstream());
//			attach.setAttachExt(FilenameUtils.getExtension(event.getFile().getFileName()));
//			attach.setRealName(Utils.generateRandomName()+"."+attach.getAttachExt());
//			attachList.add(attach);
//
//		} catch (Exception e) {
//
//		}
//	}
//
//	public void openFile() throws IOException {
//		OutputStream outputStream = null;
//		File file = null;
//		try {
//			file = new File("c:\\download.png");
//			outputStream = new FileOutputStream(file);
//
//			int read = 0;
//			byte[] bytes = new byte[1024];
//
//			while ((read = selectedItem.getAttachStream().read(bytes)) != -1) {
//				outputStream.write(bytes, 0, read);
//			}
//
//
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			if (selectedItem.getAttachStream() != null) {
//				try {
//					selectedItem.getAttachStream().close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//			if (outputStream != null) {
//				try {
//					// outputStream.flush();
//					outputStream.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//
//			}
//		}
//
//		// File file = new File(selectedItem.getAttachStream().);
//
//		// first check if Desktop is supported by Platform or not
//		if (!Desktop.isDesktopSupported()) {
//
//
//		}
//
//		Desktop desktop = Desktop.getDesktop();
//		if (file.exists())
//			desktop.open(file);
//
//		// let's try to open PDF file
//		file = new File("/Users/pankaj/java.pdf");
//		if (file.exists())
//			desktop.open(file);
//	}
//
//	public List<String> getWrkCopyEmpRecievers() {
//		return WrkCopyEmpRecievers;
//	}
//
//	public List<String> getWrkCopyMngRecievers() {
//		return WrkCopyMngRecievers;
//	}
//
//	public void setWrkCopyEmpRecievers(List<String> wrkCopyEmpRecievers) {
//		WrkCopyEmpRecievers = wrkCopyEmpRecievers;
//	}
//
//	public void setWrkCopyMngRecievers(List<String> wrkCopyMngRecievers) {
//		WrkCopyMngRecievers = wrkCopyMngRecievers;
//	}
//
//	public boolean isCheckAllDeptEmpsFlag() {
//		return checkAllDeptEmpsFlag;
//	}
//
//	public boolean isCheckAllDeptEmpsFlagMngr() {
//		return checkAllDeptEmpsFlagMngr;
//	}
//
//	public void setCheckAllDeptEmpsFlag(boolean checkAllDeptEmpsFlag) {
//		this.checkAllDeptEmpsFlag = checkAllDeptEmpsFlag;
//	}
//
//	public void setCheckAllDeptEmpsFlagMngr(boolean checkAllDeptEmpsFlagMngr) {
//		this.checkAllDeptEmpsFlagMngr = checkAllDeptEmpsFlagMngr;
//	}
//
//	public void loadData(int wrkApplicationId) {
//		wrkApplication = dataAccessService.getWrkApplicationRecordById(wrkApplicationId);
//		arcRecord = dataAccessService.getArchRecordById(wrkApplication.getArcRecordId());
//		attachs = dataAccessService.getAttachmentByArchRecordId(arcRecord.getId());
//
//	}
//
//	// update Listner for oringinal Memo Reciver Id
//	public void toUserIdChange() {
//		// wrkApplication.setToUserId(wrkApplication.getToUserId());
//
//	}
//
//	public WrkApplication getWrkApplication() {
//		return wrkApplication;
//	}
//
//	public void setWrkApplication(WrkApplication wrkApplication) {
//		this.wrkApplication = wrkApplication;
//	}
//
//	public byte[] getApplicationUserCommentBytes() {
//		return applicationUserCommentBytes;
//	}
//
//	public void setApplicationUserCommentBytes(byte[] applicationUserCommentBytes) {
//		this.applicationUserCommentBytes = applicationUserCommentBytes;
//	}
//
//	public ArcRecords getArcRecord() {
//		return arcRecord;
//	}
//
//	public void setArcRecord(ArcRecords arcRecord) {
//		this.arcRecord = arcRecord;
//	}
//
//	public List<WrkPurpose> getWrkPurposes() {
//		return wrkPurposes;
//	}
//
//	public void setWrkPurposes(List<WrkPurpose> wrkPurposes) {
//		this.wrkPurposes = wrkPurposes;
//	}
//
//	public List<ArcUsers> getAllEmployeesInDept() {
//		return allEmployeesInDept;
//	}
//
//	public void setAllEmployeesInDept(List<ArcUsers> allEmployeesInDept) {
//		this.allEmployeesInDept = allEmployeesInDept;
//	}
//
//	public List<ArcUsers> getAllEmployees() {
//		return allEmployees;
//	}
//
//	public void setAllEmployees(List<ArcUsers> allEmployees) {
//		this.allEmployees = allEmployees;
//	}
//
//	public String getApplicationPurpose() {
//		return applicationPurpose;
//	}
//
//	public void setApplicationPurpose(String applicationPurpose) {
//		this.applicationPurpose = applicationPurpose;
//	}
//
//	public boolean isSendCopyToUserNameFlag() {
//		return sendCopyToUserNameFlag;
//	}
//
//	// public EmployeeForDropDown getEmp() {
//	// return emp;
//	// }
//	//
//	// public void setEmp(EmployeeForDropDown emp) {
//	// this.emp = emp;
//	// }
//
//	public boolean isManagerFlag() {
//		return managerFlag;
//	}
//
//	public void setManagerFlag(boolean managerFlag) {
//		this.managerFlag = managerFlag;
//	}
//
//	public void setSendCopyToUserNameFlag(boolean sendCopyToUserNameFlag) {
//		this.sendCopyToUserNameFlag = sendCopyToUserNameFlag;
//	}
//
//	public Map<String, Integer> getEmployeeMap2() {
//		return employeeMap2;
//	}
//
//	public boolean isSendToUserNameFlag() {
//		return sendToUserNameFlag;
//	}
//
//	public List<EmployeeForDropDown> getToCopyList() {
//		return toCopyList;
//	}
//
//	public void setToCopyList(List<EmployeeForDropDown> toCopyList) {
//		this.toCopyList = toCopyList;
//	}
//
//	public void setSendToUserNameFlag(boolean sendToUserNameFlag) {
//		this.sendToUserNameFlag = sendToUserNameFlag;
//	}
//
//	public Integer getToIdCopy() {
//		return toIdCopy;
//	}
//
//	public void setToIdCopy(Integer toIdCopy) {
//		this.toIdCopy = toIdCopy;
//	}
//
//	public void setEmployeeMap2(Map<String, Integer> employeeMap2) {
//		this.employeeMap2 = employeeMap2;
//	}
//
//	public String getSendToUserName() {
//		return sendToUserName;
//	}
//
//	public void setSendToUserName(String sendToUserName) {
//		this.sendToUserName = sendToUserName;
//	}
//
//	public boolean isPanelShowFlag() {
//		return panelShowFlag;
//	}
//
//	public void setPanelShowFlag(boolean panelShowFlag) {
//		this.panelShowFlag = panelShowFlag;
//	}
//
//	public List<AttachmentModel> getAttachList() {
//		return attachList;
//	}
//
//	public Map<String, Integer> getAllManagersMap() {
//		return allManagersMap;
//	}
//
//	public void setAllManagersMap(Map<String, Integer> allManagersMap) {
//		this.allManagersMap = allManagersMap;
//	}
//
//	public Map<String, Integer> getGenericMap() {
//		return genericMap;
//	}
//
//	public void setGenericMap(Map<String, Integer> genericMap) {
//		this.genericMap = genericMap;
//	}
//
//	public void setAttachList(List<AttachmentModel> attachList) {
//		this.attachList = attachList;
//	}
//
//	public int getSendOriginalMemoToId() {
//		return sendOriginalMemoToId;
//	}
//
//	// public boolean isAllEmpsPanelFlag() {
//	// return allEmpsPanelFlag;
//	// }
//	//
//	// public void setAllEmpsPanelFlag(boolean allEmpsPanelFlag) {
//	// this.allEmpsPanelFlag = allEmpsPanelFlag;
//	// }
//	//
//	// public boolean isDepartmentEmpsFlag() {
//	// return departmentEmpsFlag;
//	// }
//	//
//	// public void setDepartmentEmpsFlag(boolean departmentEmpsFlag) {
//	// this.departmentEmpsFlag = departmentEmpsFlag;
//	// }
//	//
//	// public boolean isDepartmentManagersFlag() {
//	// return departmentManagersFlag;
//	// }
//
//	// public void setDepartmentManagersFlag(boolean departmentManagersFlag) {
//	// this.departmentManagersFlag = departmentManagersFlag;
//	// }
//
//	public void setSendOriginalMemoToId(int sendOriginalMemoToId) {
//		this.sendOriginalMemoToId = sendOriginalMemoToId;
//	}
//
//	public List<WrkRefrentionalSetting> getAllManagers() {
//		return allManagers;
//	}
//
//	public void setAllManagers(List<WrkRefrentionalSetting> allManagers) {
//		this.allManagers = allManagers;
//	}
//
//	public void setManager(boolean manager) {
//		this.manager = manager;
//	}
//
//	public boolean isManager() {
//		return manager;
//	}
//
//	public void setCopyToManagerId(String copyToManagerId) {
//		this.copyToManagerId = copyToManagerId;
//	}
//
//	public String getCopyToManagerId() {
//		return copyToManagerId;
//	}
//
//	public boolean isGeneralManager() {
//		return generalManager;
//	}
//
//	public void setGeneralManager(boolean generalManager) {
//		this.generalManager = generalManager;
//	}
//
//	public List<ArcAttach> getAttachs() {
//		return attachs;
//	}
//
//	public void setAttachs(List<ArcAttach> attachs) {
//		this.attachs = attachs;
//	}
//
//	public boolean isSendTo() {
//		return sendTo;
//	}
//
//	public void setSendTo(boolean sendTo) {
//		this.sendTo = sendTo;
//	}
//
//	public Integer getArcRecId() {
//		return arcRecId;
//	}
//
//	public void setArcRecId(Integer arcRecId) {
//		this.arcRecId = arcRecId;
//	}
//
//	public Map<Integer, String> getEmployeeMap() {
//		return employeeMap;
//	}
//
//	public void setEmployeeMap(Map<Integer, String> employeeMap) {
//		this.employeeMap = employeeMap;
//	}
//
//	public Map<String, Integer> getEmployeeMapInDept() {
//		return employeeMapInDept;
//	}
//
//	public void setEmployeeMapInDept(Map<String, Integer> employeeMapInDept) {
//		this.employeeMapInDept = employeeMapInDept;
//	}
//
//	public IDataAccessService getDataAccessService() {
//		return dataAccessService;
//	}
//
//	public void setDataAccessService(IDataAccessService dataAccessService) {
//		this.dataAccessService = dataAccessService;
//	}
//
//	public Integer getPanelFlag() {
//		return panelFlag;
//	}
//
//	public void setPanelFlag(Integer panelFlag) {
//		this.panelFlag = panelFlag;
//	}
//
//	public String getSendToUserLastName() {
//		return sendToUserLastName;
//	}
//
//	public ArcUsers getCurrentUser() {
//		return currentUser;
//	}
//
//	public void setCurrentUser(ArcUsers currentUser) {
//		this.currentUser = currentUser;
//	}
//
//	public void setSendToUserLastName(String sendToUserLastName) {
//		this.sendToUserLastName = sendToUserLastName;
//	}
//
//	public AttachmentModel getSelectedItem() {
//		return selectedItem;
//	}
//
//	public void setSelectedItem(AttachmentModel selectedItem) {
//		this.selectedItem = selectedItem;
//		if (attachList != null) {
//			attachList.remove(selectedItem);
//		}
//	}
}
