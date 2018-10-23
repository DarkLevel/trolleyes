package net.daw.control;

import java.io.IOException;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.daw.bean.ReplyBean;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.constant.ConnectionConstants;
import net.daw.factory.ConnectionFactory;
import net.daw.helper.EncodingHelper;
import net.daw.helper.JsonHelper;
import net.daw.service.TipousuarioService;
import net.daw.service.UsuarioService;

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
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub

        String strJson = "";
        String strOb = request.getParameter("ob");
        String strOp = request.getParameter("op");
        JsonHelper json = new JsonHelper();

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception ex) {
            strJson = "{\"status\":500,\"msg\":\"jdbc driver not found\"}";
        }

        if (strOp != null && strOb != null) {
            if (!strOp.equalsIgnoreCase("") && !strOb.equalsIgnoreCase("")) {
                if (strOb.equalsIgnoreCase("tipousuario")) {
                    if (strOp.equalsIgnoreCase("get")) {
                        TipousuarioService oService = new TipousuarioService(request);
                        try {
                            ReplyBean oReplyBean = oService.get();
                            strJson = json.strJson(oReplyBean.getStatus(), oReplyBean.getJson());

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (strOp.equalsIgnoreCase("getpage")) {
                        TipousuarioService oService = new TipousuarioService(request);
                        try {
                            ReplyBean oReplyBean = oService.getpage();
                            strJson = json.strJson(oReplyBean.getStatus(), oReplyBean.getJson());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (strOp.equalsIgnoreCase("remove")) {
                        TipousuarioService oService = new TipousuarioService(request);
                        try {
                            ReplyBean oReplyBean = oService.remove();
                            strJson = json.strJson(oReplyBean.getStatus(), oReplyBean.getJson());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (strOp.equalsIgnoreCase("getcount")) {
                        TipousuarioService oService = new TipousuarioService(request);
                        try {
                            ReplyBean oReplyBean = oService.getcount();
                            strJson = json.strJson(oReplyBean.getStatus(), oReplyBean.getJson());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (strOp.equalsIgnoreCase("create")) {
                        TipousuarioService oService = new TipousuarioService(request);
                        try {
                            ReplyBean oReplyBean = oService.create();
                            strJson = json.strJson(oReplyBean.getStatus(), oReplyBean.getJson());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    if (strOp.equalsIgnoreCase("update")) {
                        TipousuarioService oService = new TipousuarioService(request);
                        try {
                            ReplyBean oReplyBean = oService.update();
                            strJson = json.strJson(oReplyBean.getStatus(), oReplyBean.getJson());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                
                if (strOb.equalsIgnoreCase("usuario")) {
                    if (strOp.equalsIgnoreCase("connect")) {
                        try {
                            ConnectionInterface oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
                            Connection oConnection = oConnectionPool.newConnection();
                            oConnectionPool.disposeConnection();
                            response.setStatus(200);
                            strJson = json.strJson(200, "Connection OK");

                        } catch (Exception ex) {
                            response.setStatus(500);
                            strJson = json.strJson(500, "Bad Connection: "
                                    + EncodingHelper.escapeQuotes(EncodingHelper.escapeLine(ex.getMessage())));
                        }
                    }
                    
                    response.setContentType("application/json;charset=UTF-8");
                    HttpSession oSession = request.getSession();
                    
                    if (strOp.equalsIgnoreCase("login")) {
                        String strUser = request.getParameter("user");
                        String strPass = request.getParameter("pass");
                        if (strUser.equals("rafa") && strPass.equals("thebest")) {
                            oSession.setAttribute("daw", strUser);
                            response.setStatus(200);
                            strJson = json.strJson(200, strUser);
                        } else {
                            response.setStatus(401);
                            strJson = json.strJson(401, "Authentication error");
                        }
                    }
                    if (strOp.equalsIgnoreCase("logout")) {
                        oSession.invalidate();
                        response.setStatus(200);
                        strJson = json.strJson(200, "Session is closed");
                    }
                    if (strOp.equalsIgnoreCase("check")) {
                        String strUserName = (String) oSession.getAttribute("daw");
                        if (strUserName != null) {
                            response.setStatus(200);
                            strJson = json.strJson(200, "Session is closed");
                        } else {
                            response.setStatus(401);
                            strJson = json.strJson(401, "Authentication error");
                        }
                    }
                    if (strOp.equalsIgnoreCase("getsecret")) {
                        String strUserName = (String) oSession.getAttribute("daw");
                        if (strUserName != null) {
                            response.setStatus(200);
                            strJson = json.strJson(200, "985739847598");
                        } else {
                            response.setStatus(401);
                            strJson = json.strJson(401, "Authentication error");
                        }
                    }
                    if (strOp.equalsIgnoreCase("get")) {
                        UsuarioService oService = new UsuarioService(request);
                        try {
                            ReplyBean oReplyBean = oService.get();
                            strJson = json.strJson(oReplyBean.getStatus(), oReplyBean.getJson());

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (strOp.equalsIgnoreCase("getpage")) {
                        UsuarioService oService = new UsuarioService(request);
                        try {
                            ReplyBean oReplyBean = oService.getpage();
                            strJson = json.strJson(oReplyBean.getStatus(), oReplyBean.getJson());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (strOp.equalsIgnoreCase("remove")) {
                        UsuarioService oService = new UsuarioService(request);
                        try {
                            ReplyBean oReplyBean = oService.remove();
                            strJson = json.strJson(oReplyBean.getStatus(), oReplyBean.getJson());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (strOp.equalsIgnoreCase("getcount")) {
                        UsuarioService oService = new UsuarioService(request);
                        try {
                            ReplyBean oReplyBean = oService.getcount();
                            strJson = json.strJson(oReplyBean.getStatus(), oReplyBean.getJson());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (strOp.equalsIgnoreCase("create")) {
                        UsuarioService oService = new UsuarioService(request);
                        try {
                            ReplyBean oReplyBean = oService.create();
                            strJson = json.strJson(oReplyBean.getStatus(), oReplyBean.getJson());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (strOp.equalsIgnoreCase("update")) {
                        UsuarioService oService = new UsuarioService(request);
                        try {
                            ReplyBean oReplyBean = oService.update();
                            strJson = json.strJson(oReplyBean.getStatus(), oReplyBean.getJson());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                response.setStatus(500);
                strJson = json.strJson(500, "operation or object empty");
            }
        } else {
            response.setStatus(500);
            strJson = json.strJson(500, "operation or object can't be null");
        }
        response.getWriter().append(strJson).close();
    }

}
