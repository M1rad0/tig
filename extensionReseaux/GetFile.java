package web;

import java.io.*;
import java.net.Socket;

import javax.servlet.*;
import javax.servlet.http.*;
import transfer.*;
import java.util.ArrayList;
public class GetFile extends HttpServlet {
    

        private int port;
        public void init() throws ServletException {
    
            String initial = this.getInitParameter("port");
            try {
            port = Integer.parseInt(initial);
        } catch(NumberFormatException e) {
            port = 0;
            }
        }
    
        public int getPort() {
            return port;
        }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        PrintWriter out=response.getWriter();
        String server=this.getInitParameter("serveur");
        String username=request.getParameter("username");
        int portServer=0;
        out.println(server);
        int testPort=getPort();
        if (testPort==0) {
            out.println("port introuvable");
        }
        else{
            portServer=testPort;
        }
        Socket serveur=null;
        ObjectOutputStream oos=null;
        ObjectInputStream ois=null;
        try {
            String path= request.getParameter("path");
            
            path=path.replace("*","\\");
            System.out.println("path :"+path);     

            String dem="SendFile";
            Demande demande= new Demande(dem,path);
            serveur=new Socket(server,portServer);

            oos=new ObjectOutputStream(serveur.getOutputStream());
            oos.writeObject(demande);
            System.out.println("demande envoyer");
            ois=new ObjectInputStream(serveur.getInputStream());
            Object arrayList=ois.readObject();
            FileData.createArrayFile((ArrayList<FileData>)arrayList,"C:\\ReceiveGit");
            System.out.println("File cree");

            response.sendRedirect("dossier?path="+request.getParameter("return"));
        }catch(Exception e){
            e.printStackTrace();
        }
        finally{
            if(serveur!=null){
                serveur.close();
            }
            if(oos!=null){
                oos.close();
            }
            if(ois!=null){
                ois.close();
            }
        }
        
    }
}