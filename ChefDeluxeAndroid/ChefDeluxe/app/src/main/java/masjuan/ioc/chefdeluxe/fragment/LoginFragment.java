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
import masjuan.ioc.chefdeluxe.api.ApiService;
import masjuan.ioc.chefdeluxe.databinding.FragmentLoginBinding;
import masjuan.ioc.chefdeluxe.model.Login;
import masjuan.ioc.chefdeluxe.model.Token;
import masjuan.ioc.chefdeluxe.utils.ApiCodes;
import masjuan.ioc.chefdeluxe.utils.FragmentUtils;
import masjuan.ioc.chefdeluxe.utils.SharedPreferences;
import masjuan.ioc.chefdeluxe.utils.ValidateLogin;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {

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
        b.tvRegister.setOnClickListener(view -> {
            // Anar a fragment registre
        });

        // Boto d'inici de sessió
        b.bttAccept.setOnClickListener(view -> {
            // Capturem la escriptura d'inici sessió per part de l'usuari
            String usernameInput = String.valueOf(b.edtvUser.getText());
            String passwordInput = String.valueOf(b.edtvPassword.getText());

            // Validació login
            if (validate.usernameEmpty(usernameInput) || validate.passwordEmpty(passwordInput)) {
                countDownTimer(getResources().getString(R.string.tv_empty_user_pass));

            } else {
                // Objecte Login rep la informació del servidor
                Login log = userLoginData(usernameInput, passwordInput);
                getToken(log);
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

                            // Replaçem el fragment per el de l'usuari que ha iniciat sessió
                            frag.replaceFragment(R.id.container, AdminFragment.newInstance());
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

    // Dades del login
    @NonNull
    private Login userLoginData(String username, String password) {
        Login login = new Login();
        login.setUsernameOrEmail(username);
        login.setPassword(password);

        return login;
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