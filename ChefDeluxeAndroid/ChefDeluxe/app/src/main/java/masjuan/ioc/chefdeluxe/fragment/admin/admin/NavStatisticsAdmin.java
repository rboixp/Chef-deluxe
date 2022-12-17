package masjuan.ioc.chefdeluxe.fragment.admin.admin;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import masjuan.ioc.chefdeluxe.R;
import masjuan.ioc.chefdeluxe.api.ApiGlobal;
import masjuan.ioc.chefdeluxe.databinding.FragmentAdminStatisticsBinding;
import masjuan.ioc.chefdeluxe.model.Disponibilidad;
import masjuan.ioc.chefdeluxe.model.Reservation;
import masjuan.ioc.chefdeluxe.model.Role;
import masjuan.ioc.chefdeluxe.model.Tarifa;
import masjuan.ioc.chefdeluxe.model.User;
import masjuan.ioc.chefdeluxe.utils.ApiCodes;
import masjuan.ioc.chefdeluxe.utils.SharedPreferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Classe on es mostra estadístiques de la app. Només per administradors
 * Numero de usuaris, de reserves i disponibilitats.
 *
 * @author Eduard Masjuan
 */
public class NavStatisticsAdmin extends Fragment {

    private SharedPreferences preferences;
    private FragmentAdminStatisticsBinding b;
    private ApiCodes apiCodes;
    private ApiGlobal apiGlobal;

    /**
     * Constructor buit
     */
    public NavStatisticsAdmin() {
        // Required empty public constructor
    }

    /**
     * Crea una nova instancia de NavStatisticsAdmin
     *
     * @return NavStatisticsAdmin
     */
    public static NavStatisticsAdmin newInstance() {
        return new NavStatisticsAdmin();
    }

    /**
     * S'executa quan l'activitat es crea per primera vegada
     *
     * @param savedInstanceState Objecte Bundle que conte l'estat de l'activitat guardat
     * @author Eduard Masjuan
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiGlobal = new ApiGlobal();
    }

    /**
     * Dissenya la interfície d'usuari per primera vegada.
     * Opcions de modificació de dades d'usuari.
     * Canvi de contrasenya
     * Logout
     * Eliminar conta d'usuari
     *
     * @param inflater           Infla la vista
     * @param container          Vista que s'adjuntarà a la interfície d'usuari
     * @param savedInstanceState Bundle
     * @return Vista
     * @author Eduard Masjuan
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        b = FragmentAdminStatisticsBinding.inflate(inflater, container, false);
        preferences = new SharedPreferences(requireActivity());
        apiCodes = new ApiCodes();

        getAllUser();
        getAllAvailability();
        getAllReservation();
        getAllTarifa();

        return b.getRoot();
    }

    /**
     * Mètode on realitza una petició GET al servidor. Recopilem les dades de tots els usuaris
     *
     * @author Eduard Masjuan
     */
    private void getAllUser() {
        //Call<List<User>> user = apiGlobal.apiClientCert(getActivity(), preferences.getToken()).getAllUsers();
        Call<List<User>> user = apiGlobal.apiClient(preferences.getToken()).getAllUsers();

        user.enqueue(new Callback<List<User>>() {
            /**
             * Es crida si ja una resposta HTTP correcte
             * @param call Sol.licita al API les dades
             * @param response  Obtenir les dades
             */
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        // Numero d'usuaris creats totals i rols
                        int contadorTotal = 0, contadorAdmin = 0, contadorCliente = 0, contadorCocinero = 0;
                        for (User user : response.body()) {
                            for (Role role : user.getRoles()) {
                                contadorTotal++;
                                switch (role.getRole()) {
                                    case "ROLE_ADMIN":
                                        contadorAdmin++;
                                        b.tvNumberAdmin.setText("Administradores: " + contadorAdmin);
                                        break;

                                    case "ROLE_CLIENT":
                                        contadorCliente++;
                                        b.tvNumberClient.setText("Clientes: " + contadorCliente);
                                        break;

                                    case "ROLE_CHEF":
                                        contadorCocinero++;
                                        b.tvNumberCook.setText("Cocineros: " + contadorCocinero);
                                        break;
                                }
                                b.tvNumberUsers.setText("Usuarios: " + contadorTotal);
                            }
                        }

                    } else {
                        Toast.makeText(getActivity(), getResources().getString(R.string.tv_update_error2), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getActivity(), "Codi:" + response.code() + getResources().getString(R.string.codigo_401), Toast.LENGTH_SHORT).show();
                    apiCodes.codeHttp(response.code());
                }
            }

            /**
             * Es produeix una excepció de xarxa en la comunicació amb el servidor o una excepció en la gestió de la sol·licitud
             * @param call Sol·licita al API les dades
             * @param t Captura l’excepció
             */
            @Override
            public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
                if (t instanceof IOException) {
                    Log.v("Código", getResources().getString(R.string.codigo_onFailure_connexion));
                    Toast.makeText(getActivity(), getResources().getString(R.string.codigo_onFailure_connexion), Toast.LENGTH_SHORT).show();
                } else {
                    Log.v("Código", getResources().getString(R.string.codigo_onFailure_conversion));
                    Toast.makeText(getActivity(), getResources().getString(R.string.codigo_onFailure_connexion), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Mètode on realitza una petició GET al servidor. Obtenim una llista les dades de la
     * disponibilitat dels cuiners
     *
     * @author Eduard Masjuan
     */
    public void getAllAvailability() {
        //Call<List<Disponibilidad>> available = apiGlobal.apiClientCert(getActivity(), preferences.getToken()).getAllDisponibilidad();
        Call<List<Disponibilidad>> available = apiGlobal.apiClient(preferences.getToken()).getAllDisponibilidad();

        available.enqueue(new Callback<List<Disponibilidad>>() {
            /**
             * Es crida si ja una resposta HTTP correcte
             * @param call Sol.licita al API les dades
             * @param response  Obtenir les dades
             */
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<List<Disponibilidad>> call, @NonNull Response<List<Disponibilidad>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (apiCodes.codeHttp(response.code())) {

                            // Contadors per les disponibilitats
                            int contadorTotal = 0, contadorActivas = 0, contadorInactivas = 0;
                            for (Disponibilidad disponibilidad : response.body()) {
                                contadorTotal++;
                                switch (disponibilidad.getEstado()) {
                                    case "Activo":
                                        contadorActivas++;
                                        b.tvNumberAvailabilityActive.setText("Activas: " + contadorActivas);
                                        break;

                                    case "Inactivo":
                                        contadorInactivas++;
                                        b.tvNumberAvailabilityInactive.setText("Inactivas: " + contadorInactivas);
                                        break;
                                }
                                b.tvNumberAvailability.setText("Disponibilidades: " + contadorTotal);
                            }

                        }
                    }
                } else {
                    Toast.makeText(getActivity(), "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                    apiCodes.codeHttp(response.code());
                }
            }

            /**
             * Es produeix una excepció de xarxa en la comunicació amb el servidor o una excepció en la gestió de la sol·licitud
             * @param call Sol·licita al API les dades
             * @param t Captura l’excepció
             */
            @Override
            public void onFailure(@NonNull Call<List<Disponibilidad>> call, @NonNull Throwable t) {
                if (t instanceof IOException) {
                    Log.v("Código", getResources().getString(R.string.codigo_onFailure_connexion));
                    Toast.makeText(getActivity(), getResources().getString(R.string.codigo_onFailure_connexion), Toast.LENGTH_SHORT).show();
                } else {
                    Log.v("Código", getResources().getString(R.string.codigo_onFailure_conversion));
                    Toast.makeText(getActivity(), getResources().getString(R.string.codigo_onFailure_connexion), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Mètode on realitza una petició GET al servidor. Obtenim una llista de les reserves
     *
     * @author Eduard Masjuan
     */
    public void getAllReservation() {
        //Call<List<Reservation>> available = apiGlobal.apiClientCert(getActivity(), preferences.getToken()).getAllReservation();
        Call<List<Reservation>> available = apiGlobal.apiClient(preferences.getToken()).getAllReservation();

        available.enqueue(new Callback<List<Reservation>>() {
            /**
             * Es crida si ja una resposta HTTP correcte
             * @param call Sol.licita al API les dades
             * @param response  Obtenir les dades
             */
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<List<Reservation>> call, @NonNull Response<List<Reservation>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (apiCodes.codeHttp(response.code())) {

                            // Número total de reserves
                            int contadorTotal = 0, contadorPendiente = 0, contadorConfirmado = 0, contadorRechazado = 0, contadorPagado = 0, contadorConciliado = 0;
                            for (Reservation reservation : response.body()) {
                                contadorTotal++;
                                switch (reservation.getEstado()) {
                                    case "pendiente":
                                        contadorPendiente++;
                                        b.tvNumberReservationPendientes.setText("Pendientes: " + contadorPendiente);
                                        break;

                                    case "confirmado":
                                        contadorConfirmado++;
                                        b.tvNumberReservationConfirmadas.setText("Confirmadas: " + contadorConfirmado);
                                        break;

                                    case "rechazado":
                                        contadorRechazado++;
                                        b.tvNumberReservationRechazada.setText("Rechazadas: " + contadorRechazado);
                                        break;

                                    case "pagado":
                                        contadorPagado++;
                                        b.tvNumberReservationPagadas.setText("Pagadas: " + contadorPagado);
                                        break;

                                    case "conciliado":
                                        contadorConciliado++;
                                        b.tvNumberReservationConciliadas.setText("Conciliadas: " + contadorConciliado);
                                        break;

                                }
                                b.tvNumberReservation.setText("Reservas: " + contadorTotal);
                            }
                        }
                    }
                } else {
                    Toast.makeText(getActivity(), "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                    apiCodes.codeHttp(response.code());
                }
            }

            /**
             * Es produeix una excepció de xarxa en la comunicació amb el servidor o una excepció en la gestió de la sol·licitud
             * @param call Sol·licita al API les dades
             * @param t Captura l’excepció
             */
            @Override
            public void onFailure(@NonNull Call<List<Reservation>> call, @NonNull Throwable t) {
                if (t instanceof IOException) {
                    Log.v("Código", getResources().getString(R.string.codigo_onFailure_connexion));
                    Toast.makeText(getActivity(), getResources().getString(R.string.codigo_onFailure_connexion), Toast.LENGTH_SHORT).show();
                } else {
                    Log.v("Código", getResources().getString(R.string.codigo_onFailure_conversion));
                    Toast.makeText(getActivity(), getResources().getString(R.string.codigo_onFailure_connexion), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Mètode on realitza una petició GET al servidor. Obtenim una llista de les tarifes
     *
     * @author Eduard Masjuan
     */
    public void getAllTarifa() {
        //Call<List<Tarifa>> tarifa = apiGlobal.apiClientCert(getActivity(), preferences.getToken()).getAllTarifa(0, 100);
        Call<List<Tarifa>> tarifa = apiGlobal.apiClient(preferences.getToken()).getAllTarifa(0, 100);

        tarifa.enqueue(new Callback<List<Tarifa>>() {
            /**
             * Es crida si ja una resposta HTTP correcte
             * @param call Sol.licita al API les dades
             * @param response  Obtenir les dades
             */
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<List<Tarifa>> call, @NonNull Response<List<Tarifa>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (apiCodes.codeHttp(response.code())) {

                            // Contadors total tarifa
                            int contadorTotal = 0, contadorInf = 0, contadorSup = 0;
                            for (Tarifa tarifa : response.body()) {
                                contadorTotal++;

                                BigDecimal t = tarifa.getPrecioHora();

                                if (t.longValue() <= 20) {
                                    contadorInf++;
                                    b.tvNumberTarifaInf.setText("Inferiores o iguales a 20€: " + contadorInf);
                                }

                                if(t.longValue() > 20) {
                                    contadorSup++;
                                    b.tvNumberTarifaSup.setText("Superiores a 20€: " + contadorSup);
                                }

                                b.tvNumberTarifa.setText("Tarifas: " + contadorTotal);

                            }
                        }
                    }
                } else {
                    Toast.makeText(getActivity(), "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                    apiCodes.codeHttp(response.code());
                }
            }

            /**
             * Es produeix una excepció de xarxa en la comunicació amb el servidor o una excepció en la gestió de la sol·licitud
             * @param call Sol·licita al API les dades
             * @param t Captura l’excepció
             */
            @Override
            public void onFailure(@NonNull Call<List<Tarifa>> call, @NonNull Throwable t) {
                if (t instanceof IOException) {
                    Log.v("Código", getResources().getString(R.string.codigo_onFailure_connexion));
                    Toast.makeText(getActivity(), getResources().getString(R.string.codigo_onFailure_connexion), Toast.LENGTH_SHORT).show();
                } else {
                    Log.v("Código", getResources().getString(R.string.codigo_onFailure_conversion));
                    Toast.makeText(getActivity(), getResources().getString(R.string.codigo_onFailure_connexion), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}