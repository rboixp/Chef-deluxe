package masjuan.ioc.chefdeluxe.fragment.cook;

import static masjuan.ioc.chefdeluxe.utils.Methods.navigationToolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import org.jetbrains.annotations.Contract;

import java.io.IOException;

import masjuan.ioc.chefdeluxe.R;
import masjuan.ioc.chefdeluxe.api.ApiGlobal;
import masjuan.ioc.chefdeluxe.databinding.FragmentCookMenuBinding;
import masjuan.ioc.chefdeluxe.model.Menu;
import masjuan.ioc.chefdeluxe.utils.ApiCodes;
import masjuan.ioc.chefdeluxe.utils.Methods;
import masjuan.ioc.chefdeluxe.utils.SharedPreferences;
import masjuan.ioc.chefdeluxe.utils.UtilsFragments;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Calsse on els cuiners poden crear/actualitzar el seu menu
 */
public class MenuCook extends Fragment {

    private FragmentCookMenuBinding b;

    private UtilsFragments frag = null;

    private FragmentManager fragmentManager = null;
    private SharedPreferences preferences;

    private ApiCodes apiCodes;
    private ApiGlobal apiGlobal;

    private String menuEntrante, menuPrimero, menuSegundo, menuPostre, menuCafes;
    private long menuId;


    /**
     * Constructor buit
     */
    public MenuCook() {
        // Required empty public constructor
    }

    /**
     * Crea una nova instancia de MenuCook
     *
     * @return MenuCook
     */
    @NonNull
    @Contract(" -> new")
    public static MenuCook newInstance() {
        return new MenuCook();
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
     * Menu dle cuiner
     *
     * @param inflater           Infla la vista
     * @param container          Vista que s'adjuntarà a la interfície d'usuari
     * @param savedInstanceState Bundle
     * @return Vista
     * @author Eduard Masjuan
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        b = FragmentCookMenuBinding.inflate(inflater, container, false);
        preferences = new SharedPreferences(requireActivity());
        Methods method = new Methods();
        apiCodes = new ApiCodes();

        // Toolbar popBackStack (ens retorna al fragment)
        navigationToolbar(b.lyToolbar.toolbar, frag, fragmentManager);

        // Títol per la toolbar
        b.lyToolbar.toolbar.setTitle(getResources().getString(R.string.tv_title_menu));

        // TextWatcher- Escolta els canvis que es produeixen en el camp població
        b.spinnerMenuEntrante.addTextChangedListener(availableWatcher);
        b.spinnerMenuPrimero.addTextChangedListener(availableWatcher);
        b.spinnerMenuSegundo.addTextChangedListener(availableWatcher);
        b.spinnerMenuPostre.addTextChangedListener(availableWatcher);
        b.spinnerMenuCafe.addTextChangedListener(availableWatcher);

        // Mostra el menu actual del cuiner passant la tarifa com a parametre
        getMenu(preferences.getIdTarifa());

        // Boto menu
        b.bttMenu.setOnClickListener(view -> {
            // Validació de les dades introduïdes
            if (dataValidation(menuEntrante, menuPrimero, menuSegundo, menuPostre, menuCafes)) {
                menuCook();
                Toast.makeText(getActivity(), "Menú actualizado!", Toast.LENGTH_SHORT).show();
            }

        });

        return b.getRoot();
    }


    /**
     * Menu cuiner
     */
    @SuppressLint("SetTextI18n")
    private void menuCook() {
        putMenu(updateMenuData());

        if (b.tvMenuFinal.getText().equals("")) {
            //postMenu(createMenuData());
        } else {
            b.tvMenuFinal.setText("Su menú es  ");
            //putMenu(updateMenuData());
        }

        b.tvMenuFinal.setText(menuEntrante + " - " + menuPrimero + " - " + menuSegundo + " - " + menuPostre + " - " + menuCafes);
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
            menuEntrante = String.valueOf(b.spinnerMenuEntrante.getText());
            menuPrimero = String.valueOf(b.spinnerMenuPrimero.getText());
            menuSegundo = String.valueOf(b.spinnerMenuSegundo.getText());
            menuPostre = String.valueOf(b.spinnerMenuPostre.getText());
            menuCafes = String.valueOf(b.spinnerMenuCafe.getText());


            if (!menuEntrante.isEmpty() && !menuPrimero.isEmpty() && !menuSegundo.isEmpty() && !menuPostre.isEmpty() && !menuCafes.isEmpty()) {
                b.bttMenu.setEnabled(true);
            }

        }

        /**
         * Es crida després de que s'hagi canviat el text d'un camp i al clicar el boto Done del teclat
         *
         */
        @Override
        public void afterTextChanged(Editable editable) {

        }
    };


    /**
     * Mètode on realitza una petició GET. Retorna el menu fet per un chef
     *
     * @param id , long id usuari chef
     * @author Eduard Masjuan
     */
    public void getMenu(long id) {
        //Call<Menu> mMenu = apiGlobal.apiClientCert(getActivity(), preferences.getToken()).getMenu(id);
        Call<Menu> mMenu = apiGlobal.apiClient(preferences.getToken()).getMenu(id);

        mMenu.enqueue(new Callback<Menu>() {
            /**
             * Es crida si ja una resposta HTTPS correcte
             * @param call Sol.licita al API les dades
             * @param response  Obtenir les dades
             */
            @Override
            public void onResponse(@NonNull Call<Menu> call, @NonNull Response<Menu> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        Menu menu = response.body();

                        menuEntrante = menu.getEntrante();
                        menuId = menu.getId();

                        b.tvMenuFinal.setText(menuEntrante);

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
            public void onFailure(@NonNull Call<Menu> call, @NonNull Throwable t) {
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
     * Mètode on realitza una petició POST. Crea un menu pel cuiner
     *
     * @param menu Objecte de la classe Menu
     * @author Eduard Masjuan
     */
    public void postMenu(Menu menu) {
        //Call<String> mMenu = apiGlobal.apiClientCert(getActivity(), preferences.getToken()).postMenu(menu);
        Call<String> mMenu = apiGlobal.apiClient(preferences.getToken()).postMenu(menu);

        mMenu.enqueue(new Callback<String>() {
            /**
             * Es crida si ja una resposta HTTP correcte
             * @param call Sol.licita al API les dades
             * @param response  Obtenir les dades
             */
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Toast.makeText(getActivity(), "Menú creado!", Toast.LENGTH_SHORT).show();

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
     * Mètode on realitza una petició PUT. Actualiza el menu del cuiner
     *
     * @param menu Objecte de la classe Menu
     * @author Eduard Masjuan
     */
    public void putMenu(Menu menu) {
        //Call<String> mMenu = apiGlobal.apiClientCert(getActivity(), preferences.getToken()).putMenu(menu);
        Call<String> mMenu = apiGlobal.apiClient(preferences.getToken()).putMenu(menu);

        mMenu.enqueue(new Callback<String>() {
            /**
             * Es crida si ja una resposta HTTP correcte
             * @param call Sol.licita al API les dades
             * @param response  Obtenir les dades
             */
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        System.out.println("Menu actualizado");

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
     * Dades de l'objecte Menu (crear)
     *
     * @return Menu
     */
    private Menu createMenuData() {
        Menu menu = new Menu();
        menu.setIdChef(preferences.getId());
        menu.setEntrante(menuEntrante);
        menu.setPrimero(menuPrimero);
        menu.setSegundo(menuSegundo);
        menu.setPostre(menuPostre);
        menu.setCafes(menuCafes);

        return menu;
    }

    /**
     * Dades de l'objecte Menu (actualitza
     *
     * @return Menu
     */
    private Menu updateMenuData() {
        Menu menu = new Menu();
        menu.setId(preferences.getIdMenu());
        menu.setIdChef(preferences.getId());
        menu.setEntrante(menuEntrante);
        menu.setPrimero(menuPrimero);
        menu.setSegundo(menuSegundo);
        menu.setPostre(menuPostre);
        menu.setCafes(menuCafes);

        return menu;
    }

    /**
     * Comprovació i validació de les dades introduïdes dels camps de text
     *
     * @param entrante plat entrante
     * @param primer   plat primer
     * @param segundo  plat segon
     * @param postre   plat postre
     * @param cafe     plat cafe
     * @return boolean
     * @author Eduard Masjuan
     */
    private boolean dataValidation(String entrante, String primer, String segundo, String postre, String cafe) {
        Methods method = new Methods();
        Boolean regEntrante = method.patternValidation(b.edtvMenuEntrante, getString(R.string.txtLay_invalid_name), "^[a-zA-Z\\s]+$", entrante);
        Boolean regPrimer = method.patternValidation(b.edtvMenuPrimero, getString(R.string.txtLay_invalid_name), "^[a-zA-Z\\s]+$", primer);
        Boolean regSegundo = method.patternValidation(b.edtvMenuSegundo, getString(R.string.txtLay_invalid_name), "^[a-zA-Z\\s]+$", segundo);
        Boolean regPostre = method.patternValidation(b.edtvMenuPostre, getString(R.string.txtLay_invalid_name), "^[a-zA-Z\\s]+$", postre);
        Boolean regCafe = method.patternValidation(b.edtvMenuCafe, getString(R.string.txtLay_invalid_name), "^[a-zA-Z\\s]+$", cafe);

        return regEntrante && regPrimer && regSegundo && regPostre && regCafe;
    }

}