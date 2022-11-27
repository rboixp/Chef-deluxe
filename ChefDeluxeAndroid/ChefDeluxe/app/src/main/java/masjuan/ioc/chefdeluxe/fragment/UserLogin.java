package masjuan.ioc.chefdeluxe.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.util.Set;

import masjuan.ioc.chefdeluxe.R;
import masjuan.ioc.chefdeluxe.api.ApiClient;
import masjuan.ioc.chefdeluxe.api.ApiClientToken;
import masjuan.ioc.chefdeluxe.api.ApiService;
import masjuan.ioc.chefdeluxe.databinding.FragmentLoginBinding;
import masjuan.ioc.chefdeluxe.model.Login;
import masjuan.ioc.chefdeluxe.model.Role;
import masjuan.ioc.chefdeluxe.model.Token;
import masjuan.ioc.chefdeluxe.model.User;
import masjuan.ioc.chefdeluxe.utils.ApiCodes;
import masjuan.ioc.chefdeluxe.utils.Methods;
import masjuan.ioc.chefdeluxe.utils.SharedPreferences;
import masjuan.ioc.chefdeluxe.utils.UtilsFragments;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Fragment Login on es podrà iniciar la sessió d'usuari o accedir a fer el registre de nou usuari
 *
 * @author Eduard Masjuan
 */
public class UserLogin extends Fragment {

    private String usernameInput;
    private String passwordInput;
    private FragmentLoginBinding b;
    private UtilsFragments frag = null;
    private SharedPreferences preferences;
    private final ApiCodes apiCodes = new ApiCodes();
    private Methods method;

    /**
     * Constructor buit
     */
    public UserLogin() {
        // Required empty public constructor
    }

    /**
     * Crea una nova instancia de UserLogin
     *
     * @return UserLogin
     */
    @NonNull
    public static UserLogin newInstance() {
        return new UserLogin();
    }

    /**
     * S'executa quan l'activitat  es crea per primera vegada
     *
     * @param savedInstanceState Objecte Bundle que conte l'estat de l'activitat guardat
     * @author Eduard Masjuan
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        frag = new UtilsFragments(requireActivity().getSupportFragmentManager());
    }

    /**
     * Dissenya la interfície d'usuari per primera vegada
     * Mostra la pantalla del login
     *
     * @param inflater           Infla la vista
     * @param container          Vista que s'adjuntarà a la interfície d'usuari
     * @param savedInstanceState Bundle
     * @return Vista
     * @author Eduard Masjuan
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        b = FragmentLoginBinding.inflate(inflater, container, false);
        method = new Methods();

        // Nou objecte SharedPreferences
        preferences = new SharedPreferences(requireActivity());

        // TextWatcher. Escolta els canvis que es produeixen en els camps de text
        b.inputEdtvUser.addTextChangedListener(textWatcher);
        b.inputEdtvPassword.addTextChangedListener(textWatcher);

        // Boto fragment registre
        b.tvRegister.setOnClickListener(view -> frag.replaceFragment(R.id.container,
                UserRegistration.newInstance(), "registration"));

        // Boto d'inici de sessió
        b.bttAccept.setOnClickListener(view -> {
            // Objecte Login
            Login login = userLoginData();

            // Mètode amb peticio GET per aconseguir el token
            getTokenApi(login);

            // Tanquem teclat
            method.closeKeyboard(view, requireActivity());

        });

        return b.getRoot();
    }

    /**
     * Mètode on realitza una petició GET. Interceptem i guardem  el token de l'usuari que ha iniciat sessió
     * Analitza les dades de API en un fil de fons i després entrega els resultats al fil de la
     * interfície d'usuari a traves dels mètodes onResponse o onFailure
     *
     * @author Eduard Masjuan
     */
    private void getTokenApi(Login login) {
        ApiService apiService = ApiClient.getInstance();
        // Cridem l'objecte que realitzara la petició
        Call<Token> loginResponse = apiService.tokenLogin(login);

        // Envia de forma asincrònica  la petició i notifica a la app amb un Callback<Token> quan la resposta retorna.
        loginResponse.enqueue(new Callback<Token>() {

            /**
             * Es crida si ja una resposta HTTP correcte
             * @param call Sol.licita al API les dades
             * @param response  Obtenir les dades
             */
            @Override
            public void onResponse(@NonNull Call<Token> call, @NonNull Response<Token> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        String token = response.body().getTokenDeAcceso(); // Guardem el token
                        // Comprova el codi rebut
                        if (apiCodes.codeHttp(response.code())) {
                            b.edtvUser.setError(null);
                            b.edtvPassword.setError(null);
                            // Guardem el token, nom d'usuaris i password de l'usuari a les SharedPreferences
                            preferences.setToken(token);
                            preferences.setUsername(usernameInput);
                            preferences.setPassword(passwordInput);

                            // Mètode que realitza una petició per guardar el rol de l'usuari
                            getUserApi(preferences.getUsername());
                        }
                    }
                } else {
                    apiCodes.codeHttp(response.code());
                    b.edtvUser.setError(" ");
                    b.edtvPassword.setError(getString(R.string.tv_failed_user_pass));
                }
            }

            /**
             * Es produeix una excepció de xarxa en la comunicació amb el servidor o una excepció en la gestió de la sol·licitud
             * @param call Sol·licita al API les dades
             * @param t Captura l’excepció
             */
            @Override
            public void onFailure(@NonNull Call<Token> call, @NonNull Throwable t) {
                // Comprova si l'objecte t es una classe
                if (t instanceof IOException) {
                    Log.v("Código", getResources().getString(R.string.codigo_onFailure_connexion));
                    Toast.makeText(getActivity(), getResources().getString(R.string.codigo_onFailure_connexion), Toast.LENGTH_SHORT).show();

                } else {
                    Log.v("Código", getResources().getString(R.string.codigo_onFailure_conversion));
                    Toast.makeText(getActivity(), getResources().getString(R.string.codigo_onFailure_conversion), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Mètode on realitza una petició GET al servidor. Interceptem les dades de l'usuari
     *
     * @author Eduard Masjuan
     */
    public void getUserApi(String username) {
        ApiService apiService = ApiClientToken.getInstance(preferences.getToken());
        Call<User> user = apiService.getRol(username);
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

                        // Guardem el correo electronic de l'usuari
                        preferences.setEmail(response.body().getEmail());
                        preferences.setId(response.body().getId());

                        // Recuperem el rol
                        Role rol = new Role();
                        Set<Role> stRole = response.body().getRoles();
                        stRole.add(rol);

                        for (Role role : stRole) {
                            chooseRole((int) role.getId());
                        }
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

    /**
     * Crea un objecte de la classe Login
     *
     * @return Retorna l'objecte Login amb les dades introduïdes de l'usuari
     * @author Eduard Masjuan
     */
    @NonNull
    private Login userLoginData() {
        Login login = new Login();
        login.setUsernameOrEmail(usernameInput);
        login.setPassword(passwordInput);
        return login;
    }

    /**
     * Obtenim el id del rol de l'usuari i reemplacem el fragment que conte el rol elegit
     *
     * @param idRol Valor int dels rols
     * @author Eduard Masjuan
     */
    private void chooseRole(int idRol) {

        if (idRol == 1) { // Admin
            preferences.setRole(idRol);
            frag.replaceFragment(R.id.container, UserAdmin.newInstance());
        } else if (idRol == 2) { // Chef
            preferences.setRole(idRol);
            frag.replaceFragment(R.id.container, UserCook.newInstance());
        } else if (idRol == 3) { // Client
            preferences.setRole(idRol);
            frag.replaceFragment(R.id.container, UserClient.newInstance());
        }
    }

    /**
     * Interfície que observa els canvis de text
     *
     * @author Eduard Masjuan
     */
    private final TextWatcher textWatcher = new TextWatcher() {

        // Abans d'escriure el text

        /**
         * Es crida quan es rep el nou caràcter inserit però encara no es mostra en pantalla
         */
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        /**
         * Es crida quan ha acabat de canviar el text
         */
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            // Capturem la escriptura d'inici sessió per part de l'usuari
            usernameInput = String.valueOf(b.inputEdtvUser.getText());
            passwordInput = String.valueOf(b.inputEdtvPassword.getText());

            boolean emptyFields = !usernameInput.isEmpty() && !passwordInput.isEmpty();

            // El button sera visible si els camps de text no estan buits
            b.bttAccept.setEnabled(emptyFields);

            // Si els camps no estan buits
            if (emptyFields) {
                b.bttAccept.setStrokeWidth(1);
            } else {
                b.bttAccept.setStrokeWidth(0);
                // Si algun camp esta buit
                if (usernameInput.isEmpty()) {
                    b.edtvUser.setError(null);
                }
                if (passwordInput.isEmpty()) {
                    b.edtvPassword.setError(null);
                }
            }
        }

        /**
         * Es crida després de que s'hagi canviat el text d'un camp
         */
        @Override
        public void afterTextChanged(Editable editable) {

            // Al clicar l'acció del teclat (done)
            b.inputEdtvPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                    // Amaga el teclat virtual
                    method.closeKeyboard(textView, requireActivity());

                    // Button Entrar amb 'animació'
                    if (b.bttAccept.isEnabled()) {
                        b.bttAccept.performClick();
                        b.bttAccept.setPressed(true);
                        b.bttAccept.invalidate();
                        // Retardem l'animació
                        b.bttAccept.postDelayed(new Runnable() {
                            public void run() {
                                b.bttAccept.setPressed(false);
                                b.bttAccept.invalidate();
                            }
                        }, 800);  // Retard de 0.8sec
                    }
                    return true;
                }
            });
        }
    };

}
