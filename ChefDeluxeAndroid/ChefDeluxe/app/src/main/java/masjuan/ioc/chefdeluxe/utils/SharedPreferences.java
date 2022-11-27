package masjuan.ioc.chefdeluxe.utils;

import android.content.Context;

import masjuan.ioc.chefdeluxe.R;
import masjuan.ioc.chefdeluxe.api.ApiClientToken;
import masjuan.ioc.chefdeluxe.fragment.UserLogin;

/**
 * Classe que llegeix i escriu dades parell clau-valor
 *
 * @author Eduard Masjuan
 */
public class SharedPreferences {
    android.content.SharedPreferences sharedPref;
    android.content.SharedPreferences.Editor editor;

    // Nom
    String fileName = "masjuan.ioc.chefdeluxe.";

    /**
     * Mètode que crea varis fitxers de preferences compartides identificat per un nom.
     *
     * @param context Context
     */
    public SharedPreferences(Context context) {
        sharedPref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        editor = sharedPref.edit();
    }

    // SET

    /**
     * Metode que passa les claus i els valors amb el metode putString
     * Amb apply() guardem els cambis.
     *
     * @param username Nom de l'usuari, , String
     * @param password Contrasenya de l'usuari, String
     * @param token    Token de l'usuari, String
     * @param email    Correo electronic de l'usuari, String
     */
    public void setData(String username, String password, String token, String email) {
        editor.putString(fileName + "username", username);
        editor.putString(fileName + "password", password);
        editor.putString(fileName + "token", token);
        editor.putString(fileName + "email", email);
        // Canvia l'objecte SharedPreferences en la memoria al moment, en forma asíncrona.
        editor.apply();
    }

    public void setId(Long valor) {
        editor.putLong(fileName + "id", valor);
        editor.apply();
    }

    public void setUsername(String valor) {
        editor.putString(fileName + "username", valor);
        editor.apply();
    }

    public void setPassword(String valor) {
        editor.putString(fileName + "password", valor);
        editor.apply();
    }

    public void setToken(String valor) {
        editor.putString(fileName + "token", valor);
        editor.apply();
    }

    public void setRole(int valor) {
        editor.putInt(fileName + "role", valor);
        editor.apply();
    }

    public void setEmail(String valor) {
        editor.putString(fileName + "email", valor);
        editor.apply();
    }

    public void setState(String valor) {
        editor.putString(fileName + "state", valor);
        editor.apply();
    }

    public void setVillage(String valor) {
        editor.putString(fileName + "village", valor);
        editor.apply();
    }


    public void setDateBegin(String valor) {
        editor.putString(fileName + "dateBegin", valor);
        editor.apply();
    }

    public void setDateFinal(String valor) {
        editor.putString(fileName + "dateFinal", valor);
        editor.apply();
    }

    public void setTimeBegin(String valor) {
        editor.putString(fileName + "timeBegin", valor);
        editor.apply();
    }

    public void setTimeFinal(String valor) {
        editor.putString(fileName + "timeFinal", valor);
        editor.apply();
    }


    // GET

    public Long getId() {
        return sharedPref.getLong(fileName + "id", 0);
    }

    public String getUsername() {
        return sharedPref.getString(fileName + "username", "");
    }

    public String getPassword() {
        return sharedPref.getString(fileName + "password", "");
    }

    public String getToken() {
        return sharedPref.getString(fileName + "token", "");
    }

    public String getEmail() {
        return sharedPref.getString(fileName + "email", "");
    }

    public int getRole() {
        return sharedPref.getInt(fileName + "role", 0);
    }

    public String getState() {
        return sharedPref.getString(fileName + "state", "");
    }

    public String getVillage() {
        return sharedPref.getString(fileName + "village", "");
    }


    public String getDateBegin() {
        return sharedPref.getString(fileName + "dateBegin", "");
    }

    public String getDateFinal() {
        return sharedPref.getString(fileName + "dateFinal", "");
    }

    public String getTimeBegin() {
        return sharedPref.getString(fileName + "timeBegin", "");
    }

    public String getTimeFinal() {
        return sharedPref.getString(fileName + "timeFinal", "");
    }


    /**
     * Mètode per fer reset els valors.
     */
    public void cleanDate() {
        setToken("");
        setRole(0);
        setUsername("");
        setEmail("");
        setPassword("");
        editor.apply();
    }

    public void cleanToken() {
        setToken("");
        editor.apply();
    }

    public void cleanRole() {
        setRole(0);
        editor.apply();
    }

    public void cleanUsername() {
        //editor.clear();
        setUsername("");
        editor.apply();
    }

    public void cleanAvailability() {
        setState("");
        setVillage("");
        editor.apply();
    }

    public void cleanState() {
        setState("");
        editor.apply();
    }

    public void cleanVillage() {
        setVillage("");
        editor.apply();
    }

    public void cleanDateTime() {
        setDateBegin("");
        setDateFinal("");
        setTimeBegin("");
        setTimeFinal("");
        editor.apply();
    }

    /**
     * Tanca sessió eliminan dades de SharePreferences
     * Retorna el Fragment UserLogin
     *
     * @param fragment Fragment
     */
    public void logout(UtilsFragments fragment) {
        cleanDate();
        // Ens assegurem de que s'han esborrat les SharedPreferences
        if (getToken().equals("") && getRole() == 0) {
            ApiClientToken.deleteInstance();
            fragment.replaceFragment(R.id.container, UserLogin.newInstance());
        }
    }

}