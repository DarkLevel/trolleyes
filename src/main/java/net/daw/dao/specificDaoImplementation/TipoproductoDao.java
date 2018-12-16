
package net.daw.dao.specificDaoImplementation;

import java.sql.Connection;
import net.daw.dao.genericDaoImplementation.GenericDaoImplementation;
import net.daw.dao.publicDaoInterface.DaoInterface;

public class TipoproductoDao extends GenericDaoImplementation implements DaoInterface {

    public TipoproductoDao(Connection oConnection, String ob) {
        super(oConnection, ob);
    }

}
