package net.daw.bean;

import java.util.Date;

public class FacturaBean {
    
    private int id;
    private double iva;
    private Date fecha;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
}
