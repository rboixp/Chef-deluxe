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
import masjuan.ioc.chefdeluxe.databinding.FragmentCookDialogConciliarBinding;
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
public class DialogConfirmPayCook extends BottomSheetDialogFragment {


    private ApiCodes apiCodes;
    private SharedPreferences preferences;
    private static final String ARG_PARAM1 = "keyId";
    private static final String ARG_PARAM2 = "keyClient";
    private static final String ARG_PARAM3 = "keyStartDate";
    private static final String ARG_PARAM4 = "keyFinalDate";
    private static final String ARG_PARAM5 = "keyPerson";
    private static final String ARG_PARAM6 = "keyPrice";
    private long mParamId;
    private String mParamNameClient, mParamStartDate, mParamFinalDate, mParamPerson, mParamPrice;
    private ApiGlobal apiGlobal;

    /**
     * Constructor buit
     */
    public DialogConfirmPayCook() {
        // Required empty public constructor
    }

    /**
     * Crea una nova instancia de DialogConfirmPayCook
     *
     * @param id         long id de la reserva
     * @param nameClient String nom client que fa reserva
     * @return DialogConfirmPayCook
     */
    @NonNull
    public static DialogConfirmPayCook newInstance(long id, String nameClient, String startDate, String finalDate, String person, String price) {
        DialogConfirmPayCook fragment = new DialogConfirmPayCook();
        Bundle args = new Bundle();
        args.putLong(ARG_PARAM1, id);
        args.putString(ARG_PARAM2, nameClient);
        args.putString(ARG_PARAM3, startDate);
        args.putString(ARG_PARAM4, finalDate);
        args.putString(ARG_PARAM5, person);
        args.putString(ARG_PARAM6, price);
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
            mParamStartDate = getArguments().getString(ARG_PARAM3);
            mParamFinalDate = getArguments().getString(ARG_PARAM4);
            mParamPerson = getArguments().getString(ARG_PARAM5);
            mParamPrice = getArguments().getString(ARG_PARAM6);
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
        FragmentCookDialogConciliarBinding b = FragmentCookDialogConciliarBinding.inflate(inflater, container, false);
        preferences = new SharedPreferences(requireActivity());
        apiCodes = new ApiCodes();


        b.tvInfoResum.setText(getResources().getString(R.string.tv_reservation_resum_cook, mParamNameClient, mParamPerson, mParamStartDate, mParamFinalDate));


        // Boto de conciliar la reserva, s’actualitza l'estat a conciliado
        b.tvReservationConciliar.setOnClickListener(view -> {
            updateReservationPageListCook(mParamId, "conciliado");
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