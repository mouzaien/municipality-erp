
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import org.apache.logging.log4j.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.io.FilenameUtils;

import com.bkeryah.dao.CommonDao;
import com.bkeryah.dao.ICommonDao;
import com.bkeryah.entities.ArcAttach;
import com.bkeryah.hr.managedBeans.CommentBean;
import com.bkeryah.model.AttachmentModel;
import com.bkeryah.service.DataAccessService;
import com.bkeryah.service.IDataAccessService;

import utilities.Utils;

@WebServlet(name = "DWTUpload", urlPatterns = { "/DWTUpload" })
@MultipartConfig
public class DWTUpload extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static Logger LOGGER = LogManager.getLogger(DWTUpload.class.getCanonicalName());

	private CommentBean commentBean;

	private DataAccessService dataAccessService = new DataAccessService();

	private ICommonDao commonDao = new CommonDao();

	public ICommonDao getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(ICommonDao commonDao) {
		this.commonDao = commonDao;
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");

		final Part filePart = request.getPart("RemoteFile");
		final String fileName = getFileName(filePart);

		OutputStream out = null;
		InputStream filecontent = null;
		final PrintWriter writer = response.getWriter();

		try {
			// out = new FileOutputStream(new File(uploadPath + File.separator +
			// fileName));
			filecontent = filePart.getInputStream();
		

			try {

				AttachmentModel attachModel = new AttachmentModel();
				ArcAttach attach = new ArcAttach();
				List<ArcAttach> arcAttachs = new ArrayList<>();
				attachModel.setAttachRealName(filePart.getName());
				attachModel.setAttachSize(filePart.getSize());

				attachModel.setAttachStream(filePart.getInputStream());
				attachModel.setAttachExt(FilenameUtils.getExtension(filePart.getName()));
				String name = Utils.generateRandomName() + "." + "jpeg";

				Utils.saveAttachments(filePart.getInputStream(), name);

				// attach.setAttachOwner(Utils.findCurrentUser().getUserId());
				attach.setAttachDate(new Date());
				attach.setAttachSize((double) attachModel.getAttachSize());
				attach.setAttachType(1);
			
				attach.setAttachCategory("FILE");
				attach.setAttachName(name);
				CommentBean myBean = (CommentBean) request.getSession().getAttribute("commentBean");

				myBean.getAttachs().add(attach);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (FileNotFoundException fne) {
			fne.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
			if (filecontent != null) {
				filecontent.close();
			}

			if (writer != null) {
				writer.close();
			}
		}
	}

	private String getFileName(final Part part) {
		final String partHeader = part.getHeader("content-disposition");
		//LOGGER.log(Level.INFO, null,"Part Header = {0}", partHeader,null,null,null);
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T> T findBean(String beanName) {
//		FacesContext context = FacesContext.getCurrentInstance();
		return null;//(T) context.getApplication().evaluateExpressionGet(context, "#{" + beanName + "}", Object.class);
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

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();

	}

	public CommentBean getCommentBean() {
		return commentBean;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setCommentBean(CommentBean commentBean) {
		this.commentBean = commentBean;
	}

	// public void setDataAccessService(IDataAccessService dataAccessService) {
	// this.dataAccessService = dataAccessService;
	// }
}
