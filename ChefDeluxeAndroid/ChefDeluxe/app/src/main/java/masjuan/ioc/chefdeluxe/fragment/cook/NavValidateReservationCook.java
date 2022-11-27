package masjuan.ioc.chefdeluxe.fragment.cook;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import masjuan.ioc.chefdeluxe.R;
import masjuan.ioc.chefdeluxe.api.ApiClientToken;
import masjuan.ioc.chefdeluxe.api.ApiService;
import masjuan.ioc.chefdeluxe.databinding.FragmentCookNavHomeBinding;
import masjuan.ioc.chefdeluxe.model.Reservation;
import masjuan.ioc.chefdeluxe.utils.ApiCodes;
import masjuan.ioc.chefdeluxe.utils.SharedPreferences;
import masjuan.ioc.chefdeluxe.utils.UtilsFragments;
import masjuan.ioc.chefdeluxe.utils.recyclerView.RvAdapterListReservationCook;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Classe on els cuiners se li mostraran les notificacions i reserves rebudes i acceptades
 *
 * @author Eduard Masjuan
 */
public class NavValidateReservationCook extends Fragment {
    private FragmentCookNavHomeBinding b;
    private SharedPreferences preferences;
    private final UtilsFragments frag = null;
    private ApiCodes apiCodes;

    // Reservation
    private List<Reservation> mShowChefReservationList;
    private RecyclerView recyclerViewChef = null;
    private RvAdapterListReservationCook rvReservationAdapter;


    /**
     * Constructor
     */
    public NavValidateReservationCook() {
        // Required empty public constructor
    }

    /**
     * Crea una nova instancia de NavValidateReservationCook
     *
     * @return NavValidateReservationCook
     */
    public static NavValidateReservationCook newInstance() {
        return new NavValidateReservationCook();
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
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
    }

    /**
     * Dissenya la interfície d'usuari
     *
     * @param inflater           Infla la vista
     * @param container          Vista que s'adjuntarà a la interfície d'usuari
     * @param savedInstanceState Bundle
     * @return Vista
     * @author Eduard Masjuan
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        b = FragmentCookNavHomeBinding.inflate(inflater, container, false);
        preferences = new SharedPreferences(requireActivity());
        apiCodes = new ApiCodes();

        // Títol per la toolbar
        b.lyToolbar.toolbar.setTitle(getResources().getString(R.string.tv_acc_home));

        // Llista reserves
        mShowChefReservationList = new ArrayList<>();
        // Creem i obtenim un vista per RecyclerView
        recyclerViewChef = b.lyIncludeRecyclerView.rvList;
        recyclerViewChef.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewChef.setHasFixedSize(false);

        // Mostra les reserves
        getReservationPageListCook(preferences.getUsername(), 0, 1000);

        return b.getRoot();
    }


    /**
     * Mètode on realitza una petició GET al servidor.
     * Rep una llista dels clients que volen contractar el servei d'un cuiner
     *
     * @param userName  String, nom d'usuari
     * @param pageIndex int, inici paginació
     * @param pageSize  int, final paginació
     * @author Eduard Masjuan
     */
    public void getReservationPageListCook(String userName, int pageIndex, int pageSize) {
        ApiService apiService = ApiClientToken.getInstance(preferences.getToken());
        Call<List<Reservation>> reservation = apiService.getPagChefReservation(userName, pageIndex, pageSize);
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
                        if (apiCodes.codeHttp(response.code())) {

                            mShowChefReservationList = response.body();
                            // Creem un Adaptador i obtenim la llista de les reserves
                            rvReservationAdapter = new RvAdapterListReservationCook(getActivity(), mShowChefReservationList);
                            recyclerViewChef.setAdapter(rvReservationAdapter);
                        }
                    }
                } else {
                    Toast.makeText(getActivity(), "No tienes peticiones de reserva", Toast.LENGTH_SHORT).show();
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

}