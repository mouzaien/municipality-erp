package com.bkeryah.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="XXX_HR_LAW_SENTENCE")
public class HRLawSentence {


	@Id
	@Column(name ="LAW_SENTENCE_CODE")
	private String sentenceCode;
		
	@Column(name ="LAW_SENTENCE_EN_NAME")
	private String sentenceEnName;
		
	@Column(name ="LAW_SENTENCE_AR_NAME")
	private String sentenceArName;

	public String getSentenceCode() {
		return sentenceCode;
	}

	public void setSentenceCode(String sentenceCode) {
		this.sentenceCode = sentenceCode;
	}

	public String getSentenceEnName() {
		return sentenceEnName;
	}

	public void setSentenceEnName(String sentenceEnName) {
		this.sentenceEnName = sentenceEnName;
	}

	public String getSentenceArName() {
		return sentenceArName;
	}

	public void setSentenceArName(String sentenceArName) {
		this.sentenceArName = sentenceArName;
	}
}
