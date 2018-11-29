/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.service;

import com.google.gson.Gson;
import com.mchange.lang.ArrayUtils;
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
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        Gson oGson = new Gson();
        try {
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            Integer id_producto = Integer.parseInt(oRequest.getParameter("id"));
            Integer cantidad = Integer.parseInt(oRequest.getParameter("cant"));
            ArrayList<CarritoBean> alCarrito = (ArrayList<CarritoBean>) oRequest.getSession().getAttribute("carrito");
            if (alCarrito != null) {
                Boolean exists = false;
                for (CarritoBean o : alCarrito) {
                    if (id_producto == o.getObj_producto().getId()) {
                        o.setCantidad(o.getCantidad() + cantidad);
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    CarritoBean oCarritoBean = new CarritoBean();
                    ProductoDao oProductoDao = new ProductoDao(oConnection, "producto");
                    ProductoBean oProductoBean = oProductoDao.get(id_producto, 1);
                    oCarritoBean.setCantidad(cantidad);
                    oCarritoBean.setObj_producto(oProductoBean);
                    alCarrito.add(oCarritoBean);
                }
                oRequest.getSession().setAttribute("carrito", alCarrito);
            } else {
                ArrayList<CarritoBean> newAlCarrito = new ArrayList<>();
                CarritoBean oCarritoBean = new CarritoBean();
                ProductoDao oProductoDao = new ProductoDao(oConnection, "producto");
                ProductoBean oProductoBean = oProductoDao.get(id_producto, 1);
                oCarritoBean.setCantidad(cantidad);
                oCarritoBean.setObj_producto(oProductoBean);
                newAlCarrito.add(oCarritoBean);
                oRequest.getSession().setAttribute("carrito", newAlCarrito);
            }
        } catch (Exception ex) {
            throw new Exception("ERROR: Service level: getpage method: " + ob + " object: " + ex);
        } finally {
            oConnectionPool.disposeConnection();
        }
        return new ReplyBean(200, oGson.toJson(oRequest.getSession().getAttribute("carrito")));
    }
    
    public ReplyBean reduce() throws Exception {
        Gson oGson = new Gson();
        try {
            Integer id_producto = Integer.parseInt(oRequest.getParameter("id"));
            Integer cantidad = Integer.parseInt(oRequest.getParameter("cant"));
            ArrayList<CarritoBean> alCarrito = (ArrayList<CarritoBean>) oRequest.getSession().getAttribute("carrito");
            if (alCarrito != null) {
                Boolean exists = false;
                for (CarritoBean o : alCarrito) {
                    if (id_producto == o.getObj_producto().getId()) {
                        exists = true;
                        if(o.getCantidad() > cantidad){
                            o.setCantidad(o.getCantidad() - cantidad);
                        } else{
                            alCarrito.remove(o);
                        }
                        break;
                    }
                }
                if(alCarrito.isEmpty()){
                    oRequest.getSession().setAttribute("carrito", null);
                    return new ReplyBean(200, "El carrito se ha vaciado");
                }
                if(!exists){
                    return new ReplyBean(200, "El producto seleccionado no existe");
                }
                oRequest.getSession().setAttribute("producto", alCarrito);
            } else {
                return new ReplyBean(200, "El carrito está vacío");
            }
        } catch (Exception ex) {
            throw new Exception("ERROR: Service level: getpage method: " + ob + " object: " + ex);
        }
        return new ReplyBean(200, oGson.toJson(oRequest.getSession().getAttribute("carrito")));
    }
    
    public ReplyBean remove() throws Exception {
        Gson oGson = new Gson();
        try {
            Integer id_producto = Integer.parseInt(oRequest.getParameter("id"));
            ArrayList<CarritoBean> alCarrito = (ArrayList<CarritoBean>) oRequest.getSession().getAttribute("carrito");
            if (alCarrito != null) {
                Boolean exists = false;
                for (CarritoBean o : alCarrito) {
                    if (id_producto == o.getObj_producto().getId()) {
                        exists = true;
                        alCarrito.remove(o);
                        break;
                    }
                }
                if(alCarrito.isEmpty()){
                    oRequest.getSession().setAttribute("carrito", null);
                    return new ReplyBean(200, "El carrito se ha vaciado");
                }
                if(!exists){
                    return new ReplyBean(200, "El producto seleccionado no existe");
                }
                oRequest.getSession().setAttribute("producto", alCarrito);
            } else {
                return new ReplyBean(200, "El carrito está vacío");
            }
        } catch (Exception ex) {
            throw new Exception("ERROR: Service level: getpage method: " + ob + " object: " + ex);
        }
        return new ReplyBean(200, oGson.toJson(oRequest.getSession().getAttribute("carrito")));
    }
    
    public ReplyBean empty() throws Exception {
        Gson oGson = new Gson();
        oRequest.getSession().setAttribute("carrito", null);
        return new ReplyBean(200, oGson.toJson(oRequest.getSession().getAttribute("carrito")));
    }
    
    public ReplyBean show() throws Exception {
        Gson oGson = new Gson();
        return new ReplyBean(200, oGson.toJson(oRequest.getSession().getAttribute("carrito")));
    }
    
    public ReplyBean buy() throws Exception {
        return null;
    }

}
