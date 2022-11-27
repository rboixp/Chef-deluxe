package masjuan.ioc.chefdeluxe.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Model Disponibilidad
 * Disponibilitat dels cuiners
 *
 * @author Eduard Masjuan
 */
public class Disponibilidad {

    @SerializedName("usernameOrEmail")
    @Expose
    private String usernameOrEmail;

    @SerializedName("estado")
    @Expose
    private String estado;

    @SerializedName("poblacion")
    @Expose
    private String poblacion;

    @SerializedName("id")
    @Expose
    private Long id;

    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        id = id;
    }
}
