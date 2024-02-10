package transfer;
import java.io.Serializable;
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.File;

public class Demande implements Serializable{
    String dem;
    String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Demande(String dem, String path) {
        this.dem = dem;
        this.path = path;
    }

    // public void sendRessource(Serveur serv,Socket destinataire){
    //     ObjectOutputStream toWrite=new ObjectOutputStream(destinataire.getOutputStream());
    //     File fichier=new File(getPath());
    //     ArrayList<FileData> toSend=FileData.send(fichier,serv.getBase());
    //     toWrite.writeObject(toSend);
    //     return;
    // }

    public String getDem() {
        return dem;
    }

    public void setDem(String dem) {
        this.dem = dem;
    }
}