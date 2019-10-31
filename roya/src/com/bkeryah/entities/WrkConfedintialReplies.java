package com.bkeryah.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="WRK_CONFEDINTIAL_REPLIES")
public class WrkConfedintialReplies implements Serializable {

	@EmbeddedId
	private WrkConfedintialRepliesId id;

	public WrkConfedintialRepliesId getId() {
		return id;
	}

	public void setId(WrkConfedintialRepliesId id) {
		this.id = id;
	}
	
	
	
}
