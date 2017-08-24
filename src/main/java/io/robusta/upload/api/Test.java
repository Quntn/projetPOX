package io.robusta.upload.api;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFileChooser;

public class Test {


 
 

    public static void main(String[] args) throws IOException {
        // Exemple numéro 1
        
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
        // Exemple numéro 2
        // Boîte de sélection de fichier à partir du répertoire courant
        File repertoireCourant = null;
        try {
            // obtention d'un objet File qui désigne le répertoire courant. Le
            // "getCanonicalFile" n'est pas absolument nécessaire mais permet
            // d'éviter les /Truc/./Chose/ ...
            repertoireCourant = new File(".").getCanonicalFile();
            System.out.println("Répertoire courant : " + repertoireCourant);
        } catch(IOException e) {}
         
        // création de la boîte de dialogue dans ce répertoire courant
        // (ou dans "home" s'il y a eu une erreur d'entrée/sortie, auquel
        // cas repertoireCourant vaut null)
        JFileChooser dialogue = new JFileChooser(repertoireCourant);
         
        // affichage
        dialogue.showOpenDialog(null);
         */
        // récupération du fichier sélectionné
        
 
    }
}