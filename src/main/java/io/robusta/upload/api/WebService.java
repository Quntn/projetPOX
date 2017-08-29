package io.robusta.upload.api;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import io.robusta.upload.domain.FileDTO;

@Path("/files")
public class WebService {

	
	
	@GET
	@Produces("application/json")
	public List<FileDTO> getFiles() {
		
		File folder = new File("vault");
		if (!folder.exists()) folder.mkdir();
		File[] listOfFiles = folder.listFiles();
		List<FileDTO> listOfFilesName = new ArrayList<>();
		if (listOfFiles.length == 0) {
			System.out.println("pas de fichier");
		} else {
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					FileDTO fileDTO = new Outils().createFileDTOFromName(listOfFiles[i].getName());
					listOfFilesName.add(fileDTO);
				}
			}
			
		}
		
		return listOfFilesName;
	}
	
	

	
	
	
}
