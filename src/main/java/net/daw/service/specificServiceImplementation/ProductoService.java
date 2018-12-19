package net.daw.service.specificServiceImplementation;

import com.google.gson.Gson;
import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.daw.bean.specificBeanImplementation.ReplyBean;
import net.daw.bean.specificBeanImplementation.ProductoBean;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.constant.ConnectionConstants;
import net.daw.dao.specificDaoImplementation.ProductoDao;
import net.daw.factory.ConnectionFactory;
import net.daw.generadores.Generadorproductos;
import net.daw.helper.EncodingHelper;
import net.daw.service.genericServiceImplementation.GenericServiceImplementation;
import net.daw.service.publicServiceInterface.ServiceInterface;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class ProductoService extends GenericServiceImplementation implements ServiceInterface {

    public ProductoService(HttpServletRequest oRequest) {
        super(oRequest);
        ob = oRequest.getParameter("ob");
    }

    public ReplyBean filldatabase() throws Exception {
        ReplyBean oReplyBean = null;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            Integer cantidadProductos = Integer.parseInt(oRequest.getParameter("cant"));
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            Gson oGson = new Gson();
            Generadorproductos oGeneradorproductos = new Generadorproductos();
            ProductoDao oProductoDao = new ProductoDao(oConnection, ob);
            ProductoBean oProductoBean;
            for (int i = 0; i < cantidadProductos; i++) {
                oProductoBean = oGeneradorproductos.generar();
                oProductoDao.create(oProductoBean);
            }
            oReplyBean = new ReplyBean(200, oGson.toJson("{\"productos creados\":" + cantidadProductos + "}"));
        } catch (Exception ex) {
            oReplyBean = new ReplyBean(500,
                    "ERROR: " + EncodingHelper.escapeQuotes(EncodingHelper.escapeLine(ex.getMessage())));
        } finally {
            oConnectionPool.disposeConnection();
        }
        return oReplyBean;
    }

    public ReplyBean addimage() throws Exception {
        HashMap<String, String> hash = new HashMap<>();
        if (ServletFileUpload.isMultipartContent(oRequest)) {
            try {
                List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(oRequest);
                for (FileItem item : multiparts) {
                    if (!item.isFormField()) {
                        String name = new File(item.getName()).getName();
                        item.write(new File(".//..//webapps//ROOT//imagenes//" + name));
                    } else {
                        hash.put(item.getFieldName(), item.getString());
                    }
                }
            } catch (Exception ex) {
                throw new Exception(ex);
            }
        }
        Gson oGson = new Gson();
        return new ReplyBean(200, oGson.toJson("Correcto"));
    }

}
