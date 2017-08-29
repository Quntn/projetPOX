package io.robusta.upload.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuthNoRedirect;
import com.dropbox.core.DbxWriteMode;

//@WebServlet("/uploadfile")
public class UploadCloudApi extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			for (Part part : request.getParts()) {
		
				String fileName = extractFileName(part);

				DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").withUserLocale("en_US")
						.build();
				DbxClientV2 client = new DbxClientV2(config,
						"rXd4qFWPnvAAAAAAAAAAEoS_V813cFWw5PGW_j8ZZH6962yWhls6-jqRV249u44y");

				System.out.println("Linked account: " + client.users().getCurrentAccount().getName());
				InputStream inputStream = part.getInputStream();

				ListFolderResult entry = client.files().listFolder("");
				List<Metadata> metadatas = entry.getEntries();
				for (Metadata metadata : metadatas) {
					String nameData = metadata.getName();
					while (nameData.equals(fileName)) {
						String[] parts = fileName.split("\\.");
						String part1 = parts[0];
						String part2 = parts[1];
						fileName = part1 + "-bis." + part2;
					}
				}

				try {

					FileMetadata uploadedFile = client.files().uploadBuilder("/" + fileName)
							.uploadAndFinish(inputStream);
					SharedLinkMetadata sharedUrl = client.sharing().createSharedLinkWithSettings("/" + fileName);
					System.out.println("Uploaded: " + uploadedFile.toString() + " URL " + sharedUrl.getUrl());

				} catch (UploadErrorException ex) {
					System.err.println("Error uploading to Dropbox: " + ex.getMessage());

				} catch (DbxException ex) {
					System.err.println("Error uploading to Dropbox: " + ex.getMessage());

				} catch (IOException ex) {
					System.err.println("Error reading from file \"" + inputStream + "\": " + ex.getMessage());

				} finally {
					inputStream.close();
				}

			}

			response.sendRedirect(request.getContextPath() + "/accueil");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "Erreur de fichier, reessayez");
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
