package net.daw.generadores;

import net.daw.bean.specificBeanImplementation.TipoproductoBean;

public class Generadortipoproductos {
    public TipoproductoBean generar() throws Exception {
        String[] arrayDesc = {"desc_1", "desc_2", "desc_3", "desc_4", "desc_5", "desc_6", "desc_7", "desc_8", "desc_9", "desc_10"};

        int random_1 = (int)(Math.random() * ((8 - 0) + 1)) + 0;

        TipoproductoBean oTipoproductoBean = new TipoproductoBean();
        
        oTipoproductoBean.setDesc(arrayDesc[random_1]);

        return oTipoproductoBean;
    }
}
