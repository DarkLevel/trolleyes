package net.daw.service;

import com.google.gson.Gson;
import java.io.Serializable;
import java.sql.Connection;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import net.daw.bean.CarritoBean;
import net.daw.bean.FacturaBean;
import net.daw.bean.LineaBean;
import net.daw.bean.ProductoBean;
import net.daw.bean.ReplyBean;
import net.daw.bean.UsuarioBean;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.constant.ConnectionConstants;
import net.daw.dao.FacturaDao;
import net.daw.dao.LineaDao;
import net.daw.dao.ProductoDao;
import net.daw.factory.ConnectionFactory;
import net.daw.helper.EncodingHelper;

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

    protected Boolean checkPermission(String strMethodName) {
        UsuarioBean oUsuarioBean = (UsuarioBean) oRequest.getSession().getAttribute("user");
        return oUsuarioBean != null;
    }

    public ReplyBean add() throws Exception {
        if (this.checkPermission("add")) {
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
                return new ReplyBean(200, oGson.toJson(oRequest.getSession().getAttribute("carrito")));
            } catch (Exception ex) {
                throw new Exception("ERROR: Service level: getpage method: " + ob + " object: " + ex);
            } finally {
                oConnectionPool.disposeConnection();
            }
        } else {
            return new ReplyBean(401, "Unauthorized");
        }
    }

    public ReplyBean reduce() throws Exception {
        if (this.checkPermission("reduce")) {
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
                            if (o.getCantidad() > cantidad) {
                                o.setCantidad(o.getCantidad() - cantidad);
                            } else {
                                alCarrito.remove(o);
                            }
                            break;
                        }
                    }
                    if (alCarrito.isEmpty()) {
                        oRequest.getSession().setAttribute("carrito", null);
                        return new ReplyBean(200, "El carrito se ha vaciado.");
                    }
                    if (!exists) {
                        return new ReplyBean(200, "El producto seleccionado no existe.");
                    }
                    oRequest.getSession().setAttribute("producto", alCarrito);
                } else {
                    return new ReplyBean(200, "El carrito está vacío.");
                }
                return new ReplyBean(200, oGson.toJson(oRequest.getSession().getAttribute("carrito")));
            } catch (Exception ex) {
                throw new Exception("ERROR: Service level: getpage method: " + ob + " object: " + ex);
            }
        } else {
            return new ReplyBean(401, "Unauthorized");
        }
    }

    public ReplyBean remove() throws Exception {
        if (this.checkPermission("remove")) {
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
                    if (alCarrito.isEmpty()) {
                        oRequest.getSession().setAttribute("carrito", null);
                        return new ReplyBean(200, "El carrito se ha vaciado.");
                    }
                    if (!exists) {
                        return new ReplyBean(200, "El producto seleccionado no existe.");
                    }
                    oRequest.getSession().setAttribute("producto", alCarrito);
                } else {
                    return new ReplyBean(200, "El carrito está vacío.");
                }
                return new ReplyBean(200, oGson.toJson(oRequest.getSession().getAttribute("carrito")));
            } catch (Exception ex) {
                throw new Exception("ERROR: Service level: getpage method: " + ob + " object: " + ex);
            }
        } else {
            return new ReplyBean(401, "Unauthorized");
        }
    }

    public ReplyBean empty() throws Exception {
        if (this.checkPermission("empty")) {
            Gson oGson = new Gson();
            oRequest.getSession().setAttribute("carrito", null);
            return new ReplyBean(200, oGson.toJson(oRequest.getSession().getAttribute("carrito")));
        } else {
            return new ReplyBean(401, "Unauthorized");
        }
    }

    public ReplyBean show() throws Exception {
        if (this.checkPermission("show")) {
            Gson oGson = new Gson();
            return new ReplyBean(200, oGson.toJson(oRequest.getSession().getAttribute("carrito")));
        } else {
            return new ReplyBean(401, "Unauthorized");
        }
    }

    public ReplyBean buy() throws Exception {
        if (this.checkPermission("buy")) {
            ConnectionInterface oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            Connection oConnection = oConnectionPool.newConnection();
            try {
                oConnection.setAutoCommit(false);
                UsuarioBean oUsuarioBean = (UsuarioBean) oRequest.getSession().getAttribute("user");
                ArrayList<CarritoBean> alCarrito = (ArrayList<CarritoBean>) oRequest.getSession().getAttribute("carrito");
                if (alCarrito == null || alCarrito.isEmpty()) {
                    return new ReplyBean(400, "No existen productos.");
                }
                //Crear obj_factura (preguntar sobre iva en factura y hora de la fecha y como enseñarla en plist)
                FacturaBean oFacturaBean = new FacturaBean();
                FacturaDao oFacturaDao = new FacturaDao(oConnection, "factura");
                LocalDateTime fechaHora = LocalDateTime.now();
                Instant instant = fechaHora.toInstant(ZoneOffset.ofHours(+1));
                Date fecha = Date.from(instant);
                oFacturaBean.setFecha(fecha);
                oFacturaBean.setIva(21);
                oFacturaBean.setId_usuario(oUsuarioBean.getId());
                oFacturaBean = oFacturaDao.create(oFacturaBean);
                //Crear Líneas (preguntar sobre id_tipoProducto en productoBean)
                LineaBean oLineaBean = new LineaBean();
                LineaDao oLineaDao = new LineaDao(oConnection, "linea");
                ProductoBean oProductoBean = new ProductoBean();
                ProductoDao oProductoDao = new ProductoDao(oConnection, "producto");
                for (CarritoBean o : alCarrito) {
                    oLineaBean.setId_factura(oFacturaBean.getId());
                    if (o.getCantidad() <= o.getObj_producto().getExistencias()) {
                        oLineaBean.setCantidad(o.getCantidad());
                        oProductoBean = oProductoDao.get(o.getObj_producto().getId(), 0);
                        oProductoBean.setExistencias(oProductoBean.getExistencias()-o.getCantidad());
                        oProductoDao.update(oProductoBean);
                    } else {
                        oConnection.rollback();
                        return new ReplyBean(400, "Se ha seleccionado una cantidad superior a las existencias del producto " + o.getObj_producto().getDesc());
                    }
                    oLineaBean.setId_producto(o.getObj_producto().getId());
                    oLineaDao.create(oLineaBean);
                }
                oConnection.commit();
                return new ReplyBean(200, "La transacción ha sido completada satisfactoriamente.");
            } catch (Exception ex) {
                oConnection.rollback();
                return new ReplyBean(500, "ERROR: " + EncodingHelper.escapeQuotes(EncodingHelper.escapeLine(ex.getMessage())));
            } finally {
                oConnectionPool.disposeConnection();
            }
        } else {
            return new ReplyBean(401, "Unauthorized");
        }
    }

}
