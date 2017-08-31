package io.robusta.upload.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.DeleteErrorException;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.GetTemporaryLinkResult;
import com.dropbox.core.v2.files.ListFolderErrorException;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.files.RelocationErrorException;
import com.dropbox.core.v2.files.UploadErrorException;
import com.dropbox.core.v2.sharing.SharedLinkMetadata;

import io.robusta.upload.domain.FileDTO;

public class FileDropbox {

	private static String DROPBOX_ACCESS_TOKEN = "rXd4qFWPnvAAAAAAAAAAEoS_V813cFWw5PGW_j8ZZH6962yWhls6-jqRV249u44y";

	private DbxRequestConfig config;

	private DbxClientV2 client;

	public FileDropbox() throws IOException, DbxApiException, DbxException {

		this.config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").withUserLocale("en_US").build();
		this.client = new DbxClientV2(config, DROPBOX_ACCESS_TOKEN);
	}

	public List<FileDTO> findAll() throws ListFolderErrorException, DbxException {

		String folderPath = "";
		ListFolderResult listFolderResult = client.files().listFolder(folderPath);
		List<Metadata> metadatas = listFolderResult.getEntries();
		List<FileDTO> listFiles = new ArrayList<>();
		for (Metadata metadata : metadatas) {
			String name = metadata.getName();

			GetTemporaryLinkResult tempLinkResult;
			tempLinkResult = client.files().getTemporaryLink(folderPath + "/" + name);
			FileMetadata fileMetadata = tempLinkResult.getMetadata();
			String id = fileMetadata.getId();

			FileDTO file = new Outils().createFileDTOFromName(name);
			file.setId(id);
			listFiles.add(file);
		}
		return listFiles;

	}
	
	public void add(String fileName, InputStream inputStream) throws UploadErrorException, DbxException, IOException {
		FileMetadata uploadedFile = client.files().uploadBuilder("/" + fileName)
                .uploadAndFinish(inputStream);
        
        SharedLinkMetadata sharedUrl = client.sharing().createSharedLinkWithSettings("/" + fileName);
        
        System.out.println("Uploaded: " + uploadedFile.toString() + " URL " + sharedUrl.getUrl());
	}
	
	public void delete(String path) throws DeleteErrorException, DbxException {
		client.files().delete(path);

	}
	
	public void update(String previousName, String newName) throws RelocationErrorException, DbxException {

		client.files().move("/"+previousName, "/"+newName);

	}

}
