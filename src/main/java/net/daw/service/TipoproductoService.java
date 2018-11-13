/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import net.daw.bean.ReplyBean;
import net.daw.bean.TipoproductoBean;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.constant.ConnectionConstants;
import net.daw.dao.TipoproductoDao;
import net.daw.factory.ConnectionFactory;
import net.daw.generadores.Generadortipoproductos;
import net.daw.helper.EncodingHelper;
import net.daw.helper.ParameterCook;

/**
 *
 * @author a044531896d
 */
public class TipoproductoService {

    HttpServletRequest oRequest;
    String ob = null;

    public TipoproductoService(HttpServletRequest oRequest) {
        super();
        this.oRequest = oRequest;
        ob = oRequest.getParameter("ob");
    }

    public ReplyBean get() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            Integer id = Integer.parseInt(oRequest.getParameter("id"));
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            TipoproductoDao oTipoproductoDao = new TipoproductoDao(oConnection, ob);
            TipoproductoBean oTipoproductoBean = oTipoproductoDao.get(id, 1);
            Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
            oReplyBean = new ReplyBean(200, oGson.toJson(oTipoproductoBean));
        } catch (Exception ex) {
            oReplyBean = new ReplyBean(500,
                    "ERROR: " + EncodingHelper.escapeQuotes(EncodingHelper.escapeLine(ex.getMessage())));
        } finally {
            oConnectionPool.disposeConnection();
        }

        return oReplyBean;

    }

    public ReplyBean remove() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            Integer id = Integer.parseInt(oRequest.getParameter("id"));
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            TipoproductoDao oTipoproductoDao = new TipoproductoDao(oConnection, ob);
            int iRes = oTipoproductoDao.remove(id);
            oReplyBean = new ReplyBean(200, Integer.toString(iRes));
        } catch (Exception ex) {
            oReplyBean = new ReplyBean(500,
                    "ERROR: " + EncodingHelper.escapeQuotes(EncodingHelper.escapeLine(ex.getMessage())));
        } finally {
            oConnectionPool.disposeConnection();
        }
        return oReplyBean;

    }

    public ReplyBean getcount() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            TipoproductoDao oTipoproductoDao = new TipoproductoDao(oConnection, ob);
            int registros = oTipoproductoDao.getcount();
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

    public ReplyBean create() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            String strJsonFromClient = oRequest.getParameter("json");
            Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
            TipoproductoBean oTipoproductoBean = new TipoproductoBean();
            oTipoproductoBean = oGson.fromJson(strJsonFromClient, TipoproductoBean.class);
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            TipoproductoDao oTipoproductoDao = new TipoproductoDao(oConnection, ob);
            oTipoproductoBean = oTipoproductoDao.create(oTipoproductoBean);
            oReplyBean = new ReplyBean(200, oGson.toJson(oTipoproductoBean));
        } catch (Exception ex) {
            oReplyBean = new ReplyBean(500,
                    "ERROR: " + EncodingHelper.escapeQuotes(EncodingHelper.escapeLine(ex.getMessage())));
        } finally {
            oConnectionPool.disposeConnection();
        }
        return oReplyBean;
    }

    public ReplyBean update() throws Exception {
        int iRes = 0;
        ReplyBean oReplyBean = null;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            String strJsonFromClient = oRequest.getParameter("json");
            Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
            TipoproductoBean oTipoproductoBean = new TipoproductoBean();
            oTipoproductoBean = oGson.fromJson(strJsonFromClient, TipoproductoBean.class);
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            TipoproductoDao oTipoproductoDao = new TipoproductoDao(oConnection, ob);
            iRes = oTipoproductoDao.update(oTipoproductoBean);
            oReplyBean.setStatus(200);
            oReplyBean.setJson(Integer.toString(iRes));
        } catch (Exception ex) {
            oReplyBean = new ReplyBean(500,
                    "ERROR: " + EncodingHelper.escapeQuotes(EncodingHelper.escapeLine(ex.getMessage())));
        } finally {
            oConnectionPool.disposeConnection();
        }
        return oReplyBean;
    }

    public ReplyBean getpage() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            Integer iRpp = Integer.parseInt(oRequest.getParameter("rpp"));
            Integer iPage = Integer.parseInt(oRequest.getParameter("page"));
            HashMap<String, String> hmOrder = ParameterCook.getOrderParams(oRequest.getParameter("order"));
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            TipoproductoDao oTipoproductoDao = new TipoproductoDao(oConnection, ob);
            ArrayList<TipoproductoBean> alTipoproductoBean = oTipoproductoDao.getpage(iRpp, iPage, hmOrder, 1);
            Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
            oReplyBean = new ReplyBean(200, oGson.toJson(alTipoproductoBean));
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
            Integer cantidadTipoproductos = Integer.parseInt(oRequest.getParameter("cant"));
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            Gson oGson = new Gson();
            Generadortipoproductos oGeneradortipoproductos = new Generadortipoproductos();
            TipoproductoDao oTipoproductoDao = new TipoproductoDao(oConnection, ob);
            TipoproductoBean oTipoproductoBean;
            for (int i = 0; i < cantidadTipoproductos; i++) {
                oTipoproductoBean = oGeneradortipoproductos.generar();
                oTipoproductoDao.create(oTipoproductoBean);
            }
            oReplyBean = new ReplyBean(200, oGson.toJson("{\"tipoproductos creados\":" + cantidadTipoproductos + "}"));
        } catch (Exception ex) {
            oReplyBean = new ReplyBean(500,
                    "ERROR: " + EncodingHelper.escapeQuotes(EncodingHelper.escapeLine(ex.getMessage())));
        } finally {
            oConnectionPool.disposeConnection();
        }
        return oReplyBean;
    }
}
