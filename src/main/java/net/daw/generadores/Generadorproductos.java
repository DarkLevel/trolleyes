package net.daw.generadores;

public class Generadorproductos {
    public String generar() throws Exception {
        String[] arrayCodigo = {"\"codigo_1\"", "\"codigo_2\"", "\"codigo_3\"", "\"codigo_4\"", "\"codigo_5\"", "\"codigo_6\"", "\"codigo_7\"", "\"codigo_8\"", "\"codigo_9\"", "\"codigo_10\""};
        String[] arrayDesc = {"\"desc_1\"", "\"desc_2\"", "\"desc_3\"", "\"desc_4\"", "\"desc_5\"", "\"desc_6\"", "\"desc_7\"", "\"desc_8\"", "\"desc_9\"", "\"desc_10\""};
        Integer[] arrayExistencias = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Double[] arrayPrecio = {1.00, 2.00, 3.00, 4.00, 5.00, 6.00, 7.00, 8.00, 9.00, 10.00};
        String[] arrayFoto = {"\"foto_1\"", "\"foto_2\"", "\"foto_3\"", "\"foto_4\"", "\"foto_5\"", "\"foto_6\"", "\"foto_7\"", "\"foto_8\"", "\"foto_9\"", "\"foto_10\""};
        Integer[] arrayId_tipoProducto = {1, 2, 3, 4, 5};

        int random_1 = (int) (Math.random() * 9);
        int random_2 = (int) (Math.random() * 9);
        int random_3 = (int) (Math.random() * 9);
        int random_4 = (int) (Math.random() * 9);
        int random_5 = (int) (Math.random() * 9);
        int random_6 = (int) (Math.random() * 4);

        String strJson = "{\"codigo\":" + arrayCodigo[random_1] + ",\"desc\":" + arrayDesc[random_2] + ",\"existencias\":" + arrayExistencias[random_3]
                + ",\"precio\":" + arrayPrecio[random_4] + ",\"foto\":" + arrayFoto[random_5] + ",\"id_tipoProducto\":" + arrayId_tipoProducto[random_6] + "}";

        return strJson;
    }
}
