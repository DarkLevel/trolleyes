/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.bean.specificBeanImplementation;

import com.google.gson.annotations.Expose;
import java.sql.Connection;
import java.sql.ResultSet;
import net.daw.bean.genericBeanImplementation.GenericBeanImplementation;
import net.daw.bean.publicBeanInterface.BeanInterface;
import net.daw.dao.specificDaoImplementation.FacturaDao;
import net.daw.dao.specificDaoImplementation.ProductoDao;

/**
 *
 * @author a044531896d
 */
public class LineaBean extends GenericBeanImplementation implements BeanInterface {
    
    @Expose
    private int cantidad;
    @Expose(serialize = false)
    private int id_producto;
    @Expose(serialize = false)
    private int id_factura;
    @Expose(deserialize = false)
    private ProductoBean obj_producto;
    @Expose(deserialize = false)
    private FacturaBean obj_factura;

    public ProductoBean getObj_producto() {
        return obj_producto;
    }

    public void setObj_producto(ProductoBean obj_producto) {
        this.obj_producto = obj_producto;
    }

    public FacturaBean getObj_factura() {
        return obj_factura;
    }

    public void setObj_factura(FacturaBean obj_factura) {
        this.obj_factura = obj_factura;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public int getId_factura() {
        return id_factura;
    }

    public void setId_factura(int id_factura) {
        this.id_factura = id_factura;
    }
    
    @Override
    public LineaBean fill(ResultSet oResultSet, Connection oConnection, Integer expand) throws Exception {
        this.setId(oResultSet.getInt("id"));
        this.setCantidad(oResultSet.getInt("cantidad"));
        if (expand > 0) {
            ProductoDao oProductoDao = new ProductoDao(oConnection, "producto");
            this.setObj_producto((ProductoBean) oProductoDao.get(oResultSet.getInt("id_producto"), expand - 1));
            FacturaDao oFacturaDao = new FacturaDao(oConnection, "factura");
            this.setObj_factura((FacturaBean) oFacturaDao.get(oResultSet.getInt("id_factura"), expand - 1));
        } else {
            this.setId_producto(oResultSet.getInt("id_producto"));
            this.setId_factura(oResultSet.getInt("id_factura"));
        }
        return this;
    }

    @Override
    public String getColumns() {
        String strColumns = "";
        strColumns += "linea.id,";
        strColumns += "linea.cantidad,";
        strColumns += "linea.id_producto,";
        strColumns += "linea.id_factura";
        return strColumns;
    }

    @Override
    public String getValues() {
        String strColumns = "";
        strColumns += "null,";
        strColumns += cantidad + ",";
        strColumns += id_producto + ",";
        strColumns += id_factura;
        return strColumns;
    }

    @Override
    public String getPairs() {
        String strPairs = "";
        strPairs += "linea.id=" + id + ",";
        strPairs += "linea.cantidad=" + cantidad + ",";
        strPairs += "linea.id_producto=" + id_producto + ",";
        strPairs += "linea.id_factura=" + id_factura;
        strPairs += " WHERE linea.id = " + id;
        return strPairs;
    }
    
}
