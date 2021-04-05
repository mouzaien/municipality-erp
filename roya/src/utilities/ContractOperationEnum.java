package utilities;

public enum ContractOperationEnum {
	NEW(1, Utils.loadMessagesFromFile("delete")), RENEW(2, Utils.loadMessagesFromFile("renew")), CANCEL(3,
			Utils.loadMessagesFromFile("cancel")), STOPPED(4, Utils.loadMessagesFromFile("sadad")), GIVE_UP(5,
					Utils.loadMessagesFromFile("give_up")), BILL(6, Utils.loadMessagesFromFile("bills")), DELETE(7,
							Utils.loadMessagesFromFile("delete")), SADAD(8,
									Utils.loadMessagesFromFile("sadad")), SMS(9, Utils.loadMessagesFromFile("contract.sms.msg"));

	private int action;
	private String name;

	private ContractOperationEnum(int action, String name) {
		this.setAction(action);
		this.name = name;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
