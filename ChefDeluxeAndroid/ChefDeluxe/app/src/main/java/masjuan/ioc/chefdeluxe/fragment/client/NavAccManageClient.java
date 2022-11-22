package masjuan.ioc.chefdeluxe.fragment.client;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import masjuan.ioc.chefdeluxe.R;
import masjuan.ioc.chefdeluxe.databinding.FragmentClientNavSettingsBinding;
import masjuan.ioc.chefdeluxe.fragment.userAccountSettings.ChangePassword;
import masjuan.ioc.chefdeluxe.fragment.userAccountSettings.DeleteAccount;
import masjuan.ioc.chefdeluxe.fragment.userAccountSettings.UpdateData;
import masjuan.ioc.chefdeluxe.utils.SharedPreferences;
import masjuan.ioc.chefdeluxe.utils.UtilsFragments;

/**
 * Classe Fragment de gestió del compte d'usuari Client
 */
public class NavAccManageClient extends Fragment {

    private UtilsFragments frag = null;
    private SharedPreferences preferences;

    /**
     * Constructor buit
     */
    public NavAccManageClient() {
        // Required empty public constructor
    }

    /**
     * Crea una nova instancia de NavAccManageClient
     *
     * @return NavAccManageClient
     */
    public static NavAccManageClient newInstance() {
        return new NavAccManageClient();
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
    }

    /**
     * Dissenya la interfície d'usuari per primera vegada.
     * Opcions de modificació de dades d'usuari.
     * Canvi de contrasenya
     * Logout
     * Eliminar conta d'usuari
     *
     * @param inflater           Infla la vista
     * @param container          Vista que s'adjuntarà a la interfície d'usuari
     * @param savedInstanceState Bundle
     * @return Vista
     * @author Eduard Masjuan
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentClientNavSettingsBinding b = FragmentClientNavSettingsBinding.inflate(inflater, container, false);
        preferences = new SharedPreferences(requireActivity());

        // Títol de la toolbar
        b.lyToolbar.toolbar.setTitle(getResources().getString(R.string.tv_acc_management));

        // Dades usuari client
        b.tvDatauser.setOnClickListener(view -> frag.replaceFragment(R.id.user_container, UpdateData.newInstance(), "accClientFrag"));

        // Canvi de password
        b.tvDatauserPassword.setOnClickListener(view -> frag.replaceFragment(R.id.user_container, ChangePassword.newInstance(), "changePasswordFrag"));

        // Tanquem sessió
        b.tvLogout.setOnClickListener(view -> preferences.logout(frag));

        // Elimina compte d'usuari client
        b.tvAccDeleted.setOnClickListener(view -> new DeleteAccount().show(getChildFragmentManager(), "deletedAccFrag"));

        return b.getRoot();
    }

}