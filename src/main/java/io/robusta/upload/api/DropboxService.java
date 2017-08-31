package io.robusta.upload.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.ejb.Singleton;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.DbxException;
import com.dropbox.core.v2.files.DeleteErrorException;
import com.dropbox.core.v2.files.ListFolderErrorException;
import com.dropbox.core.v2.files.RelocationErrorException;
import com.dropbox.core.v2.files.UploadErrorException;

import io.robusta.upload.domain.FileDTO;

@Singleton
public class DropboxService {

	FileDropbox fdb = new FileDropbox();
	List<FileDTO> files = null;

	public DropboxService() throws IOException, DbxApiException, DbxException {
	}

	public List<FileDTO> findAll() throws ListFolderErrorException, DbxException {
		if (this.files == null) {
			System.out.println("pas cool");
			this.files = fdb.findAll();
		} else
			System.out.println("coucouuuuuu");
		return this.files;
	}

	public void add(String fileName, InputStream inputStream) throws UploadErrorException, DbxException, IOException {
		fdb.add(fileName, inputStream);
		this.files = fdb.findAll();
	}
	
	public void delete(String path) throws DeleteErrorException, DbxException {
		fdb.delete(path);
		this.files = fdb.findAll();

	}
	
	public void update(String previousName, String newName) throws RelocationErrorException, DbxException {

		fdb.update(previousName, newName);
		this.files = fdb.findAll();

	}
}
