package masjuan.ioc.chefdeluxe.api;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.Contract;

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
 * Instancia de Retrofit
 * Envia i rep sol·licituds de red a una API
 */
public class ApiClient {

    private static ApiService API_SERVICE;
    private static ApiService API_SERVICE_TOKEN;

    public static synchronized ApiService getInstance() {

        if (API_SERVICE == null) {

            API_SERVICE = retrofit(okHttpClient()).build().create(ApiService.class);
        }

        return API_SERVICE;
    }

    public static synchronized ApiService getInstance(String token) {

        if (API_SERVICE_TOKEN == null) {

            API_SERVICE_TOKEN = retrofit(okHttpClientToken(token)).build().create(ApiService.class);
        }

        return API_SERVICE_TOKEN;
    }

    /**
     * Client REST, que permet fer peticions al servidor
     *
     * @param okhttp client https
     * @return Retrofit
     */
    @NonNull
    private static Retrofit.Builder retrofit(OkHttpClient okhttp) {
        String dateFormat = "yyyy-MM-dd'T'HH:mm:ss";

        return new Retrofit.Builder()
                .baseUrl(GlobalVariables.urlApiRest)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson(dateFormat)))
                .client(okhttp);
    }

    /**
     * Client HTTP, realitza peticions http/2 i spdy
     *
     * @return OkHttpClient
     */
    @NonNull
    private static OkHttpClient okHttpClient() {

        return new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(logInterceptor())
                .build();
    }

    /**
     * Client HTTP, realitza peticions http/2 i spdy
     * amb token d'accés
     *
     * @return OkHttpClient
     */
    @NonNull
    private static OkHttpClient okHttpClientToken(String token) {

        return new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(logInterceptor())
                .addInterceptor(headerInterceptor(token))
                .build();
    }

    /**
     * Converteix objectes Java a Json o viceversa
     *
     * @param dateFormat String date
     * @return Objecte Gson
     */
    @NonNull
    private static Gson gson(String dateFormat) {
        return new GsonBuilder().setDateFormat(dateFormat).setLenient().create();
    }

    /**
     * Intercepta a nivell de Body
     *
     * @return HttpLoggingInterceptor interceptor
     */
    @NonNull
    private static HttpLoggingInterceptor logInterceptor() {
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    /**
     * Crea un Interceptor personalitzat per aplicar capçaleres a tota la app
     *
     * @param token String token
     * @return Interceptor
     */
    @NonNull
    private static Interceptor headerInterceptor(String token) {

        return chain -> {
            Request newRequest = chain.request().newBuilder() // Request , li enviem els Headers
                    .addHeader("Accept", "application/json")
                    .addHeader("Authorization", "Bearer " + token)
                    .build();
            return chain.proceed(newRequest);
        };
    }

    /**
     * S'elimina la instancia
     */
    public static void deleteInstance() {
        API_SERVICE_TOKEN = null;
    }

}