package transfer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.io.IOException;
import java.io.FileNotFoundException;

public class FileData implements Serializable{
    File path;
    String senderRoot;
    byte[] data;

    public FileData(File path, String senderRoot,byte[] data){
        setPath(path);
        setSenderRoot(senderRoot);
        setData(data);
    }

    public static FileData toFileData(String senderRoot,File fichier) throws IOException{
        FileInputStream in=new FileInputStream(fichier);
        byte[] takeDatas=new byte[(int)fichier.length()];
        in.read(takeDatas);

        return new FileData(fichier,senderRoot,takeDatas);
    }

    public static FileData sendFile(File fichier) throws IOException{
        return toFileData(fichier.getParent(),fichier);
    }

    public static void sendDirectory(ArrayList<FileData> array,String senderRoot,File directory) throws IOException{
        File[] liste=directory.listFiles();
        if(senderRoot==null){
            senderRoot=directory.getParent();
        }
        for(File fichier : liste){
            if(fichier.isDirectory()){
                sendDirectory(array,senderRoot,fichier);
            }
            else{
                array.add(toFileData(senderRoot,fichier));
            }
        }
    }

    public static ArrayList<FileData> send(File fichier) throws IOException{
        ArrayList<FileData> newaL=new ArrayList<FileData>();
        if(fichier.isDirectory()){
            sendDirectory(newaL,null,fichier);
        }
        else{
            newaL.add(toFileData(fichier.getParent(),fichier));
        }
        return newaL;
    }

    public void createFile(File receiverRoot) throws IOException,FileNotFoundException{
        File fichier=new File(getNewPath(receiverRoot.getPath()));
        fichier.getParentFile().mkdirs();
        fichier.createNewFile();
        FileOutputStream writer=new FileOutputStream(fichier);
        writer.write(data);
        writer.flush();
        writer.close();
    }

    public static void createArrayFile(ArrayList<FileData> datas,String receiverRoot) throws IOException{
        for(FileData fichier : datas){
            fichier.createFile(new File(receiverRoot));
        }
    }

    public String getNewPath(String receiverRoot){
        return path.getPath().replace(senderRoot,receiverRoot);
    }

    public File getPath() {
        return path;
    }

    public void setPath(File path) {
        this.path = path;
    }

    public String getSenderRoot() {
        return senderRoot;
    }

    public void setSenderRoot(String senderRoot) {
        this.senderRoot = senderRoot;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }


}
