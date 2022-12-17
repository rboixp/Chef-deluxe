package masjuan.ioc.chefdeluxe.api;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Connexió HTTPS acceptant qualsevol certificat
 */
public class UnsafeSSL {

    /**
     * Client Https que es connecta acceptant qualsevol certificat, amb token d'accés
     *
     * @param tokenDeAcceso token d'accés
     * @return OkHttpClient
     */
    public static OkHttpClient getUnsafeOkHttpClient(String tokenDeAcceso) {
        try {
            // Crea un administrador de confiança que no validi les cadenes de certificats
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Administrador de confiança
            SSLContext sslContext = null;
            try {
                sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            } catch (NoSuchAlgorithmException | KeyManagementException e) {
                e.printStackTrace();
            }


            // Creea una SSLSocketFactory amb el nostre administrador de confiança
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            // Registre les dades de les sol·licituds  i respostes HTTP
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            // Nivell de registre
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            // Crea un Interceptor personalitzat per aplicar capçaleres a tota la app
            Interceptor headerInterceptor = chain -> {
                Request newRequest = chain.request().newBuilder() // Request , li enviem els Headers
                        .addHeader("Accept", "application/json")
                        .addHeader("Authorization", "Bearer " + tokenDeAcceso)
                        .build();
                return chain.proceed(newRequest);
            };

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(10, TimeUnit.SECONDS);
            builder.readTimeout(10, TimeUnit.SECONDS);
            builder.writeTimeout(5, TimeUnit.SECONDS);
            builder.addInterceptor(loggingInterceptor);
            builder.addInterceptor(headerInterceptor);

            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier((hostname, session) -> true);


            return builder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Client Https que es connecta acceptant qualsevol certificat, sense token d'accés
     *
     * @return OkHttpClient
     */
    public static OkHttpClient getUnsafeOkHttpClient() {
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            SSLContext sslContext = null;
            try {
                sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            } catch (NoSuchAlgorithmException | KeyManagementException e) {
                e.printStackTrace();
            }

            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(10, TimeUnit.SECONDS);
            builder.readTimeout(10, TimeUnit.SECONDS);
            builder.writeTimeout(5, TimeUnit.SECONDS);
            builder.addInterceptor(loggingInterceptor);

            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier((hostname, session) -> true);

            return builder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

// OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient(); Retrofit...  .client(okHttpClient)
