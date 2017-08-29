package io.robusta.upload.api;

import java.util.List;

import javax.servlet.http.Part;
public class Outils {
    public FileDTO createFileDTOFromName(String name) {
        FileDTO file = new FileDTO();
        
        String[] splitter = name.split("\\.");
        String extension = "";
        if (splitter.length>1) extension="."+splitter[splitter.length-1];
        String placeholder = name.substring (0, ((name.length() - extension.length())));
        file.setNom(name);
        file.setExtension(extension);
        file.setPlaceholder(placeholder);
        return file;
    }
    
    public String createFileName(List<String> names, String name) {
        while (names.contains(name)) {
            String[] parts = name.split("\\.");
            String part1 = parts[0];
            String part2 = parts[1];
            name = part1 + "-bis." + part2;
        }
        return name;
    }
    
    public String extractFileName(Part part) {
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