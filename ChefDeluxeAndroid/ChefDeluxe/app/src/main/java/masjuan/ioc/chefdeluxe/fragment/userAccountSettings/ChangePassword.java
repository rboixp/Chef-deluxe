package masjuan.ioc.chefdeluxe.fragment.userAccountSettings;

import static masjuan.ioc.chefdeluxe.utils.Methods.navigationToolbar;

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

import java.io.IOException;

import masjuan.ioc.chefdeluxe.R;
import masjuan.ioc.chefdeluxe.api.ApiGlobal;
import masjuan.ioc.chefdeluxe.databinding.FragmentUserChangePasswordBinding;
import masjuan.ioc.chefdeluxe.model.User;
import masjuan.ioc.chefdeluxe.utils.ApiCodes;
import masjuan.ioc.chefdeluxe.utils.Methods;
import masjuan.ioc.chefdeluxe.utils.SharedPreferences;
import masjuan.ioc.chefdeluxe.utils.UtilsFragments;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Classe que ens permet canviar la contrasenya de l'usuari
 *
 * @author Eduard Masjuan
 */
public class ChangePassword extends Fragment {

    private FragmentUserChangePasswordBinding b;

    private UtilsFragments frag = null;
    private Methods method;

    private FragmentManager fragmentManager = null;
    private SharedPreferences preferences;

    private String currentPassword, newPassword, repeatNewPassword;
    private ApiCodes apiCodes;
    private ApiGlobal apiGlobal;

    /**
     * Constructor buit
     */
    public ChangePassword() {
        // Required empty public constructor
    }

    /**
     * Crea una nova instancia de ChangePassword
     *
     * @return ChangePassword
     */
    public static ChangePassword newInstance() {
        return new ChangePassword();
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
     * Opció de de canvi de contrasenya
     *
     * @param inflater           Infla la vista
     * @param container          Vista que s'adjuntarà a la interfície d'usuari
     * @param savedInstanceState Bundle
     * @return Vista
     * @author Eduard Masjuan
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        b = FragmentUserChangePasswordBinding.inflate(inflater, container, false);
        preferences = new SharedPreferences(requireActivity());
        method = new Methods();
        apiCodes = new ApiCodes();

        // Toolbar popBackStack (ens retorna al fragment)
        navigationToolbar(b.lyToolbar.toolbar, frag, fragmentManager);

        // Títol per la toolbar
        b.lyToolbar.toolbar.setTitle(getResources().getString(R.string.tv_title_password));

        // TextWatcher- Escolta els canvis que es produeixen en els camps següents
        b.inputEdtvCurrentPassword.addTextChangedListener(textWatcher);
        b.inputEdtvNewPassword.addTextChangedListener(textWatcher);
        b.inputEdtvRepeatNewPassword.addTextChangedListener(textWatcher);

        // Boto per canviar la contrasenya
        b.bttConfirmPassword.setOnClickListener(view -> {
            // si es modifica

            if (checkCurrentPassword() && !checkCurrentNewPassword() && checkRepeatNewPassword()) {
                method.dataInputLayoutEmpty(b.edtvCurrentPassword, null, currentPassword);
                method.dataInputLayoutEmpty(b.edtvNewPassword, null, newPassword);
                method.dataInputLayoutEmpty(b.edtvRepeatNewPassword, null, repeatNewPassword);
                putChangePassword(preferences.getUsername(), newPassword);

            } else {

                if (!checkCurrentPassword()) {
                    method.dataInputLayoutEmpty(b.edtvCurrentPassword, getResources().getString(R.string.tv_error_password), currentPassword);
                } else {
                    method.dataInputLayoutEmpty(b.edtvCurrentPassword, null, currentPassword);
                }

                if (!checkRepeatNewPassword()) {
                    method.dataInputLayoutEmpty(b.edtvNewPassword, getResources().getString(R.string.tv_error_password2), newPassword);
                    method.dataInputLayoutEmpty(b.edtvRepeatNewPassword, getResources().getString(R.string.tv_error_password2), repeatNewPassword);
                } else {
                    method.dataInputLayoutEmpty(b.edtvNewPassword, null, newPassword);
                    method.dataInputLayoutEmpty(b.edtvRepeatNewPassword, null, repeatNewPassword);
                }

                if (checkCurrentNewPassword()) {
                    method.dataInputLayoutEmpty(b.edtvNewPassword, getResources().getString(R.string.tv_error_password3), newPassword);
                } else {
                    method.dataInputLayoutEmpty(b.edtvNewPassword, null, newPassword);
                }
            }
        });

        return b.getRoot();
    }

    /**
     * Comprovar que la contrasenya escrita és l'actual
     *
     * @return boolean
     */
    private boolean checkCurrentPassword() {
        return currentPassword.equals(preferences.getPassword());
    }

    /**
     * Comprovar que la nova contrasenya no siguin igual que l'anterior
     *
     * @return boolean
     */
    private boolean checkCurrentNewPassword() {
        return newPassword.equals(preferences.getPassword());
    }

    /**
     * Comprovar que la nova contrasenya i la repetició de contranseya sigui'n iguals
     *
     * @return boolean
     */
    private boolean checkRepeatNewPassword() {
        return newPassword.equals(repeatNewPassword);
    }

    /**
     * Mètode on realitza una petició PUT. Canvi de contrasenya
     *
     * @param username    String nom d'usuari
     * @param newPassword String nova contrasenya
     * @author Eduard Masjuan
     */
    private void putChangePassword(String username, String newPassword) {
       // Call<User> updatePassword = apiGlobal.apiClientCert(getActivity(), preferences.getToken()).putChangePass(username, newPassword);
        Call<User> updatePassword = apiGlobal.apiClient(preferences.getToken()).putChangePass(username, newPassword);

        updatePassword.enqueue(new Callback<User>() {
            /**
             * Es crida si ja una resposta HTTP correcte
             * @param call Sol.licita al API les dades
             * @param response  Obtenir les dades
             */
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (apiCodes.codeHttp(response.code())) {
                            Toast.makeText(getActivity(), getResources().getString(R.string.tv_info_password2), Toast.LENGTH_SHORT).show();
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
     * Interfície que observa els canvis de text
     *
     * @author Eduard Masjuan
     */
    private final TextWatcher textWatcher = new TextWatcher() {

        /**
         * Es crida quan es rep el nou caràcter inserit però encara no es mostra en pantalla
         */
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        /**
         * Es crida quan ha acabat de canviar el text
         * Si els camps estan omplerts s’habilitarà el boto per poder acceptar el canvi de contrasenya
         */
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            // Capturem la escriptura
            currentPassword = String.valueOf(b.inputEdtvCurrentPassword.getText());
            newPassword = String.valueOf(b.inputEdtvNewPassword.getText());
            repeatNewPassword = String.valueOf(b.inputEdtvRepeatNewPassword.getText());

            // Si el camp contrasenya no es buit activa el camp de repetició de contrasenya
            boolean repeatPassword = !newPassword.isEmpty();
            b.inputEdtvRepeatNewPassword.setEnabled(repeatPassword);

            // Si els camps estan plens s'activa el boto de guardar.
            boolean validat = method.dataEmpty(currentPassword, newPassword, repeatNewPassword);
            b.bttConfirmPassword.setEnabled(validat);


            // Si tots els camps estan omplerts
            if (validat) {
                b.bttConfirmPassword.setStrokeWidth(1);

            } else { // Si tots camps estan en blanc o alguns d'ells
                b.bttConfirmPassword.setStrokeWidth(0);
                method.dataInputLayoutEmpty(b.edtvCurrentPassword, null, currentPassword);
                method.dataInputLayoutEmpty(b.edtvNewPassword, null, newPassword);
                method.dataInputLayoutEmpty(b.edtvRepeatNewPassword, null, repeatNewPassword);
            }
        }

        /**
         * Es crida després de que s'hagi canviat el text d'un camp i al clicar el boto Done del teclat
         */
        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

}