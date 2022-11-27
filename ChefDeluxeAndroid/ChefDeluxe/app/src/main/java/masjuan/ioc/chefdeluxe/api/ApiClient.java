package masjuan.ioc.chefdeluxe.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import masjuan.ioc.chefdeluxe.utils.GlobalVariables;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Instancia de Retrofit
 * Envia i rep sol·licituds  de red a una API
 */
public class ApiClient {

    private static ApiService API_SERVICE;

    public static synchronized ApiService getInstance() {
        if (API_SERVICE == null) {

            // Registre les dades de les sol·licituds  i respostes HTTP
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            // Nivell de registre
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            // Converteix objectes Java a Json o viceversa
            Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").setLenient().create();

            // Client HTTP, envia i rep sol·licituds de xarxa basats en HTPP
            OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(5, TimeUnit.SECONDS)
                    .addInterceptor(loggingInterceptor);

            // Client REST, que permet fer peticions al servidor
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(GlobalVariables.urlBase)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpBuilder.build())
                    .build();

            API_SERVICE = retrofit.create(ApiService.class);
        }

        return API_SERVICE;
    }

    /**
     * S'elimina la instancia
     */
    public static void deleteInstance() {
        API_SERVICE = null;
    }

}
