package io.robusta.upload.api;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/renamefile")
public class RenameFileServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("filename");
		String newName = req.getParameter("newfilename");
		String extension = req.getParameter("extension");
		File fileToRename = new File("vault/"+name);
		File fileRenamed = new File("vault/"+newName+extension);
		System.out.println(name);
		System.out.println(newName);
		System.out.println(extension);
		
		if (fileToRename.exists()) {
			System.out.println("Le fichier � renommer existe");
			if (fileRenamed.exists()) {
				System.out.println("Le nouveau nom est d�j� pris") ;
			} else {
				System.out.println("Le nouveau nom est disponible");
				if (fileToRename.renameTo(fileRenamed)) System.out.println("Fichier renomm� avec succ�s");
			}
		} else {
			System.out.println("Le fichier � renommer n'existe pas");
		}
		resp.sendRedirect(req.getContextPath() + "/accueil");
		
		
	}
	
}
