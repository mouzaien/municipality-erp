package com.bkeryah.mails;

public class ServicesFactory extends Factory{
	private static ServicesFactory instance = null;

	private ServicesFactory() {
	}

	public static synchronized ServicesFactory getInstance() {
		if (instance == null) {
			instance = new ServicesFactory();
		}
		return instance;
	}

	public String getPage() {
			return "Models";
	}
}