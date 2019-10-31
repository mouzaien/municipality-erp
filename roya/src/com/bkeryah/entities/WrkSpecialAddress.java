package com.bkeryah.entities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="WRK_SPECIAL_ADDRESS")
public class WrkSpecialAddress {
	@EmbeddedId
	private WrkSpecialAddressId id;

	public WrkSpecialAddressId getId() {
		return id;
	}

	public void setId(WrkSpecialAddressId id) {
		this.id = id;
	}
	


}
