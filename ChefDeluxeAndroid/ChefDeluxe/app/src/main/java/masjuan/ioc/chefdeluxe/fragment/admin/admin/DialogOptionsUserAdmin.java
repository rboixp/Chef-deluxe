package masjuan.ioc.chefdeluxe.fragment.admin.admin;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import masjuan.ioc.chefdeluxe.R;
import masjuan.ioc.chefdeluxe.api.ApiGlobal;
import masjuan.ioc.chefdeluxe.databinding.FragmentAdminDialogOptionsUserBinding;
import masjuan.ioc.chefdeluxe.model.Disponibilidad;
import masjuan.ioc.chefdeluxe.model.Reservation;
import masjuan.ioc.chefdeluxe.utils.ApiCodes;
import masjuan.ioc.chefdeluxe.utils.SharedPreferences;
import masjuan.ioc.chefdeluxe.utils.UtilsFragments;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Diàleg d’opcions  per l'usuari seleccionat. Edició i eliminació d'usuari
 *
 * @author edu
 */
public class DialogOptionsUserAdmin extends BottomSheetDialogFragment {

    private ApiGlobal apiGlobal;
    private ApiCodes apiCodes;
    private SharedPreferences preferences;
    private static final String ARG_PARAM1 = "keyUser";
    private static final String ARG_PARAM2 = "keyUsername";
    private String mParamUserName;
    private String mParamName;

    private List<Long> listId;
    private List<Long> listDispo;

    private UtilsFragments frag = null;

    /**
     * Constructor buit
     */
    public DialogOptionsUserAdmin() {
        // Required empty public constructor
    }

    /**
     * Crea una nova instancia de DialogOptionsUserAdmin
     *
     * @param name     String nom real de l'usuari
     * @param userName String nom de l'usuario
     * @return DialogOptionsUserAdmin
     */
    public static DialogOptionsUserAdmin newInstance(String name, String userName) {
        DialogOptionsUserAdmin fragment = new DialogOptionsUserAdmin();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, name);
        args.putString(ARG_PARAM2, userName);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * onCreate s'executa quan l'activitat  es crea per primera vegada
     *
     * @param savedInstanceState Objecte Bundle que conte l'estat de l'activitat guardat
     * @author Eduard Masjuan
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        frag = new UtilsFragments(requireActivity().getSupportFragmentManager());
        preferences = new SharedPreferences(requireActivity());
        apiCodes = new ApiCodes();
        apiGlobal = new ApiGlobal();

        if (getArguments() != null) {
            mParamName = getArguments().getString(ARG_PARAM1);
            mParamUserName = getArguments().getString(ARG_PARAM2);
        }
    }

    /**
     * Dissenya la interfície d'usuari per primera vegada.
     * Opcions d'edició i eliminació d'usuari
     *
     * @param inflater           Infla la vista
     * @param container          Vista que s'adjuntarà a la interfície d'usuari
     * @param savedInstanceState Bundle
     * @return Vista
     * @author Eduard Masjuan
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        masjuan.ioc.chefdeluxe.databinding.FragmentAdminDialogOptionsUserBinding b = FragmentAdminDialogOptionsUserBinding.inflate(inflater, container, false);

        b.tvTitle.setText(getString(R.string.tv_userName_admin, mParamName));

        // Llistes Array
        listId = new ArrayList<>();
        listDispo = new ArrayList<>();

        getAllReservation();
        getAllAvailability();

        // Edita les dades de l'usuari,
        b.tvEdit.setOnClickListener(view -> {
            // Enviem boolean per saber que estem dins la opció de Edició
            dataSendEdit(true, mParamUserName);
            dismiss();
            frag.replaceFragment(R.id.container_users_admin, NavAddEditUsersAdmin.newInstance());
        });

        // ELimina l'usuari amb les seves disponibilitats i/o reserves
        b.tvDeleted.setOnClickListener(view -> {
            // Elimina usuari
            deleteUser(mParamUserName);

            //Elimina les seves disponibilitats si es chef i si en te
            for (int i = 0; i < listDispo.size(); i++) {
                deleteAvailability(listDispo.get(i));
            }

            // Elimina les reserves en que estigui involucat
            for (int i = 0; i < listId.size(); i++) {
                deleteReservation(listId.get(i));
            }

            // Substitueix fragment
            frag.replaceFragment(R.id.container_users_admin, NavDataUsersAdmin.newInstance());

        });

        // Tanca el dialeg
        b.tvExit.setOnClickListener(view -> dismiss());

        return b.getRoot();
    }

    /**
     * Mètode on realitza una petició DELETE. Elimina la conta de l'usuari.
     *
     * @param username String nom d'usuari
     * @author Eduard Masjuan
     */
    public void deleteUser(String username) {
        //Call<String> user = apiGlobal.apiClientCert(getActivity(), preferences.getToken()).deleteUserData(username);
        Call<String> user = apiGlobal.apiClient(preferences.getToken()).deleteUserData(username);

        user.enqueue(new Callback<String>() {
            /**
             * Es crida si ja una resposta HTTP correcte
             * @param call Sol.licita al API les dades
             * @param response  Obtenir les dades
             */
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        // Comprova el codi rebut, si hi ha error mostrarà un missatge
                        if (apiCodes.codeHttp(response.code())) {

                            // Tanquem diàleg
                            Toast.makeText(getActivity(), "El usuario " + mParamUserName + " se ha eliminado", Toast.LENGTH_SHORT).show();
                            dismiss();

                        }
                    }
                } else {
                    apiCodes.codeHttp(response.code());
                }
            }

            /**
             * Es produeix una excepció de xarxa en la comunicació amb el servidor o una excepció en la gestió de la sol·licitud
             * @param call Sol·licita al API les dades
             * @param t Captura l’excepció
             */
            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                if (t instanceof IOException) {
                    Log.v("Código", t.getMessage() + "  " + getResources().getString(R.string.codigo_onFailure_connexion));
                } else {
                    Log.v("Código", t.getMessage() + "  " + getResources().getString(R.string.codigo_onFailure_conversion));
                }
            }
        });
    }

    /**
     * Mostra totes les Disponibilitats dels cuiners
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

                            for (Disponibilidad dispo : response.body()) {
                                // Busquem les disponibilidats fetes pels Chef i les guardem en una List per eliminar-les
                                if (dispo.getUsernameOrEmail().equals(mParamUserName)) {
                                    listDispo.add(dispo.getId());
                                }
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
     * Elimina la disponibilitat d'un cuiner passant el seu id
     *
     * @param id long id cuiner
     * @author Eduard Masjuan
     */
    public void deleteAvailability(long id) {
        //Call<String> deleteDispo = apiGlobal.apiClientCert(getActivity(), preferences.getToken()).deleteAvailableCook(id);
        Call<String> deleteDispo = apiGlobal.apiClient(preferences.getToken()).deleteAvailableCook(id);

        deleteDispo.enqueue(new Callback<String>() {
            /**
             * Es crida si ja una resposta HTTP correcte
             * @param call Sol.licita al API les dades
             * @param response  Obtenir les dades
             */
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (apiCodes.codeHttp(response.code())) {

                        }
                    }
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.tv_info_reservation_no), Toast.LENGTH_SHORT).show();
                    apiCodes.codeHttp(response.code());
                }
            }

            /**
             * Es produeix una excepció de xarxa en la comunicació amb el servidor o una excepció en la gestió de la sol·licitud
             * @param call Sol·licita al API les dades
             * @param t Captura l’excepció
             */
            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
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
     * Mostra totes les reserves fetes
     *
     * @author Eduard Masjuan
     */
    private void getAllReservation() {
        //Call<List<Reservation>> reservation = apiGlobal.apiClientCert(getActivity(), preferences.getToken()).getAllReservation();
        Call<List<Reservation>> reservation = apiGlobal.apiClient(preferences.getToken()).getAllReservation();

        reservation.enqueue(new Callback<List<Reservation>>() {
            /**
             * Es crida si ja una resposta HTTP correcte
             * @param call Sol.licita al API les dades
             * @param response  Obtenir les dades
             */

            @Override
            public void onResponse(@NonNull Call<List<Reservation>> call, @NonNull Response<List<Reservation>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        // Guardem els id d'usuari a una List
                        for (Reservation reserv : response.body()) {
                            // Busquem les reserves fetes pels clients o Chef i les guardem en uan List per eliminar-les
                            if (reserv.getCliente().equals(mParamUserName) || reserv.getChef().equals(mParamUserName)) {
                                listId.add(reserv.getId());
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
     * Mètode on realitza una petició DELETE al servidor.
     * Elimina una reserva
     *
     * @param id int, id de la reserva
     * @author Eduard Masjuan
     */
    public void deleteReservation(long id) {
        //Call<String> deleteReservation = apiGlobal.apiClientCert(getActivity(), preferences.getToken()).deleteClientReservation(id);
        Call<String> deleteReservation = apiGlobal.apiClient(preferences.getToken()).deleteClientReservation(id);

        deleteReservation.enqueue(new Callback<String>() {
            /**
             * Es crida si ja una resposta HTTP correcte
             * @param call Sol.licita al API les dades
             * @param response  Obtenir les dades
             */
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (apiCodes.codeHttp(response.code())) {
                        }
                    }
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.tv_info_reservation_no), Toast.LENGTH_SHORT).show();
                    apiCodes.codeHttp(response.code());
                }
            }

            /**
             * Es produeix una excepció de xarxa en la comunicació amb el servidor o una excepció en la gestió de la sol·licitud
             * @param call Sol·licita al API les dades
             * @param t Captura l’excepció
             */
            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
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
     * Enviem les dades introduïdes dels camps de text al fragment *****
     * mitjançant un Bundle
     *
     * @param edit     boolean per saber si estem a l'opció de edició
     * @param userName String, nom d'usuari que es vol editar
     * @author Eduard Masjuan
     */
    private void dataSendEdit(boolean edit, String userName) {
        Bundle dataSend = new Bundle();
        dataSend.putBoolean("keyEdit", edit);
        dataSend.putString("keyUserName", userName);
        getParentFragmentManager().setFragmentResult("key", dataSend);
    }

}