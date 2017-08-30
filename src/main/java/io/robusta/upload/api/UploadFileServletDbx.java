package io.robusta.upload.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.UploadErrorException;
import com.dropbox.core.v2.sharing.PathLinkMetadata;
import com.dropbox.core.v2.sharing.SharedLinkMetadata;

@WebServlet("/uploadfiledropbox")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 10, // 10MB
		maxRequestSize = 1024 * 1024 * 50) // 50MB
public class UploadFileServletDbx extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final int BUFFER_SIZE = 16177216;
	public static final String VAULT = "C://code//servers//wildfly-10.0.0.Final//bin//vault";
	private Connection connection;
	private String dbURL = "jdbc:mysql://localhost:3306/AppDB";
	private String dbUser = "root";
	private String dbPass = "root";
	

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		InputStream dataStream = null;
		// extraction du nom du fichier et de son stream de données
		try {
			File folder = new File("vault");

			if (!folder.exists())
				folder.mkdir();

			for (Part part : request.getParts()) {
				ArrayList<String> names = new ArrayList<String>(Arrays.asList(folder.list()));
				String fileName = extractFileName(part);

				while (names.contains(fileName)) {
					String[] parts = fileName.split("\\.");
					String part1 = parts[0];
					String part2 = parts[1];
					fileName = part1 + "-bis." + part2;
				}

				String filePath = folder.getAbsolutePath() + "/" + fileName;
				//part.write(filePath);

				DropboxUploader uploader = new DropboxUploader();

				dataStream = part.getInputStream();
			
				String uploadedUrl = uploader.uploadFile(fileName, dataStream);
				if (uploadedUrl != null) {
					System.out.println("pas de fichier uploadé");
				} else {
					System.out.println("le fichier est uploadé");
				}
			}

			response.sendRedirect(request.getContextPath() + "/accueil");

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "Erreur de fichier, réessayez");
			response.sendRedirect(request.getContextPath() + "/accueil");
		} finally {
			dataStream.close();
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