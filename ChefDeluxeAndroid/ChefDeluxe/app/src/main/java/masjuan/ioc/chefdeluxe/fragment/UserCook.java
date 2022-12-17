package masjuan.ioc.chefdeluxe.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationBarView;

import masjuan.ioc.chefdeluxe.R;
import masjuan.ioc.chefdeluxe.databinding.ToolbarNavigationUsersBinding;
import masjuan.ioc.chefdeluxe.fragment.cook.NavAccManageCook;
import masjuan.ioc.chefdeluxe.fragment.cook.NavAvailableCook;
import masjuan.ioc.chefdeluxe.fragment.cook.NavListReservationCook;
import masjuan.ioc.chefdeluxe.utils.UtilsFragments;

/**
 * Fragment que ens permetra gestionar els usuaris cuiners
 *
 * @author Eduard Masjuan
 */
public class UserCook extends Fragment {

    private UtilsFragments frag = null;

    /**
     * Constructor buit
     */
    public UserCook() {
        // Required empty public constructor
    }

    /**
     * Crea una nova instancia de UserCook
     *
     * @return UserCook
     */
    public static UserCook newInstance() {
        return new UserCook();
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
     * Mostra el panel principal del rol cuiner
     *
     * @param inflater           Infla la vista
     * @param container          Vista que s'adjuntarà a la interfície d'usuari
     * @param savedInstanceState Bundle
     * @return Vista
     * @author Eduard Masjuan
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ToolbarNavigationUsersBinding b = ToolbarNavigationUsersBinding.inflate(inflater, container, false);

        b.bottomNavigation.getMenu().clear(); // Netejem el menu anterior
        b.bottomNavigation.inflateMenu(R.menu.bottom_nav_menu_cook); // Menu Cuiner
        b.bottomNavigation.setOnItemSelectedListener(navClientListener);
        b.bottomNavigation.setSelectedItemId(R.id.nav_home); // Inici bottomNavigation

        return b.getRoot();
    }

    /**
     * Barra de navegació inferior que ens permet navegar entre diferents destins
     */
    private final NavigationBarView.OnItemSelectedListener navClientListener = item -> {
        Fragment fragment = null;
        int idItem = item.getItemId();
        if (idItem == R.id.nav_home) {
            fragment = new NavAvailableCook();
        } else if (idItem == R.id.nav_dispo) {
            fragment = new NavListReservationCook();
        } else if (idItem == R.id.nav_config) {
            fragment = new NavAccManageCook();
        }

        if (fragment != null) {
            frag.replaceFragment(R.id.user_container, fragment);
        }
        return true;
    };

}