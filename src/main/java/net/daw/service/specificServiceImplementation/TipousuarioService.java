package net.daw.service.specificServiceImplementation;

import java.sql.Connection;
import javax.servlet.http.HttpServletRequest;
import com.google.gson.Gson;
import net.daw.bean.specificBeanImplementation.ReplyBean;
import net.daw.bean.specificBeanImplementation.TipousuarioBean;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.constant.ConnectionConstants;
import net.daw.dao.specificDaoImplementation.TipousuarioDao;
import net.daw.factory.ConnectionFactory;
import net.daw.generadores.Generadortipousuarios;
import net.daw.helper.EncodingHelper;
import net.daw.service.genericServiceImplementation.GenericServiceImplementation;
import net.daw.service.publicServiceInterface.ServiceInterface;

public class TipousuarioService extends GenericServiceImplementation implements ServiceInterface {

    public TipousuarioService(HttpServletRequest oRequest) {
        super(oRequest);
        ob = oRequest.getParameter("ob");
    }

    public ReplyBean filldatabase() throws Exception {
        ReplyBean oReplyBean = null;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            Integer cantidadTipousuarios = Integer.parseInt(oRequest.getParameter("cant"));
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            Gson oGson = new Gson();
            Generadortipousuarios oGeneradortipousuarios = new Generadortipousuarios();
            TipousuarioDao oTipousuarioDao = new TipousuarioDao(oConnection, ob);
            TipousuarioBean oTipousuarioBean;
            for (int i = 0; i < cantidadTipousuarios; i++) {
                oTipousuarioBean = oGeneradortipousuarios.generar();
                oTipousuarioDao.create(oTipousuarioBean);
            }
            oReplyBean = new ReplyBean(200, oGson.toJson("{\"tipousuarios creados\":" + cantidadTipousuarios + "}"));
        } catch (Exception ex) {
            oReplyBean = new ReplyBean(500,
                    "ERROR: " + EncodingHelper.escapeQuotes(EncodingHelper.escapeLine(ex.getMessage())));
        } finally {
            oConnectionPool.disposeConnection();
        }
        return oReplyBean;
    }
}
