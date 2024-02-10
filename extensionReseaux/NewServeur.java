package service;
import transfer.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.util.ArrayList;

public class NewServeur {
    static File base;

    static File[] allDirectory(String path)
    {   
        File bas=new File(path);
        
        if(bas.listFiles().length>=1){
            return bas.listFiles();
        }
        else{
            File[] toReturn=new File[1];
            toReturn[0]=new File(path,"Empty");
            return toReturn;
        }
    }
    

    public static void main(String[] args) throws Exception{
        ServerSocket serv= new ServerSocket(12345);
        boolean coucou=true;
        while(coucou){
            Socket client= serv.accept();
            System.out.println("connexion etablie");
            ObjectInputStream ois=new ObjectInputStream(client.getInputStream());
            Object toGet=ois.readObject();
            if(toGet instanceof Demande){
                Demande demande=(Demande)toGet;
                if(toGet instanceof SendFile)
                    {
                        SendFile send=(SendFile)demande;
                        FileData.createArrayFile(send.getToSend(),send.getPath());
                    }
                 else if(demande.getDem().compareTo("dir")==0){
                    ObjectOutputStream oos=new ObjectOutputStream(client.getOutputStream());
                    oos.writeObject(allDirectory(demande.getPath()));
                    oos.close();
                    Thread.sleep(500);
                    }
                
                else if(demande.getDem().compareToIgnoreCase("SendFile")==0)
                    {
                        ObjectOutputStream oos=new ObjectOutputStream(client.getOutputStream());
                        File file= new File(demande.getPath());
                        oos.writeObject(FileData.send(file));
                        oos.close();
                        Thread.sleep(500);
                    }
            }
            
        }

        serv.close();
    }
}
