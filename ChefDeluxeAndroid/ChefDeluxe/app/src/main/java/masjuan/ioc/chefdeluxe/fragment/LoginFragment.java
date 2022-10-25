package masjuan.ioc.chefdeluxe.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.io.IOException;

import masjuan.ioc.chefdeluxe.R;
import masjuan.ioc.chefdeluxe.api.ApiClient;
import masjuan.ioc.chefdeluxe.api.ApiClientToken;
import masjuan.ioc.chefdeluxe.api.ApiService;
import masjuan.ioc.chefdeluxe.api.ApiServiceToken;
import masjuan.ioc.chefdeluxe.databinding.FragmentLoginBinding;
import masjuan.ioc.chefdeluxe.model.Login;
import masjuan.ioc.chefdeluxe.model.Token;
import masjuan.ioc.chefdeluxe.model.User;
import masjuan.ioc.chefdeluxe.utils.ApiCodes;
import masjuan.ioc.chefdeluxe.utils.FragmentUtils;
import masjuan.ioc.chefdeluxe.utils.SharedPreferences;
import masjuan.ioc.chefdeluxe.utils.ValidateLogin;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {

    private String usernameInput;
    private String passwordInput;

    private FragmentLoginBinding b;
    private FragmentUtils frag = null;
    private SharedPreferences preferences;

    ApiCodes apiCodes = new ApiCodes();

    public LoginFragment() {
        // Required empty public constructor
    }

    @NonNull
    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        frag = new FragmentUtils(requireActivity().getSupportFragmentManager());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        b = FragmentLoginBinding.inflate(inflater, container, false);
        b.lyconstFailedUserPass.setVisibility(View.GONE);
        ValidateLogin validate = new ValidateLogin();

        // Nou objecte SharedPreferences
        preferences = new SharedPreferences(requireActivity());

        // Boto fragment registre
        b.tvRegister.setOnClickListener(view -> frag.replaceFragment(R.id.container, RegistrationFragment.newInstance(), "registration"));


        // Boto d'inici de sessió
        b.bttAccept.setOnClickListener(view -> {
            preferences.cleanToken();

            // Capturem la escriptura d'inici sessió per part de l'usuari
            usernameInput = String.valueOf(b.edtvUser.getText());
            passwordInput = String.valueOf(b.edtvPassword.getText());
            preferences.setUsername(usernameInput);

            // Validació login
            if (validate.usernameEmpty(usernameInput) || validate.passwordEmpty(passwordInput)) {
                countDownTimer(getResources().getString(R.string.tv_empty_user_pass));
            } else {

                // Objecte Login rep la informació del servidor
                Login login = userLoginData(usernameInput, passwordInput);
                getToken(login);

            }
        });

        return b.getRoot();
    }


    // Mètode que retorna un el token de l'usuari
    private void getToken(Login login) {
        ApiService apiService = ApiClient.getInstance();

        // Cridem l'objecte que realitzara la petició
        Call<Token> loginResponse = apiService.tokenLogin(login);

        // Envia de forma asincrònica  la petició i notifica a la app amb un Callback<Token> quan la resposta retorna.
        loginResponse.enqueue(new Callback<Token>() {
            // Es crida quan hi ha una resposta Http
            @Override
            public void onResponse(@NonNull Call<Token> call, @NonNull Response<Token> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        String token = response.body().getTokenDeAcceso(); // Guardem el token
                        // Comprova el codi rebut
                        if (apiCodes.codeHttp(response.code())) {
                            // Guardem el token de l'usuari a les SharedPreferences
                            preferences.setToken(token);
                            preferences.setUsername(usernameInput);
                            preferences.setPassword(passwordInput);
                            getRol(preferences.getUsername(), preferences.getPassword());


                            // Replaçem el fragment per el de l'usuari que ha iniciat sessió
                            //frag.replaceFragment(R.id.container, AdminFragment.newInstance());
                        }
                    }
                } else {
                    apiCodes.codeHttp(response.code());
                    countDownTimer(getResources().getString(R.string.tv_failed_user_pass));
                }
            }

            // Es crida quan hi ha problema a la xarxa
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

    // Aconseguim el rol de l'usuari passat per parametre (parametre de l'ApiService)
    private void getRol(String username, String password) {
        ApiServiceToken apiServiceToke = ApiClientToken.getInstance(preferences.getToken());

        Call<User> rol = apiServiceToke.getRol(username, password);

        rol.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        // Fragment depenen del rol de cada usuari
                        takeRol("ROLE_ADMIN"); // ROLE_ADMI, ROLE_CHEF, ROLE_CLIENT


                    } else {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(getActivity(), "Codi:" + response.code() + getResources().getString(R.string.codigo_401), Toast.LENGTH_SHORT).show();
                    apiCodes.codeHttp(response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                if (t instanceof IOException) {
                    Log.v("Código", "Fallo en la red. Comprueba tu conexión");
                    Toast.makeText(getActivity(), "Fallo en la red. Comprueba la conexión.", Toast.LENGTH_SHORT).show();

                } else {
                    Log.v("Código", "Problema de conversión2.");
                    Toast.makeText(getActivity(), "Problema de conversión2.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Dades del login
    @NonNull
    private Login userLoginData(String username, String password) {
        Login login = new Login();
        login.setUsernameOrEmail(username);
        login.setPassword(password);
        return login;
    }

    // Depenen del rol de l'usuari va a un fragment o un altre
    private void takeRol(@NonNull String role) {
        // Replaçem el fragment per el de l'usuari que ha iniciat sessió
        if (role.equals(getResources().getString(R.string.name_rol_admin))) {
            frag.replaceFragment(R.id.container, AdminFragment.newInstance());
        } else if (role.equals(getResources().getString(R.string.name_rol_client))) {
            frag.replaceFragment(R.id.container, ClientFragment.newInstance());
        } else if (role.equals(getResources().getString(R.string.name_rol_chef))) {
            frag.replaceFragment(R.id.container, CookFragment.newInstance());
        }
    }


    // Mètode que mostra i oculta un tv al fallar el login
    private void countDownTimer(String errorLogin) {

        new CountDownTimer(5000, 1000) {
            public void onTick(long millisUntilFinished) {
                b.lyconstFailedUserPass.setVisibility(View.VISIBLE);
                b.tvFailedUserPass.setText(errorLogin);
            }

            public void onFinish() {
                b.lyconstFailedUserPass.setVisibility(View.INVISIBLE);
            }
        }.start();
    }

}