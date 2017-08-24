package io.robusta.upload.api;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

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

	private Connection conn;

    public UploadFileServlet() {
	
        conn = DbUtil.getConnection();
    }

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		

		try {

			
			File folder = new File("vault");

			if (!folder.exists())
				folder.mkdir();

			for (Part part : request.getParts()) {
				ArrayList<String> names = new ArrayList<String>(Arrays.asList(folder.list()));
				String fileName = extractFileName(part);

			
			

				/*----------------------------------------------*/

				boolean rep = true;

				for (int i = 0; i < names.size(); i++) {
					int j = i++;
					if (fileName == names.get(j)) {
						rep = false;
					}
				}
				if (rep) {
					String[] parts = fileName.split("\\.");
					String part1 = parts[0];
					String part2 = parts[1];
					String filePath = folder.getAbsolutePath() + "/" + part1 + "-bis." + part2;
					part.write(filePath);
				}
				 try {
			            // constructs SQL statement
			            String sql = "INSERT INTO contacts (file_name) values (?)";
			            PreparedStatement statement = conn.prepareStatement(sql);
			            statement.setString(1, fileName);
			           

			            

			            // sends the statement to the database server
			            int row = statement.executeUpdate();
			           
			        } catch (SQLException ex) {
			          
			            ex.printStackTrace();
			        }

				/*----------------------------------------------*/

			}

			response.sendRedirect(request.getContextPath() + "/accueil");

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "Erreur de fichier, réessayez");
			response.sendRedirect(request.getContextPath() + "/accueil");
		}
		
		
		
//		finally {
//			if (conn != null) {
//				// closes the database connection
//				try {
//					conn.close();
//				} catch (SQLException ex) {
//					ex.printStackTrace();
//				}
//			}
//		}
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

}
