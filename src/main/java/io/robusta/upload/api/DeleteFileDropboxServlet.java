package io.robusta.upload.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/deletefiledropbox")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 10, // 10MB
		maxRequestSize = 1024 * 1024 * 50) // 50MB
public class DeleteFileDropboxServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try {
			FileDropbox fdb = new FileDropbox();
			String path = req.getParameter("filename");
			fdb.delete(path);
			resp.sendRedirect(req.getContextPath() + "/accueil");
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("message", "Erreur de fichier, réessayez");
			resp.sendRedirect(req.getContextPath() + "/accueil");
		}
		
	}

}
