package utilities;

public enum ContractOperationEnum {
	SADAD(1, Utils.loadMessagesFromFile("sadad")),DELETE(1, Utils.loadMessagesFromFile("delete")), RENEW(2, Utils.loadMessagesFromFile("renew")), CANCEL(3, Utils.loadMessagesFromFile("cancel")), BILL(4, Utils.loadMessagesFromFile("bills")), GIVE_UP(5, Utils.loadMessagesFromFile("give_up"));
	
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
