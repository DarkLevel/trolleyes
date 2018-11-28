/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import net.daw.bean.CarritoBean;
import net.daw.bean.ProductoBean;
import net.daw.bean.ReplyBean;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.constant.ConnectionConstants;
import net.daw.dao.ProductoDao;
import net.daw.factory.ConnectionFactory;

/**
 *
 * @author a073597589g
 */
public class CarritoService implements Serializable {

    HttpServletRequest oRequest;
    String ob = null;

    public CarritoService(HttpServletRequest oRequest) {
        super();
        this.oRequest = oRequest;
        ob = oRequest.getParameter("ob");
    }

    public ReplyBean add() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        ArrayList<CarritoBean> alCarritoBean = new ArrayList<>();
        CarritoBean oCarritoBean = new CarritoBean();
        try {
            Integer id_producto = Integer.parseInt(oRequest.getParameter("id"));
            Integer cantidad = Integer.parseInt(oRequest.getParameter("cant"));
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            HttpSession oSession = oRequest.getSession();
            ProductoDao oProductoDao = new ProductoDao(oConnection, "producto");
            ProductoBean oProductoBean = oProductoDao.get(id_producto, 1);
            ArrayList<CarritoBean> productosCarrito = (ArrayList<CarritoBean>) oSession.getAttribute("carrito");
            if (productosCarrito != null) {
                for (CarritoBean o : productosCarrito) {
                    if (oProductoBean.getId() == o.getObj_producto().getId()) {
                        cantidad++;
                    } else {
                        alCarritoBean.add(o);
                    }
                }
            }
            oCarritoBean.setCantidad(cantidad);
            oCarritoBean.setObj_producto(oProductoBean);
            alCarritoBean.add(oCarritoBean);
            oRequest.getSession().setAttribute("productos", alCarritoBean);
            Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
            oReplyBean = new ReplyBean(200, oGson.toJson(alCarritoBean));
        } catch (Exception ex) {
            throw new Exception("ERROR: Service level: get method: " + ob + " object", ex);
        } finally {
            oConnectionPool.disposeConnection();
        }
        return oReplyBean;
    }

}
