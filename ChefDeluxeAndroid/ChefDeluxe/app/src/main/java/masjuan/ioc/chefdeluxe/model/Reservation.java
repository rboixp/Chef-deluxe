package masjuan.ioc.chefdeluxe.model;

import java.math.BigDecimal;
import java.security.Timestamp;

/**
 * Model Reservation
 * Els clients fan la reserva i els cuiners la reben
 *
 * @author Eduard Masjuan
 */
public class Reservation {

    private long id;
    private String estado;
    private String  cliente;
    private String  chef;
    private long comensales;
    private String instruccions;

    private java.sql.Timestamp incio;
    private java.sql.Timestamp fin;
    private BigDecimal precio;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getChef() {
        return chef;
    }

    public void setChef(String chef) {
        this.chef = chef;
    }

    public java.sql.Timestamp getIncio() {
        return incio;
    }

    public void setIncio(java.sql.Timestamp incio) {
        this.incio = incio;
    }

    public java.sql.Timestamp getFin() {
        return fin;
    }

    public void setFin(java.sql.Timestamp fin) {
        this.fin = fin;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public long getComensales() {
        return comensales;
    }

    public void setComensales(long comensales) {
        this.comensales = comensales;
    }

    public String getInstruccions() {
        return instruccions;
    }

    public void setInstruccions(String instruccions) {
        this.instruccions = instruccions;
    }
}
