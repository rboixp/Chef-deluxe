package masjuan.ioc.chefdeluxe.api;

import java.util.List;

import masjuan.ioc.chefdeluxe.model.Role;
import masjuan.ioc.chefdeluxe.model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServiceToken {
    String RUTA_USER = "/api/users/";

    //CRUD

    // Obtenir tots els usuaris de la BD
    @GET(RUTA_USER + "get/users")
    Call<List<User>> getAllUsers();

    // Obtenir un usuari passat per parametre
    @GET(RUTA_USER + "get/user")
    Call<User> getRol(@Query("usernameOrEmail") String usernameOrEmail, @Query("password") String password);

}
