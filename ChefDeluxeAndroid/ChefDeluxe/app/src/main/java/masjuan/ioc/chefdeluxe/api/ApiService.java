package masjuan.ioc.chefdeluxe.api;

import masjuan.ioc.chefdeluxe.model.Login;
import masjuan.ioc.chefdeluxe.model.Token;
import masjuan.ioc.chefdeluxe.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {
    String RUTA_AUTH = "/api/auth/";
    String RUTA_USER = "/api/users/";

    // SESSION
    @Headers("Content-Type: application/json")
    @POST(RUTA_AUTH + "iniciarSesion")
    Call<User> userLogin(@Body Login login);

    @Headers("Content-Type: application/json")
    @POST(RUTA_AUTH + "iniciarSesion")
    Call<Token> tokenLogin(@Body Login login);

}