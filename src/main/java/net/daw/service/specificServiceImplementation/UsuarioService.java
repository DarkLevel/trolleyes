package net.daw.service.specificServiceImplementation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.Connection;
import javax.servlet.http.HttpServletRequest;
import net.daw.bean.publicBeanInterface.BeanInterface;
import net.daw.bean.specificBeanImplementation.ReplyBean;
import net.daw.bean.specificBeanImplementation.UsuarioBean;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.constant.ConnectionConstants;
import net.daw.dao.publicDaoInterface.DaoInterface;
import net.daw.dao.specificDaoImplementation.UsuarioDao;
import net.daw.factory.BeanFactory;
import net.daw.factory.ConnectionFactory;
import net.daw.factory.DaoFactory;
import net.daw.generadores.Generadorusuarios;
import net.daw.helper.EncodingHelper;
import net.daw.service.genericServiceImplementation.GenericServiceImplementation;
import net.daw.service.publicServiceInterface.ServiceInterface;

public class UsuarioService extends GenericServiceImplementation implements ServiceInterface {

    public UsuarioService(HttpServletRequest oRequest) {
        super(oRequest);
        ob = oRequest.getParameter("ob");
    }

    protected Boolean checkPermission(String strMethodName) {
        UsuarioBean oUsuarioBean = (UsuarioBean) oRequest.getSession().getAttribute("user");
        return oUsuarioBean != null;
    }

    public ReplyBean filldatabase() throws Exception {
        ReplyBean oReplyBean = null;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        if (this.checkPermission("filldatabase")) {
            try {
                Integer cantidadUsuarios = Integer.parseInt(oRequest.getParameter("cant"));
                oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
                oConnection = oConnectionPool.newConnection();
                Gson oGson = new Gson();
                Generadorusuarios oGeneradorusuarios = new Generadorusuarios();
                UsuarioDao oUsuarioDao = new UsuarioDao(oConnection, ob);
                UsuarioBean oUsuarioBean;
                for (int i = 0; i < cantidadUsuarios; i++) {
                    oUsuarioBean = oGeneradorusuarios.generar();
                    oUsuarioDao.create(oUsuarioBean);
                }
                oReplyBean = new ReplyBean(200, oGson.toJson("{\"usuarios creados\":" + cantidadUsuarios + "}"));
            } catch (Exception ex) {
                oReplyBean = new ReplyBean(500,
                        "ERROR: " + EncodingHelper.escapeQuotes(EncodingHelper.escapeLine(ex.getMessage())));
            } finally {
                oConnectionPool.disposeConnection();
            }
        } else {
            oReplyBean = new ReplyBean(401, "Unauthorized");
        }
        return oReplyBean;
    }

    public ReplyBean login() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            String strLogin = oRequest.getParameter("user").toLowerCase();
            String strPassword = oRequest.getParameter("pass");
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            UsuarioDao oUsuarioDao = new UsuarioDao(oConnection, ob);
            UsuarioBean oUsuarioBean = oUsuarioDao.login(strLogin, strPassword);
            if (oUsuarioBean != null) {
                oRequest.getSession().setAttribute("user", oUsuarioBean);
                Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
                oReplyBean = new ReplyBean(200, oGson.toJson(oUsuarioBean));
            } else {
                oReplyBean = new ReplyBean(401, "Bad Authentication");
            }
        } catch (Exception ex) {
            oReplyBean = new ReplyBean(500,
                    "ERROR: " + EncodingHelper.escapeQuotes(EncodingHelper.escapeLine(ex.getMessage())));
        } finally {
            oConnectionPool.disposeConnection();
        }
        return oReplyBean;
    }

    public ReplyBean logout() throws Exception {
        oRequest.getSession().invalidate();
        return new ReplyBean(200, EncodingHelper.quotate("OK"));
    }

    public ReplyBean check() throws Exception {
        ReplyBean oReplyBean;
        UsuarioBean oUsuarioBean = (UsuarioBean) oRequest.getSession().getAttribute("user");
        if (oUsuarioBean != null) {
            Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
            oReplyBean = new ReplyBean(200, oGson.toJson(oUsuarioBean));
        } else {
            oReplyBean = new ReplyBean(401, "No active session");
        }
        return oReplyBean;
    }

    public ReplyBean changepass() throws Exception {
        ReplyBean oReplyBean = null;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            UsuarioBean oLoggedUsuarioBean = (UsuarioBean) oRequest.getSession().getAttribute("user");
            if (oLoggedUsuarioBean != null) {
                String actualPass = oLoggedUsuarioBean.getPass();
                String oldPass = oRequest.getParameter("oldpass");
                if (actualPass.equals(oldPass)) {
                    String newPass = oRequest.getParameter("newpass");
                    if (newPass.length() == 64) {
                        UsuarioBean oUsuarioBean = new UsuarioBean();
                        oUsuarioBean.setId(oLoggedUsuarioBean.getId());
                        oUsuarioBean.setPass(newPass);
                        UsuarioDao oUsuarioDao = new UsuarioDao(oConnection, ob);
                        int iRes = oUsuarioDao.updatepass(oUsuarioBean);
                        oReplyBean = new ReplyBean(200, Integer.toString(iRes));
                    } else {
                        oReplyBean = new ReplyBean(400, "Unencrypted password");
                    }
                } else {
                    oReplyBean = new ReplyBean(401, "Bad Authentication");
                }
            } else {
                oReplyBean = new ReplyBean(401, "Unauthorized");
            }
        } catch (Exception ex) {
            oReplyBean = new ReplyBean(500,
                    "ERROR: " + EncodingHelper.escapeQuotes(EncodingHelper.escapeLine(ex.getMessage())));
        } finally {
            oConnectionPool.disposeConnection();
        }
        return oReplyBean;
    }

    public ReplyBean getprofile() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            Integer id = ((UsuarioBean) oRequest.getSession().getAttribute("user")).getId();
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            DaoInterface oDao = DaoFactory.getDao(oConnection, ob);
            BeanInterface oBean = oDao.get(id, 1);
            Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
            oReplyBean = new ReplyBean(200, oGson.toJson(oBean));
        } catch (Exception ex) {
            throw new Exception("ERROR: Service level: get method: " + ob + " object", ex);
        } finally {
            oConnectionPool.disposeConnection();
        }
        return oReplyBean;
    }

    public ReplyBean createuser() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            String strJsonFromClient = oRequest.getParameter("json");
            Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
            UsuarioBean oBean = (UsuarioBean) BeanFactory.getBeanFromJson(ob, oGson, strJsonFromClient);
            oBean.setId_tipoUsuario(2);
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            DaoInterface oDao = DaoFactory.getDao(oConnection, ob);
            BeanInterface oGenericBean = oDao.create(oBean);
            oReplyBean = new ReplyBean(200, oGson.toJson(oGenericBean));
        } catch (Exception ex) {
            throw new Exception("ERROR: Service level: create method: " + ob + " object", ex);
        } finally {
            oConnectionPool.disposeConnection();
        }
        return oReplyBean;
    }

    public ReplyBean updateprofile() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            UsuarioBean oLoggedUser = (UsuarioBean) oRequest.getSession().getAttribute("user");
            String strJsonFromClient = oRequest.getParameter("json");
            Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
            UsuarioBean oBean = (UsuarioBean) BeanFactory.getBeanFromJson(ob, oGson, strJsonFromClient);
            oBean.setId(oLoggedUser.getId());
            oBean.setPass(null);
            oBean.setId_tipoUsuario(oLoggedUser.getObj_tipoUsuario().getId());
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            DaoInterface oDao = DaoFactory.getDao(oConnection, ob);
            int iRes = oDao.update(oBean);
            oReplyBean = new ReplyBean(200, Integer.toString(iRes));
        } catch (Exception ex) {
            throw new Exception("ERROR: Service level: update method: " + ob + " object", ex);
        } finally {
            oConnectionPool.disposeConnection();
        }
        return oReplyBean;
    }

}
