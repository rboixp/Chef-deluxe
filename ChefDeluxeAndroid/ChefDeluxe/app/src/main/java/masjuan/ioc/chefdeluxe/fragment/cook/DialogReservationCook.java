package masjuan.ioc.chefdeluxe.fragment.cook;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.IOException;

import masjuan.ioc.chefdeluxe.R;
import masjuan.ioc.chefdeluxe.api.ApiGlobal;
import masjuan.ioc.chefdeluxe.databinding.FragmentCookDialogReservationBinding;
import masjuan.ioc.chefdeluxe.model.Reservation;
import masjuan.ioc.chefdeluxe.utils.ApiCodes;
import masjuan.ioc.chefdeluxe.utils.SharedPreferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Diàleg pels cuiners on poden Confirmar o denegar la reserva.
 *
 * @author Eduard Masjuan
 */
public class DialogReservationCook extends BottomSheetDialogFragment {
    private ApiCodes apiCodes;
    private SharedPreferences preferences;
    private static final String ARG_PARAM1 = "keyId";
    private static final String ARG_PARAM2 = "keyClient";
    private long mParamId;
    private String mParamNameClient;
    private ApiGlobal apiGlobal;

    /**
     * Constructor buit
     */
    public DialogReservationCook() {
        // Required empty public constructor
    }

    /**
     * Crea una nova instancia de DialogReservationCook
     *
     * @param id         long id de la reserva
     * @param nameClient String nom client que fa reserva
     * @return DialogReservationCook
     */
    @NonNull
    public static DialogReservationCook newInstance(long id, String nameClient) {
        DialogReservationCook fragment = new DialogReservationCook();
        Bundle args = new Bundle();
        args.putLong(ARG_PARAM1, id);
        args.putString(ARG_PARAM2, nameClient);
        fragment.setArguments(args);
        return fragment;
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

        if (getArguments() != null) {
            mParamId = getArguments().getLong(ARG_PARAM1);
            mParamNameClient = getArguments().getString(ARG_PARAM2);
        }
    }

    /**
     * Dissenya la interfície d'usuari per primera vegada.
     * Boto de nou usuari
     * Boto de mostra el llistat d'usuaris
     *
     * @param inflater           Infla la vista
     * @param container          Vista que s'adjuntarà a la interfície d'usuari
     * @param savedInstanceState Bundle
     * @return Vista
     * @author Eduard Masjuan
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        masjuan.ioc.chefdeluxe.databinding.FragmentCookDialogReservationBinding b = FragmentCookDialogReservationBinding.inflate(inflater, container, false);
        preferences = new SharedPreferences(requireActivity());
        apiCodes = new ApiCodes();

        b.tvTitle.setText(getResources().getString(R.string.txt_title_reservation_message, mParamNameClient));

        // Boto confirmació reserva, s’actualitza l'estat a Confirmado
        b.tvReservationAcept.setOnClickListener(view -> {
            updateReservationPageListCook(mParamId, "confirmado");
            dismiss();
        });

        // Boto de denegació de reserva, s’actualitza l'estat a Denegado
        b.tvReservationDeny.setOnClickListener(view -> {
            updateReservationPageListCook(mParamId, "rechazado");
            dismiss();
        });

        return b.getRoot();
    }

    /**
     * Mètode que realitza una petició PUT al servidor. Actualiza la llista de reserves dels cuiners
     *
     * @param id     long id reserva
     * @param estado String estat de la reserva
     * @author Eduard Masjuan
     */
    public void updateReservationPageListCook(long id, String estado) {
       //Call<Reservation> reservation = apiGlobal.apiClientCert(getActivity(), preferences.getToken()).putReservaCook(id, estado);
        Call<Reservation> reservation = apiGlobal.apiClient(preferences.getToken()).putReservaCook(id, estado);

        reservation.enqueue(new Callback<Reservation>() {
            /**
             * Es crida si ja una resposta HTTPS correcte
             * @param call Sol.licita al API les dades
             * @param response  Obtenir les dades
             */
            @Override
            public void onResponse(@NonNull Call<Reservation> call, @NonNull Response<Reservation> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (apiCodes.codeHttp(response.code())) {
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
            public void onFailure(@NonNull Call<Reservation> call, @NonNull Throwable t) {
                if (t instanceof IOException) {
                    Log.v("Código", getResources().getString(R.string.codigo_onFailure_connexion));

                } else {
                    Log.v("Código", getResources().getString(R.string.codigo_onFailure_conversion));
                }
            }
        });
    }

}