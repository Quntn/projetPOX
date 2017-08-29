package io.robusta.upload.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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

@WebServlet("/uploadfilecloud")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 10, // 10MB
		maxRequestSize = 1024 * 1024 * 50) // 50MB
public class uploadCloudApi extends HttpServlet {

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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

				DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").withUserLocale("en_US")
						.build();
				DbxClientV2 client = new DbxClientV2(config,
						"rXd4qFWPnvAAAAAAAAAAEoS_V813cFWw5PGW_j8ZZH6962yWhls6-jqRV249u44y");

				System.out.println("Linked account: " + client.users().getCurrentAccount().getName());

				InputStream inputStream = part.getInputStream();

				try {

					FileMetadata uploadedFile = client.files().uploadBuilder("/" + fileName)
							.uploadAndFinish(inputStream);
					SharedLinkMetadata sharedUrl = client.sharing().createSharedLinkWithSettings("/" + fileName);
					System.out.println("Uploaded: " + uploadedFile.toString() + " URL " + sharedUrl.getUrl());

				} catch (UploadErrorException ex) {
					System.err.println("Error uploading to Dropbox: " + ex.getMessage());
					System.exit(1);
				} catch (DbxException ex) {
					System.err.println("Error uploading to Dropbox: " + ex.getMessage());
					System.exit(1);
				} catch (IOException ex) {
					System.err.println("Error reading from file \"" + inputStream + "\": " + ex.getMessage());
					System.exit(1);

				} finally {
					inputStream.close();
				}

			}

			response.sendRedirect(request.getContextPath() + "/accueil");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "Erreur de fichier, r�essayez");
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
