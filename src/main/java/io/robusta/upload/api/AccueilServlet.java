package io.robusta.upload.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/accueil")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
maxFileSize = 1024 * 1024 * 10, // 10MB
maxRequestSize = 1024 * 1024 * 50) // 50MB
public class AccueilServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//		File folder = new File("vault");
//		File[] listOfFiles = folder.listFiles();
//		List<String> listOfFilesName = new ArrayList<>();
//		if (listOfFiles.length == 0) {
//			System.out.println("pas de fichier");
//		} else {
//			for (int i = 0; i < listOfFiles.length; i++) {
//				if (listOfFiles[i].isFile()) {
//					listOfFilesName.add(listOfFiles[i].getName());
//				}
//			}
//			
//		}
//		req.setAttribute("filenames", listOfFilesName);
//		req.getRequestDispatcher("/WEB-INF/accueil.jsp").forward(req, resp);
		resp.sendRedirect("http://10.31.1.29:4200/");
	}

}
