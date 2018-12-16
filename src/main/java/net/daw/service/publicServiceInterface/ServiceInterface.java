
package net.daw.service.publicServiceInterface;

import net.daw.bean.specificBeanImplementation.ReplyBean;

public interface ServiceInterface {
    
    public ReplyBean get() throws Exception;
    
    public ReplyBean remove() throws Exception;
    
    public ReplyBean getcount() throws Exception;
    
    public ReplyBean create() throws Exception;
    
    public ReplyBean update() throws Exception;
    
    public ReplyBean getpage() throws Exception;

}
