package utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.net.ftp.FTPClient;

import com.bkeryah.dao.DataBaseConnectionClass;

import common.Util.EncrypterDecrypter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class FtpTransferFile {
	final static String host = "10.27.9.5";
	final static String user = "Administrator";
	final static String pass = "Adminoraserver200";
	final static int port = 21;

	public static InputStream getFile(String fileName) throws IOException {
		InputStream inputStream = null;
		URLConnection conn;
		String ftpUrl = "ftp://%s:%s@%s/%s;type=i";
		ftpUrl = String.format(ftpUrl, user, pass, host, fileName);

		try {
			URL url;

			url = new URL(ftpUrl);

			conn = url.openConnection();
			inputStream = conn.getInputStream();

			return inputStream;
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		} finally {
//			if (inputStream != null)
//				try {
//					inputStream.close();
//				} catch (IOException e) {
//				}
		}
	}

	public static boolean saveFile(InputStream inputStreamFile, String savePath) throws IOException {

		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(savePath);
			byte[] buffer = new byte[1024];
			int bytesRead = -1;
			while ((bytesRead = inputStreamFile.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}

			return true;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return false;

		} finally {
			outputStream.close();
			inputStreamFile.close();

		}
	}

	public static boolean deleteFile(String fileName) throws IOException {
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(host, port);
			ftpClient.login(user, pass);
			boolean deleted = ftpClient.deleteFile(fileName);
			if (deleted) {

			} else {

			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ftpClient.logout();
		}
		return false;

	}

	public static String uploadFile(InputStream inputStream, String uploadPath) {
		String ftpUrl = "ftp://%s:%s@%s/%s;type=i";
		ftpUrl = String.format(ftpUrl, user, pass, host, uploadPath);
		OutputStream outputStream = null;
		try {
			URL url = new URL(ftpUrl);
			URLConnection conn = url.openConnection();
			outputStream = conn.getOutputStream();
			byte[] buffer = new byte[99999999];
			int bytesRead = -1;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
			inputStream.close();
			outputStream.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
		}
		return uploadPath;
	}
	public static String uploadSalariesFile(InputStream inputStream, String uploadPath) {
		String ftpUrl = "ftp://%s:%s@%s/%s;type=i";
		ftpUrl = String.format(ftpUrl, user, pass, host, uploadPath);
		OutputStream outputStream = null;
		try {
			URL url = new URL(ftpUrl);
			URLConnection conn = url.openConnection();
			outputStream = conn.getOutputStream();
			
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "windows-1256"));
			BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
			String bytesRead =in.readLine();
			while (bytesRead !=null ) {
				writer.write(bytesRead);
				bytesRead =in.readLine();
				if(bytesRead !=null)writer.newLine();
			}
			writer.flush();
			inputStream.close();
			outputStream.close();
			in.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
		}
		return uploadPath;
	}
	public static OutputStream uploadFileFrom(String uploadPath) {
		String ftpUrl = "ftp://%s:%s@%s/%s;type=i";
		ftpUrl = String.format(ftpUrl, user, pass, host, uploadPath);

		try {
			URL url = new URL(ftpUrl);
			URLConnection conn = url.openConnection();
			OutputStream outputStream = conn.getOutputStream();
			return outputStream;
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static void uploadFileToFtp(String incomNo) throws JRException, SQLException {
		Connection connection = DataBaseConnectionClass.getConnection();
		try {
			String incomNoDcrypted = EncrypterDecrypter.decrypt(incomNo);
			JasperReport jasperReport = JasperCompileManager.compileReport("d:/reports/letter.jrxml");
			// Parameters for report
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("p1", incomNoDcrypted);
			parameters.put("textSize", 16);
			parameters.put("copy", 0);

			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);

			OutputStream outputStream = FtpTransferFile.uploadFileFrom("letters/" + incomNo + ".pdf");

			JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

		} catch (Exception e) {

			// TODO: handle exception
		} finally {

			connection.close();
		}
	}

	// public static void upload() {
	// FTPClient client = new FTPClient();
	// FileInputStream fis = null;
	//
	// client.connect("ftp.domain.com");
	// client.login("admin", "secret");
	//
	// String filename = "Touch.dat";
	// fis = new FileInputStream(filename);
	// client.storeFile(filename, fis);
	// client.logout();
	// fis.close();
	// }

}
