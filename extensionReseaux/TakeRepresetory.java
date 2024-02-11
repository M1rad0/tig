package web;

//NOTES : ASORY LE INIT PARAMETER HOE BASE
//D RAH BASE NO ANGATAHINY D LE PATHNLE DEMANDE OVAITSIKA HOE "Base" d any am serveur voa traiteny

import java.io.*;
import java.net.Socket;

import javax.servlet.*;
import javax.servlet.http.*;
import transfer.*;

public class TakeRepresetory extends HttpServlet {
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
        response.setContentType("text/html;charset=UTF-8");
        Socket serveur=null;
        ObjectOutputStream oos=null;
        ObjectInputStream ois=null;

        try  {
            PrintWriter out=response.getWriter();
            String server=this.getInitParameter("serveur");
            String username=request.getParameter("username");
            int portServer=0;
            int testPort=getPort();

            if (testPort==0) {
                out.println("port introuvable");
            }
            else{
                portServer=testPort;
            }

            String path= request.getParameter("path");
            if(path==null){
                //Io le izy
                path="base";
            }
            else{
                path=path.replace("*","\\");
            }
            String dem="dir";
            Demande demande= new Demande(dem,path);
            serveur=new Socket(server,portServer);

            oos=new ObjectOutputStream(serveur.getOutputStream());
            oos.writeObject(demande);

            ois=new ObjectInputStream(serveur.getInputStream());
            
            InfoFichier[] reception=(InfoFichier[])ois.readObject();

            request.setAttribute("files",reception);

            request.getRequestDispatcher("L_bouquet.jsp").forward(request,response);
            //Sender.sendName(username);
                
        }   
        catch (Exception e) {
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