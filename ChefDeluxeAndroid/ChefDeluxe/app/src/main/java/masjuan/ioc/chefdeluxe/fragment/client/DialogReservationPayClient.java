package masjuan.ioc.chefdeluxe.fragment.client;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.IOException;

import masjuan.ioc.chefdeluxe.R;
import masjuan.ioc.chefdeluxe.api.ApiGlobal;
import masjuan.ioc.chefdeluxe.databinding.FragmentClientDialogReservationPayBinding;
import masjuan.ioc.chefdeluxe.model.Reservation;
import masjuan.ioc.chefdeluxe.model.User;
import masjuan.ioc.chefdeluxe.utils.ApiCodes;
import masjuan.ioc.chefdeluxe.utils.SharedPreferences;
import masjuan.ioc.chefdeluxe.utils.UtilsFragments;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Diáleg per finalitzar el pagament. Per a clients
 */
public class DialogReservationPayClient extends BottomSheetDialogFragment {
    private ApiCodes apiCodes;
    private SharedPreferences preferences;
    private ApiGlobal apiGlobal;

    private static final String ARG_PARAM1 = "keyId";
    private static final String ARG_PARAM2 = "keyClient";
    private static final String ARG_PARAM3 = "keyPrice";
    private long mParamId;
    private FragmentClientDialogReservationPayBinding b;
    private long mParamPrice;
    String iban = "";

    /**
     * Constructor buit
     */
    public DialogReservationPayClient() {
        // Required empty public constructor
    }

    @NonNull
    public static DialogReservationPayClient newInstance(long id, String nameClient, long price) {
        DialogReservationPayClient fragment = new DialogReservationPayClient();
        Bundle args = new Bundle();
        args.putLong(ARG_PARAM1, id);
        args.putString(ARG_PARAM2, nameClient);
        args.putLong(ARG_PARAM3, price);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UtilsFragments frag = new UtilsFragments(requireActivity().getSupportFragmentManager());
        preferences = new SharedPreferences(requireActivity());
        apiCodes = new ApiCodes();
        apiGlobal = new ApiGlobal();

        if (getArguments() != null) {
            mParamId = getArguments().getLong(ARG_PARAM1);
            String mParamNameClient = getArguments().getString(ARG_PARAM2);
            mParamPrice = getArguments().getLong(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        b = FragmentClientDialogReservationPayBinding.inflate(inflater, container, false);

        //Agafem IBAN
        getUser(preferences.getUsername());

        b.tvPrice.setText("Precio: " + mParamPrice + "€");

        b.tvReservationPayAcept.setOnClickListener(view -> {
            updateReservationPageListCook(mParamId, "pagado");
            Toast.makeText(requireActivity(), "Pago hecho", Toast.LENGTH_SHORT).show();
            dismiss();
        });

        b.tvReservationPayDeny.setOnClickListener(view -> dismiss());

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
       // Call<Reservation> reservation = apiGlobal.apiClientCert(getActivity(), preferences.getToken()).putReservaCook(id, estado);
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

    /**
     * Mètode on realitza una petició GET al servidor. Rebem les dades de l'usuari passat per paràmetre
     *
     * @param username String, nom d'usuari del cuiner
     * @author Eduard Masjuan
     */
    private void getUser(String username) {
        //Call<User> user = apiGlobal.apiClientCert(getActivity(), preferences.getToken()).getRol(username);
        Call<User> user = apiGlobal.apiClient(preferences.getToken()).getRol(username);

        user.enqueue(new Callback<User>() {
            /**
             * Es crida si ja una resposta HTTPS correcte
             * @param call Sol.licita al API les dades
             * @param response  Obtenir les dades
             */
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        iban = response.body().getIban();
                        b.tvAccBancNumber.setText(iban);

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
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
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