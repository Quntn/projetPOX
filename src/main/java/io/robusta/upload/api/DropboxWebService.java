package io.robusta.upload.api;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.files.ListFolderErrorException;

import io.robusta.upload.domain.FileDTO;

@Path("/filesdropbox")
public class DropboxWebService {
	@EJB
	private DropboxService dbs;

	@GET
	@Produces("application/json")
	public List<FileDTO> getFiles() throws ListFolderErrorException, DbxException, IOException {
		List<FileDTO> liste = dbs.findAll();
		return liste;

	}

}
