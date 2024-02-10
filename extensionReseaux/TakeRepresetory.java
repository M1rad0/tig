package web;

import java.io.*;
import java.net.Socket;

import javax.servlet.*;
import javax.servlet.http.*;
import transfer.*;

public class TakeRepresetory extends HttpServlet {

    private int port;
    private String repertoireDeBase;
    public void init() throws ServletException {

        String initial = this.getInitParameter("port");
        try {
        port = Integer.parseInt(initial);
    } catch(NumberFormatException e) {
        port = 0;
        }

        repertoireDeBase=this.getInitParameter("base");
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

            String path= request.getParameter("path");
            if(path==null){
                path=repertoireDeBase;
            }
            else{
                
                path=path.replace("*","\\");
                System.out.println("path :"+path);     
            }
            String dem="dir";
            Demande demande= new Demande(dem,path);
            Socket serveur=new Socket(server,portServer);

            ObjectOutputStream oos=new ObjectOutputStream(serveur.getOutputStream());
            oos.writeObject(demande);

            ObjectInputStream ois=new ObjectInputStream(serveur.getInputStream());
            
            File[] reception=(File[])ois.readObject();

            request.setAttribute("files",reception);

            request.getRequestDispatcher("L_bouquet.jsp").forward(request,response);
            //Sender.sendName(username);
                
        }   
        catch (Exception e) {
            
            e.printStackTrace();
        }
    }
    


}