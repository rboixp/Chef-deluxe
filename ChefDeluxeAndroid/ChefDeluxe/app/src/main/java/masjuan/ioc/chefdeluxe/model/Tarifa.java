package masjuan.ioc.chefdeluxe.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

/**
 * Tarifa  i menu del cuiner
 *
 * @author Eduard Masjuan
 */
public class Tarifa {

    @SerializedName("usernameOrEmail")
    @Expose
    private String usernameOrEmail;

    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("idchef")
    @Expose
    private long idchef;

    @SerializedName("precioHora")
    @Expose
    private BigDecimal precioHora;


    @SerializedName("idMenu")
    @Expose
    private long idMenu;

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

    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }

    public long getIdchef() {
        return idchef;
    }

    public void setIdchef(long idchef) {
        this.idchef = idchef;
    }

    public BigDecimal getPrecioHora() {
        return precioHora;
    }

    public void setPrecioHora(BigDecimal precioHora) {
        this.precioHora = precioHora;
    }

    public long getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(long idMenu) {
        this.idMenu = idMenu;
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
