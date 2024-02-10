package web;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import transfer.*;

public class InscriptionClient extends HttpServlet {

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
            //Sender.sendName(username);
            
         
} 
        catch (Exception e) {
            
        }
    }
    


}