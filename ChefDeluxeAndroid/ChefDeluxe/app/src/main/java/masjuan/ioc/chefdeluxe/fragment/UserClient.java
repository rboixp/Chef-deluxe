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
import masjuan.ioc.chefdeluxe.fragment.client.NavAccManageClient;
import masjuan.ioc.chefdeluxe.fragment.client.NavListReservationClient;
import masjuan.ioc.chefdeluxe.fragment.client.NavReservationClient;
import masjuan.ioc.chefdeluxe.utils.UtilsFragments;

/**
 * Fragment que ens permetra gestionar els usuaris clients
 *
 * @author Eduard Masjuan
 */
public class UserClient extends Fragment {

    private UtilsFragments frag = null;

    /**
     * Constructor buit
     */
    public UserClient() {
        // Required empty public constructor
    }

    /**
     * Crea una nova instancia de UserClient
     *
     * @return UserClient
     */
    public static UserClient newInstance() {
        return new UserClient();
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
     * Dissenya la interfície d'usuari per primera vegada
     * Mostra el panel principal del rol client
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
        b.bottomNavigation.inflateMenu(R.menu.bottom_nav_menu_client); // Menu Client
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
            fragment = new NavReservationClient();
        } else if (idItem == R.id.nav_book) {
            fragment = new NavListReservationClient();
        } else if (idItem == R.id.nav_config) {
            fragment = new NavAccManageClient();
        }

        if (fragment != null) {
            frag.replaceFragment(R.id.user_container, fragment);
        }
        return true;
    };

}





