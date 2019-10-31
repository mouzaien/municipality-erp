package com.bkeryah.testssss;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import utilities.Utils;

@ManagedBean(name = "sessionbean")
@SessionScoped
public class Sessionbean {

	private String name;
	String line;
	Process p;
	// ComDao dtatac;

	public Sessionbean() {


	}

	public String outPut() throws IOException {

		// ComDao daoc = (ComDao) BeansManager.getBean("daoc");
		// daoc.print();
		return null;

	}

	public String xx() {
		// dtatac = (DataAccesTest) BeansManager.getBean("dataAccessService");



		// ComDao daoc = (ComDao) BeansManager.getBean("daoc");
		// daoc.print();
		return "";

	}

	// public String out() {
	// ComDao daoc=(ComDao) BeansManager.getBean1("daoc");
	// daoc.print();
	// // dtatac = (DataAccesTest) BeansManager.getBean("dataAccessService");
	// System.out.println("outttt");
	// FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	// System.out.println("name is " + name);
	// return "";
	//
	// }

	@PreDestroy
	public void y() {

	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String executeScanner() {

		ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "C:\\WINDOWS\\ARCHIVING\\Scan.exe");
		builder.redirectErrorStream(true);

		try {
			p = builder.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));

		while (true) {
			try {
				line = r.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (line == null) {
				break;
			}

		}
		return "";
	}

	public String openPDF() {
		try {

			if ((new File("C:\\Windows\\Archiving\\image.pdf")).exists()) {

				Process p = Runtime.getRuntime()
						.exec("rundll32 url.dll,FileProtocolHandler C:\\Windows\\Archiving\\image.pdf");
				p.waitFor();

			} else {



			}



		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

	public String toServer() throws Exception {
		File file = new File("C:\\Windows\\Archiving\\image.pdf");
		FileInputStream fis = null;

		try {
			
			fis = new FileInputStream(file);
			Utils.saveAttachments(fis, "testetstetsttestet.pdf");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null)
					fis.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		// try {
		//
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		return "";
	}
}
