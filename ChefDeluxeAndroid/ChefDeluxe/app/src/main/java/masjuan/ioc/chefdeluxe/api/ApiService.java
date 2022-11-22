package masjuan.ioc.chefdeluxe.api;

import java.util.List;

import masjuan.ioc.chefdeluxe.model.Disponibilidad;
import masjuan.ioc.chefdeluxe.model.Login;
import masjuan.ioc.chefdeluxe.model.Registration;
import masjuan.ioc.chefdeluxe.model.Reservation;
import masjuan.ioc.chefdeluxe.model.Token;
import masjuan.ioc.chefdeluxe.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Interfície ApiService amb mètodes que es fan servir per executar sol·licituds HTTP.
 *
 * @author Eduard Masjuan
 */
public interface ApiService {
    String RUTA_AUTH = "/api/auth/";
    String RUTA_USER = "/api/users/";
    String RUTA_CHEF = "/api/chef/";
    String RUTA_CLIENT = "/api/client/";

    // SESSION

    /**
     * Capturem el Token al introduir l'objecte Login
     *
     * @param login Objecte Login
     * @return Token
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST(RUTA_AUTH + "iniciarSesion")
    Call<Token> tokenLogin(@Body Login login);

    /**
     * Registre d'un nou usuari
     *
     * @param register Objecte Registration
     * @return String
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST(RUTA_AUTH + "registrar")
    Call<String> addUser(@Body Registration register);


    //CRUD

    /**
     * Obtenir tots els usuaris de la BD
     * Per usuaris Administrador
     *
     * @return Llista d'usuaris
     */
    @GET(RUTA_USER + "get/users")
    Call<List<User>> getAllUsers();

    /**
     * Obtenir les dades d'un usuari passat per paràmetre
     *
     * @param usernameOrEmail String, nom d'usuari o correo electrònic
     * @return User
     */
    @GET(RUTA_USER + "get/user")
    Call<User> getRol(@Query("usernameOrEmail") String usernameOrEmail);

    /**
     * Modifica les dades d'un usuari
     *
     * @param usernameOrEmail String, nom d'usuari o correo electrònic
     * @param register        Objecte register
     * @return User
     */
    @PUT(RUTA_USER + "update/user")
    Call<User> putUserData(@Query("usernameOrEmail") String usernameOrEmail, @Body Registration register);

    /**
     * Elimina un usuari
     *
     * @param usernameOrEmail String, nom d'usuari o correo electrònic
     * @return String
     */
    @DELETE(RUTA_USER + "delete/user")
    Call<String> deleteUserData(@Query("usernameOrEmail") String usernameOrEmail);

    /**
     * Alta en la disponibilitat del cuiner
     *
     * @param availability Objecte availability
     * @return String
     */
    @POST(RUTA_CHEF + "disponibilidad/post")
    Call<String> postAvailability(@Body Disponibilidad availability);

    /**
     * Obtenim la disponibilitat d'un cuiner passat per paràmetre
     *
     * @param usernameOrEmail String, nom d'usuari o correo electrònic
     * @return Llistat Disponibilidad
     */
    @GET(RUTA_CHEF + "disponibilidad/get/username")
    Call<List<Disponibilidad>> getAvailableUser(@Query("usernameOrEmail") String usernameOrEmail);

    /**
     * Modifica la disponibilitat del cuiner
     *
     * @param usernameOrEmail String, nom d'usuari o correo electrònic
     * @param poblacion       String, poble on es vol canviar la disponibilitat
     * @param availability    Objecte availability
     * @return Disponibilidad
     */
    @PUT(RUTA_CHEF + "disponibilidad/update/estado")
    Call<Disponibilidad> putAvailableUser2(@Query("usernameOrEmail") String usernameOrEmail,
                                           @Query("poblacion") String poblacion,
                                           @Body Disponibilidad availability);

    /**
     * Obtenir un llistat de cuiners amb filtres
     *
     * @param estado    String, estat actiu o inactiu
     * @param poblacion String poble on sera disponible
     * @return Llista de disponibilitat
     */
    @GET(RUTA_CHEF + "disponibilidad/get/filtrado")
    Call<List<Disponibilidad>> getFilterCookAvailable(@Query("estado") String estado,
                                                      @Query("poblacion") String poblacion);

    /**
     * Alta d'una reserva
     *
     * @param reservation Objecte reservation
     * @return String
     */
    @POST(RUTA_CLIENT + "reserva/post")
    Call<String> postReservaClient(@Body Reservation reservation);

    /**
     * Obtenir un llistat de reserves fetes pels clients
     *
     * @param usernameOrEmail String, nom d'usuari del client
     * @param pageIndex       int, paginació primer index
     * @param pageSize        int, paginació tamany total
     * @return Llistat de Reservation
     */
    @GET(RUTA_CLIENT + "reserva/get/client/paginable")
    Call<List<Reservation>> getPagClientReservation(@Query("usernameOrEmail") String usernameOrEmail,
                                                    @Query("pageIndex") int pageIndex,
                                                    @Query("pageSize") int pageSize);

    /**
     * Elimina una reserva feta per un client donant el id
     *
     * @param id long, id de la reserva
     * @return String
     */
    @DELETE(RUTA_CLIENT + "reserva/delete")
    Call<String> deleteClientReservation(@Query("id") long id);


    /**
     * Obtenir un llistat de reserves fetes pels clients per els cuiners
     *
     * @param usernameOrEmail String, nom d'usuari del cuiner
     * @param pageIndex       int, paginació primer index
     * @param pageSize        int, paginació tamany total
     * @return llista de disponibilitats
     */
    @GET(RUTA_CLIENT + "reserva/get/chef/paginable")
    Call<List<Reservation>> getPagChefReservation(@Query("usernameOrEmail") String usernameOrEmail,
                                                  @Query("pageIndex") int pageIndex,
                                                  @Query("pageSize") int pageSize);


    //ADMIN

    /**
     * Obtenir un llistat de cuiners (ADMIN)
     *
     * @return LLista de disponibilitats
     */
    @GET(RUTA_CHEF + "disponibilidad/get/all")
    Call<List<Disponibilidad>> getAllCooks();

    /**
     * Elimina la disponibilitat d'un cuiner (ADMIN)
     *
     * @param id int, id d'un cuiner
     * @return String
     */
    @DELETE(RUTA_CHEF + "disponibilidad/delete")
    Call<String> deleteAvailableCook(@Query("id") Long id);

    /**
     * Obtenir una reserva per id feta per un client
     * Admin o chef
     *
     * @param id long, id reserva
     * @return llista Reservation
     */
    @GET(RUTA_CLIENT + "reserva/get/id")
    Call<Reservation> getFilterIdClientReservation(@Query("id") long id);

}