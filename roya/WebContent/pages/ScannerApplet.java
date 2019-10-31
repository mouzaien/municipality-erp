package utilities;

import java.applet.Applet;
import java.awt.Graphics;

public class ScannerApplet extends Applet {
	public void paint(Graphics g) {
		MorenaScanner.scanFileList();
	}
}
