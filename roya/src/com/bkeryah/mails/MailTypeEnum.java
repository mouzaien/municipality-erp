package com.bkeryah.mails;

public enum MailTypeEnum {
	COPY(0), KITAB(143), MODAKARA(242), MAILS(1), AUTORIZATION(230), SPECIAL_AUTORIZATION(245), WORKMISSION(153), LEAVE(
			3), LEAVESUCCESSOR(144), TRAINING(235), RESIGNATION(6), RETURNED_ITEMS_INVENTORY(250), PROJECTeXTRACT(
					236), EARLYRETIREMENT(7), EXCHANGEREQUEST(237), SALARY(232), SALAR(233), MEDICALEXAMINATION(
							9), ERROR(-1), VACATION(25), VACNEEDED(26), SICK_VACATION(28), TRANSACTION(
									114), LIC(118), MEDICALREPORT(234), HEALTH_LICENCE(
											117), ACTUALEXCHANGE(238), PROCUREMENT_MATERIALS(239), PERSONAL_COMMENT(
													240), FINE_REBOUND(241), MEMO_RECEIPT(197), NEW_BUILDING_LICENCE(
															119), ATTACH_BUILDING_LICENCE(
																	243), NEW_BUILDING_WALL_LICENCE(
																			244), COMPACT_PERFORMANCE(
																					246), GENERAL_APPRECIATION(
																							248), PERFORMANCE_EVALUATION(
																									247), TEMPORERY_RECEIPT(
																											251);
	private int value;

	private MailTypeEnum(int value) {
		this.setValue(value);
	}

	public static MailTypeEnum getValue(int x) {
		switch (x) {
		case 0:
			return MailTypeEnum.COPY;// soura mo3amla
		case 230:
			return MailTypeEnum.AUTORIZATION;
		case 245:
			return MailTypeEnum.SPECIAL_AUTORIZATION;
		case 236:
			return MailTypeEnum.PROJECTeXTRACT;
		case 153:
			return MailTypeEnum.WORKMISSION;
		case 237:
			return MailTypeEnum.EXCHANGEREQUEST;
		case 238:
			return MailTypeEnum.ACTUALEXCHANGE;
		case 3:
			return MailTypeEnum.LEAVE;
		case 144:
			return MailTypeEnum.LEAVESUCCESSOR;
		case 5:
			return MailTypeEnum.TRAINING;
		case 6:
			return MailTypeEnum.RESIGNATION;
		case 7:
			return MailTypeEnum.EARLYRETIREMENT;
		case 25:
			return MailTypeEnum.VACATION;
		case 26:
			return MailTypeEnum.VACNEEDED;
		case 28:
			return MailTypeEnum.SICK_VACATION;
		case 8:
			return MailTypeEnum.SALARY;
		case 1:
			return MailTypeEnum.MAILS;
		case 114:
			return MailTypeEnum.TRANSACTION;
		case 232:
			return MailTypeEnum.SALARY;
		case 233:
			return MailTypeEnum.SALAR;
		case 118:
			return MailTypeEnum.LIC;
		case 235:
			return MailTypeEnum.TRAINING;
		case 234:
			return MailTypeEnum.MEDICALREPORT;
		case 117:
			return MailTypeEnum.HEALTH_LICENCE;
		case 240:
			return MailTypeEnum.PERSONAL_COMMENT;

		case 239:
			return MailTypeEnum.PROCUREMENT_MATERIALS;
		case 143:
			return MailTypeEnum.KITAB;
		case 242:

			return MailTypeEnum.MODAKARA;
		case 241:
			return MailTypeEnum.FINE_REBOUND;
		case 197:
			return MailTypeEnum.MEMO_RECEIPT;
		case 119:
			return MailTypeEnum.NEW_BUILDING_LICENCE;
		case 243:
			return MailTypeEnum.ATTACH_BUILDING_LICENCE;
		case 244:
			return MailTypeEnum.NEW_BUILDING_WALL_LICENCE;
		case 246:
			return MailTypeEnum.COMPACT_PERFORMANCE;
		case 248:
			return MailTypeEnum.GENERAL_APPRECIATION;
		case 247:
			return MailTypeEnum.PERFORMANCE_EVALUATION;
		case 250:
			return MailTypeEnum.RETURNED_ITEMS_INVENTORY;
		case 251:
			return MailTypeEnum.TEMPORERY_RECEIPT;
		default:
			return MailTypeEnum.ERROR;
		}
	}

	public static MailTypeEnum getValue(String x) {
		switch (x) {
		case "EDITMAIL":
			return MailTypeEnum.COPY;
		case "MAILS":
			return MailTypeEnum.MAILS;
		case "AUTORIZATION":
			return MailTypeEnum.AUTORIZATION;
		case "SPECIAL_AUTORIZATION":
			return MailTypeEnum.SPECIAL_AUTORIZATION;
		case "WORKMISSION":
			return MailTypeEnum.WORKMISSION;
		case "LEAVE":
			return MailTypeEnum.LEAVE;
		case "LEAVESUCCESSOR":
			return MailTypeEnum.LEAVESUCCESSOR;
		case "TRAINING":
			return MailTypeEnum.TRAINING;
		case "RESIGNATION":
			return MailTypeEnum.RESIGNATION;
		case "EARLYRETIREMENT":
			return MailTypeEnum.EARLYRETIREMENT;
		case "VACATION":
			return MailTypeEnum.VACATION;
		case "VACNEEDED":
			return MailTypeEnum.VACNEEDED;
		case "SICK_VACATION":
			return MailTypeEnum.SICK_VACATION;
		case "SALARY":
			return MailTypeEnum.SALARY;
		case "SALAR":
			return MailTypeEnum.SALAR;
		case "LIC":
			return MailTypeEnum.LIC;
		case "MEDICALREPORT":
			return MailTypeEnum.MEDICALREPORT;
		case "EXCHANGEREQUEST":
			return MailTypeEnum.EXCHANGEREQUEST;
		case "ACTUALEXCHANGE":
			return MailTypeEnum.ACTUALEXCHANGE;
		case "PERSONAL_COMMENT":
			return MailTypeEnum.PERSONAL_COMMENT;
		case "FINE_REBOUND":
			return MailTypeEnum.FINE_REBOUND;
		case "NEW_BUILDING_LICENCE":
			return MailTypeEnum.NEW_BUILDING_LICENCE;
		case "ATTACH_BUILDING_LICENCE":
			return MailTypeEnum.ATTACH_BUILDING_LICENCE;
		case "NEW_BUILDING_WALL_LICENCE":
			return MailTypeEnum.NEW_BUILDING_WALL_LICENCE;
		case "COMPACT_PERFORMANCE":
			return MailTypeEnum.COMPACT_PERFORMANCE;
		case "GENERAL_APPRECIATION":
			return MailTypeEnum.GENERAL_APPRECIATION;
		case "PERFORMANCE_EVALUATION":
			return MailTypeEnum.PERFORMANCE_EVALUATION;
		case "RETURNED_IEMS_INVENTORY":
			return MailTypeEnum.RETURNED_ITEMS_INVENTORY;
		case "TEMPORERY_RECEIPT":
			return MailTypeEnum.TEMPORERY_RECEIPT;
		default:
			return MailTypeEnum.ERROR;
		}
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public static MailTypeEnum parseType(int value) {
		for (MailTypeEnum type : MailTypeEnum.values()) {
			if (value == type.getValue())
				return type;
		}
		return null;
	}
}