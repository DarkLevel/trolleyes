package net.daw.service.specificServiceImplementation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import net.daw.bean.specificBeanImplementation.FacturaBean;
import net.daw.bean.specificBeanImplementation.ReplyBean;
import net.daw.bean.specificBeanImplementation.UsuarioBean;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.constant.ConnectionConstants;
import net.daw.dao.specificDaoImplementation.FacturaDao;
import net.daw.factory.ConnectionFactory;
import net.daw.generadores.Generadorfacturas;
import net.daw.helper.EncodingHelper;
import net.daw.helper.ParameterCook;
import net.daw.service.genericServiceImplementation.GenericServiceImplementation;
import net.daw.service.publicServiceInterface.ServiceInterface;

public class FacturaService extends GenericServiceImplementation implements ServiceInterface {

    public FacturaService(HttpServletRequest oRequest) {
        super(oRequest);
        ob = oRequest.getParameter("ob");
    }
    
    public ReplyBean getcountX() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            Integer id_user = Integer.parseInt(oRequest.getParameter("id_user"));
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            FacturaDao oFacturaDao = new FacturaDao(oConnection, ob);
            int registros = oFacturaDao.getcountX(id_user);
            Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
            oReplyBean = new ReplyBean(200, oGson.toJson(registros));
        } catch (Exception ex) {
            oReplyBean = new ReplyBean(500,
                    "ERROR: " + EncodingHelper.escapeQuotes(EncodingHelper.escapeLine(ex.getMessage())));
        } finally {
            oConnectionPool.disposeConnection();
        }
        return oReplyBean;
    }
    
    public ReplyBean getpageX() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            Integer id_usuario = Integer.parseInt(oRequest.getParameter("id_user"));
            Integer iRpp = Integer.parseInt(oRequest.getParameter("rpp"));
            Integer iPage = Integer.parseInt(oRequest.getParameter("page"));
            HashMap<String, String> hmOrder = ParameterCook.getOrderParams(oRequest.getParameter("order"));
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            FacturaDao oFacturaDao = new FacturaDao(oConnection, ob);
            ArrayList<FacturaBean> alFacturaBean = oFacturaDao.getpageX(id_usuario, iRpp, iPage, hmOrder, 1);
            Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
            oReplyBean = new ReplyBean(200, oGson.toJson(alFacturaBean));
        } catch (Exception ex) {
            oReplyBean = new ReplyBean(500,
                    "ERROR: " + EncodingHelper.escapeQuotes(EncodingHelper.escapeLine(ex.getMessage())));
        } finally {
            oConnectionPool.disposeConnection();
        }

        return oReplyBean;
    }
    
    public ReplyBean getcountfacturasuser() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            Integer id_user = ((UsuarioBean) oRequest.getSession().getAttribute("user")).getId();
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            FacturaDao oFacturaDao = new FacturaDao(oConnection, ob);
            int registros = oFacturaDao.getcountX(id_user);
            Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
            oReplyBean = new ReplyBean(200, oGson.toJson(registros));
        } catch (Exception ex) {
            oReplyBean = new ReplyBean(500,
                    "ERROR: " + EncodingHelper.escapeQuotes(EncodingHelper.escapeLine(ex.getMessage())));
        } finally {
            oConnectionPool.disposeConnection();
        }
        return oReplyBean;
    }
    
    public ReplyBean getpagefacturasuser() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            Integer id_usuario = ((UsuarioBean) oRequest.getSession().getAttribute("user")).getId();
            Integer iRpp = Integer.parseInt(oRequest.getParameter("rpp"));
            Integer iPage = Integer.parseInt(oRequest.getParameter("page"));
            HashMap<String, String> hmOrder = ParameterCook.getOrderParams(oRequest.getParameter("order"));
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            FacturaDao oFacturaDao = new FacturaDao(oConnection, "factura");
            ArrayList<FacturaBean> alFacturaBean = oFacturaDao.getpageX(id_usuario, iRpp, iPage, hmOrder, 1);
            Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
            oReplyBean = new ReplyBean(200, oGson.toJson(alFacturaBean));
        } catch (Exception ex) {
            oReplyBean = new ReplyBean(500,
                    "ERROR: " + EncodingHelper.escapeQuotes(EncodingHelper.escapeLine(ex.getMessage())));
        } finally {
            oConnectionPool.disposeConnection();
        }

        return oReplyBean;
    }

    public ReplyBean filldatabase() throws Exception {
        ReplyBean oReplyBean = null;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            Integer cantidadFacturas = Integer.parseInt(oRequest.getParameter("cant"));
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            Gson oGson = new Gson();
            Generadorfacturas oGeneradorfacturas = new Generadorfacturas();
            FacturaDao oFacturaDao = new FacturaDao(oConnection, ob);
            FacturaBean oFacturaBean;
            for (int i = 0; i < cantidadFacturas; i++) {
                oFacturaBean = oGeneradorfacturas.generar();
                oFacturaDao.create(oFacturaBean);
            }
            oReplyBean = new ReplyBean(200, oGson.toJson("{\"facturas creadas\":" + cantidadFacturas + "}"));
        } catch (Exception ex) {
            oReplyBean = new ReplyBean(500,
                    "ERROR: " + EncodingHelper.escapeQuotes(EncodingHelper.escapeLine(ex.getMessage())));
        } finally {
            oConnectionPool.disposeConnection();
        }
        return oReplyBean;
    }
}
