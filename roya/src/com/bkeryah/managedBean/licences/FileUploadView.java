package com.bkeryah.managedBean.licences;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.faces.bean.ManagedBean;
import javax.servlet.http.Part;

@ManagedBean
public class FileUploadView {

	// public void upload(FileUploadEvent event) {
	// System.out.println(event.getFile().getFileName());
	// FacesMessage message = new FacesMessage("Succesful",
	// event.getFile().getFileName() + " is uploaded.");
	// FacesContext.getCurrentInstance().addMessage(null, message);
	// }
	private Part file1;

	// getters and setters for file1 and file2

	public String upload() throws IOException {
		InputStream inputStream = file1.getInputStream();
		FileOutputStream outputStream = new FileOutputStream(getFilename(file1));

		byte[] buffer = new byte[4096];
		int bytesRead = 0;
		while (true) {
			bytesRead = inputStream.read(buffer);
			if (bytesRead > 0) {
				outputStream.write(buffer, 0, bytesRead);
			} else {
				break;
			}
		}
		outputStream.close();
		inputStream.close();

		return "success";
	}

	public Part getFile1() {
		return file1;
	}

	public void setFile1(Part file1) {
		this.file1 = file1;
	}

	private static String getFilename(Part part) {
		for (String cd : part.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
				return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE
																													// fix.
			}
		}
		return null;
	}
}