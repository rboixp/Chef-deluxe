package masjuan.ioc.chefdeluxe.fragment.userAccountSettings;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.IOException;

import masjuan.ioc.chefdeluxe.R;
import masjuan.ioc.chefdeluxe.api.ApiGlobal;
import masjuan.ioc.chefdeluxe.databinding.FragmentClientNavSettingsBinding;
import masjuan.ioc.chefdeluxe.utils.ApiCodes;
import masjuan.ioc.chefdeluxe.utils.SharedPreferences;
import masjuan.ioc.chefdeluxe.utils.UtilsFragments;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Classe que ens servirà perquè el propi client pugui eliminar el seu conte d'usuari
 */
public class DeleteAccount extends DialogFragment {
    private SharedPreferences preferences;
    private ApiCodes apiCodes;
    private UtilsFragments frag = null;
    private ApiGlobal apiGlobal;

    /**
     * Constructor
     */
    public DeleteAccount() {
    }

    /**
     * S'executa quan l'activitat es crea per primera vegada
     *
     * @param savedInstanceState Objecte Bundle que conte l'estat de l'activitat guardat
     * @author Eduard Masjuan
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentClientNavSettingsBinding b = FragmentClientNavSettingsBinding.inflate(inflater, container, false);
        frag = new UtilsFragments(requireActivity().getSupportFragmentManager());
        preferences = new SharedPreferences(requireActivity());
        apiCodes = new ApiCodes();
        apiGlobal = new ApiGlobal();
        return b.getRoot();
    }

    /**
     * Mostra un quadre de diàleg on confirmarem le'liminació de la conta d'usuari
     *
     * @param savedInstanceState Objecte Bundle que conte l'estat de l'activitat guardat
     * @return Alert Dialog
     * @author Eduard Masjuan
     */
    @NonNull
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(requireActivity(),
                com.google.android.material.R.style.MaterialAlertDialog_Material3)
                .setTitle(getResources().getString(R.string.txt_title_accountDelete))
                .setMessage(getResources().getString(R.string.txt_delete_accountInfo))
                .setPositiveButton(getResources().getString(R.string.txt_delete_yes), (dialog, which) -> deleteUserApi(preferences.getUsername()))
                .setNegativeButton(getResources().getString(R.string.txt_delete_no), (dialog, which) -> dialog
                        .cancel());

        return dialogBuilder.create();
    }

    /**
     * Mètode on realitza una petició DELETE. Elimina la conta de l'usuari.
     *
     * @param username String nom d'usuari
     * @author Eduard Masjuan
     */
    public void deleteUserApi(String username) {
        //Call<String> deletedUser = apiGlobal.apiClientCert(getActivity(), preferences.getToken()).deleteUserData(username);
        Call<String> deletedUser = apiGlobal.apiClient(preferences.getToken()).deleteUserData(username);

        deletedUser.enqueue(new Callback<String>() {
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
                            // Tanquem diàleg
                            dismiss();
                            // Logout
                            preferences.logout(frag);
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

}