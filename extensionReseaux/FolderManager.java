package transfer;
import java.io.*;
import java.nio.file.*;

public class FolderManager {
    public static void creatingfolder(String parent,String username) {
        // Spécifiez le chemin absolu ou relatif du répertoire parent
        String parentDirectory = parent;

        // Spécifiez le nom du dossier à créer
        String folderName = username;

        // Utilisez Path pour représenter le chemin du dossier à créer
        Path folderPath = Paths.get(parentDirectory, folderName);
        

        try {
            // Créez le dossier
            Files.createDirectory(folderPath);
            System.out.println("Le dossier a été créé avec succès.");
        } catch (FileAlreadyExistsException e) {
            System.err.println("Le dossier existe déjà.");
        } catch (Exception e) {
            System.err.println("Erreur lors de la création du dossier : " + e.getMessage());
        }
    }

}
