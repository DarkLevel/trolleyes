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
import net.daw.dao.specificDaoImplementation.TipousuarioDao;
import net.daw.helper.EncodingHelper;

/**
 *
 * @author jesus
 */
public class UsuarioBean extends GenericBeanImplementation implements BeanInterface {

    @Expose
    private String dni;
    @Expose
    private String nombre;
    @Expose
    private String ape1;
    @Expose
    private String ape2;
    @Expose
    private String login;
    @Expose(serialize = false)
    private String pass;
    @Expose(serialize = false)
    private int id_tipoUsuario;
    @Expose(deserialize = false)
    private TipousuarioBean obj_tipoUsuario;
    @Expose
    private int link_factura;

    public int getLink_factura() {
        return link_factura;
    }

    public void setLink_factura(int link_factura) {
        this.link_factura = link_factura;
    }

    public TipousuarioBean getObj_tipoUsuario() {
        return obj_tipoUsuario;
    }

    public void setObj_tipoUsuario(TipousuarioBean obj_tipoUsuario) {
        this.obj_tipoUsuario = obj_tipoUsuario;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApe1() {
        return ape1;
    }

    public void setApe1(String ape1) {
        this.ape1 = ape1;
    }

    public String getApe2() {
        return ape2;
    }

    public void setApe2(String ape2) {
        this.ape2 = ape2;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getId_tipoUsuario() {
        return id_tipoUsuario;
    }

    public void setId_tipoUsuario(int id_tipoUsuario) {
        this.id_tipoUsuario = id_tipoUsuario;
    }

    @Override
    public UsuarioBean fill(ResultSet oResultSet, Connection oConnection, Integer expand) throws Exception {
        this.setId(oResultSet.getInt("id"));
        this.setDni(oResultSet.getString("dni"));
        this.setNombre(oResultSet.getString("nombre"));
        this.setApe1(oResultSet.getString("ape1"));
        this.setApe2(oResultSet.getString("ape2"));
        this.setLogin(oResultSet.getString("login"));
        this.setPass(oResultSet.getString("pass"));
        this.setLink_factura((new FacturaDao(oConnection, "factura")).getcountX(oResultSet.getInt("id")));
        if (expand > 0) {
            TipousuarioDao otipousuarioDao = new TipousuarioDao(oConnection, "tipousuario");
            this.setObj_tipoUsuario((TipousuarioBean) otipousuarioDao.get(oResultSet.getInt("id_tipoUsuario"), expand - 1));
        } else {
            this.setId_tipoUsuario(oResultSet.getInt("id_tipoUsuario"));
        }
        return this;
    }

    @Override
    public String getColumns() {
        String strColumns = "";
        strColumns += "usuario.id,";
        strColumns += "usuario.dni,";
        strColumns += "usuario.nombre,";
        strColumns += "usuario.ape1,";
        strColumns += "usuario.ape2,";
        strColumns += "usuario.login,";
        strColumns += "usuario.pass,";
        strColumns += "usuario.id_tipoUsuario";
        return strColumns;
    }

    @Override
    public String getValues() {
        String strColumns = "";
        strColumns += "null,";
        strColumns += EncodingHelper.quotate(dni) + ",";
        strColumns += EncodingHelper.quotate(nombre) + ",";
        strColumns += EncodingHelper.quotate(ape1) + ",";
        strColumns += EncodingHelper.quotate(ape2) + ",";
        strColumns += EncodingHelper.quotate(login) + ",";
        strColumns += EncodingHelper.quotate(pass) + ",";
        strColumns += id_tipoUsuario;
        return strColumns;
    }

    @Override
    public String getPairs() {
        String strPairs = "";
        strPairs += "usuario.id=" + id + ",";
        strPairs += "usuario.dni=" + EncodingHelper.quotate(dni) + ",";
        strPairs += "usuario.nombre=" + EncodingHelper.quotate(nombre) + ",";
        strPairs += "usuario.ape1=" + EncodingHelper.quotate(ape1) + ",";
        strPairs += "usuario.ape2=" + EncodingHelper.quotate(ape2) + ",";
        strPairs += "usuario.login=" + EncodingHelper.quotate(login) + ",";
        strPairs += "usuario.id_tipoUsuario=" + id_tipoUsuario;
        strPairs += " WHERE usuario.id = " + id;
        return strPairs;
    }
    
    public String getPairsPass() {
        String strPairs = "";
        strPairs += "usuario.pass=" + EncodingHelper.quotate(pass);
        strPairs += " WHERE usuario.id = " + id;
        return strPairs;
    }

}
