package com.bkeryah.entities.licences;

public enum LicenceTypeEnum {
	NEW_BUILDING(1), EXCAVATION(2), DEMOLITION(3), FENCING(4), ADD_FLOOR(5), RESTORATION(6), REMOVE_WASTE(7), BUILDING_EXTENSION(8);
	private int value;

	private LicenceTypeEnum(int value) {
		this.setValue(value);
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	public static LicenceTypeEnum parseType(int value) {
	    for(LicenceTypeEnum type : LicenceTypeEnum.values()){
	    	if(value == type.getValue())
	    		return type;
	    }
		return null;
	}
}