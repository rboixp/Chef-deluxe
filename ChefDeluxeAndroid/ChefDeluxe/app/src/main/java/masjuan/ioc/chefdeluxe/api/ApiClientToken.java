package masjuan.ioc.chefdeluxe.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import masjuan.ioc.chefdeluxe.utils.GlobalVariables;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Instancia de Retrofit amb Token
 * Envia i rep sol·licituds  de red a una API
 */
public class ApiClientToken {

    private static ApiService API_SERVICE;

    public static synchronized ApiService getInstance(final String tokenDeAcceso) {

        if (API_SERVICE == null) {

            // Registre les dades de les sol·licituds  i respostes HTTP
            HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
            logger.setLevel(HttpLoggingInterceptor.Level.BODY);

            // Crea un Interceptor personalitzat per aplicar capçaleres a tota la app
            Interceptor headerInterceptor = chain -> {
                Request newRequest = chain.request().newBuilder() // Request , li enviem els Headers
                        .addHeader("Accept", "application/json")
                        .addHeader("Authorization", "Bearer " + tokenDeAcceso)
                        .build();
                return chain.proceed(newRequest);
            };

            // Converteix objectes Java a Json o viceversa
            //Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").setLenient().create();
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").setLenient().create();

            // Client HTTP, envia i rep sol·licituds de xarxa basats en HTPP
            OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(5, TimeUnit.SECONDS)
                    .addInterceptor(headerInterceptor)
                    .addInterceptor(logger);

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