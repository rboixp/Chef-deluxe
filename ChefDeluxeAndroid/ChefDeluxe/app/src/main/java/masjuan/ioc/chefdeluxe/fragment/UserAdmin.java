package masjuan.ioc.chefdeluxe.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textview.MaterialTextView;

import masjuan.ioc.chefdeluxe.R;
import masjuan.ioc.chefdeluxe.databinding.NavToolbarDrawerAdminBinding;
import masjuan.ioc.chefdeluxe.fragment.admin.admin.NavGestionUserAdmin;
import masjuan.ioc.chefdeluxe.fragment.admin.admin.NavStatisticsAdmin;
import masjuan.ioc.chefdeluxe.utils.Methods;
import masjuan.ioc.chefdeluxe.utils.SharedPreferences;
import masjuan.ioc.chefdeluxe.utils.UtilsFragments;

/**
 * Fragment inicial per els usuaris administradors
 *
 * @author Eduard Masjuan
 */
public class UserAdmin extends Fragment {
    private NavToolbarDrawerAdminBinding b;
    private SharedPreferences preferences;
    private UtilsFragments frag = null;

    /**
     * Constructor buit
     */
    public UserAdmin() {
        // Required empty public constructor
    }

    /**
     * Crea una nova instancia de UserAdmin
     *
     * @return UserAdmin
     */
    public static UserAdmin newInstance() {
        return new UserAdmin();
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
     * Dissenya la interfície d'usuari per primera vegada
     * Mostra el panel principal del rol admin
     *
     * @param inflater           Infla la vista
     * @param container          Vista que s'adjuntarà a la interfície d'usuari
     * @param savedInstanceState Bundle
     * @return Vista
     * @author Eduard Masjuan
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        b = NavToolbarDrawerAdminBinding.inflate(inflater, container, false);
        preferences = new SharedPreferences(requireActivity());

        // Títol de la barra
        b.lyToolbar.toolbar.setTitle("Gestión Chefdeluxe");

        // Drawer
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(getActivity(), b.drawerLayout, b.lyToolbar.toolbar, R.string.drawer_layout_open_admin, R.string.drawer_layout_close_admin);
        b.drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        // Nom de l'usuari administrador que esta connectat
        View headerView = b.navView.getHeaderView(0);
        MaterialTextView mHeader = headerView.findViewById(R.id.tv_name_user);
        mHeader.setText(getString(R.string.tv_userName_admin, preferences.getUsername()));

        // Panel Listener Navigation View
        b.navView.setNavigationItemSelectedListener(navViewListener);
        b.navView.setCheckedItem(R.id.mStatistics);

        b.bottomNavigation.setVisibility(View.GONE);

        // Mostra el fragment
        frag.replaceFragment(R.id.user_container, NavStatisticsAdmin.newInstance());

        return b.getRoot();
    }

    /**
     * Barra de navegació lateral d'opcions, permet escollir usuaris, reserves o disponibilitats
     */
    private final NavigationView.OnNavigationItemSelectedListener navViewListener = item -> {

        int idItem = item.getItemId();

        if (idItem == R.id.mStatistics) {
            frag.replaceFragment(R.id.user_container, NavStatisticsAdmin.newInstance());
        } else if (idItem == R.id.user) {
            frag.replaceFragment(R.id.user_container, NavGestionUserAdmin.newInstance());
        } else if (idItem == R.id.available) {

        } else if (idItem == R.id.reservation) {


        } else if (idItem == R.id.logout) {
            preferences.logout(frag);
        }

        // Tanca la Drawer Layout
        b.drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    };

}