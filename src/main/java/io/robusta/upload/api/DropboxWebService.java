package io.robusta.upload.api;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.files.ListFolderErrorException;

import io.robusta.upload.domain.FileDTO;

@Path("/filesdropbox")
public class DropboxWebService {
	
	@GET
	@Produces("application/json")
	public List<FileDTO> getFiles() throws ListFolderErrorException, DbxException, IOException {
		FileDropbox fdb = new FileDropbox();
		List<FileDTO> liste = fdb.findAll();
		return liste;
		
	}

}
