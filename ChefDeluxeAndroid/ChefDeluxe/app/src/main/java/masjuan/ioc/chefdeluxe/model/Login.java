package masjuan.ioc.chefdeluxe.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login {


    @SerializedName("usernameOrEmail") //Gson mapeja les claus Json a camps d’objectes Java
    @Expose // Indica que esta exposat a serialització o deserialització Json.
    private String usernameOrEmail;

    @SerializedName("password")
    @Expose
    private String password;

    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
