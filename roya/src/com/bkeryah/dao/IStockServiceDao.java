package com.bkeryah.dao;

import java.util.List;
import java.util.Map;

import com.bkeryah.entities.Article;
import com.bkeryah.entities.ArticleStatus;
import com.bkeryah.entities.FinEntity;
import com.bkeryah.entities.FinFinancialYear;
import com.bkeryah.entities.StockEntryMaster;
import com.bkeryah.entities.StockInDetails;
import com.bkeryah.entities.StockInOutType;
import com.bkeryah.entities.StoreTemporeryReceiptMaster;
import com.bkeryah.entities.WhsMoveType;
import com.bkeryah.entities.WhsWarehouses;
import com.bkeryah.model.MemoReceiptModel;

public interface IStockServiceDao {

	public List<StockInOutType> getAllTransactionsStockType();

	public List<ArticleStatus> getAllArticleStatus();

	public List<WhsMoveType> getAllMovesType();

	public List<FinEntity> getAllSuppliers();

	public Map<String, Integer> getAllNameSuppliers();

	public Map<String, Integer> getAllNameArticles();

	public List<FinFinancialYear> getAllFinancialYear();

	public List<WhsWarehouses> getAllWareHouses();

	public Integer getUserDeptJob(int managerId);

	public StockInOutType getTransactionStockTypeById(int transcationStockTypeId);

	public ArticleStatus getArticleStatusById(int articleStatusId);

	public WhsMoveType getMoveTypeById(int moveTypeId);

	public FinEntity getSupplierById(int supplierId);

	public FinFinancialYear getFinancialYearById(int financialYear);

	public Integer addStockInDetails(StockInDetails stockInDetails);

	public Integer addStockIn(StockEntryMaster stockEntryMaster, Integer createdForId,
			List<StockInDetails> stockInDetailslIST);

	// List<StockInDetails> stockEntryElements);
	// public int addStockOutMasterTransaction(StockOutMaster stockOut,
	// List<StockOutDetails> stockOutElements);
	public List<Article> getAllArticles();

	public List<Article> getAllArticlesByStrno(Integer strNo);

	public MemoReceiptModel getMemoReceiptDetails(Integer memo_receipt_id);

	public List<WhsWarehouses> getStoreDeanWharehouses(Integer userId);

	// تجيب مستودعات الرجيع فقط
	public List<WhsWarehouses> getStoreWharehouses(Integer strType);

}
