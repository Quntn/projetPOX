package io.robusta.upload.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuthNoRedirect;
import com.dropbox.core.DbxWriteMode;

@WebServlet("/uploadfile")
public class uploadCloudApi extends HttpServlet {

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

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		final String APP_KEY = "9xgjdh5771cjsvv";
		final String APP_SECRET = "5dsbbx64henfnfw";
		try {

			for (Part part : request.getParts()) {

				String fileName = extractFileName(part);
				System.out.println("file name : " + fileName);

				

				DbxRequestConfig config = new DbxRequestConfig("JavaTutorial/1.0", Locale.getDefault().toString());

				

				DbxClient client = new DbxClient(config, "XtwFq1pT03AAAAAAAAAAC1oCLItBvynAESvqCHJmL7A");

				String filePath = "C:/Users/AELION/Desktop" + "/" + fileName;

				File inputFile = new File(filePath);
				FileInputStream inputStream = new FileInputStream(inputFile);
				try {
					DbxEntry.File uploadedFile = client.uploadFile(fileName, DbxWriteMode.add(), inputFile.length(),
							inputStream);
				} finally {
					inputStream.close();
				}
			}

			response.sendRedirect(request.getContextPath() + "/accueil");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "Erreur de fichier, réessayez");
			response.sendRedirect(request.getContextPath() + "/accueil");
		}
	}

}
