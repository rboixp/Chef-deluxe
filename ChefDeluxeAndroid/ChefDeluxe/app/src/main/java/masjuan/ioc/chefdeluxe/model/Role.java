package masjuan.ioc.chefdeluxe.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Model Role.
 * Perfil d'usuari: Admin, Chef, Client
 *
 * @author Eduard Masjuan
 */
public class Role {

    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("role")
    @Expose
    private String role;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
