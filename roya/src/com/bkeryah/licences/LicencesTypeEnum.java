package com.bkeryah.licences;

public enum LicencesTypeEnum {
	ERROR(-1), BUILDLICNEW(1), BUILDLICEXT(2), ARCPEOPLE(3), BLDLICLIST(4),;
	private int value;

	private LicencesTypeEnum(int value) {
		this.setValue(value);
	}

	public static LicencesTypeEnum getValue(Integer reqType) {
		if (reqType == null)
			return LicencesTypeEnum.ERROR;
		switch (reqType) {
		case 1:
			return LicencesTypeEnum.BUILDLICNEW;
		case 3:
			return LicencesTypeEnum.ARCPEOPLE;
		case 4:
			return LicencesTypeEnum.BLDLICLIST;
		default:
			return LicencesTypeEnum.ERROR;
		}
	}

	public static LicencesTypeEnum getValue(String reqType) {
		if (reqType == null)
			return LicencesTypeEnum.ERROR;
		switch (reqType) {
		case "1":
			return LicencesTypeEnum.BUILDLICNEW;
		case "3":
			return LicencesTypeEnum.ARCPEOPLE;
		case "4":
			return LicencesTypeEnum.BLDLICLIST;
		default:
			return LicencesTypeEnum.ERROR;
		}
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public static LicencesTypeEnum parseType(int value) {
		for (LicencesTypeEnum type : LicencesTypeEnum.values()) {
			if (value == type.getValue())
				return type;
		}
		return LicencesTypeEnum.ERROR;
	}
}
