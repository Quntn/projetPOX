package io.robusta.upload.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.robusta.upload.domain.FileDTO;

@WebServlet("/renamefiledatabase")
public class RenameFileDatabaseServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try {
			FileDAO dao = new FileDAO();
			
			String id = req.getParameter("id");
			String newName = req.getParameter("newfilename");
			String fileName = req.getParameter("filename");
			String extension = req.getParameter("extension");
			if (newName.contains(" ")||newName.isEmpty()||fileName.equals(newName+extension)) {
				resp.sendRedirect(req.getContextPath() + "/accueil");
				return;
			}
			
			List<FileDTO> listFiles = dao.findAll();
			List<String> names = new ArrayList<>();
			for (FileDTO file : listFiles) {
				names.add(file.getNom());
			}
			
			newName = new Outils().createFileName(names, newName+extension);
			
			dao.update(id, newName);

			
			resp.sendRedirect(req.getContextPath() + "/accueil");
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("message", "Erreur de fichier, réessayez");
			resp.sendRedirect(req.getContextPath() + "/accueil");
		}
		
		
		
		
	}
	
}
