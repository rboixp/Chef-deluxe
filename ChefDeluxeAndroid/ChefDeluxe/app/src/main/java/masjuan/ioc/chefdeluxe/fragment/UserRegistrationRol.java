package masjuan.ioc.chefdeluxe.fragment;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;

import java.io.IOException;

import masjuan.ioc.chefdeluxe.R;
import masjuan.ioc.chefdeluxe.api.ApiClient;
import masjuan.ioc.chefdeluxe.api.ApiService;
import masjuan.ioc.chefdeluxe.databinding.FragmentRegistrationRolBinding;
import masjuan.ioc.chefdeluxe.model.Registration;
import masjuan.ioc.chefdeluxe.utils.ApiCodes;
import masjuan.ioc.chefdeluxe.utils.GlobalVariables;
import masjuan.ioc.chefdeluxe.utils.UtilsFragments;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Classe on permet escollir el rol que l'usuari vol ser
 *
 * @author Eduard Masjuan
 */
public class UserRegistrationRol extends Fragment {

    private FragmentRegistrationRolBinding b;
    private UtilsFragments frag = null;
    private FragmentManager fragmentManager = null;
    private String name, surname, email, userName, password;
    private String rol_user = "";

    /**
     * Constructor buit
     */
    public UserRegistrationRol() {
        // Required empty public constructor
    }

    /**
     * Crea una nova instancia de UserRegistrationRol
     *
     * @return UserRegistrationRol
     */
    public static UserRegistrationRol newInstance() {

        return new UserRegistrationRol();
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
        fragmentManager = requireActivity().getSupportFragmentManager();

        // Cridem a FragmentResultListener per rebre dades
        getParentFragmentManager().setFragmentResultListener("key", this, new FragmentResultListener() {
            /**
             * Rebem les dades passades per el RegistrationFragment
             * @param requestKey Mateixa clau que s'ha donat al Bundle
             * @param result Bundle
             * @author Eduard Masjuan
             */
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                // Una vegada rebut les dades s'eliminen automàticament
                String saveName = result.getString("keyName");
                String saveSurname = result.getString("keySurname");
                String saveEmail = result.getString("keyEmail");
                String saveNameUser = result.getString("keyUserName");
                String savePassword = result.getString("keyPassword");

                // Guardem les dades en variables
                name = saveName;
                surname = saveSurname;
                email = saveEmail;
                userName = saveNameUser;
                password = savePassword;
            }
        });
    }

    /**
     * Dissenya la interfície d'usuari per primera vegada
     *
     * @param inflater           Infla la vista
     * @param container          Vista que s'adjuntarà a la interfície d'usuari
     * @param savedInstanceState Bundle
     * @return Vista
     * @author Eduard Masjuan
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        b = FragmentRegistrationRolBinding.inflate(inflater, container, false);
        roleSelection();

        b.tvRegisterBack.setOnClickListener(view -> frag.popBackStack(fragmentManager));

        // Boto de finalització de registre
        b.bttRegisterNext.setOnClickListener(view -> {
            if (rol_user.isEmpty()) {
                Toast.makeText(getActivity(), "Ha de seleccionar un tipo de usuario", Toast.LENGTH_SHORT).show();
            } else {
                Registration user = userRegistrationData();
                postRegister(user);
            }
        });

        return b.getRoot();
    }

    /**
     * Selecciona el rol d'usuari a traves de CardView
     *
     * @return Retorna el rol
     * @author Eduard Masjuan
     */
    private String roleSelection() {
        // Necessari per fer el marcat Html en recursos String programàticament
        CharSequence tv_client = Html.fromHtml(getResources().getString(R.string.tv_select_rol_info_client));
        CharSequence tv_cook = Html.fromHtml(getResources().getString(R.string.tv_select_rol_info_cocinero));
        float img_aplha_pulse = 1.0f;
        float img_aplha = 0.5f;

        // CardView Client
        b.cardViewClient.setOnClickListener(view -> {
            b.tvSelectRolInfo3.setText(tv_client);
            b.imgClient.setAlpha(img_aplha_pulse);
            b.imgCook.setAlpha(img_aplha);
            rol_user = GlobalVariables.nameRolClient;
        });

        // CardView Cuiner
        b.cardViewCook.setOnClickListener(view -> {
            b.tvSelectRolInfo3.setText(tv_cook);
            b.imgCook.setAlpha(img_aplha_pulse);
            b.imgClient.setAlpha(img_aplha);
            rol_user = GlobalVariables.nameRolCook;
        });
        return rol_user;
    }

    /**
     * Crea un objecte de la classe Registration
     *
     * @return Retorna l'objecte Registration amb les dades introduïdes de l'usuari
     * @author Eduard Masjuan
     */
    private Registration userRegistrationData() {
        Registration dataUser = new Registration();
        dataUser.setNombre(name);
        dataUser.setApellidos(surname);
        dataUser.setEmail(email);
        dataUser.setUsername(userName);
        dataUser.setPassword(password);
        dataUser.setPerfil(roleSelection());

        return dataUser;
    }

    /**
     * Mètode on realitza una petició POST. enqueue() envia una sol·licitud i notifica a la
     * app amb una devolució de llamada quan rep una resposta.
     *
     * @param user Objecte de la classe Registration
     * @author Eduard Masjuan
     */
    private void postRegister(Registration user) {
        ApiCodes apiCode = new ApiCodes();
        ApiService apiService = ApiClient.getInstance();
        Call<String> callRegister = apiService.addUser(user);
        callRegister.enqueue(new Callback<String>() {
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
                        if (apiCode.codeHttp(response.code())) {
                            Toast.makeText(getActivity(), "Registro completado!", Toast.LENGTH_SHORT).show();
                            frag.replaceFragment(R.id.container, UserLogin.newInstance());
                        }
                    }
                } else {
                    Toast.makeText(getActivity(), "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                    apiCode.codeHttp(response.code());
                }
            }

            /**
             * Es produeix una excepció de xarxa en la comunicació amb el servidor o una excepció en la gestió de la sol·licitud
             * @param call Sol·licita al API les dades
             * @param t Captura l’excepció
             */
            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                // Comprova si l'objecte t es una classe
                if (t instanceof IOException) {
                    Log.v("Código", t.getMessage() + "  " + getResources().getString(R.string.codigo_onFailure_connexion));
                    Toast.makeText(getActivity(), getResources().getString(R.string.codigo_onFailure_connexion), Toast.LENGTH_SHORT).show();

                } else {
                    Log.v("Código", t.getMessage() + "  " + getResources().getString(R.string.codigo_onFailure_conversion));
                    Toast.makeText(getActivity(), getResources().getString(R.string.codigo_onFailure_conversion), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}