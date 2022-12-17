package masjuan.ioc.chefdeluxe.fragment.admin.admin;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import masjuan.ioc.chefdeluxe.R;
import masjuan.ioc.chefdeluxe.api.ApiGlobal;
import masjuan.ioc.chefdeluxe.databinding.FragmentAdminUserBinding;
import masjuan.ioc.chefdeluxe.model.User;
import masjuan.ioc.chefdeluxe.utils.ApiCodes;
import masjuan.ioc.chefdeluxe.utils.Methods;
import masjuan.ioc.chefdeluxe.utils.SharedPreferences;
import masjuan.ioc.chefdeluxe.utils.recyclerView.RvAdapterListUsersAdmin;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Classe on mostra un llistat d'usuaris o un sol usuari, només per Administradors
 *
 * @author Eduard Masjuan
 */
public class NavDataUsersAdmin extends Fragment {

    private SharedPreferences preferences;
    private FragmentAdminUserBinding b;
    private Methods methods;
    private ApiCodes apiCodes;
    private ApiGlobal apiGlobal;

    private String userNameInput;
    private String name;

    // Disponibilitat
    private List<User> mShowUsersAdmin;
    private List<String> listUserName;
    private RecyclerView recyclerViewUsersAdmin = null;
    private RvAdapterListUsersAdmin rvUsersAdminAdapter;

    /**
     * Constructor buit
     */
    public NavDataUsersAdmin() {
        // Required empty public constructor
    }

    /**
     * Crea una nova instancia de NavDataUsersAdmin
     *
     * @return NavDataUsersAdmin
     */
    public static NavDataUsersAdmin newInstance() {
        return new NavDataUsersAdmin();
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
        methods = new Methods();
        apiGlobal = new ApiGlobal();
    }

    /**
     * Dissenya la interfície d'usuari per primera vegada.
     * Mostra llistat d'usuaris
     * Mostra un sol usuari
     *
     * @param inflater           Infla la vista
     * @param container          Vista que s'adjuntarà a la interfície d'usuari
     * @param savedInstanceState Bundle
     * @return Vista
     * @author Eduard Masjuan
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        b = FragmentAdminUserBinding.inflate(inflater, container, false);
        preferences = new SharedPreferences(requireActivity());
        apiCodes = new ApiCodes();

        listUserName = new ArrayList<>();

        // TextWatcher- Escolta els canvis que es produeixen en el camp usuari
        b.inputEdtvName.addTextChangedListener(availableWatcher);

        // Llista cuiners
        mShowUsersAdmin = new ArrayList<>();

        // Creem i obtenim un vista per RecyclerView
        recyclerViewUsersAdmin = b.lyIncludeRecyclerView.rvList;
        recyclerViewUsersAdmin.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewUsersAdmin.setHasFixedSize(false);

        // Mostra tots els usuaris
        getAllUser();

        // Al fer clic a un usuari s'obre un Diàleg
        b.lyIncludeCardVieww.cardView.setOnClickListener(view -> {
            BottomSheetDialogFragment sheetDialog = DialogOptionsUserAdmin.newInstance(name, userNameInput);
            sheetDialog.show(getParentFragmentManager(), "BottomSheett");
        });

        // Borrar el text del buscador d'usuaris
        b.edtvName.setEndIconOnClickListener(view -> b.inputEdtvName.setText(""));

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
         * Si en l'ArrayList conte l'usuari introduït es mostrarà aquest mateix usuari en solitari
         */
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            // Capturem la escriptura
            userNameInput = String.valueOf(b.inputEdtvName.getText());

            // Compara l'array amb el text introduït. Si ho es mostra l'usuari escrit
            if (listUserName.contains(userNameInput)) {
                recyclerViewUsersAdmin.removeAllViewsInLayout();
                getUser(userNameInput);
                b.lyRecyclerView.setVisibility(View.GONE);
                b.lyCardView.setVisibility(View.VISIBLE);
                methods.closeKeyboard(b.edtvName, requireActivity());
            } else { // Si no mostra tots els usuaris
                getAllUser();
                b.lyRecyclerView.setVisibility(View.VISIBLE);
                b.lyCardView.setVisibility(View.GONE);
            }
        }

        /**
         * Es crida després de que s'hagi canviat el text d'un camp
         */
        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

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

            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        // Guardem els nom d'usuari a una List
                        for (User user : response.body()) {
                            listUserName.add(user.getUsername());
                        }

                        mShowUsersAdmin = response.body();

                        // Creem un Adaptador i obtenim la llista de tots els usuaris
                        rvUsersAdminAdapter = new RvAdapterListUsersAdmin(getActivity(), mShowUsersAdmin);
                        recyclerViewUsersAdmin.setAdapter(rvUsersAdminAdapter);

                        // Ordena l'Array per ordre numeric
                        Collections.sort(mShowUsersAdmin, new Comparator<User>() {
                            @Override
                            public int compare(User user, User t1) {
                                return user.getId().compareTo(t1.getId());
                            }
                        });

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
     * Mètode on realitza una petició GET al servidor. Rebem les dades de l'usuari passat per paràmetre
     *
     * @param username String, nom d'usuari del cuiner
     * @author Eduard Masjuan
     */
    public void getUser(String username) {
       // Call<User> user = apiGlobal.apiClientCert(getActivity(), preferences.getToken()).getRol(username);
        Call<User> user = apiGlobal.apiClient(preferences.getToken()).getRol(username);
        user.enqueue(new Callback<User>() {

            /**
             * Es crida si ja una resposta HTTP correcte
             * @param call Sol.licita al API les dades
             * @param response  Obtenir les dades
             */
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        User user = response.body();

                        name = user.getNombre();

                        b.lyIncludeCardVieww.tvMId.setText(String.valueOf(user.getId()));
                        b.lyIncludeCardVieww.tvMUsername.setText(user.getUsername());
                        b.lyIncludeCardVieww.tvMEmail.setText(user.getEmail());
                        b.lyIncludeCardVieww.tvMName.setText(user.getNombre());
                        b.lyIncludeCardVieww.tvMSurname.setText(user.getApellidos());
                        b.lyIncludeCardVieww.tvMAge.setText(String.valueOf(user.getEdad()));
                        b.lyIncludeCardVieww.tvMPhone.setText(String.valueOf(user.getTelefono()));
                        b.lyIncludeCardVieww.tvMStreet.setText(user.getDireccion());
                        b.lyIncludeCardVieww.tvMVillage.setText(user.getPoblacion());
                        b.lyIncludeCardVieww.tvMPostalcode.setText(String.valueOf(user.getCodigoPostal()));
                        b.lyIncludeCardVieww.tvMCountry.setText(user.getNacionalidad());
                        b.lyIncludeCardVieww.tvMIban.setText(String.valueOf(user.getIban()));

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