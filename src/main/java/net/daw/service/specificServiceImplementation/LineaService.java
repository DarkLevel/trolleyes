package net.daw.service.specificServiceImplementation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import net.daw.bean.specificBeanImplementation.FacturaBean;
import net.daw.bean.specificBeanImplementation.LineaBean;
import net.daw.bean.specificBeanImplementation.ReplyBean;
import net.daw.bean.specificBeanImplementation.UsuarioBean;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.constant.ConnectionConstants;
import net.daw.dao.specificDaoImplementation.FacturaDao;
import net.daw.dao.specificDaoImplementation.LineaDao;
import net.daw.factory.ConnectionFactory;
import net.daw.generadores.Generadorlineas;
import net.daw.helper.EncodingHelper;
import net.daw.helper.ParameterCook;
import net.daw.service.genericServiceImplementation.GenericServiceImplementation;
import net.daw.service.publicServiceInterface.ServiceInterface;

public class LineaService extends GenericServiceImplementation implements ServiceInterface {

    public LineaService(HttpServletRequest oRequest) {
        super(oRequest);
        ob = oRequest.getParameter("ob");
    }

    public ReplyBean getcountX() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            Integer id_factura = Integer.parseInt(oRequest.getParameter("id_fact"));
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            LineaDao oLineaDao = new LineaDao(oConnection, ob);
            int registros = oLineaDao.getcountX(id_factura);
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
            Integer id_factura = Integer.parseInt(oRequest.getParameter("id_fact"));
            Integer iRpp = Integer.parseInt(oRequest.getParameter("rpp"));
            Integer iPage = Integer.parseInt(oRequest.getParameter("page"));
            HashMap<String, String> hmOrder = ParameterCook.getOrderParams(oRequest.getParameter("order"));
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            LineaDao oLineaDao = new LineaDao(oConnection, ob);
            ArrayList<LineaBean> alLineaBean = oLineaDao.getpageX(id_factura, iRpp, iPage, hmOrder, 1);
            Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
            oReplyBean = new ReplyBean(200, oGson.toJson(alLineaBean));
        } catch (Exception ex) {
            oReplyBean = new ReplyBean(500,
                    "ERROR: " + EncodingHelper.escapeQuotes(EncodingHelper.escapeLine(ex.getMessage())));
        } finally {
            oConnectionPool.disposeConnection();
        }
        return oReplyBean;
    }

    public ReplyBean getcountlineauser() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            Integer id_factura = Integer.parseInt(oRequest.getParameter("id_fact"));
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
            FacturaDao oFacturaDao = new FacturaDao(oConnection, "factura");
            FacturaBean oFacturaBean = (FacturaBean) oFacturaDao.get(id_factura, 0);
            if (oFacturaBean.getId_usuario() == ((UsuarioBean) oRequest.getSession().getAttribute("user")).getId()) {
                LineaDao oLineaDao = new LineaDao(oConnection, ob);
                int registros = oLineaDao.getcountX(id_factura);
                oReplyBean = new ReplyBean(200, oGson.toJson(registros));
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

    public ReplyBean getpagelineauser() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            Integer id_factura = Integer.parseInt(oRequest.getParameter("id_fact"));
            Integer iRpp = Integer.parseInt(oRequest.getParameter("rpp"));
            Integer iPage = Integer.parseInt(oRequest.getParameter("page"));
            HashMap<String, String> hmOrder = ParameterCook.getOrderParams(oRequest.getParameter("order"));
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
            FacturaDao oFacturaDao = new FacturaDao(oConnection, "factura");
            FacturaBean oFacturaBean = (FacturaBean) oFacturaDao.get(id_factura, 0);
            if (oFacturaBean.getId_usuario() == ((UsuarioBean) oRequest.getSession().getAttribute("user")).getId()) {
                LineaDao oLineaDao = new LineaDao(oConnection, ob);
                ArrayList<LineaBean> alLineaBean = oLineaDao.getpageX(id_factura, iRpp, iPage, hmOrder, 1);
                oReplyBean = new ReplyBean(200, oGson.toJson(alLineaBean));
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

    public ReplyBean filldatabase() throws Exception {
        ReplyBean oReplyBean = null;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            Integer cantidadLineas = Integer.parseInt(oRequest.getParameter("cant"));
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            Gson oGson = new Gson();
            Generadorlineas oGeneradorlineas = new Generadorlineas();
            LineaDao oLineaDao = new LineaDao(oConnection, ob);
            LineaBean oLineaBean;
            for (int i = 0; i < cantidadLineas; i++) {
                oLineaBean = oGeneradorlineas.generar();
                oLineaDao.create(oLineaBean);
            }
            oReplyBean = new ReplyBean(200, oGson.toJson("{\"lineas creadas\":" + cantidadLineas + "}"));
        } catch (Exception ex) {
            oReplyBean = new ReplyBean(500,
                    "ERROR: " + EncodingHelper.escapeQuotes(EncodingHelper.escapeLine(ex.getMessage())));
        } finally {
            oConnectionPool.disposeConnection();
        }
        return oReplyBean;
    }
}
