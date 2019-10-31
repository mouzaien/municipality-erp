package com.bkeryah.managedBean.setting;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import com.bkeryah.entities.ArcUsers;
import com.bkeryah.entities.SysProperties;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;

@ManagedBean
@ViewScoped
public class SysPropertiesBean {
	

	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private List<SysProperties> sysPropertiesList;
	private List<SysProperties> filteredSysPropertiesList;
	private List<String> listArcUsers=new ArrayList<String>();;
	private List<ArcUsers> arcusersList = new ArrayList<ArcUsers>();
	private SysProperties sysProp =new SysProperties();
	
	
	
	

	public String refreshPage()
	{
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("PF('vtWidget').clearFilters()");
		return "";
		
	}
	

	@PostConstruct
	public void init() {
		
		    
	}
	public void getAllSysProperties(){
       sysPropertiesList = dataAccessService.loadAllSysProperties();
		
//		arcusersList =dataAccessService.getAllEmployees();
//		 
//		 for (ArcUsers emp : arcusersList) {
//			listArcUsers.add(emp.getEmployeeName());
//		}
		refreshPage();
	}
	public void onRowEdit(RowEditEvent event) {
		String  propValue =  ((SysProperties)event.getObject()).getValue();
		Integer id=((SysProperties)event.getObject()).getId();
		sysProp =(SysProperties)dataAccessService.findEntityById(SysProperties.class, id);
		if( !propValue.trim().equals("") )
        {
        	
        		try {
					sysProp.setValue(propValue);
        			dataAccessService.updateObject(sysProp);
					MsgEntry.addAcceptFlashInfoMessage("تم تععديل المعرف الوظيفي");
				} catch (Exception e) {
					MsgEntry.addErrorMessage("خطا فى تنفيذ العملية");
					e.printStackTrace();
				}
        		
        	
        }
        else
        {
        	
        	
        	MsgEntry.addErrorMessage("يجب إدخال المعرف الوظيفي");
        	
        	
        }
        	
         
    }
     
    public void onRowCancel(RowEditEvent event) {
    	MsgEntry.addAcceptFlashInfoMessage("تم إلغاء العملية");
      
    }
     
    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
         
        if(newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
	
	public List<SysProperties> getSysPropertiesList() {
		return sysPropertiesList;
	}

	public void setSysPropertiesList(List<SysProperties> sysPropertiesList) {
		this.sysPropertiesList = sysPropertiesList;
	}

	public List<SysProperties> getFilteredSysPropertiesList() {
		return filteredSysPropertiesList;
	}

	public void setFilteredSysPropertiesList(List<SysProperties> filteredSysPropertiesList) {
		this.filteredSysPropertiesList = filteredSysPropertiesList;
	}
	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}
	public List<ArcUsers> getArcusersList() {
		return arcusersList;
	}


	public void setArcusersList(List<ArcUsers> arcusersList) {
		this.arcusersList = arcusersList;
	}


	public List<String> getListArcUsers() {
		return listArcUsers;
	}


	public void setListArcUsers(List<String> listArcUsers) {
		this.listArcUsers = listArcUsers;
	}

	public SysProperties getSysProp() {
		return sysProp;
	}


	public void setSysProp(SysProperties sysProp) {
		this.sysProp = sysProp;
	}

}
