package net.daw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



import net.daw.bean.TipousuarioBean;

public class TipousuarioDao {

	Connection oConnection;
	String ob = "tipoUsuario";

	public TipousuarioDao(Connection oConnection) {
		super();
		this.oConnection = oConnection;
	}

	public TipousuarioBean get(int id) throws Exception {
		String strSQL = "SELECT * FROM " + ob + " WHERE id=?";
		TipousuarioBean oTipousuarioBean;
		ResultSet oResultSet = null;
		PreparedStatement oPreparedStatement =null;
		try {
			oPreparedStatement = oConnection.prepareStatement(strSQL);
			oPreparedStatement.setInt(1, id);
			oResultSet = oPreparedStatement.executeQuery();
			if (oResultSet.next()) {
				oTipousuarioBean = new TipousuarioBean();
				oTipousuarioBean.setId(oResultSet.getInt("id"));
				oTipousuarioBean.setDesc(oResultSet.getString("desc"));
			} else {
				oTipousuarioBean = null;
			}
		} catch (SQLException e) {
			throw new Exception ("Error en Dao get de tipousuario",e);
		} finally {
			if (oResultSet!=null) {
				oResultSet.close();
			}
			if (oPreparedStatement!=null) {
				oPreparedStatement.close();
			}
		}

		return oTipousuarioBean;

	}
        
        public boolean remove(int id) throws Exception {
		String strSQL = "DELETE FROM " + ob + " WHERE id=?";
                boolean estado = false;
		PreparedStatement oPreparedStatement = null;
		try {
			oPreparedStatement = oConnection.prepareStatement(strSQL);
			oPreparedStatement.setInt(1, id);
			oPreparedStatement.execute();
                        estado = true;
		} catch (SQLException e) {
			throw new Exception ("Error en Dao remove de tipousuario",e);
		} finally {
			if (oPreparedStatement!=null) {
				oPreparedStatement.close();
			}
		}
		return estado;
	}
        
        public int getCount() throws Exception {
		String strSQL = "SELECT COUNT(DISTINCT(id)) FROM " + ob;
                int numeroTipoUsuarios;
                ResultSet oResultSet = null;
		PreparedStatement oPreparedStatement = null;
		try {
			oPreparedStatement = oConnection.prepareStatement(strSQL);
                        oResultSet = oPreparedStatement.executeQuery();
                        if (oResultSet.next()) {
				numeroTipoUsuarios = oResultSet.getInt(1);
			} else {
				numeroTipoUsuarios = 0;
			}
		} catch (SQLException e) {
			throw new Exception ("Error en Dao count de tipousuario",e);
		} finally {
			if (oResultSet!=null) {
				oResultSet.close();
			}
			if (oPreparedStatement!=null) {
				oPreparedStatement.close();
			}
		}
		return numeroTipoUsuarios;
	}
        
        public int getMaxId() throws Exception {
		String strSQL = "SELECT MAX(id) FROM " + ob;
                int maxId = 0;
                ResultSet oResultSet = null;
		PreparedStatement oPreparedStatement = null;
		try {
			oPreparedStatement = oConnection.prepareStatement(strSQL);
                        oResultSet = oPreparedStatement.executeQuery();
                        if (oResultSet.next()) {
				maxId = oResultSet.getInt(1);
			}
		} catch (SQLException e) {
			throw new Exception ("Error en Dao maxId de tipousuario",e);
		} finally {
			if (oResultSet!=null) {
				oResultSet.close();
			}
			if (oPreparedStatement!=null) {
				oPreparedStatement.close();
			}
		}
		return maxId += 1;
	}
        
        public boolean create(TipousuarioBean oTipousuarioBean) throws Exception {
		String strSQL = "INSERT INTO `" + ob + "`(`id`, `desc`) VALUES (?,?)";
                boolean estado = false;
		PreparedStatement oPreparedStatement = null;
		try {
			oPreparedStatement = oConnection.prepareStatement(strSQL);
                        oPreparedStatement.setInt(1, oTipousuarioBean.getId());
			oPreparedStatement.setString(2, oTipousuarioBean.getDesc());
			oPreparedStatement.execute();
                        estado = true;
		} catch (SQLException e) {
			throw new Exception ("Error en Dao create de tipousuario",e);
		} finally {
			if (oPreparedStatement!=null) {
				oPreparedStatement.close();
			}
		}
		return estado;
	}
        
        public boolean update(TipousuarioBean oTipousuarioBean) throws Exception {
		String strSQL = "UPDATE `" + ob + "` SET `desc`=? WHERE id=?";
                boolean estado = false;
		PreparedStatement oPreparedStatement = null;
		try {
			oPreparedStatement = oConnection.prepareStatement(strSQL);
                        oPreparedStatement.setString(1, oTipousuarioBean.getDesc());
                        oPreparedStatement.setInt(2, oTipousuarioBean.getId());
			oPreparedStatement.execute();
                        estado = true;
		} catch (SQLException e) {
			throw new Exception ("Error en Dao update de tipousuario",e);
		} finally {
			if (oPreparedStatement!=null) {
				oPreparedStatement.close();
			}
		}
		return estado;
	}

}
