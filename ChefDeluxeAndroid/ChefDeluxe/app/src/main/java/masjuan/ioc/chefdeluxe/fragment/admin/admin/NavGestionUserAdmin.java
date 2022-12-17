package masjuan.ioc.chefdeluxe.fragment.admin.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;

import masjuan.ioc.chefdeluxe.R;
import masjuan.ioc.chefdeluxe.databinding.FragmentAdminGestionBinding;
import masjuan.ioc.chefdeluxe.utils.SharedPreferences;
import masjuan.ioc.chefdeluxe.utils.UtilsFragments;

/**
 * Classe on es podrà seleccionar si es mostra el llistat d'usuaris o la creació d'un nou usuari
 * Només per administradors
 *
 * @author Eduard Masjuan
 */
public class NavGestionUserAdmin extends Fragment {

    private UtilsFragments frag = null;

    /**
     * Constructor buit
     */
    public NavGestionUserAdmin() {
        // Required empty public constructor
    }

    /**
     * Crea una nova instancia de NavGestionUserAdmin
     *
     * @return NavGestionUserAdmin
     */
    public static NavGestionUserAdmin newInstance() {
        return new NavGestionUserAdmin();
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
     * Boto de nou usuari
     * Boto de mostra el llistat d'usuaris
     *
     * @param inflater           Infla la vista
     * @param container          Vista que s'adjuntarà a la interfície d'usuari
     * @param savedInstanceState Bundle
     * @return Vista
     * @author Eduard Masjuan
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        masjuan.ioc.chefdeluxe.databinding.FragmentAdminGestionBinding b = FragmentAdminGestionBinding.inflate(inflater, container, false);
        SharedPreferences preferences = new SharedPreferences(requireActivity());

        // Chip Listener
        b.chipGroupFilter.setOnCheckedStateChangeListener(chipListener);
        if (b.chipInfo.isChecked()) {
            frag.replaceFragment(R.id.container_users_admin, NavDataUsersAdmin.newInstance());
        }

        return b.getRoot();
    }

    /**
     * Listener ChipGroup
     * Obrirà un fragment depenent del que es seleccioni, si llista o crea nou usuari
     */
    private final ChipGroup.OnCheckedStateChangeListener chipListener = new ChipGroup.OnCheckedStateChangeListener() {
        @Override
        public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {
            for (Integer id : checkedIds) {
                Chip chip = group.findViewById(id);
                if (chip != null) {
                    if (chip.getText().equals("Lista usuarios")) {
                        frag.replaceFragment(R.id.container_users_admin, NavDataUsersAdmin.newInstance());
                    } else if (chip.getText().equals("Nuevo usuario")) {
                        frag.replaceFragment(R.id.container_users_admin, NavAddEditUsersAdmin.newInstance());
                    }
                }
            }
        }
    };

}