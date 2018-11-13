/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.bean;

import com.google.gson.annotations.Expose;
import java.sql.Connection;
import java.sql.ResultSet;
import net.daw.dao.FacturaDao;
import net.daw.dao.ProductoDao;

/**
 *
 * @author a044531896d
 */
public class LineaBean {
    
    @Expose
    private int id;
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
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    
    public LineaBean fill(ResultSet oResultSet, Connection oConnection, Integer expand) throws Exception {
        this.setId(oResultSet.getInt("id"));
        this.setCantidad(oResultSet.getInt("cantidad"));
        if (expand > 0) {
            ProductoDao oProductoDao = new ProductoDao(oConnection, "producto");
            this.setObj_producto(oProductoDao.get(oResultSet.getInt("id_producto"), expand - 1));
            FacturaDao oFacturaDao = new FacturaDao(oConnection, "factura");
            this.setObj_factura(oFacturaDao.get(oResultSet.getInt("id_factura"), expand - 1));
        } else {
            this.setId_producto(oResultSet.getInt("id_producto"));
            this.setId_factura(oResultSet.getInt("id_factura"));
        }
        return this;
    }

    public String getColumns() {
        String strColumns = "";
        strColumns += "id,";
        strColumns += "cantidad,";
        strColumns += "id_producto";
        strColumns += "id_factura";
        return strColumns;
    }

    public String getValues() {
        String strColumns = "";
        strColumns += "null,";
        strColumns += cantidad + ",";
        strColumns += id_producto + ",";
        strColumns += id_factura;
        return strColumns;
    }

    public String getPairs() {
        String strPairs = "";
        strPairs += "id=" + id + ",";
        strPairs += "cantidad=" + cantidad + ",";
        strPairs += "id_producto=" + id_producto + ",";
        strPairs += "id_factura=" + id_factura;
        strPairs += " WHERE id = ?";
        return strPairs;
    }
    
}
