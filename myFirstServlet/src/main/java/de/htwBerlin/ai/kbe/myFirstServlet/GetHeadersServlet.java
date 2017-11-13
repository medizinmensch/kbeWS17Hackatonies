package main.java.de.htwBerlin.ai.kbe.myFirstServlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

/*
export JPDA_ADDRESS=9999
export JPDA_TRANSPORT=dt_socket
bin/catalina.sh jpda start
 */
@WebServlet(
        name = "GetHeadersServlet",
        urlPatterns = "/getHeaders",
        initParams = {
                @WebInitParam(name = "signature",
                        value = "Thanks for using AI-KBE's Echo Servlet © 2017! ")
        }
)
public class GetHeadersServlet extends HttpServlet {


    protected String mySignature = null;

    public void init(ServletConfig servletConfig) throws ServletException {
        this.mySignature = servletConfig.getInitParameter("signature");
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {

        StringBuilder responseStr = new StringBuilder("");
        // alle Parameter (keys)
        Enumeration<String> paramNames = request.getParameterNames();

        String param = "";
        String paramValue = "";
        while (paramNames.hasMoreElements()) {
            param = paramNames.nextElement();
            paramValue = request.getParameter(param);
            if (param.equals("all")) {
                Enumeration headerNames = request.getHeaderNames();
                while (headerNames.hasMoreElements()) {
                    String key = (String) headerNames.nextElement();
                    responseStr.append(key + "=");
                    String value2 = request.getHeader(key);
                    responseStr.append(value2 + "\n");
                }
            }
            else if (param.equals("header")) {
                Enumeration headerNames = request.getHeaderNames();
                while (headerNames.hasMoreElements()) {
                    String headerName = headerNames.nextElement().toString();
                    if (headerName.equals(paramValue)) {
                        responseStr.append(headerName + "=");
                        String value2 = request.getHeader(headerName);
                        responseStr.append(value2 + "\n");
                    }

                }
            }
        }

        try (PrintWriter out = response.getWriter()) {
            responseStr.append("\n" + mySignature);
            out.println(responseStr.toString());
        }
    }


}
