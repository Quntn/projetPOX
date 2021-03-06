package io.robusta.upload.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dropbox.core.DbxException;

import io.robusta.upload.domain.FileDTO;

@WebServlet("/renamefiledropbox")
public class RenameFileDropboxServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB
	private DropboxService dbs;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			
			String fileName = req.getParameter("filename");
			String newName = req.getParameter("newfilename");
			if (newName.contains(" ")||newName.isEmpty()) {
				resp.sendRedirect(req.getContextPath() + "/accueil");
				return;
			}
			
			String extension = req.getParameter("extension");
			
			List<FileDTO> files = dbs.findAll();
			List<String> names = new ArrayList<>();
			for (FileDTO file : files) {
				names.add(file.getNom());
			}
			
			newName = new Outils().createFileName(names, newName+extension);
			
			dbs.update(fileName, newName);
			
			resp.sendRedirect(req.getContextPath() + "/accueil");
			
		} catch (DbxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resp.sendRedirect(req.getContextPath() + "/accueil");
		}
		
		
	}
	
	

}
