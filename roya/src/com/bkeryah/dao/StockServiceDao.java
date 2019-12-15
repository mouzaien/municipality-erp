package com.bkeryah.dao;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import com.bkeryah.entities.ArcRecords;
import com.bkeryah.entities.ArcUsers;
import com.bkeryah.entities.ArcUsersExtension;
import com.bkeryah.entities.Article;
import com.bkeryah.entities.ArticleStatus;
import com.bkeryah.entities.FinEntity;
import com.bkeryah.entities.FinFinancialYear;
import com.bkeryah.entities.StockEntryMaster;
import com.bkeryah.entities.StockInDetails;
import com.bkeryah.entities.StockInOutType;
import com.bkeryah.entities.WhsMoveType;
import com.bkeryah.entities.WhsWarehouses;
import com.bkeryah.entities.WrkApplication;
import com.bkeryah.mails.MailTypeEnum;
import com.bkeryah.model.MemoReceiptModel;
import com.bkeryah.service.IDataAccessService;

import utilities.HijriCalendarUtil;
import utilities.MyConstants;
import utilities.Utils;

public class StockServiceDao extends CommonServicesDao implements Serializable, IStockServiceDao {
	private static final long serialVersionUID = 1L;
	private SessionFactory sessionFactory;
	@Inject
	private IDataAccessService dataAccessService;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	@Override
	@Transactional
	public Integer save(Object myObject) {
		Integer save = (Integer) sessionFactory.getCurrentSession().save(myObject);
		return save;
	}

	@Override
	@Transactional
	public synchronized String createInocmeNUmber() {
		String sql = readQuery("createIncomeNumber");
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		Object result = query.uniqueResult();
		return result.toString();
	}

	@Override
	@Transactional
	public Integer findUserSection(int userId) {
		String sql = readQuery("findUserSection");
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setParameter("puserId", userId);
		Object result = query.uniqueResult();
		return Integer.parseInt(result.toString());
	}

	@Override
	@Transactional
	public Integer createNewArcRecord(Integer arcType, String recTitle, boolean withIncomeNumber, Integer toId) {
		Integer letterto = findUserSection(toId);
		ArcRecords arcRecord = getNewArcRecord(arcType, recTitle, letterto);
		String CurrentHijriDate = HijriCalendarUtil.findCurrentHijriDate();
		if (withIncomeNumber) {
			arcRecord.setIncomeNo(createInocmeNUmber().toString());
			arcRecord.setIncomeHDate(CurrentHijriDate);
		} else {
			arcRecord.setIncomeNo("");
			arcRecord.setIncomeHDate("");
		}
		Integer newArcRecord = save(arcRecord);
		return newArcRecord;
	}

	@Override
	@Transactional
	public List<StockInOutType> getAllTransactionsStockType() {

		return loadAll(StockInOutType.class);
	}

	@Override
	@Transactional
	public List<ArticleStatus> getAllArticleStatus() {
		return loadAll(ArticleStatus.class);

	}

	@Override
	@Transactional
	public List<WhsMoveType> getAllMovesType() {
		return loadAll(WhsMoveType.class);
	}

	@Override
	@Transactional
	public List<FinEntity> getAllSuppliers() {
		return loadAll(FinEntity.class);
	}

	/*
	 * 
	 * */

	@Override
	@Transactional
	public StockInOutType getTransactionStockTypeById(int transcationStockTypeId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(StockInOutType.class);
		criteria.add(Restrictions.eq("stockInOutTypeId", transcationStockTypeId));
		return (StockInOutType) criteria.uniqueResult();
	}

	@Override
	@Transactional
	public ArticleStatus getArticleStatusById(int articleStatusId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ArticleStatus.class);
		criteria.add(Restrictions.eq("articleStatusId", articleStatusId));
		return (ArticleStatus) criteria.uniqueResult();
	}

	@Override
	@Transactional
	public WhsMoveType getMoveTypeById(int moveTypeId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(WhsMoveType.class);
		criteria.add(Restrictions.eq("moveTypeId", moveTypeId));
		return (WhsMoveType) criteria.uniqueResult();
	}

	@Override
	@Transactional
	public FinEntity getSupplierById(int supplierId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FinEntity.class);
		criteria.add(Restrictions.eq("finEntityId", supplierId));
		return (FinEntity) criteria.uniqueResult();
	}

	@Override
	@Transactional
	public List<FinFinancialYear> getAllFinancialYear() {
		return loadAll(FinFinancialYear.class);
	}

	@Override
	@Transactional
	public FinFinancialYear getFinancialYearById(int financialYear) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FinFinancialYear.class);
		criteria.add(Restrictions.eq("YEARID", financialYear));
		return (FinFinancialYear) criteria.uniqueResult();
	}

	@Override
	@Transactional
	public List<WhsWarehouses> getAllWareHouses() {
		return loadAll(WhsWarehouses.class);
	}

	@Override
	@Transactional
	public Map<String, Integer> getAllNameSuppliers() {
		Map<String, Integer> SupplierList = new HashMap<>();

		List<FinEntity> lst = dataAccessService.loadSupliers();

		for (FinEntity supplier : lst) {
			SupplierList.put(supplier.getFinEntityName(), supplier.getFinEntityId());
		}
		return SupplierList;

	}

	@Override
	@Transactional
	public Map<String, Integer> getAllNameArticles() {

		Map<String, Integer> articleList = new HashMap<>();

		List<Article> lst = loadAll(Article.class);
		for (Article article : lst) {
			articleList.put(article.getName(), article.getId());
		}
		return articleList;
	}

	@Override
	@Transactional
	public Integer addStockInDetails(StockInDetails stockInDetails) {
		return save(stockInDetails);

	}

	@Override
	@Transactional
	public Integer addStockIn(StockEntryMaster stockEntryMaster, Integer createdForId,
			List<StockInDetails> stockInDetailslIST) {
		Integer stockEntryMasterId = (Integer) save(stockEntryMaster);

		for (StockInDetails itemIn : stockInDetailslIST) {
			itemIn.setStockmasterIn(stockEntryMasterId);
			Integer itemInId = addStockInDetails(itemIn);

		}
		ArcUsers user = Utils.findCurrentUser();
		Integer toId = dataAccessService.getUserTo(MailTypeEnum.MEMO_RECEIPT.getValue(), 1);
		Integer applicationUserDeptJob = getUserDeptJob(toId);

		ArcRecords arcRecord = new ArcRecords();
		arcRecord.setApplicationType(MailTypeEnum.MEMO_RECEIPT.getValue());
		arcRecord.setRecTitle(MessageFormat.format(Utils.loadMessagesFromFile("memo.receipt"), user.getEmployeeName()));
		// String recTitle = arcRecord.getRecTitle();
		int recordId = dataAccessService.createNewArcRecord(arcRecord, false, toId);

		WrkApplication application = new WrkApplication();
		application.setToUserId(toId);
		// application.setId(new WrkApplicationId(commonDao.createWrkId(),
		// findStepId(recordId)));

		application.setApplicationPurpose(MyConstants.SIGNATURE_TYPE);
		application.setApplicationStatus(MyConstants.STATUS_PERMISSION);

		String items = "";
		for (StockInDetails detail : stockInDetailslIST) {
			items = items + detail.getArticleName() + Utils.loadMessagesFromFile("article.quantity")+" " + detail.getQty() + "\n";
		}
		String userComment = MessageFormat.format(Utils.loadMessagesFromFile("memo.receipt"),user.getEmployeeName()) + " "+Utils.loadMessagesFromFile("for.articles")+ " " + items;
		dataAccessService.createNewWrkApplication(recordId, application, userComment, false, applicationUserDeptJob);

		dataAccessService.saveHrsSigns(recordId, stockEntryMasterId, false, null, Utils.findCurrentUser().getUserId(),
				MailTypeEnum.MEMO_RECEIPT.getValue());
		return stockEntryMasterId;

	}

	@Override
	@Transactional
	public Integer getUserDeptJob(int managerId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ArcUsersExtension.class);
		criteria.add(Restrictions.eq("userId", managerId));
		ArcUsersExtension user = (ArcUsersExtension) criteria.uniqueResult();
		if (user == null)
			return null;
		return user.getUserDeptJob();
	}

	@Override
	@Transactional
	public List<Article> getAllArticlesByStrno(Integer strNo) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Article.class);
		criteria.add(Restrictions.eq("strNo", strNo));
		List<Article> articles = criteria.list();
		return articles;
	}

	@Override
	public List<Article> getAllArticles() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<WhsWarehouses> getStoreDeanWharehouses(Integer storeDeanId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(WhsWarehouses.class);
		Criterion strdean = Restrictions.eq("storeUserId", storeDeanId);
		Criterion strboss = Restrictions.eq("storeBossId", storeDeanId);
		LogicalExpression deanOrBoss = Restrictions.or(strdean, strboss);
		criteria.add(deanOrBoss);
		return criteria.list();
	}
	
	@Override
	@Transactional
	public MemoReceiptModel getMemoReceiptDetails(Integer memo_receipt_id){
		return dataAccessService.getMemoReceiptDetails(memo_receipt_id);
	}
}
