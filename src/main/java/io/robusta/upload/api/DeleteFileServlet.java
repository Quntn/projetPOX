package io.robusta.upload.api;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/deletefile")
public class DeleteFileServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			String name = req.getParameter("filename");
			System.out.println(name);
			File fileToDelete = new File("vault/"+name);
			if (fileToDelete.exists()) System.out.println("Le fichier existe");
			if (fileToDelete.delete()) System.out.println("Le fichier est supprimé");
			resp.sendRedirect(req.getContextPath() + "/accueil");
			
	}

}
