package com.bkeryah.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import com.bkeryah.dao.IStockServiceDao;
import com.bkeryah.entities.ArcUsers;
import com.bkeryah.entities.Article;
import com.bkeryah.entities.InventoryMaster;
import com.bkeryah.entities.InventoryRecord;
import com.bkeryah.entities.WhsWarehouses;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class InventoryMasterBean {
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	@ManagedProperty(value = "#{stockServiceDao}")
	private IStockServiceDao stockServiceDao;
	private InventoryMaster inventoryMaster;
	private InventoryRecord inventoryRecord = new InventoryRecord();
	private List<InventoryRecord> inventoryRecordList = new ArrayList<>();
	private List<InventoryModel> inventoryModelFiltredList = new ArrayList<>();
	private List<InventoryModel> inventoryModelList;
	private InventoryModel inventoryModel;
	private Integer StrNo = 10;
	private ArcUsers currentUser;
	private List<Article> articlesList = new ArrayList<>();
	private Article article = new Article();
	private String item;
	private List<WhsWarehouses> allWareHouses = new ArrayList<WhsWarehouses>();
	private Integer articleId;
	private Integer itemId;
	private Integer inventoryRecordId;
	private boolean stockIsBlocked;
	private boolean inventoryIsBlocked;
	private Integer inventoryId;
	private List<InventoryMaster> inventoriesList = new ArrayList<InventoryMaster>();

	public String refreshPage() {
		
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("PF('vtWidget').clearFilters()");
		return "";

	}

	public InventoryModel getInventoryModel() {
		return inventoryModel;
	}

	public void setInventoryModel(InventoryModel inventoryModel) {
		this.inventoryModel = inventoryModel;
	}

	public List<InventoryModel> getInventoryModelFiltredList() {
		return inventoryModelFiltredList;
	}

	public void setInventoryModelFiltredList(List<InventoryModel> inventoryModelFiltredList) {
		this.inventoryModelFiltredList = inventoryModelFiltredList;
	}

	public List<InventoryModel> getInventoryModelList() {
		return inventoryModelList;
	}

	public void setInventoryModelList(List<InventoryModel> inventoryModelList) {
		this.inventoryModelList = inventoryModelList;
	}

	public Integer getInventoryRecordId() {
		return inventoryRecordId;
	}

	public void setInventoryRecordId(Integer inventoryRecordId) {
		this.inventoryRecordId = inventoryRecordId;
	}

	public List<InventoryRecord> getInventoryRecordList() {
		return inventoryRecordList;
	}

	public void setInventoryRecordList(List<InventoryRecord> inventoryRecordList) {
		this.inventoryRecordList = inventoryRecordList;
	}

	public List<Article> getArticlesList() {
		return articlesList;
	}

	public void setArticlesList(List<Article> articlesList) {
		this.articlesList = articlesList;
	}

	@PostConstruct
	public void init() {
		currentUser = Utils.findCurrentUser();
		getInventoryMasterDetails();
		setAllWareHouses(stockServiceDao.getStoreDeanWharehouses(currentUser.getUserId()));
		inventoriesList = dataAccessService.getInventoriesByStrNo(StrNo);
		// loadAllArticles();
		// loadAllInventoryList();
	}

	public void setInventoryMaster(InventoryMaster inventoryMaster) {
		this.inventoryMaster = inventoryMaster;
	}

	public InventoryMaster getInventoryMaster() {

		// inventoryMaster=dataAccessService.getInventoryMasterById(1);
		return inventoryMaster;

	}

	private InventoryMaster getInventoryMasterDetails() {

		inventoryMaster = dataAccessService.getInventoryMasterById(1);
		return inventoryMaster;

	}

	public List<InventoryMaster> loadInventories() {

		inventoriesList = dataAccessService.getInventoriesByStrNo(StrNo);
		return inventoriesList;

	}

	/**
	 * lOAD ALL ARTICLES
	 */

	public void loadAllArticles() {
		for (WhsWarehouses whsWarehouses : allWareHouses) {
			if (whsWarehouses.getStoreNumber() == StrNo)
				stockIsBlocked = whsWarehouses.getInvIsBlocked().equals(1);
		}
		for (InventoryMaster invMaster : inventoriesList) {
			if (invMaster.getInventoryId() == inventoryId) {
				if (invMaster.getInventoryBlocked() == 1) {
					inventoryIsBlocked = true;
				} else {
					inventoryIsBlocked = false;
				}
			}
		}
		inventoryModelList = this.dataAccessService.ListInventories(getStrNo(), inventoryId);
		refreshPage();
	}

	public void updateUnite(AjaxBehaviorEvent event) {
		try {
			if (item != null && !item.equals(null) && !item.isEmpty()) {
				itemId = Integer.parseInt(item.trim());
				article = (Article) dataAccessService.findEntityById(Article.class, itemId);
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"من فضلك اختر  الصنف المطلوب", "من فضلك اختر  الصنف المطلوب"));

			}
		} catch (NullPointerException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"من فضلك اختر  الصنف المطلوب", "من فضلك اختر  الصنف المطلوب"));
			e.printStackTrace();
		}

	}

	public void save() {
		if (inventoryRecordList.size() > 0) {
			try {
				inventoryRecordId = dataAccessService.addInventories(inventoryRecordList);

				inventoryRecordList = new ArrayList<>();
				article = new Article();
				MsgEntry.addAcceptFlashInfoMessage("تم ارسال طلبك بنجاح");
			} catch (Exception e) {
				MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.execution"));

			}
		} else {
			MsgEntry.addErrorMessage("من فضلك اضف على الاقل واحد صنف");
		}
	}

	public void addInventoryItem(AjaxBehaviorEvent event) {
		inventoryRecord.setArticleId(itemId);
		inventoryRecord.setArticleName(article.getName());
		inventoryRecord.setInventoryMasterId(inventoryMaster.getInventoryId());
		try {
			inventoryRecord.setUniteName(article.getItemUnite().getName());
			inventoryRecordList.add(inventoryRecord);
			inventoryRecord = new InventoryRecord();
			article = new Article();
		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.execution"));
		}

	}

	public void removeRecord(InventoryRecord inventoryRecord) {
		inventoryRecordList.remove(inventoryRecord);
	}

	/**
	 * for edit column DatatablePrime
	 **/
	public void onRowEdit(RowEditEvent event) {
		Integer articleIdParam = ((InventoryModel) event.getObject()).getArticleId();
		inventoryRecordList = dataAccessService.getInventoryrecordByarticleId(articleIdParam, inventoryId);
		Integer quantite = ((InventoryModel) event.getObject()).getStock();
		Integer lastInvQty =((InventoryModel) event.getObject()).getLastGardQty();
		
		if (quantite == null || quantite == 0) {
			if (inventoryRecordList.size() > 0) {
				try {
					inventoryRecord = inventoryRecordList.get(0);
					dataAccessService.deleteObject(inventoryRecord);
					MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("success.execution"));
				} catch (Exception e) {
					MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.execution"));
				}
			}
		} else {
			if (inventoryRecordList.size() > 0) {
				try {
					inventoryRecord = inventoryRecordList.get(0);
					
					if(lastInvQty>=quantite )
					inventoryRecord.setInventoryCalculated(((InventoryModel) event.getObject()).getQteActuel()-(lastInvQty-quantite));
					else
					inventoryRecord.setInventoryCalculated(((InventoryModel) event.getObject()).getQteActuel()+(quantite -lastInvQty));
					inventoryRecord.setInventoryActual(quantite);					
					if (inventoryRecord.getInventoryActual() >= inventoryRecord.getInventoryCalculated()) {
						inventoryRecord.setInventoryMinus(
								inventoryRecord.getInventoryActual() - inventoryRecord.getInventoryCalculated());
					} else {
						inventoryRecord.setGardAdd(
								inventoryRecord.getInventoryCalculated() - inventoryRecord.getInventoryActual());
					}
					dataAccessService.updateObject(inventoryRecord);
					MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("success.execution"));
				} catch (Exception e) {
					MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.execution"));
				}
			} else {
				try {
					inventoryRecord.setArticleId(((InventoryModel) event.getObject()).getArticleId());
					inventoryRecord.setInventoryMasterId(inventoryId);
					inventoryRecord.setInventoryCalculated(((InventoryModel) event.getObject()).getQteActuel());
					inventoryRecord.setInventoryActual(quantite);
					if (inventoryRecord.getInventoryCalculated() >= inventoryRecord.getInventoryActual()) {
						inventoryRecord.setInventoryMinus(
								inventoryRecord.getInventoryCalculated() - inventoryRecord.getInventoryActual());
					} else {
						inventoryRecord.setGardAdd(
								inventoryRecord.getInventoryActual() - inventoryRecord.getInventoryCalculated());
					}
					inventoryRecordId = dataAccessService.addNewInventoryDetailsRecors(inventoryRecord);
					MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("success.execution"));
				} catch (Exception e) {
					MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.execution"));

				}
			}

		}
		inventoryModelList = this.dataAccessService.ListInventories(getStrNo(), inventoryId);
		refreshPage();
	}

	public void onRowCancel(RowEditEvent event) {
		MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("cancel"));
	}

	public void onCellEdit(CellEditEvent event) {
		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();

		if (newValue != null && !newValue.equals(oldValue)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed",
					"Old: " + oldValue + ", New:" + newValue);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	/*
	 * printInventoryReportAction برأة ذمة
	 **/
	public String printInventoryReportAction() {
		String reportName = "/reports/NewInventory.jrxml";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("gardId", inventoryMaster.getInventoryId());
		parameters.put("STRNO", getStrNo());// "259306";

		Utils.printPdfReport(reportName, parameters);
		return "";
	}

	/*
	 * printInventoryReportAction إستمارة جرد
	 **/
	public String printFormInventoryReportAction() {
		String reportName = "/reports/InventoryForm.jrxml";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("gardId", inventoryMaster.getInventoryId());
		parameters.put("STRNO", getStrNo());// "259306";

		Utils.printPdfReport(reportName, parameters);
		return "";
	}

	/*
	 * LOAD ALL DATA INVENTORY MODEL
	 **/
	// public void loadAllInventoryList() {
	//
	// inventoryModelList = this.dataAccessService.ListInventories();
	// }

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public InventoryRecord getInventoryRecord() {
		return inventoryRecord;
	}

	public void setInventoryRecord(InventoryRecord inventoryRecord) {
		this.inventoryRecord = inventoryRecord;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getStrNo() {
		return StrNo;
	}

	public void setStrNo(Integer strNo) {
		StrNo = strNo;
	}

	public Boolean getStockIsBlocked() {
		return stockIsBlocked;
	}

	public void setStockIsBlocked(Boolean stockIsBlocked) {
		this.stockIsBlocked = stockIsBlocked;
	}

	public IStockServiceDao getStockServiceDao() {
		return stockServiceDao;
	}

	public void setStockServiceDao(IStockServiceDao stockServiceDao) {
		this.stockServiceDao = stockServiceDao;
	}

	public List<WhsWarehouses> getAllWareHouses() {
		return allWareHouses;
	}

	public void setAllWareHouses(List<WhsWarehouses> allWareHouses) {
		this.allWareHouses = allWareHouses;
	}

	public void setStockIsBlocked(boolean stockIsBlocked) {
		this.stockIsBlocked = stockIsBlocked;
	}

	public Integer getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(Integer inventoryId) {
		this.inventoryId = inventoryId;
	}

	public List<InventoryMaster> getInventoriesList() {
		return inventoriesList;
	}

	public void setInventoriesList(List<InventoryMaster> inventoriesList) {
		this.inventoriesList = inventoriesList;
	}

	public boolean isInventoryIsBlocked() {
		return inventoryIsBlocked;
	}

	public void setInventoryIsBlocked(boolean inventoryIsBlocked) {
		this.inventoryIsBlocked = inventoryIsBlocked;
	}

}
