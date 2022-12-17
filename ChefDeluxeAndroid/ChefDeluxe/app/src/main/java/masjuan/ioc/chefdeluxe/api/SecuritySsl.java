package masjuan.ioc.chefdeluxe.api;

import android.content.Context;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import masjuan.ioc.chefdeluxe.R;
import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.TlsVersion;
import okhttp3.logging.HttpLoggingInterceptor;

public class SecuritySsl {

    private final OkHttpClient client;
    private final Context context;

    public SecuritySsl(Context context) {
        this.context = context;
        X509TrustManager trustManager;
        SSLSocketFactory sslSocketFactory;

        try {
            trustManager = trustManagerForCertificates(trustedCertificatesInputStream());

            // Crea un SSLContext que fa servir el TrustManager creat
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{trustManager}, null);
            sslSocketFactory = sslContext.getSocketFactory();

        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }

        client = new OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory, trustManager)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .connectionSpecs(Collections.singletonList(spec()))
                .retryOnConnectionFailure(true)
                .hostnameVerifier((s, sslSession) -> true)
                .addInterceptor(logInterceptor())
                .build();
    }

    public SecuritySsl(Context context, String token) {
        this.context = context;
        X509TrustManager trustManager;
        SSLSocketFactory sslSocketFactory;

        try {
            trustManager = trustManagerForCertificates(trustedCertificatesInputStream());

            // Crea un SSLContext que fa servir el TrustManager creat
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{trustManager}, null);
            sslSocketFactory = sslContext.getSocketFactory();

        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }

        client = new OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory, trustManager)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .connectionSpecs(Collections.singletonList(spec()))
                .retryOnConnectionFailure(true)
                .hostnameVerifier((s, sslSession) -> true)
                .addInterceptor(headerInterceptor(token))
                .addInterceptor(logInterceptor())
                .build();
    }

    public OkHttpClient getClient() {
        return client;
    }

    @NonNull
    private HttpLoggingInterceptor logInterceptor() {
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    /**
     * Crea un Interceptor personalitzat per aplicar capçaleres a tota la app
     *
     * @param token String token
     * @return Interceptor
     */
    @NonNull
    private Interceptor headerInterceptor(String token) {

        return chain -> {
            Request newRequest = chain.request().newBuilder() // Request , li enviem els Headers
                    .addHeader("Accept", "application/json")
                    .addHeader("Authorization", "Bearer " + token)
                    .build();
            return chain.proceed(newRequest);
        };
    }

    @NonNull
    private ConnectionSpec spec() {
        return new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.TLS_1_2)
                .cipherSuites(
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256)
                .build();
    }

    /**
     * Certificat
     *
     * @return InputStream abm el certificat
     */
    private InputStream trustedCertificatesInputStream() {
        return context.getResources().openRawResource(R.raw.ca);
    }

    private X509TrustManager trustManagerForCertificates(InputStream in) throws GeneralSecurityException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");

        Collection<? extends Certificate> certificates = certificateFactory.generateCertificates(in);

        //Agrega el certificat autofirmat al magatzem de claus
        char[] password = "123456".toCharArray();
        KeyStore keyStore = newEmptyKeyStore(password);
        for (Certificate certificate : certificates) {
            keyStore.setCertificateEntry("client", certificate);
        }

        // Assignem i inicialitzem un KeyManagerFactory
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, password);

        // Assignem i inicialitzem un TrustManagerFactory que confi en la CA de la Keystore que hem creat
        // Validar certificats del servidor, i al crear una CA desde KeyStore seran les uniques en les que confiara el TrustManager
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);

        // Identifica objectes de confiança
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        return (X509TrustManager) trustManagers[0];
    }

    /**
     * Crea un magatzem de claus (keystore) i inserta la clau publica del certifica que hem llegit
     *
     * @param password 123456
     * @return KeyStyore
     */
    @NonNull
    private KeyStore newEmptyKeyStore(char[] password) throws GeneralSecurityException {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            InputStream in = null;
            keyStore.load(in, password);

            return keyStore;

        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

}