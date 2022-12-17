package masjuan.ioc.chefdeluxe.fragment.cook;

import static masjuan.ioc.chefdeluxe.utils.Methods.navigationToolbar;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import org.jetbrains.annotations.Contract;

import java.io.IOException;
import java.math.BigDecimal;

import masjuan.ioc.chefdeluxe.MainActivity;
import masjuan.ioc.chefdeluxe.R;
import masjuan.ioc.chefdeluxe.api.ApiGlobal;
import masjuan.ioc.chefdeluxe.databinding.FragmentCookTarifaBinding;
import masjuan.ioc.chefdeluxe.model.Tarifa;
import masjuan.ioc.chefdeluxe.utils.ApiCodes;
import masjuan.ioc.chefdeluxe.utils.Methods;
import masjuan.ioc.chefdeluxe.utils.SharedPreferences;
import masjuan.ioc.chefdeluxe.utils.UtilsFragments;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Calsse on els cuiners poden crear/actualitzar la seva tarifa.
 */
public class TarifaCook extends Fragment {

    private FragmentCookTarifaBinding b;
    private UtilsFragments frag = null;
    private Methods method;
    private FragmentManager fragmentManager = null;
    private SharedPreferences preferences;
    private ApiCodes apiCodes;
    private ApiGlobal apiGlobal;

    private BigDecimal priceCook;
    private long idTarifa, idMenu;
    private String priceInput;

    /**
     * Constructor buit
     */
    public TarifaCook() {
        // Required empty public constructor
    }

    /**
     * Crea una nova instancia de TarifaCook
     *
     * @return TarifaCook
     */
    @NonNull
    @Contract(" -> new")
    public static TarifaCook newInstance() {
        return new TarifaCook();
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
        frag = new UtilsFragments(requireActivity().getSupportFragmentManager());
        fragmentManager = requireActivity().getSupportFragmentManager();
        apiGlobal = new ApiGlobal();
    }

    /**
     * Dissenya la interfície d'usuari per primera vegada.
     * Tarifa del cuiner
     *
     * @param inflater           Infla la vista
     * @param container          Vista que s'adjuntarà a la interfície d'usuari
     * @param savedInstanceState Bundle
     * @return Vista
     * @author Eduard Masjuan
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        b = FragmentCookTarifaBinding.inflate(inflater, container, false);
        preferences = new SharedPreferences(requireActivity());
        method = new Methods();
        apiCodes = new ApiCodes();

        // Toolbar popBackStack (ens retorna al fragment)
        ((MainActivity) requireActivity()).setSupportActionBar(b.lyToolbar.toolbar);
        navigationToolbar(b.lyToolbar.toolbar, frag, fragmentManager);
        setHasOptionsMenu(true);

        // Títol per la toolbar
        b.lyToolbar.toolbar.setTitle(getResources().getString(R.string.tv_title_tarifa));

        // TextWatcher- Escolta els canvis que es produeixen en el camp població
        b.inputEdtvPrice.addTextChangedListener(availableWatcher);

        // Escullir tarifes fixes
        b.tarifa1.setOnClickListener(view -> tarifaCook(10.00));
        b.tarifa2.setOnClickListener(view -> tarifaCook(15.00));
        b.tarifa3.setOnClickListener(view -> tarifaCook(20.00));
        b.tarifa4.setOnClickListener(view -> tarifaCook(25.00));
        b.tarifa5.setOnClickListener(view -> tarifaCook(30.00));
        b.tarifa6.setOnClickListener(view -> tarifaCook(35.00));

        // Mostra la tarifa actual del cuiner
        getTarifa(preferences.getUsername());

        return b.getRoot();
    }

    /**
     * Especifica les opcions del menu
     *
     * @param menu     menu
     * @param inflater Infla la vista menu
     */
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_update_reservation, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * Selecció d'items del menu
     *
     * @param item Item del menu
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_clean) {
            deleteUserApi(preferences.getIdTarifa());
            preferences.cleanTarifa();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Tarifa del cuiner
     *
     * @param tarifa long, valor tarifa
     */
    private void tarifaCook(double tarifa) {
        priceCook = BigDecimal.valueOf(formatDecimals(tarifa, 3));

        if (b.tvTarifaFinalPrice.getText().equals("") || preferences.getIdTarifa() == 0) {
            postTarifa(createTarifaData());
        } else {
            b.tvTarifaFinal.setText("Su tarifa és de ");
            putTarifa(updateTarifaData());
        }

        b.tvTarifaFinalPrice.setText(getString(R.string.tv_tarifa_final, priceCook));
    }

    /**
     * Mètode on realitza una petició POST. Crea la tarifa pel cuiner
     *
     * @param tarifa Objecte de la classe Tarifa
     * @author Eduard Masjuan
     */
    public void postTarifa(Tarifa tarifa) {
        Call<String> mTarifa = apiGlobal.apiClientCert(getActivity(), preferences.getToken()).postTarifa(tarifa);
        //Call<String> mTarifa = apiGlobal.apiClient(preferences.getToken()).getRol(tarifa);

        mTarifa.enqueue(new Callback<String>() {
            /**
             * Es crida si ja una resposta HTTP correcte
             * @param call Sol.licita al API les dades
             * @param response  Obtenir les dades
             */
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Toast.makeText(getActivity(), "Tarifa creada!", Toast.LENGTH_SHORT).show();
                        getTarifa(preferences.getUsername());

                    } else {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
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
     * Mètode on realitza una petició GET. Retorna la tarifa d'un chef
     *
     * @param username , String usuari del chef
     * @author Eduard Masjuan
     */
    public void getTarifa(String username) {
        Call<Tarifa> mTarifa = apiGlobal.apiClientCert(getActivity(), preferences.getToken()).getTarifaUser(username);
        //Call<Tarifa> mTarifa = apiGlobal.apiClient(preferences.getToken()).getTarifaUser(username);

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

                        Tarifa tarifa = response.body();

                        priceCook = tarifa.getPrecioHora();
                        idTarifa = tarifa.getId();
                        preferences.setIdTarifa(idTarifa);

                        idMenu = tarifa.getIdMenu();
                        preferences.setIdMenu(idMenu);

                        b.tvTarifaFinalPrice.setText(getString(R.string.tv_tarifa_final, priceCook));
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
     * Mètode on realitza una petició PUT. Actualiza la tarifa del cuiner
     *
     * @param tarifa Objecte de la classe Tarifa
     * @author Eduard Masjuan
     */
    public void putTarifa(Tarifa tarifa) {
        Call<String> mTarifa = apiGlobal.apiClientCert(getActivity(), preferences.getToken()).putTarifa(tarifa);
        //Call<String> mTarifa = apiGlobal.apiClient(preferences.getToken()).putTarifa(tarifa);

        mTarifa.enqueue(new Callback<String>() {
            /**
             * Es crida si ja una resposta HTTP correcte
             * @param call Sol.licita al API les dades
             * @param response  Obtenir les dades
             */
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        System.out.println("Tarifa actualizada");
                        Toast.makeText(getActivity(), "Tarifa actualizada!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
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
     * Mètode on realitza una petició DELETE. Elimina la tarifa del cuiner
     *
     * @param id de la tarifa del cuiner
     * @author Eduard Masjuan
     */
    public void deleteUserApi(long id) {
        Call<String> deletedTarifa = apiGlobal.apiClientCert(getActivity(), preferences.getToken()).deleteTarifa(id);
        //Call<String> deletedTarifa = apiGlobal.apiClient(preferences.getToken()).deleteTarifa(id);

        deletedTarifa.enqueue(new Callback<String>() {
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
                            Toast.makeText(getActivity(), "Tarifa eliminada!", Toast.LENGTH_SHORT).show();
                            b.tvTarifaFinalPrice.setText("");
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
     * Dades de l'objecte Tarifa (crear)
     *
     * @return Tarifa
     */
    @NonNull
    private Tarifa createTarifaData() {
        Tarifa tr = new Tarifa();
        tr.setUsernameOrEmail(preferences.getUsername());
        tr.setPrecioHora(priceCook);
        tr.setIdchef(preferences.getId());

        return tr;
    }

    /**
     * Actualitza tarifa i menu
     *
     * @return Tarifa
     */
    @NonNull
    private Tarifa updateTarifaData() {
        Tarifa tr = new Tarifa();

        tr.setId(preferences.getIdTarifa());
        tr.setIdchef(preferences.getId());
        tr.setIdMenu(preferences.getIdMenu());

        // Elimina els dos ultims caracters (espai i €)
        String preuFi = String.valueOf(b.tvTarifaFinalPrice.getText());
        int indice = 2 > preuFi.length() ? 0 : preuFi.length() - 2;

        tr.setPrecioHora(priceCook);

        return tr;
    }

    /**
     * Limitar el numero de decimals
     *
     * @param num  Double valor
     * @param numD Integer numero de decimals
     * @return double
     */
    @NonNull
    public static Double formatDecimals(Double num, Integer numD) {
        return Math.round(num * Math.pow(10, numD)) / Math.pow(10, numD);
    }

    /**
     * Interfície que observa els canvis de text
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
         *
         */
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // Capturem la escriptura
            priceInput = String.valueOf(b.inputEdtvPrice.getText());

        }

        /**
         * Es crida després de que s'hagi canviat el text d'un camp i al clicar el boto Done del teclat
         *
         */
        @Override
        public void afterTextChanged(Editable editable) {

            // Al clicar l'acció del teclat (done)
            b.inputEdtvPrice.setOnEditorActionListener((textView, i, keyEvent) -> {

                if (priceInput.isEmpty()) {
                    Toast.makeText(getActivity(), "Introduzca una tarifa", Toast.LENGTH_SHORT).show();
                } else {
                    tarifaCook(Double.parseDouble(priceInput));
                    method.closeKeyboard(textView, requireActivity());
                }

                return true;
            });
        }
    };

}