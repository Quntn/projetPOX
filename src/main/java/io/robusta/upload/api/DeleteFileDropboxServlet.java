package io.robusta.upload.api;

import java.io.IOException;

import javax.ejb.EJB;
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
	
	@EJB
	private DropboxService dbs;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try {
			String path = req.getParameter("filename");
			dbs.delete("/"+path);
			resp.sendRedirect(req.getContextPath() + "/accueil");
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("message", "Erreur de fichier, réessayez");
			resp.sendRedirect(req.getContextPath() + "/accueil");
		}
		
	}

}
