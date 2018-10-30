package net.daw.generadores;

import net.daw.bean.ProductoBean;

public class Generadorproductos {
    public ProductoBean generar() throws Exception {
        String[] arrayCodigo = {"codigo_1", "codigo_2", "codigo_3", "codigo_4", "codigo_5", "codigo_6", "codigo_7", "codigo_8", "codigo_9", "codigo_10"};
        String[] arrayDesc = {"desc_1", "desc_2", "desc_3", "desc_4", "desc_5", "desc_6", "desc_7", "desc_8", "desc_9", "desc_10"};
        Integer[] arrayExistencias = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Float[] arrayPrecio = {1.00f, 2.00f, 3.00f, 4.00f, 5.00f, 6.00f, 7.00f, 8.00f, 9.00f, 10.00f};
        String[] arrayFoto = {"foto_1", "foto_2", "foto_3", "foto_4", "foto_5", "foto_6", "foto_7", "foto_8", "foto_9", "foto_10"};
        Integer[] arrayId_tipoProducto = {1, 2, 3, 4, 5};

        int random_1 = (int)(Math.random() * 9);
        int random_2 = (int)(Math.random() * 9);
        int random_3 = (int)(Math.random() * 9);
        int random_4 = (int)(Math.random() * 9);
        int random_5 = (int)(Math.random() * 9);
        int random_6 = (int)(Math.random() * 4);

        ProductoBean oProductoBean = new ProductoBean();
        
        oProductoBean.setCodigo(arrayCodigo[random_1]);
        oProductoBean.setDesc(arrayDesc[random_2]);
        oProductoBean.setExistencias(arrayExistencias[random_3]);
        oProductoBean.setPrecio(arrayPrecio[random_4]);
        oProductoBean.setFoto(arrayFoto[random_5]);
        oProductoBean.setId_tipoProducto(arrayId_tipoProducto[random_6]);

        return oProductoBean;
    }
}
