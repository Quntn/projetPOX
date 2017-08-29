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
					FileDTO fileDTO = new FileDTO();
					String name = listOfFiles[i].getName();
					String[] splitter = name.split("\\.");
					String extension = "";
					if (splitter.length>1) extension="."+splitter[splitter.length-1];
					String placeholder = name.substring (0, ((name.length() - extension.length())));
					fileDTO.setNom(name);
					fileDTO.setExtension(extension);
					fileDTO.setPlaceholder(placeholder);
					listOfFilesName.add(fileDTO);
				}
			}
			
		}
		
		return listOfFilesName;
	}
	
	

	
	
	
}
