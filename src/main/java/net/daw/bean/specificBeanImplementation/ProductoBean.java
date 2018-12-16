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
import net.daw.dao.specificDaoImplementation.TipoproductoDao;
import net.daw.helper.EncodingHelper;

/**
 *
 * @author a044531896d
 */
public class ProductoBean extends GenericBeanImplementation implements BeanInterface {

    @Expose
    private String codigo;
    @Expose
    private String desc;
    @Expose
    private int existencias;
    @Expose
    private float precio;
    @Expose
    private String foto;
    @Expose(serialize = false)
    private int id_tipoProducto;
    @Expose(deserialize = false)
    private TipoproductoBean obj_tipoProducto;
    
    public TipoproductoBean getObj_tipoProducto() {
        return obj_tipoProducto;
    }

    public void setObj_tipoProducto(TipoproductoBean obj_tipoProducto) {
        this.obj_tipoProducto = obj_tipoProducto;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getExistencias() {
        return existencias;
    }

    public void setExistencias(int existencias) {
        this.existencias = existencias;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getId_tipoProducto() {
        return id_tipoProducto;
    }

    public void setId_tipoProducto(int id_tipoProducto) {
        this.id_tipoProducto = id_tipoProducto;
    }

    @Override
    public ProductoBean fill(ResultSet oResultSet, Connection oConnection, Integer expand) throws Exception {
        this.setId(oResultSet.getInt("id"));
        this.setCodigo(oResultSet.getString("codigo"));
        this.setDesc(oResultSet.getString("desc"));
        this.setExistencias(oResultSet.getInt("existencias"));
        this.setPrecio(oResultSet.getFloat("precio"));
        this.setFoto(oResultSet.getString("foto"));
        if (expand > 0) {
            TipoproductoDao oTipoproductoDao = new TipoproductoDao(oConnection, "tipoproducto");
            this.setObj_tipoProducto((TipoproductoBean) oTipoproductoDao.get(oResultSet.getInt("id_tipoProducto"), expand - 1));
        } else{
            this.setId_tipoProducto(oResultSet.getInt("id_tipoProducto"));
        }
        return this;
    }
    
    @Override
    public String getColumns() {
        String strColumns = "";
        strColumns += "producto.id,";
        strColumns += "producto.codigo,";
        strColumns += "producto.desc,";
        strColumns += "producto.existencias,";
        strColumns += "producto.precio,";
        strColumns += "producto.foto,";
        strColumns += "producto.id_tipoProducto";
        return strColumns;
    }

    @Override
    public String getValues() {
        String strColumns = "";
        strColumns += "null,";
        strColumns += EncodingHelper.quotate(codigo) + ",";
        strColumns += EncodingHelper.quotate(desc) + ",";
        strColumns += existencias + ",";
        strColumns += precio + ",";
        strColumns += EncodingHelper.quotate(foto) + ",";
        strColumns += id_tipoProducto;
        return strColumns;
    }

    @Override
    public String getPairs() {
        String strPairs = "";
        strPairs += "producto.id=" + id + ",";
        strPairs += "producto.codigo=" + EncodingHelper.quotate(codigo) + ",";
        strPairs += "producto.desc=" + EncodingHelper.quotate(desc) + ",";
        strPairs += "producto.existencias=" + existencias + ",";
        strPairs += "producto.precio=" + precio + ",";
        strPairs += "producto.foto=" + EncodingHelper.quotate(foto) + ",";
        strPairs += "producto.id_tipoProducto=" + id_tipoProducto;
        strPairs += " WHERE producto.id = " + id;
        return strPairs;
    }

}
