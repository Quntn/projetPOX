package io.robusta.upload.api;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import io.robusta.upload.domain.FileDTO;

@Path("/filesdatabase")
public class DatabaseWebService {
	
	@GET
	@Produces("application/json")
	public List<FileDTO> getFiles() {
		FileDAO dao = new FileDAO();
		List<FileDTO> liste = dao.findAll();
		return liste;
		
	}
	
	
	

}
