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
import java.util.Set;

import masjuan.ioc.chefdeluxe.R;
import masjuan.ioc.chefdeluxe.api.ApiClient;
import masjuan.ioc.chefdeluxe.api.ApiClientToken;
import masjuan.ioc.chefdeluxe.api.ApiService;
import masjuan.ioc.chefdeluxe.api.ApiServiceToken;
import masjuan.ioc.chefdeluxe.databinding.FragmentLoginBinding;
import masjuan.ioc.chefdeluxe.model.Login;
import masjuan.ioc.chefdeluxe.model.Role;
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
    private ApiCodes apiCodes = new ApiCodes();

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

                            //frag.replaceFragment(R.id.container, AdminFragment.newInstance());
                            getUser(preferences.getUsername(), preferences.getPassword());

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
                    countDownTimer(getResources().getString(R.string.codigo_onFailure_connexion));
                    //Toast.makeText(getActivity(), getResources().getString(R.string.codigo_onFailure_connexion), Toast.LENGTH_SHORT).show();

                } else {
                    Log.v("Código", getResources().getString(R.string.codigo_onFailure_conversion));
                    countDownTimer(getResources().getString(R.string.codigo_onFailure_conversion));
                    //Toast.makeText(getActivity(), getResources().getString(R.string.codigo_onFailure_conversion), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Aconseguim el rol de l'usuari passat per parametre (parametre de l'ApiService)
    private void getUser(String username, String password) {
        ApiServiceToken apiServiceToke = ApiClientToken.getInstance(preferences.getToken());

        Call<User> user = apiServiceToke.getRol(username, password);

        user.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        // Recuperem el rol
                        Role rol = new Role();
                        Set<Role> stRole = response.body().getRoles();
                        stRole.add(rol);

                        for (Role role : stRole) {
                            takeRol((int) role.getId());
                            // System.out.print("Id rol" + rol.getId() + "Nom rol" + role.getRole());
                        }

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
                    Log.v("Código", getResources().getString(R.string.codigo_onFailure_connexion));
                    countDownTimer(getResources().getString(R.string.codigo_onFailure_connexion));


                } else {
                    Log.v("Código", getResources().getString(R.string.codigo_onFailure_conversion));
                    countDownTimer(getResources().getString(R.string.codigo_onFailure_conversion));
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
    private void takeRol(int idRol) {
        // Replaçem el fragment per el de l'usuari que ha iniciat sessió
        if (idRol == 1) { // Admin
            frag.replaceFragment(R.id.container, AdminFragment.newInstance());
        } else if (idRol == 2) { // Chef
            frag.replaceFragment(R.id.container, CookFragment.newInstance());
        } else if (idRol == 3) { // Client
            frag.replaceFragment(R.id.container, ClientFragment.newInstance());
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