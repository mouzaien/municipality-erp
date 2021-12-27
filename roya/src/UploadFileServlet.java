//
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//import java.util.logging.Logger;
//
//import javax.servlet.ServletContext;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.MultipartConfig;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.fileupload.disk.DiskFileItemFactory;
//import org.apache.commons.fileupload.servlet.ServletFileUpload;
//
//import com.bkeryah.hr.managedBeans.CommentBean;
//import com.bkeryah.service.IDataAccessService;
//
//import utilities.Utils;
//
//@WebServlet(name = "Upload", urlPatterns = { "/Upload" })
//@MultipartConfig
//public class UploadFileServlet extends HttpServlet {
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//	private final static Logger LOGGER = LogManager.getLogger(UploadFileServlet.class.getCanonicalName());
//
//	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		response.setContentType("text/html;charset=UTF-8");
//		// Create a factory for disk-based file items
//		DiskFileItemFactory factory = new DiskFileItemFactory();
//
//		// Configure a repository (to ensure a secure temp location is used)
//		ServletContext servletContext = this.getServletConfig().getServletContext();
//		File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
//		factory.setRepository(repository);
//		// Create a new file upload handler
//		ServletFileUpload upload = new ServletFileUpload(factory);
//		List<org.apache.commons.fileupload.FileItem> items = null;
//		// Parse the request
//		try {
//			items = upload.parseRequest(request);
//			Utils.saveAttachments(items.get(0).getInputStream(), "name.pdf");
//		} catch (Exception e2) {
//			e2.printStackTrace();
//		}
//
//	}
//
//	@Override
//	protected void doGet(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		processRequest(request, response);
//	}
//
//	@Override
//	protected void doPost(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		processRequest(request, response);
//	}
//
//	/**
//	 * Returns a short description of the servlet.
//	 *
//	 * @return a String containing servlet description
//	 */
//	@Override
//	public String getServletInfo() {
//		return "Short description";
//	}
//
//	@Override
//	public void destroy() {
//		// TODO Auto-generated method stub
//		super.destroy();
//
//	}
//}
