package masjuan.ioc.chefdeluxe.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClientToken {
    private static ApiServiceToken API_SERVICE;

    public static final String BASE_URL = "http://ip:8080";

    public static synchronized ApiServiceToken getInstance(final String tokenDeAcceso) {

        // Crear un Logger
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(HttpLoggingInterceptor.Level.BODY);


        if (API_SERVICE == null) {
            // Crea un Interceptor personalitzat per aplicar capçaleres a tota la app
            Interceptor headerInterceptor = chain -> {
                Request newRequest = chain.request().newBuilder()   // Request , li enviem els Headers
                        .addHeader("Accept", "application/json")
                        .addHeader("Authorization", "Bearer " + tokenDeAcceso) // Amb el Bearer?
                        .build();
                return chain.proceed(newRequest);
            };

            Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();

            // Crear OkHttp Client
            OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(5, TimeUnit.SECONDS)
                    .addInterceptor(headerInterceptor)
                    .addInterceptor(logger);

            // Crear Retrofit Builder
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .client(okHttpBuilder.build())
                    .build();

            API_SERVICE = retrofit.create(ApiServiceToken.class);
        }

        return API_SERVICE;
    }

    public static void deleteInstance() {
        API_SERVICE = null;
    }

}