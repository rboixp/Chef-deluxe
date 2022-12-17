package masjuan.ioc.chefdeluxe.api;

import android.content.Context;

public class ApiGlobal {

    public ApiService apiClient() {
        return ApiClient.getInstance();
    }

    public ApiService apiClient(String token) {
        return ApiClient.getInstance(token);
    }

    public ApiService apiClientCert(Context context) {
        return ApiClientSSL.getInstance(context);
    }

    public ApiService apiClientCert(Context context, String token) {
        return ApiClientSSL.getInstance(context, token);
    }

}
