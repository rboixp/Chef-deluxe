package masjuan.ioc.chefdeluxe.api;

import static masjuan.ioc.chefdeluxe.utils.VariablesGlobales.urlBase;

import androidx.viewbinding.BuildConfig;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {

    private static ApiService API_SERVICE;

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
                    .baseUrl(urlBase)
                    .addConverterFactory(GsonConverterFactory.create(gson))
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
