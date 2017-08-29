 package io.robusta.upload.api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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

	private Connection connection;
	private static final int BUFFER_SIZE = 16177216;
	private static final long serialVersionUID = 1L;
	public static final String VAULT = "C:/code/wildfly-10.0.0.Final/bin/vault";
	// DB conn
	String DBUrl = "jdbc:mysql://localhost:3306/AppDB";
	String DBUser = "root";
	String DBPassword = "root";

	private Connection getConnection() throws SQLException {

		Connection connection = DriverManager.getConnection(DBUrl, DBUser, DBPassword);
		return connection;

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
				System.out.println("file name : " + fileName);

				while (names.contains(fileName)) {
					String[] parts = fileName.split("\\.");
					String part1 = parts[0];
					String part2 = parts[1];
					fileName = part1 + "-bis." + part2;

				}

				String filePath = folder.getAbsolutePath() + "/" + fileName;
				System.out.println("copie dans vault");

				InputStream inputStream = part.getInputStream();
				DriverManager.registerDriver(new com.mysql.jdbc.Driver());
				System.out.println("connection BD");
				connection = DriverManager.getConnection(DBUrl, DBUser, DBPassword);

				String sql = "INSERT INTO files (file_name,photo) values (?,?)";
				PreparedStatement statement = (PreparedStatement) connection.prepareStatement(sql);
				statement.setString(1, fileName);
				statement.setBlob(2, inputStream);
				statement.executeUpdate();

			}
			response.sendRedirect(request.getContextPath() + "/accueil");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "Erreur de fichier, réessayez");
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

}