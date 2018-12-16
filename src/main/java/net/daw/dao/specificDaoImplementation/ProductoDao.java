
package net.daw.dao.specificDaoImplementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import net.daw.bean.specificBeanImplementation.ProductoBean;
import net.daw.dao.genericDaoImplementation.GenericDaoImplementation;
import net.daw.dao.publicDaoInterface.DaoInterface;
import net.daw.helper.SqlBuilder;

/**
 *
 * @author a044531896d
 */
public class ProductoDao extends GenericDaoImplementation implements DaoInterface {

    public ProductoDao(Connection oConnection, String ob) {
        super(oConnection, ob);
    }

    public int getcountX(int id_tipoProducto) throws Exception {
        String strSQL = "SELECT COUNT(id) FROM " + ob + " WHERE id_tipoProducto=?";
        int res = 0;
        ResultSet oResultSet = null;
        PreparedStatement oPreparedStatement = null;
        try {
            oPreparedStatement = oConnection.prepareStatement(strSQL);
            oPreparedStatement.setInt(1, id_tipoProducto);
            oResultSet = oPreparedStatement.executeQuery();
            if (oResultSet.next()) {
                res = oResultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new Exception("Error en Dao getcountX de " + ob, e);
        } finally {
            if (oResultSet != null) {
                oResultSet.close();
            }
            if (oPreparedStatement != null) {
                oPreparedStatement.close();
            }
        }
        return res;
    }

    public ArrayList<ProductoBean> getpageX(int id_tipoProducto, int iRpp, int iPage, HashMap<String, String> hmOrder, Integer expand) throws Exception {
        String strSQL = "SELECT * FROM " + ob;
        if (id_tipoProducto != 0) {
            strSQL += " WHERE id_tipoProducto=?";
        }
        strSQL += SqlBuilder.buildSqlOrder(hmOrder);
        ArrayList<ProductoBean> alProductoBean;
        if (iRpp > 0 && iRpp < 100000 && iPage > 0 && iPage < 100000000) {
            strSQL += " LIMIT " + (iPage - 1) * iRpp + ", " + iRpp;
            ResultSet oResultSet = null;
            PreparedStatement oPreparedStatement = null;
            try {
                oPreparedStatement = oConnection.prepareStatement(strSQL);
                if (id_tipoProducto != 0) {
                    oPreparedStatement.setInt(1, id_tipoProducto);
                }
                oResultSet = oPreparedStatement.executeQuery();
                alProductoBean = new ArrayList<>();
                while (oResultSet.next()) {
                    ProductoBean oProductoBean = new ProductoBean();
                    oProductoBean.fill(oResultSet, oConnection, expand);
                    alProductoBean.add(oProductoBean);
                }
            } catch (SQLException e) {
                throw new Exception("Error en Dao getpageX de " + ob, e);
            } finally {
                if (oResultSet != null) {
                    oResultSet.close();
                }
                if (oPreparedStatement != null) {
                    oPreparedStatement.close();
                }
            }
        } else {
            throw new Exception("Error en Dao getpage de " + ob);
        }
        return alProductoBean;
    }

}
