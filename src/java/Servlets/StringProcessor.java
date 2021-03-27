
package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class StringProcessor extends HttpServlet 
{
   
   
    private static String showResult="";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
     
            out.println("<!DOCTYPE html>");
            out.println("<html>\n" +
                        "    <head>\n" +
                        "       <style>\n" +
                        "           table, th, td  {\n" +
                        "             border: 1px solid black;\n" +
                        "             table-layout: fixed; width: 50%"+                       
                        "                           }\n" +
                        "       </style>"+
                        "        <title>LAB 4</title>\n" +
                        "        <meta charset=\"UTF-8\">\n" +
                        "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                        "    </head>\n" +
                        "    <body>\n" +
                        "         <br>\n" +
                        "           <br>\n" +
                        "        <center>   \n" +
                        "            <div>Enter symbols</div>       \n" +
                        "            <form action=\"StringProcessor\" method=\"GET\">               \n" +
                        "                <p>\n" +
                        "                    Input:&nbsp\n" +
                        "                    <input type=\"text\" name=\"string\" size=\"80\"/>\n" +
                        "                    <input type=\"submit\" value=\"Start\"/>\n" +
                        "                </p>           \n" +
                        "            </form>\n" +
                        "                <table>" +
                        "                   <tr> "+
                        "                     <th>Result</th>"+
                        "                   </tr>"+
                        "                   <tr>"+
                        "                     <td style=\"word-wrap: break-word\">"+StringProcessor.showResult+"</td>"+
                        "                   </tr>"+
                        "                 </table>"+
                        "        </center>\n" +
                        "    </body>\n" +
                        "</html>");

        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        
        HttpSession session = request.getSession();

        boolean tryAgainBool=false;
        boolean showFigures=false;
        String letters="";
        double figures=0;
        
           
        if (session.isNew())   
        {
            letters="";
            figures=0;
        }
        else 
        {
            Object obj = session.getServletContext().getAttribute("count");
                if(obj != null && obj instanceof Double)
                {
                    figures = (Double)obj;
                }  
                
            Object objStr = session.getServletContext().getAttribute("words");
                if(objStr != null && objStr instanceof String)
                {
                    letters = (String)objStr;
                } 
        } 
        
        String input=request.getParameter("string").trim();
        
            if(input.length()>80) input=input.substring(0,79);

                try
                {                   
                    figures+=Double.parseDouble(input);
                    session.getServletContext().setAttribute("count", figures);
                    showFigures=true;
                }

                catch(NumberFormatException e)
                {
                    
                    if(input!=null&&input.length()>0) 
                    {
                        letters+=input+"<br>";
                        session.getServletContext().setAttribute("words", letters);
                    }
                    else tryAgainBool=true;
                }
        
        showResult = showFigures?(""+figures):tryAgainBool?("Please, enter something"):letters;
        processRequest(request, response);
        tryAgainBool=false;
        showFigures=false; 
        letters="";
    }
}