package masjuan.ioc.chefdeluxe.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import masjuan.ioc.chefdeluxe.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient {

    private static ApiService API_SERVICE;

    public static final String BASE_URL = "http://ip:8080";

    public static synchronized ApiService getInstance() {
        if (API_SERVICE == null) {

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            if (BuildConfig.DEBUG) { // Per no exposar els registres a consola, ja que es mostren dades sensibles
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            } else {
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
            }

            Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").setLenient().create();

            OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(5, TimeUnit.SECONDS)
                    .addInterceptor(loggingInterceptor);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .client(okHttpBuilder.build())
                    .build();

            API_SERVICE = retrofit.create(ApiService.class);
        }

        return API_SERVICE;
    }


    public static void deleteInstance() {
        API_SERVICE = null;
    }

}
