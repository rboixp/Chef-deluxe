package masjuan.ioc.chefdeluxe.api;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import masjuan.ioc.chefdeluxe.utils.GlobalVariables;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Instancia de Retrofit
 * Envia i rep sol·licituds de red a una API
 */
public class ApiClientSSL {

    private static ApiService API_SERVICE;
    private static ApiService API_SERVICE_TOKEN;

    public static synchronized ApiService getInstance(Context context) {
        SecuritySsl securitySsl = new SecuritySsl(context);

        if (API_SERVICE == null) {

            API_SERVICE = retrofit(securitySsl.getClient()).create(ApiService.class);
        }

        return API_SERVICE;
    }

    public static synchronized ApiService getInstance(Context context, String token) {
        SecuritySsl securitySsl = new SecuritySsl(context, token);

        if (API_SERVICE_TOKEN == null) {

            API_SERVICE_TOKEN = retrofit(securitySsl.getClient()).create(ApiService.class);
        }

        return API_SERVICE_TOKEN;
    }

    /**
     * Client REST, que permet fer peticions al servidor
     *
     * @param okhttp client https
     * @return Retrofit
     */
    private static Retrofit retrofit(OkHttpClient okhttp) {
        String dateFormat = "yyyy-MM-dd'T'HH:mm:ss";

        return new Retrofit.Builder()
                .baseUrl(GlobalVariables.urlBaseHttps)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson(dateFormat)))
                .client(okhttp)
                .build();
    }

    /**
     * Converteix objectes Java a Json o viceversa
     *
     * @param dateFormat String date
     * @return Objecte Gson
     */
    private static Gson gson(String dateFormat) {
        return new GsonBuilder().setDateFormat(dateFormat).setLenient().create();
    }

    /**
     * S'elimina la instancia
     */
    public static void deleteInstance() {
        API_SERVICE_TOKEN = null;
    }

}