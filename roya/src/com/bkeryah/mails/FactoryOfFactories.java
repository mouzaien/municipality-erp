package com.bkeryah.mails;

public class FactoryOfFactories {
	private static FactoryOfFactories instance = null;

	private FactoryOfFactories() {
	}

	public static synchronized FactoryOfFactories getInstance() {
		if (instance == null) {
			instance = new FactoryOfFactories();
		}
		return instance;
	}

	public Factory getFactory(final MailTypeEnum mailType) {
		switch (mailType) {
		case PROJECTeXTRACT:
		case HEALTH_LICENCE:
		case EXCHANGEREQUEST:
		case ACTUALEXCHANGE:
		case PROCUREMENT_MATERIALS:
		case FINE_REBOUND:
		case SICK_VACATION:
		case MEMO_RECEIPT:
		case NEW_BUILDING_LICENCE:
		case NEW_BUILDING_WALL_LICENCE:
		case ATTACH_BUILDING_LICENCE:
		case COMPACT_PERFORMANCE:
		case GENERAL_APPRECIATION:
		case PERFORMANCE_EVALUATION:
		case RETURNED_ITEMS_INVENTORY:
		case TEMPORERY_RECEIPT:
		case TRANSFER_OWNERSHIP:	
			return ServicesFactory.getInstance();
		default:
			return MailsFactory.getInstance();
		}
	}
}
