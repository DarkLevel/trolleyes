
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

}
