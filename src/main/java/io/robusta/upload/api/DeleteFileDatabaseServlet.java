package io.robusta.upload.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/deletefiledatabase")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 10, // 10MB
		maxRequestSize = 1024 * 1024 * 50) // 50MB
public class DeleteFileDatabaseServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
			FileDAO dao = new FileDAO();
			String id = req.getParameter("id");
			dao.delete(id);
			resp.sendRedirect(req.getContextPath() + "/accueil");
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("message", "Erreur de fichier, réessayez");
			resp.sendRedirect(req.getContextPath() + "/accueil");
		}

	}

}
