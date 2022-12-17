package masjuan.ioc.chefdeluxe.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Menu que fa els cuiners per els clients
 */
public class Menu {

    @SerializedName("idMenu")
    @Expose
    private long id;

    @SerializedName("idChef")
    @Expose
    private long idChef;

    @SerializedName("entrante")
    @Expose
    private String entrante;

    @SerializedName("primero")
    @Expose
    private String primero;

    @SerializedName("segundo")
    @Expose
    private String segundo;

    @SerializedName("postre")
    @Expose
    private String postre;

    @SerializedName("cafes")
    @Expose
    private String cafes;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdChef() {
        return idChef;
    }

    public void setIdChef(long idChef) {
        this.idChef = idChef;
    }

    public String getEntrante() {
        return entrante;
    }

    public void setEntrante(String entrante) {
        this.entrante = entrante;
    }

    public String getPrimero() {
        return primero;
    }

    public void setPrimero(String primero) {
        this.primero = primero;
    }

    public String getSegundo() {
        return segundo;
    }

    public void setSegundo(String segundo) {
        this.segundo = segundo;
    }

    public String getPostre() {
        return postre;
    }

    public void setPostre(String postre) {
        this.postre = postre;
    }

    public String getCafes() {
        return cafes;
    }

    public void setCafes(String cafes) {
        this.cafes = cafes;
    }
}