
package net.daw.dao.publicDaoInterface;

import java.util.ArrayList;
import java.util.HashMap;
import net.daw.bean.publicBeanInterface.BeanInterface;

public interface DaoInterface {

    public BeanInterface get(int id, Integer expand) throws Exception;

    public int remove(int id) throws Exception;

    public int getcount() throws Exception;

    public BeanInterface create(BeanInterface oBean) throws Exception;

    public int update(BeanInterface oBean) throws Exception;

    public ArrayList<BeanInterface> getpage(int iRpp, int iPage, HashMap<String, String> hmOrder, Integer expand) throws Exception;

}
