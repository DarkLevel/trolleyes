package net.daw.service.specificServiceImplementation;

import com.google.gson.Gson;
import java.sql.Connection;
import javax.servlet.http.HttpServletRequest;
import net.daw.bean.specificBeanImplementation.ReplyBean;
import net.daw.bean.specificBeanImplementation.TipoproductoBean;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.constant.ConnectionConstants;
import net.daw.dao.specificDaoImplementation.TipoproductoDao;
import net.daw.factory.ConnectionFactory;
import net.daw.generadores.Generadortipoproductos;
import net.daw.helper.EncodingHelper;
import net.daw.service.genericServiceImplementation.GenericServiceImplementation;
import net.daw.service.publicServiceInterface.ServiceInterface;

public class TipoproductoService extends GenericServiceImplementation implements ServiceInterface {

    public TipoproductoService(HttpServletRequest oRequest) {
        super(oRequest);
        ob = oRequest.getParameter("ob");
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
