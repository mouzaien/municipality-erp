package com.bkeryah.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="XXX_HR_EMP_RANKS")
public class HREmpRanks {

	
	@Id
	@Column(name ="RANK_CODE")
	private String rankCode;
	
	@Column(name ="EMP_CAT_CODE")
	private String empCatCode;
		
	@Column(name ="RANK_US_NAME")
	private String rankUsName;
	
	@Column(name ="RANK_AR_NAME")
	private String rankArName;

	public String getRankCode() {
		return rankCode;
	}

	public void setRankCode(String rankCode) {
		this.rankCode = rankCode;
	}

	public String getEmpCatCode() {
		return empCatCode;
	}

	public void setEmpCatCode(String empCatCode) {
		this.empCatCode = empCatCode;
	}

	public String getRankUsName() {
		return rankUsName;
	}

	public void setRankUsName(String rankUsName) {
		this.rankUsName = rankUsName;
	}

	public String getRankArName() {
		return rankArName;
	}

	public void setRankArName(String rankArName) {
		this.rankArName = rankArName;
	}
}
