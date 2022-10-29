package masjuan.ioc.chefdeluxe.utils;


import android.content.Context;

public class SharedPreferences {

    android.content.SharedPreferences sharedPref;
    android.content.SharedPreferences.Editor editor;

    String fichero = "masjuan.ioc.chefdeluxe.";

    public SharedPreferences(Context context) {
        sharedPref = context.getSharedPreferences(fichero, Context.MODE_PRIVATE);
        editor = sharedPref.edit();
    }

    public void setData(String username, String password, String token) {
        editor.putString(fichero + "username", username);
        editor.putString(fichero + "password", password);
        editor.putString(fichero + "token", token);
        editor.apply(); // Canvia l'objecte SharedPreferences en la memoria al moment, en forma asíncrona.
    }

    public void setUsername(String valor) {
        editor.putString(fichero + "username", valor);
        editor.apply();
    }

    public void setPassword(String valor) {
        editor.putString(fichero + "password", valor);
        editor.apply();
    }

    public void setToken(String valor) {
        editor.putString(fichero + "token", valor);
        editor.apply();
    }

    public String getUsername() {
        return sharedPref.getString(fichero + "username", "");
    }

    public String getPassword() {
        return sharedPref.getString(fichero + "password", "");
    }

    public String getToken() {
        return sharedPref.getString(fichero + "token", "");
    }

    public void cleanToken() {
        //editor.clear();
        setToken("");
        editor.apply();
    }

    public void cleanUsername() {
        //editor.clear();
        setUsername("");
        editor.apply();
    }
}