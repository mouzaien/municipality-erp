package com.bkeryah.entities;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="arc_record_linking")
public class ArcRecordLinking implements Serializable{

	@EmbeddedId
	private ArcRecordLinkingId id;

	public ArcRecordLinkingId getId() {
		return id;
	}

	public void setId(ArcRecordLinkingId id) {
		this.id = id;
	}
	
	
	
}
