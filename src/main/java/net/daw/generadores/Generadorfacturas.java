package net.daw.generadores;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.daw.bean.FacturaBean;

public class Generadorfacturas {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    
    private Date getDateFromString (String fecha) throws ParseException {
        Date date = dateFormat.parse(fecha);
        return date;
    }
    
    public FacturaBean generar() throws Exception {
        Date [] arrayFecha = new Date[10];
        arrayFecha[0] = getDateFromString("01-01-2001");
        arrayFecha[1] = getDateFromString("01-01-2002");
        arrayFecha[2] = getDateFromString("01-01-2003");
        arrayFecha[3] = getDateFromString("01-01-2004");
        arrayFecha[4] = getDateFromString("01-01-2005");
        arrayFecha[5] = getDateFromString("01-01-2006");
        arrayFecha[6] = getDateFromString("01-01-2007");
        arrayFecha[7] = getDateFromString("01-01-2008");
        arrayFecha[8] = getDateFromString("01-01-2009");
        arrayFecha[9] = getDateFromString("01-01-2010");
        Float[] arrayIva = {1.00f, 2.00f, 3.00f, 4.00f, 5.00f, 6.00f, 7.00f, 8.00f, 9.00f, 10.00f};
        Integer[] arrayId_usuario = {1, 2, 3, 4, 5};

        int random_1 = (int)(Math.random() * 9);
        int random_2 = (int)(Math.random() * 9);
        int random_3 = (int)(Math.random() * 4);

        FacturaBean oFacturaBean = new FacturaBean();
        
        oFacturaBean.setFecha(arrayFecha[random_1]);
        oFacturaBean.setIva(arrayIva[random_2]);
        oFacturaBean.setId_usuario(arrayId_usuario[random_3]);
        
        return oFacturaBean;
    }
}
