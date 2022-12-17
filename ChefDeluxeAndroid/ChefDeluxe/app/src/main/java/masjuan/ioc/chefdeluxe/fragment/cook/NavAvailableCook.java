package masjuan.ioc.chefdeluxe.fragment.cook;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import masjuan.ioc.chefdeluxe.R;
import masjuan.ioc.chefdeluxe.api.ApiClientSSL;
import masjuan.ioc.chefdeluxe.api.ApiGlobal;
import masjuan.ioc.chefdeluxe.api.ApiService;
import masjuan.ioc.chefdeluxe.databinding.FragmentCookNavAvailabilityBinding;
import masjuan.ioc.chefdeluxe.model.Disponibilidad;
import masjuan.ioc.chefdeluxe.model.Tarifa;
import masjuan.ioc.chefdeluxe.utils.ApiCodes;
import masjuan.ioc.chefdeluxe.utils.Methods;
import masjuan.ioc.chefdeluxe.utils.SharedPreferences;
import masjuan.ioc.chefdeluxe.utils.recyclerView.RvAdapterListAvailableCook;
import masjuan.ioc.chefdeluxe.utils.recyclerView.RvItemClickListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Classe on els cuiners podran afegir la seva disponabilitat per poblacions
 *
 * @author Eduard Masjuan
 */
public class NavAvailableCook extends Fragment {

    private FragmentCookNavAvailabilityBinding b;
    private ApiCodes apiCodes;
    private Methods method;
    private SharedPreferences preferences;
    private ApiGlobal apiGlobal;

    private String[] villageArray;
    private String villageInput;
    private String state = "";
    private boolean selectAddEdit = false;
    private long price = -1;


    // Reservation
    private List<Disponibilidad> mShowClientAvailableList;
    private RecyclerView recyclerViewCook = null;
    private RvAdapterListAvailableCook rvAvailableAdapter;

    // Boolean per comparar l'estat d'un poble amb l'estat que li don l'usuari al editar
    boolean sameState = false;

    /**
     * Constructor
     */
    public NavAvailableCook() {
        // Required empty public constructor
    }

    /**
     * Crea una nova instancia de NavAvailableCook
     *
     * @return NavAvailableCook
     */
    public static NavAvailableCook newInstance() {
        return new NavAvailableCook();
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
        b = FragmentCookNavAvailabilityBinding.inflate(inflater, container, false);

        preferences = new SharedPreferences(requireActivity());
        method = new Methods();
        apiCodes = new ApiCodes();

        // Títol per la toolbar
        b.lyToolbar.toolbar.setTitle(getResources().getString(R.string.tv_title_availability));

        // Llista reserves
        mShowClientAvailableList = new ArrayList<>();
        // Creem i obtenim un vista per RecyclerView
        recyclerViewCook = b.lyIncludeRecyclerView.rvList;
        recyclerViewCook.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewCook.setHasFixedSize(false);
        recyclerViewCook.addItemDecoration(new DividerItemDecoration(recyclerViewCook.getContext(), DividerItemDecoration.VERTICAL));

        // Refresquem la ListView
        refreshList();

        // TextWatcher- Escolta els canvis que es produeixen en el camp població
        b.inputEdtvSelectVillage.addTextChangedListener(availableWatcher);

        // Boto afegir
        b.bttAdd.setOnClickListener(view -> bttAddEdit(R.color.black, R.color.background_main, R.color.text_disable_color, R.color.white, false));

        // Boto editar
        b.bttEdit.setOnClickListener(view -> bttAddEdit(R.color.text_disable_color, R.color.white, R.color.black, R.color.background_main, true));

        // Switch Listener Actiu/Inactiu
        b.switchAvailability.setOnCheckedChangeListener(switchListener);
        b.switchAvailability.setChecked(true);

        // Array de Strings que conté les poblacions de tot Espanya
        villageArray = getResources().getStringArray(R.array.villages_array);

        // Adaptador per el 'Spinner AutoCompletText'
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.villages_array, R.layout.spinner_item);
        b.inputEdtvSelectVillage.setAdapter(adapter);

        // Comprova si existeix tarifa
        getTarifa(preferences.getUsername());

        // Boto per enviar la confirmació de la disponibilitat
        b.bttConfirmSearch.setOnClickListener(view -> {

            if (price == -1) {
                Toast.makeText(getActivity(), "Antes tiene que crear una tarifa.", Toast.LENGTH_SHORT).show();

            } else {
                // Afegir o editar una disponabilitat
                if (selectAddEdit) {
                    // Comprova si la disponibilitat(estat) donada d'un poble per l'usuari és la mateixa
                    for (Disponibilidad dispo : mShowClientAvailableList) {
                        if (villageInput.equals(dispo.getPoblacion())) {
                            if (state.equals(dispo.getEstado())) {
                                sameState = true;
                            } else {
                                sameState = false;
                            }
                        }
                    }

                    // Si no te el mateix estat, el modifiquem (Editar)
                    if (!sameState) {
                        putAvailableUser(preferences.getUsername(), villageInput, cookAvailabilityData());
                    } else { // Si te el mateix estat, missatge
                        Toast.makeText(getActivity(), villageInput + " ya se encuentra " + state, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    postAvailability(cookAvailabilityData());
                    b.inputEdtvSelectVillage.setText("");
                }

            }

            // Tanca el teclat virtual
            method.closeKeyboard(view, requireActivity());
            refreshList();
        });

        // Boto per refresca la llista de disponibilitats
        b.icRefresh.setOnClickListener(view -> refreshList());

        // Controla el onItemclick al fer clic a un element del Recyclerview
        recyclerViewCook.addOnItemTouchListener(new RvItemClickListener(getActivity(), recyclerViewCook, new RvItemClickListener.OnItemClickListener() {

            /**
             * Quan es fa clic a un item es guarden el nom d'usuari del item seleccionat
             * @param view Vista
             * @param position int, posició de l'element
             */
            @Override
            public void onItemClick(View view, int position) {
                Disponibilidad cookAvailable = mShowClientAvailableList.get(position);
                bttAddEdit(R.color.text_disable_color, R.color.white, R.color.black, R.color.background_main, true);
                if (cookAvailable.getEstado().equals("Activo")) {
                    b.switchAvailability.setChecked(true);
                } else {
                    b.switchAvailability.setChecked(false);
                }
                method.closeKeyboard(b.inputEdtvSelectVillage, requireActivity());
                b.inputEdtvSelectVillage.setText(cookAvailable.getPoblacion());
                b.icRefresh.requestFocus();
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
     * Interfície que observa els canvis de text
     *
     * @author Eduard Masjuan
     */
    private final TextWatcher availableWatcher = new TextWatcher() {

        /**
         * Es crida quan es rep el nou caràcter inserit però encara no es mostra en pantalla
         */
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        /**
         * Es crida quan ha acabat de canviar el text
         * Si en l'ArrayList conte el poble introduït per l'usuari,portarà a terme unes accions
         */
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            // Capturem la escriptura
            villageInput = String.valueOf(b.inputEdtvSelectVillage.getText());

            if (Arrays.asList(villageArray).contains(villageInput)) {
                b.inputEdtvSelectVillage.dismissDropDown();
                b.bttConfirmSearch.setEnabled(true);
                method.closeKeyboard(b.inputEdtvSelectVillage, requireActivity());

            } else {
                b.bttConfirmSearch.setEnabled(false);
            }
        }

        /**
         * Es crida després de que s'hagi canviat el text d'un camp i al clicar el boto Done del teclat
         * Si en l'ArrayList conte el poble introduït per l'usuari, portarà a terme unes accions
         */
        @Override
        public void afterTextChanged(Editable editable) {
            // Al clicar l'acció del teclat (done)
            b.inputEdtvSelectVillage.setOnEditorActionListener((textView, i, keyEvent) -> {

                if (!Arrays.asList(villageArray).contains(villageInput)) {
                    b.inputEdtvSelectVillage.dismissDropDown();
                    b.inputEdtvSelectVillage.setText("");
                    Toast.makeText(getActivity(), getResources().getString(R.string.txt_cook_dispo_send4), Toast.LENGTH_SHORT).show();

                } else {
                    method.closeKeyboard(textView, requireActivity());
                    b.inputEdtvSelectVillage.dismissDropDown();
                }

                return true;
            });

        }
    };

    /**
     * Switch. Detecta els canvis del switch (estat actiu o inactiu)
     *
     * @author Eduard Masjuan
     */
    private final CompoundButton.OnCheckedChangeListener switchListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            if (isChecked) {
                state = "Activo";
            } else {
                state = "Inactivo";
            }
        }
    };

    /**
     * Mètode que guarda canvis de les vistes Add i Edit
     *
     * @param colorTextAdd   int color
     * @param colorBackgAdd  int color
     * @param colorTextEdit  int color
     * @param colorBackgEdit int color
     * @param selectOption   boolean per seleccionar opció add o edit
     * @author Eduard Masjuan
     */
    private void bttAddEdit(int colorTextAdd, int colorBackgAdd, int colorTextEdit, int colorBackgEdit, Boolean selectOption) {
        method.closeKeyboard(b.inputEdtvSelectVillage, requireActivity());
        b.bttAdd.setTextColor(getResources().getColor(colorTextAdd));
        b.bttAdd.setBackgroundColor(getResources().getColor(colorBackgAdd));
        b.bttEdit.setTextColor(getResources().getColor(colorTextEdit));
        b.bttEdit.setBackgroundColor(getResources().getColor(colorBackgEdit));
        b.inputEdtvSelectVillage.setText("");
        selectAddEdit = selectOption;
    }

    /**
     * Dades de l'objecte Disponibilidad
     *
     * @return Retorna l'objecte Disponibilidad amb les dades introduïdes per l'usuari
     * @author Eduard Masjuan
     */
    @NonNull
    private Disponibilidad cookAvailabilityData() {
        Disponibilidad cookAvailable = new Disponibilidad();
        cookAvailable.setUsernameOrEmail(preferences.getUsername());
        cookAvailable.setEstado(state);
        cookAvailable.setPoblacion(villageInput);

        return cookAvailable;
    }

    /**
     * Mètode on realitza una petició POST. Agrega noves disponibilitats
     *
     * @param availability Objecte de la classe Disponibilidad
     * @author Eduard Masjuan
     */
    public void postAvailability(Disponibilidad availability) {
       // Call<String> available = apiGlobal.apiClientCert(getActivity(), preferences.getToken()).postAvailability(availability);
        Call<String> available = apiGlobal.apiClient(preferences.getToken()).postAvailability(availability);


        available.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (apiCodes.codeHttp(response.code())) {
                            Toast.makeText(getActivity(), getResources().getString(R.string.txt_cook_dispo_send), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    apiCodes.codeHttp(response.code());
                    Toast.makeText(getActivity(), getResources().getString(R.string.txt_cook_dispo_send2), Toast.LENGTH_SHORT).show();
                }
            }

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
     * Mètode on realitza una petició GET al servidor. Rebem la disponibilitat del cuiner i
     * l'agreguem a una RecyclerView.
     *
     * @param username String, nom d'usuari del cuiner
     * @author Eduard Masjuan
     */
    public void getAvailableUser(String username) {
        //Call<List<Disponibilidad>> availableUser = apiGlobal.apiClientCert(getActivity(), preferences.getToken()).getAvailableUser(username);
        Call<List<Disponibilidad>> availableUser = apiGlobal.apiClient(preferences.getToken()).getAvailableUser(username);

        availableUser.enqueue(new Callback<List<Disponibilidad>>() {
            /**
             * Es crida si ja una resposta HTTP correcte
             * @param call Sol.licita al API les dades
             * @param response  Obtenir les dades
             */
            @Override
            public void onResponse(@NonNull Call<List<Disponibilidad>> call, @NonNull Response<List<Disponibilidad>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        mShowClientAvailableList = response.body();
                        // Creem un Adaptador i obtenim la llista de les reserves
                        rvAvailableAdapter = new RvAdapterListAvailableCook(getActivity(), mShowClientAvailableList);
                        recyclerViewCook.setAdapter(rvAvailableAdapter);

                        // Ordena l'Array per ordre alfabètic
                        Collections.sort(mShowClientAvailableList, new Comparator<Disponibilidad>() {
                            @Override
                            public int compare(Disponibilidad disponibilidad, Disponibilidad t1) {
                                return disponibilidad.getEstado().compareTo(t1.getEstado());
                            }
                        });
                    }

                } else {
                    apiCodes.codeHttp(response.code());
                    Toast.makeText(getActivity(), getResources().getString(R.string.txt_cook_dispo_send3), Toast.LENGTH_SHORT).show();
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
     * Mètode on realitza una petició PUT al servidor. Actualitzem la disponibilitat del cuiner
     *
     * @param username     String nom d'usuari del cuiner
     * @param poblacion    String, població que es vol modificar l'estat de la disponibilitat
     * @param availability Disponibilidad
     * @author Eduard Masjuan
     */
    public void putAvailableUser(String username, String poblacion, Disponibilidad availability) {
        //Call<Disponibilidad> availableUser = apiGlobal.apiClientCert(getActivity(), preferences.getToken()).putAvailableUser(username, poblacion, availability);
        Call<Disponibilidad> availableUser = apiGlobal.apiClient(preferences.getToken()).putAvailableUser(username, poblacion, availability);

        availableUser.enqueue(new Callback<Disponibilidad>() {
            /**
             * Es crida si ja una resposta HTTP correcte
             * Notifiquem a l'adaptador
             * @param call Sol.licita al API les dades
             * @param response  Obtenir les dades
             */
            @Override
            public void onResponse(@NonNull Call<Disponibilidad> call, @NonNull Response<Disponibilidad> response) {
                if (response.isSuccessful()) {

                    if (response.body() != null) {
                        Toast.makeText(getActivity(), getResources().getString(R.string.txt_cook_dispo_send_error), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.txt_cook_dispo_send_error2), Toast.LENGTH_SHORT).show();
                    apiCodes.codeHttp(response.code());
                }
            }

            /**
             * Es produeix una excepció de xarxa en la comunicació amb el servidor o una excepció en la gestió de la sol·licitud
             * @param call Sol·licita al API les dades
             * @param t Captura l’excepció
             */
            @Override
            public void onFailure(@NonNull Call<Disponibilidad> call, @NonNull Throwable t) {
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
     * Mètode on realitza una petició GET. Retorna la tarifa d'un chef
     *
     * @param usernameOrEmail String, id usuari del chef
     * @author Eduard Masjuan
     */
    public void getTarifa(String usernameOrEmail) {
      // Call<Tarifa> mTarifa = apiGlobal.apiClientCert(getActivity(), preferences.getToken()).getTarifaUser(usernameOrEmail);
        Call<Tarifa> mTarifa = apiGlobal.apiClient(preferences.getToken()).getTarifaUser(usernameOrEmail);

        mTarifa.enqueue(new Callback<Tarifa>() {
            /**
             * Es crida si ja una resposta HTTPS correcte
             * @param call Sol.licita al API les dades
             * @param response  Obtenir les dades
             */
            @Override
            public void onResponse(@NonNull Call<Tarifa> call, @NonNull Response<Tarifa> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        // Preu Tarifa
                        Tarifa tarifa = response.body();
                        BigDecimal preu = tarifa.getPrecioHora();

                        price = preu.longValue();

                    } else {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //Toast.makeText(getActivity(), "Codi:" + response.code() + getResources().getString(R.string.codigo_401), Toast.LENGTH_SHORT).show();
                    apiCodes.codeHttp(response.code());
                }
            }

            /**
             * Es produeix una excepció de xarxa en la comunicació amb el servidor o una excepció en la gestió de la sol·licitud
             * @param call Sol·licita al API les dades
             * @param t Captura l’excepció
             */
            @Override
            public void onFailure(@NonNull Call<Tarifa> call, @NonNull Throwable t) {
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
     * Mètode que retorna la disponibilitat del cuiner
     *
     * @author Eduard Masjuan
     */
    public void refreshList() {
        // Refresca la llista
        mShowClientAvailableList.clear();
        getAvailableUser(preferences.getUsername());
    }

}