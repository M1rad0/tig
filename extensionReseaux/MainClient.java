package service;
import transfer.*;
import java.util.Scanner;
import service.*;
import java.io.File;
import java.io.ObjectOutputStream;
import java.net.Socket;
public class MainClient {
 public static void main(String[] args) throws Exception {
        String adresseIPServeur = "172.20.109.116";  // Adresse IP du serveur
        int portServeur = 12345;  // Port du serveur
       //Sender.sendNamesFiles("C:\\Tomcatt\\apache-tomcat-9.0.80\\webapps\\github\\WEB-INF\\classes\\web\\", adresseIPServeur, portServeur);
        //Sender.sendName("nom", adresseIPServeur, portServeur);
        //Sender.envoyerFichierParSocket("C:\\Tomcatt\\apache-tomcat-9.0.80\\webapps\\github\\WEB-INF\\classes\\transfer\\Sender.class", adresseIPServeur, portServeur);
        // Sender.sendListFile(adresseIPServeur, portServeur,"E:\\CRUD\\ServsetFarany");
        Socket socket= new Socket(adresseIPServeur, portServeur);
        ObjectOutputStream obj= new ObjectOutputStream(socket.getOutputStream());
        File file= new File("C:\\Users\\rohyr\\Documents\\java\\classes\\transfer");
        obj.writeObject(FileData.send(file));
        obj.flush();
        socket.close();
        Thread.sleep(500);
    }  
}
