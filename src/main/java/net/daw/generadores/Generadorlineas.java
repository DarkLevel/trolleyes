package net.daw.generadores;

import net.daw.bean.specificBeanImplementation.LineaBean;

public class Generadorlineas {
    public LineaBean generar() throws Exception {
        Integer[] arrayExistencias = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Integer[] arrayId_producto = {1, 2, 3, 4, 5};
        Integer[] arrayId_factura = {1, 2, 3, 4, 5};

        int random_1 = (int)(Math.random() * ((8 - 0) + 1)) + 0;
        int random_2 = (int)(Math.random() * ((8 - 0) + 1)) + 0;
        int random_3 = (int)(Math.random() * ((3 - 0) + 1)) + 0;

        LineaBean oLineaBean = new LineaBean();
        
        oLineaBean.setCantidad(arrayExistencias[random_1]);
        oLineaBean.setId_producto(arrayId_producto[random_2]);
        oLineaBean.setId_factura(arrayId_factura[random_3]);
        
        return oLineaBean;
    }
}
