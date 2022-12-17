package masjuan.ioc.chefdeluxe.fragment.client;

import static masjuan.ioc.chefdeluxe.utils.Methods.navigationToolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import masjuan.ioc.chefdeluxe.R;
import masjuan.ioc.chefdeluxe.api.ApiGlobal;
import masjuan.ioc.chefdeluxe.databinding.FragmentClientSearchCookBinding;
import masjuan.ioc.chefdeluxe.model.Disponibilidad;
import masjuan.ioc.chefdeluxe.model.Tarifa;
import masjuan.ioc.chefdeluxe.utils.ApiCodes;
import masjuan.ioc.chefdeluxe.utils.SharedPreferences;
import masjuan.ioc.chefdeluxe.utils.UtilsFragments;
import masjuan.ioc.chefdeluxe.utils.recyclerView.RvAdapterListCook;
import masjuan.ioc.chefdeluxe.utils.recyclerView.RvItemClickListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Classe on mostra el llistat de disponibilitats dels cuiners
 */
public class CookListClient extends Fragment {

    private FragmentClientSearchCookBinding b;
    private ApiGlobal apiGlobal;
    private ApiCodes apiCodes;
    private UtilsFragments frag = null;
    private SharedPreferences preferences;
    private FragmentManager fragmentManager = null;

    // Disponibilitat
    private List<Disponibilidad> mShowCookAvailableList;
    private RecyclerView recyclerViewCook = null;
    private RvAdapterListCook rvCookAdapter;

    private String village;
    private String dispo = "Activo";

    /**
     * Constructor buit
     */
    public CookListClient() {
        // Required empty public constructor
    }

    /**
     * Crea una nova instancia de CookListClient
     *
     * @return CookListClient
     */
    public static CookListClient newInstance() {
        return new CookListClient();
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
        apiGlobal = new ApiGlobal();
        fragmentManager = requireActivity().getSupportFragmentManager();
    }

    /**
     * Dissenya la interfície d'usuari per primera vegada.
     * Opció de logout
     *
     * @param inflater           Infla la vista
     * @param container          Vista que s'adjuntarà a la interfície d'usuari
     * @param savedInstanceState Bundle
     * @return Vista
     * @author Eduard Masjuan
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        b = FragmentClientSearchCookBinding.inflate(inflater, container, false);
        preferences = new SharedPreferences(requireActivity());
        apiCodes = new ApiCodes();

        // Toolbar popBackStack (ens retorna al fragment)
        navigationToolbar(b.lyToolbar.toolbar, frag, fragmentManager);

        // Títol per la toolbar
        b.lyToolbar.toolbar.setTitle(getResources().getString(R.string.txt_cookList));

        // Chip Listener
        b.chipGroupFilter.setOnCheckedStateChangeListener(chipListener);

        // Llista cuiners
        mShowCookAvailableList = new ArrayList<>();
        // Creem i obtenim un vista per RecyclerView
        recyclerViewCook = b.lyIncludeRecyclerView.rvList;
        recyclerViewCook.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewCook.setHasFixedSize(false);

        // Cridem a FragmentResultListener per rebre les dades enviades
        getParentFragmentManager().setFragmentResultListener("keyCook", this, new FragmentResultListener() {
            /**
             * Rebem les dades passades per el Fragment NavHomeClient
             * @param requestKey Mateixa clau que s'ha donat al Bundle
             * @param result Bundle
             * @author Eduard Masjuan
             */
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                // Una vegada rebut les dades s'eliminen automàticament
                // disponibilitat = result.getString("keyDispo");
                village = result.getString("keyVillage");

                // Obtenim les disponibilitats dels cuiners
                getAvailabilityListCook(dispo, village);
            }
        });

        // Controla el onItemclick al fer clic a un element del Recyclerview
        recyclerViewCook.addOnItemTouchListener(new RvItemClickListener(getActivity(), recyclerViewCook, new RvItemClickListener.OnItemClickListener() {

            /**
             * Quan es fa clic a un item es guarden el nom d'usuari del item seleccionat
             * @param view Vista
             * @param position int, posició de l'element
             */
            @Override
            public void onItemClick(View view, int position) {

                if (dispo.equals("Activo")) {
                    Disponibilidad cookDispo = mShowCookAvailableList.get(position);
                    dataBundle(cookDispo.getUsernameOrEmail());
                    frag.popBackStack(fragmentManager);
                } else {
                    Toast.makeText(getActivity(), "Cocinero no disponible para reservar", Toast.LENGTH_SHORT).show();
                }
            }

            /**
             * Quan es manté el clic a un item
             * @param view Vista
             * @param position int, posició de l'element
             */
            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        return b.getRoot();
    }

    /**
     * Element que permet seleccionar disponibilitat per veure un llistat o altre.
     * Obtenir el llistat de disponibilitats dels cuiners
     *
     * @author Eduard Masjuan
     */
    private final ChipGroup.OnCheckedStateChangeListener chipListener = new ChipGroup.OnCheckedStateChangeListener() {
        @Override
        public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {
            for (Integer id : checkedIds) {
                Chip chip = group.findViewById(id);
                if (chip != null) {
                    if (chip.getText().equals("Activo")) {
                        dispo = "Activo";
                        b.tvSelecCook.setText("Seleccione un cocinero");
                    } else if (chip.getText().equals("Inactivo")) {
                        dispo = "Inactivo";
                        b.tvSelecCook.setText("Listado de cocineros inactivos");
                    }
                    getAvailabilityListCook(dispo, village);
                }
            }
        }
    };

    /**
     * Guarda, envia el nom d'usuari seleccionat
     *
     * @param nameUser String nom d'usuari
     * @author Eduard Masjuan
     */
    private void dataBundle(String nameUser) {
        Bundle dataSend = new Bundle();
        dataSend.putString("keyNameUser", nameUser);
        getParentFragmentManager().setFragmentResult("keyChooseCook", dataSend);
    }

    /**
     * Mètode on realitza una petició GET al servidor. Obtenim en una llista les dades de la
     * disponibilitat dels cuiners
     *
     * @param state   String, estat de la disponibilitat
     * @param village String, població
     * @author Eduard Masjuan
     */
    public void getAvailabilityListCook(String state, String village) {
        //Call<List<Disponibilidad>> available = apiGlobal.apiClientCert(getActivity(), preferences.getToken()).getFilterCookAvailable(state, village);
        Call<List<Disponibilidad>> available = apiGlobal.apiClient(preferences.getToken()).getFilterCookAvailable(state, village);

        available.enqueue(new Callback<List<Disponibilidad>>() {
            /**
             * Es crida si ja una resposta HTTP correcte
             * @param call Sol.licita al API les dades
             * @param response  Obtenir les dades
             */
            @Override
            public void onResponse(@NonNull Call<List<Disponibilidad>> call, @NonNull Response<List<Disponibilidad>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (apiCodes.codeHttp(response.code())) {

                            mShowCookAvailableList = response.body();
                            // Creem un Adaptador i obtenim la llista dels cuiners
                            rvCookAdapter = new RvAdapterListCook(getActivity(), mShowCookAvailableList);
                            recyclerViewCook.setAdapter(rvCookAdapter);

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

}