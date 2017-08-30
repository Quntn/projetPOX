package io.robusta.upload.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import io.robusta.upload.domain.FileDTO;

@WebServlet("/uploadfiledatabase")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 10, // 10MB
		maxRequestSize = 1024 * 1024 * 50) // 50MB
public class UploadFileDatabaseServlet extends HttpServlet {


	private static final long serialVersionUID = 1L;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			FileDAO dao = new FileDAO();
			

			for (Part part : request.getParts()) {
				List<FileDTO> listFiles = dao.findAll();
				List<String> names = new ArrayList<>();
				for (FileDTO file : listFiles) {
					names.add(file.getNom());
				}
				
				String fileName = new Outils().extractFileName(part);

				fileName = new Outils().createFileName(names, fileName);

				InputStream inputStream = part.getInputStream();
				dao.add(fileName, inputStream);
				

			}
			response.sendRedirect(request.getContextPath() + "/accueil");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "Erreur de fichier, réessayez");
			response.sendRedirect(request.getContextPath() + "/accueil");
		}
	}


}