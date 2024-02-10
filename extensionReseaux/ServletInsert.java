package web;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.net.Socket;
import transfer.*;
import java.util.ArrayList;

public class ServletInsert extends HttpServlet {

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
        try  {
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
            
            String path=request.getParameter("path");
            String toSend=request.getParameter("toSend");
            path=path.replace("*","\\");
            System.out.println("path :"+path);
            System.out.println(toSend);
            File fichier=new File(toSend);
            ArrayList<FileData> getDatas=FileData.send(fichier);

            Socket connection= new Socket(server,portServer);
            SendFile demande=new SendFile(path,getDatas);
            System.out.println("demande envoyer");
            ObjectOutputStream oos=new ObjectOutputStream(connection.getOutputStream());
            oos.writeObject(demande);
            oos.flush();
            System.out.println("objet");
            response.sendRedirect("dossier?path="+request.getParameter("path"));
        } 
        catch (Exception e) {
         e.printStackTrace();   
        }
    }
}