package net.daw.control;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.daw.bean.ReplyBean;
import net.daw.constant.ConfigurationConstants;
import net.daw.constant.ConfigurationConstants.EnvironmentConstants;
import net.daw.factory.ServiceFactory;
import net.daw.helper.JsonHelper;

/**
 * Servlet implementation class json
 */
public class json extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public json() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("origin"));
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");
        response.setHeader("Access-Control-Max-Age", "86400");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "Origin, Accept, x-requested-with, Content-Type");
        String strJson;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            try {
                ReplyBean oReplyBean = ServiceFactory.executeService(request);
                strJson = JsonHelper.strJson(oReplyBean.getStatus(), oReplyBean.getJson());
            } catch (Exception e) {
                response.setStatus(500);
                strJson = JsonHelper.strJson(500, "Server Error");
                if (ConfigurationConstants.environment == EnvironmentConstants.Debug) {
                    PrintWriter out = response.getWriter();
                    out.println(e.getMessage());
                    e.printStackTrace(out);
                }
            }
        } catch (Exception ex) {
            strJson = "{\"status\":500,\"msg\":\"jdbc driver not found\"}";
        }
        response.getWriter().append(strJson).close();
    }

}
