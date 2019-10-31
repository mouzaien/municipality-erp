package utilities;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bkeryah.entities.ArcUsers;
import com.bkeryah.model.ArchUserModel;

/**
 * Servlet implementation class ShowImageServlrt
 */
@WebServlet("/ShowImageServlrt")
public class ShowImageServlrt extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ArcUsers user=(ArcUsers) request.getSession().getAttribute("user");
		response.setContentType("image/jpeg");
		ServletOutputStream out;
		out = response.getOutputStream();
		
		
		InputStream inputStream = new ByteArrayInputStream(user.getWrkProfile().getSign2());

		BufferedInputStream bin = new BufferedInputStream(inputStream);
		BufferedOutputStream bout = new BufferedOutputStream(out);
		int ch = 0;
		;
		while ((ch = bin.read()) != -1) {
			bout.write(ch);
		}

		bin.close();
		inputStream.close();
		bout.close();
		out.close();
	}

}
