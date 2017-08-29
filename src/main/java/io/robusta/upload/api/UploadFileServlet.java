 package io.robusta.upload.api;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/uploadfile")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
maxFileSize = 1024 * 1024 * 10, // 10MB
maxRequestSize = 1024 * 1024 * 50) // 50MB
public class UploadFileServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String VAULT = "C://code//servers//wildfly-10.0.0.Final//bin//vault";

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			File folder = new File("vault");

			if (!folder.exists()) folder.mkdir();
			
			for (Part part : request.getParts()) {
				ArrayList<String> names = new ArrayList<String>(Arrays.asList(folder.list()));
				String fileName = new Outils().extractFileName(part);

				/*----------------------------------------------*/

				
				fileName = new Outils().createFileName(names, fileName);
				
				String filePath = folder.getAbsolutePath() + "/" + fileName ;
				part.write(filePath);
				/*----------------------------------------------*/

			} 

			response.sendRedirect(request.getContextPath() + "/accueil");

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "Erreur de fichier, rÃ©essayez");
			response.sendRedirect(request.getContextPath() + "/accueil");
		}
	}
	
	private String extractFileName(Part part) {

		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		for (String s : items) {
			if (s.trim().startsWith("filename")) {

				String clientFileName = s.substring(s.indexOf("=") + 2, s.length() - 1);
				clientFileName = clientFileName.replace("\\", "/");
				int i = clientFileName.lastIndexOf('/');

				return clientFileName.substring(i + 1);
			}
		}
		return null;
	}
	public List<FileDTO> findAll() {
        try {
            String sql = "SELECT * FROM `files`";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            List<FileDTO> f = new ArrayList<>();
            while (resultSet.next()) {
                String name = resultSet.getString("file_name");
                int id = resultSet.getInt("file_id");
                FileDTO file = new Outils().createFileDTOFromName(name);
                file.setId(id);
                f.add(file);
            }
            return f;
        } catch (SQLException e) {
            throw new RuntimeException("Impossible de réaliser l(es) opération(s)", e);
        }
    }

}
