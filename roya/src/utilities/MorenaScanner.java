package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import eu.gnome.morena.Configuration;
import eu.gnome.morena.Device;
import eu.gnome.morena.Manager;
import eu.gnome.morena.Scanner;

public final class MorenaScanner {

	public static void main(String[] args) {
	//	scannPapers();
System.out.println(scanFileList().size());
	}

	public static void scannPapers() {

		{

			Configuration.addDeviceType(".*fficejet.*", true);

			Manager manager = Manager.getInstance();
			List<? extends Device> devices = manager.listDevices();
			if (devices.size() > 0) {
				Device device = devices.get(0);

				if (device != null) {
					// for scanner device set the scanning parameters
					if (device instanceof Scanner) {
						Scanner scanner = (Scanner) device;
						scanner.setMode(Scanner.RGB_8);
						scanner.setResolution(75);
						try {
							File file = null;
							int i = 1;
							while ((file = SynchronousHelper.scanFile(device, 1)) != null) {
								int extIx = file.getName().lastIndexOf('.');
								File newFile = new File(file.getParentFile(),
										"MorenaImg_" + i + (extIx > 0 ? file.getName().substring(extIx) : ""));
								if (newFile.exists())
									newFile.delete();
								boolean renamed = file.renameTo(newFile);
								System.out.println("image renamed " + renamed + "  from : " + file.getAbsolutePath()
										+ "  to : " + newFile.getAbsolutePath());
								i++;
								Thread.sleep(1000);
							}

						} catch (Exception ex) { // check if error is related to
													// empty ADF
//							if (ex.getMessage().indexOf("[" + SynchronousHelper.WIA_ERROR_PAPER_EMPTY + "]") < 0)
							

						}
					}
				}
			} 

		}

	}

	public static List<InputStream> scanFileList() {
		List<InputStream> list = new ArrayList<>();

		{

			Configuration.addDeviceType(".*fficejet.*", true);

			Manager manager = Manager.getInstance();
			List<? extends Device> devices = manager.listDevices();
			if (devices.size() > 0) {
				Device device = devices.get(0);

				if (device != null) {
					// for scanner device set the scanning parameters
					if (device instanceof Scanner) {
						Scanner scanner = (Scanner) device;
						scanner.setMode(Scanner.RGB_8);
						scanner.setResolution(75);
						try {
							File file = null;
							int i = 1;
							while ((file = SynchronousHelper.scanFile(device, 1)) != null) {
								int extIx = file.getName().lastIndexOf('.');
								File newFile = new File(file.getParentFile(),
										"MorenaImg_" + i + (extIx > 0 ? file.getName().substring(extIx) : ""));
								if (newFile.exists())
									newFile.delete();
								boolean renamed = file.renameTo(newFile);
								System.out.println("image renamed " + renamed + "  from : " + file.getAbsolutePath()
										+ "  to : " + newFile.getAbsolutePath());
								i++;
								Thread.sleep(1000);
								list.add((InputStream) new FileInputStream(newFile.getAbsolutePath()));
							}

						} catch (Exception ex) { // check if error is related to
													// empty ADF
//							if (ex.getMessage().indexOf("[" + SynchronousHelper.WIA_ERROR_PAPER_EMPTY + "]") < 0)

							

						}
					}
				}
			} 

		}
		return list;
	}
}
