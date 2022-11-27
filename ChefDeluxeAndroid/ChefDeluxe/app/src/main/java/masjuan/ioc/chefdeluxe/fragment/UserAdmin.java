package masjuan.ioc.chefdeluxe.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import masjuan.ioc.chefdeluxe.R;
import masjuan.ioc.chefdeluxe.databinding.FragmentAdminBinding;
import masjuan.ioc.chefdeluxe.utils.SharedPreferences;
import masjuan.ioc.chefdeluxe.utils.UtilsFragments;

/**
 * Fragment per els usuaris administradors
 *
 * @author Eduard Masjuan
 */
public class UserAdmin extends Fragment {

    private FragmentAdminBinding b;
    private UtilsFragments frag = null;
    private SharedPreferences preferences;

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
        b = FragmentAdminBinding.inflate(inflater, container, false);
        preferences = new SharedPreferences(requireActivity());
        return b.getRoot();
    }

    /**
     * Canvis en la configuració de vista. Fem logout des del menu Toolbar.
     *
     * @param view               Vista
     * @param savedInstanceState Bundle
     * @author Eduard Masjuan
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        b.toolbar.inflateMenu(R.menu.main_menu);
        updateToolbar();
        b.toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_logout) {
                preferences.logout(frag);
            }
            return super.onOptionsItemSelected(item);
        });
    }

    /**
     * Modifica la barra. Mostrar o ocultar items del menu
     *
     * @author Eduard Masjuan
     */
    public void updateToolbar() {
        MenuItem itemOption = b.toolbar.getMenu().findItem(R.id.action_settings);
        itemOption.setVisible(false);
    }

}