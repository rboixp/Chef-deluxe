package masjuan.ioc.chefdeluxe.fragment.cook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import masjuan.ioc.chefdeluxe.R;
import masjuan.ioc.chefdeluxe.databinding.FragmentCookNavSettingsBinding;
import masjuan.ioc.chefdeluxe.fragment.userAccountSettings.ChangePassword;
import masjuan.ioc.chefdeluxe.fragment.userAccountSettings.DeleteAccount;
import masjuan.ioc.chefdeluxe.fragment.userAccountSettings.UpdateData;
import masjuan.ioc.chefdeluxe.utils.SharedPreferences;
import masjuan.ioc.chefdeluxe.utils.UtilsFragments;

/**
 * Classe Fragment de gestió del compte d'usuari Cuiner
 */
public class NavAccManageCook extends Fragment {

    private UtilsFragments frag = null;
    private SharedPreferences preferences;

    /**
     * Constructor buit
     */
    public NavAccManageCook() {
        // Required empty public constructor
    }

    /**
     * Crea una nova instancia de NavAccManageCook
     *
     * @return NavAccManageCook
     */
    public static NavAccManageCook newInstance() {
        return new NavAccManageCook();
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
    }

    /**
     * Dissenya la interfície d'usuari per primera vegada.
     * Opció de logout
     *
     * @param inflater           Infla la vista
     * @param container          Vista que s'adjuntarà a la interfície d'usuari
     * @param savedInstanceState Bundle
     * @return Vista
     * @author Eduard Masjuan
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentCookNavSettingsBinding b = FragmentCookNavSettingsBinding.inflate(inflater, container, false);
        preferences = new SharedPreferences(requireActivity());

        // Títol per la toolbar
        b.lyToolbar.toolbar.setTitle(getResources().getString(R.string.tv_acc_management));

        // Dades usuari client
        b.tvDatauser.setOnClickListener(view -> frag.replaceFragment(R.id.user_container, UpdateData.newInstance(), "accCookFrag"));

        // Canvi de password
        b.tvDatauserPassword.setOnClickListener(view -> frag.replaceFragment(R.id.user_container, ChangePassword.newInstance(), "changePasswordFrag"));

        // Tarifa
        b.tvDatauserTarifa.setOnClickListener(view -> frag.replaceFragment(R.id.user_container, TarifaCook.newInstance(), "tarifaFrag"));

        // Menu
        b.tvDatauserMenu.setOnClickListener(view -> frag.replaceFragment(R.id.user_container, MenuCook.newInstance(), "menuFrag"));

        // Tanquem sessió
        b.tvLogout.setOnClickListener(view -> preferences.logout(frag));

        // Elimina compte d'usuari client
        b.tvAccDeleted.setOnClickListener(view -> new DeleteAccount().show(getChildFragmentManager(), "deletedAccFrag"));

        return b.getRoot();
    }

}