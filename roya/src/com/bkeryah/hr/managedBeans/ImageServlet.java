package com.bkeryah.hr.managedBeans;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;

/*
 * @author Brendan Healey
 *
 * Originates from BalusC.
 *
 */

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bkeryah.service.IDataAccessService;
import com.bkeryah.testssss.BeansManager;

@WebServlet(name = "ImageServlet", urlPatterns = { "/ImageServlet/*" })
public class ImageServlet extends HttpServlet {

	private IDataAccessService dataAccessService;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("image/jpeg");
		ServletOutputStream out = null;

		try {
			response.reset();
			out = response.getOutputStream();

			Object dataAccessServ = BeansManager.getBean("dataAccessService");
			Object commonDao = BeansManager.getBean("commonDao");
			Object dataAccessDAO = BeansManager.getBean("dataAccessDAO");
			Object sessionFactory = BeansManager.getBean("SessionFactory");

			setDataAccessService((IDataAccessService) dataAccessServ);
			dataAccessService.injectDaos(commonDao, dataAccessDAO, sessionFactory);

			byte[] myPicture = dataAccessService.loadArcPeoplePic(Long.parseLong(request.getParameter("id")));//2125472791L);
			if (myPicture != null && myPicture.length != 0) {
				out.write(myPicture);
			}
			File file = new File("c:\\temp1\\XX.jpg");
			if(!file.exists())
				return;
			FileOutputStream fos = new FileOutputStream("c:\\temp1\\XX.jpg");
			fos.write(myPicture);
			fos.close();

			response.setContentType("image/jpeg");
			ServletOutputStream outt;
			outt = response.getOutputStream();
			FileInputStream fin = new FileInputStream("c:\\temp1\\XX.jpg");

			BufferedInputStream bin = new BufferedInputStream(fin);
			BufferedOutputStream bout = new BufferedOutputStream(outt);
			int ch = 0;
			
			while ((ch = bin.read()) != -1) {
				bout.write(ch);
			}

			bin.close();
			fin.close();
			bout.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			close(out);
		}

	}

	private void byteToImage(byte[] bytes, File imageFile) throws IOException {

		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		Iterator readers = ImageIO.getImageReadersByFormatName("image");

		ImageReader reader = (ImageReader) readers.next();
		Object source = bis; // File or InputStream, it seems file is OK

		ImageInputStream iis = ImageIO.createImageInputStream(source);

		reader.setInput(iis, true);
		ImageReadParam param = reader.getDefaultReadParam();

		Image image = reader.read(0, param);
		// got an image file

		BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null),
				BufferedImage.TYPE_INT_RGB);

		Graphics2D g2 = bufferedImage.createGraphics();
		g2.drawImage(image, null, null);

		ImageIO.write(bufferedImage, "jpeg", imageFile);
		// "jpeg" is the format of the image
		// imageFile is the file to be written to.



	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	// Helpers (can be refactored to public utility class)
	// ----------------------------------------
	private static void close(Closeable resource) {
		if (resource != null) {
			try {
				resource.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}
}