package masjuan.ioc.chefdeluxe.utils;

import android.util.Log;

/**
 * Classe que retorna codis d'error
 *
 * @author Eduard Masjuan
 */
public class ApiCodes {

    public ApiCodes() {
    }

    public boolean codeHttp(int code) {

        if (code >= 100 && code <= 199) {
            Log.v("Código", code + ". ");
            return true;

        } else if (code >= 200 && code <= 299) {
            Log.v("Código", code + ". La solicitud ha tenido éxito.");
            return true;

        } else if (code >= 400 && code <= 499) {
            Log.v("Código", code + ". Petición no ejecutada, carece de credenciales validas de autenticación.");
            return true;

        } else if (code >= 500 && code <= 599) {
            Log.v("Código", code + ". El correo electrónico o la contraseña no son correctos.");
            return true;
        }

        return false;
    }

}
