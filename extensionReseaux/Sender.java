package transfer;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Sender {
    
      
      public static void sendName(String nomDossier,Socket socket) throws IOException{
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(nomDossier);
        }
      
        public static void envoyerFichierParSocket(String cheminFichier, String adresseIP, int port) {
            try {
                // Créer un socket client
                
                Socket socket = new Socket(adresseIP, port);
                DataOutputStream typeTransfer = new DataOutputStream(socket.getOutputStream());
                typeTransfer.writeUTF("transferFile");
                // Obtenir le nom du fichier à partir du chemin
                File fichier =new File(cheminFichier);
                String nomFichier =fichier.getName();
                System.out.println(nomFichier);
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                dos.writeUTF(nomFichier);
    
                // Attendre la confirmation du serveur
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                String confirmation = dis.readUTF();
                System.out.println(confirmation);
    
                if (confirmation.equals("OK")) {
                    // Envoyer le contenu du fichier au serveur
                    FileInputStream fis = new FileInputStream(cheminFichier);
                    byte[] buffer = new byte[4096];
                    int bytesRead;
    
                    while ((bytesRead = fis.read(buffer)) != -1) {
                        dos.write(buffer, 0, bytesRead);
                    }
    
                    System.out.println("Le fichier " + nomFichier + " a été envoyé avec succès.");
                    fis.close();
                } else {
                    System.out.println("Le serveur n'a pas confirmé la réception du nom de fichier.");
                }
    
                // Fermer les flux et la socket
                
                dos.close();
                dis.close();
                socket.close();
    
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
        
        public static ArrayList<File> getFileToSend(String dossier) throws Exception{
            File toSend= new File(dossier);
            File [] files=toSend.listFiles();
            ArrayList<File> sendingList= new ArrayList<File>();
            for (File file : files) {
                sendingList.add(file);          
            }
            return sendingList;
        }
        
        

        public static void sendListFile(String addressIP,int port,String chemin_dossier) throws Exception
            {    
                
                Socket socket=new Socket(addressIP, port);
                ArrayList<File> toSend=getFileToSend(chemin_dossier);
                String fil=((File)toSend.get(0)).getParentFile().getName();
                sendName(fil,socket);
                ObjectOutputStream obj= new ObjectOutputStream(socket.getOutputStream());
                
                    obj.writeObject(toSend);                    
                
                obj.flush();
                obj.close();
                socket.close();
            }
    //    // public static void requestFolder(String folderRequest)
    //         {
    //             DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
    //             dos.writeUTF(folderRequest);
                

    //         }
        
       
}
