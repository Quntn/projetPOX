package io.robusta.upload.api;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFileChooser;

public class Test {


 
 

    public static void main(String[] args) throws IOException {
        // Exemple num�ro 1
        
        {
            //Servlet Choix Fichier
            JFileChooser dialogue = new JFileChooser();
             
            
            dialogue.showOpenDialog(null);
             
            
            System.out.println("Fichier choisi : " + dialogue.getSelectedFile());
            
            
            
            
            
            //Servlet Copie Fichier
            FileInputStream in = new FileInputStream(dialogue.getSelectedFile());
            FileOutputStream out = new FileOutputStream("C://code//workspace//pox3//src//main//webapp//vault//bis-image.jpg");
            int c;
            while ((c = in.read()) != -1) {
            out.write(c);
            }
            in.close();
            out.close();
        }
         
         /*
        // Exemple num�ro 2
        // Bo�te de s�lection de fichier � partir du r�pertoire courant
        File repertoireCourant = null;
        try {
            // obtention d'un objet File qui d�signe le r�pertoire courant. Le
            // "getCanonicalFile" n'est pas absolument n�cessaire mais permet
            // d'�viter les /Truc/./Chose/ ...
            repertoireCourant = new File(".").getCanonicalFile();
            System.out.println("R�pertoire courant : " + repertoireCourant);
        } catch(IOException e) {}
         
        // cr�ation de la bo�te de dialogue dans ce r�pertoire courant
        // (ou dans "home" s'il y a eu une erreur d'entr�e/sortie, auquel
        // cas repertoireCourant vaut null)
        JFileChooser dialogue = new JFileChooser(repertoireCourant);
         
        // affichage
        dialogue.showOpenDialog(null);
         */
        // r�cup�ration du fichier s�lectionn�
        
 
    }
}