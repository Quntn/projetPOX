package io.robusta.upload.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.sharing.SharedLinkMetadata;;

public class DropboxUploader {
	// constantes privées
	private Logger log = Logger.getLogger(DropboxUploader.class.getSimpleName());
	private String accesToken = "rXd4qFWPnvAAAAAAAAAAEoS_V813cFWw5PGW_j8ZZH6962yWhls6-jqRV249u44y";

	DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").withUserLocale("en_US")
			.build();
	DbxClientV2 client = new DbxClientV2(config, accesToken);

	//System.out.println("Linked account: " + client.users().getCurrentAccount().getName());


	String uploadFile(String fileName, InputStream dataStream) throws IOException{
		try {
			
			FileMetadata uploadedFile = client.files().uploadBuilder("/" + fileName)
					.uploadAndFinish(dataStream);
			SharedLinkMetadata sharedUrl = client.sharing().createSharedLinkWithSettings("/" + fileName);
			System.out.println("Uploaded: " + uploadedFile.toString() + " URL " + sharedUrl.getUrl());
			log.log(Level.INFO, "Le fichier est uploadé");
		
		} catch (Exception e) {
			log.log(Level.SEVERE, "Une erreur s'est produite", e);
		} finally {
			dataStream.close();
		}
		
		return null;
	}
	
	
}
