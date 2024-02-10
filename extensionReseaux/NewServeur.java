package service;
import transfer.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.util.ArrayList;

public class NewServeur {
    static int port;
    static File base;

    public Serveur(int port, File base){
        setPort(port);
        setBase(base);
    }

    public static int confPort(File fichierConf) throws Exception{
        BufferedReader readFile=null;
        try{
            readFile=Files.newBufferedReader(fichierConf);
            String analyse=null;
            while(analyse=readFile.readLine()!=null){
                String[] splitted=analyse.split("=");
                if(splitted[0].compareTo("port")==0){
                    return Integer.parseInt(spitted[1]);
                }
            }
            throw new Exception("Port introuvable dans le fichier de configuration");
        }catch(IOException | SecurityException e){
            e.printStackTrace();
        }catch(Exception e){
            throw e;
        }
        finally{
            if(readFile!=null){
                readFile.close();
            }
        }
    }

    public static int confBase(File fichierConf) throws Exception{
        BufferedReader readFile=null;
        try{
            readFile=Files.newBufferedReader(fichierConf);
            String analyse=null;
            while(analyse=readFile.readLine()!=null){
                String[] splitted=analyse.split("=");
                if(splitted[0].compareTo("base")==0){
                    return new File(spitted[1]);
                }
            }
            throw new Exception("Base introuvable dans le fichier de configuration");
        }catch(IOException | SecurityException e){
            e.printStackTrace();
        }catch(Exception e){
            throw e;
        }
        finally{
            if(readFile!=null){
                readFile.close();
            }
        }
    }

    static File[] allDirectory(String path)
    {   
        File bas=new File(path);
        
        if(bas.listFiles().length>=1){
            File[] initial= bas.listFiles();
            for(int i=0;i<initial.length;i++){
                String iPath=initial[i].getAbsolutePath();
                iPath=iPath.replace(NewServeur.base,"");

                initial[i]=new File(iPath);
            }
        }
        else{
            File[] toReturn=new File[1];
            toReturn[0]=new File("Empty");
            return toReturn;
        }
    }
    
    public static void main(String[] args) throws Exception{
        File conf=new File("C:\\confGIT.txt");
        
        File base=confBase(conf);
        int port=confPort(conf);

        NewServeur.base=base;
        NewServeur.port=port;

        ObjectInputStream ois=null;
        ObjectOutputStream oos=null;
        Socket client=null;
        ServerSocket serv= null;

        try {
            serv= new ServerSocket(12345);
            while(true){
                client= serv.accept();
                System.out.println("connexion etablie");
                ois=new ObjectInputStream(client.getInputStream());
                Object toGet=ois.readObject();
                if(toGet instanceof Demande){
                    Demande demande=(Demande)toGet;
                    if(toGet instanceof SendFile){
                        SendFile send=(SendFile)demande;
                        FileData.createArrayFile(send.getToSend(),send.getPath());
                    }
    
                    else if(demande.getDem().compareTo("dir")==0){
                        oos=new ObjectOutputStream(client.getOutputStream());
                        if(demande.getPath().compareTo("base")==0){
                            demande.setPath(base);
                        }

                        //CONSIDERER LE PATH DANS DEM COMME UN CHEMIN RELATIF EN PARTANT DE LA BASE
                        else if(!demande.getPath().contains(NewServeur.base)){
                            String truePath= NewServeur.base+demande.getPath();
                            demande.setPath();
                        }

                        oos.writeObject(allDirectory(demande.getPath()));
                        oos.close();
                        Thread.sleep(500);
                    }
                    
                    else if(demande.getDem().compareToIgnoreCase("SendFile")==0){
                        oos=new ObjectOutputStream(client.getOutputStream());
                        File file= new File(demande.getPath());
                        oos.writeObject(FileData.send(file));
                        oos.close();
                        Thread.sleep(500);
                    }
                }
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(client!=null){
                client.close();
            }
            if(serv!=null){
                serv.close();
            }
            if(ois!=null){
                ois.close();
            }
            if(oos!=null){
                oos.close();
            }
        }
    }
}
