/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.bean;

import com.google.gson.annotations.Expose;
import java.sql.Connection;
import java.sql.ResultSet;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import net.daw.dao.LineaDao;
import net.daw.dao.UsuarioDao;
import net.daw.helper.EncodingHelper;

/**
 *
 * @author a044531896d
 */
public class FacturaBean {

    @Expose
    private int id;
    @Expose
    private Date fecha;
    @Expose
    private double iva;
    @Expose(serialize = false)
    private int id_usuario;
    @Expose(deserialize = false)
    private UsuarioBean obj_usuario;
    @Expose
    private int link_linea;

    public int getLink_linea() {
        return link_linea;
    }

    public void setLink_linea(int link_linea) {
        this.link_linea = link_linea;
    }

    public UsuarioBean getObj_usuario() {
        return obj_usuario;
    }

    public void setObj_usuario(UsuarioBean obj_usuario) {
        this.obj_usuario = obj_usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }
    
    public FacturaBean fill(ResultSet oResultSet, Connection oConnection, Integer expand) throws Exception {
        this.setId(oResultSet.getInt("id"));
        this.setFecha(oResultSet.getDate("fecha"));
        this.setIva(oResultSet.getDouble("iva"));
        this.setLink_linea((new LineaDao(oConnection, "linea")).getcountX(oResultSet.getInt("id")));
        if (expand > 0) {
            UsuarioDao oUsuarioDao = new UsuarioDao(oConnection, "usuario");
            this.setObj_usuario(oUsuarioDao.get(oResultSet.getInt("id_usuario"), expand - 1));
        } else {
            this.setId_usuario(oResultSet.getInt("id_usuario"));
        }
        return this;
    }
    
    public String getColumns() {
        String strColumns = "";
        strColumns += "factura.id,";
        strColumns += "factura.fecha,";
        strColumns += "factura.iva,";
        strColumns += "factura.id_usuario";
        return strColumns;
    }

    public String getValues() {
        //Getting the default zone id
        ZoneId defaultZoneId = ZoneId.systemDefault();

        //Converting the date to Instant
        Instant instant = fecha.toInstant();

        //Converting the Date to LocalDate
        LocalDateTime localDateTime = instant.atZone(defaultZoneId).toLocalDateTime();
        
        String strColumns = "";
        strColumns += "null,";
        strColumns += EncodingHelper.quotate(localDateTime.toString()) + ",";
        strColumns += iva + ",";
        strColumns += id_usuario;
        return strColumns;
    }

    public String getPairs() {
        //Getting the default zone id
        ZoneId defaultZoneId = ZoneId.systemDefault();

        //Converting the date to Instant
        Instant instant = fecha.toInstant();

        //Converting the Date to LocalDate
        LocalDateTime localDateTime = instant.atZone(defaultZoneId).toLocalDateTime();

        String strPairs = "";
        strPairs += "factura.id=" + id + ",";
        strPairs += "factura.fecha=" + EncodingHelper.quotate(localDateTime.toString()) + ",";
        strPairs += "factura.iva=" + iva + ",";
        strPairs += "factura.id_usuario=" + id_usuario;
        strPairs += " WHERE id = " + id;
        return strPairs;
    }

}
