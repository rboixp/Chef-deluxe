package masjuan.ioc.chefdeluxe.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import masjuan.ioc.chefdeluxe.R;
import masjuan.ioc.chefdeluxe.databinding.FragmentCookBinding;

public class CookFragment extends Fragment {

    private FragmentCookBinding b;

    public CookFragment() {
        // Required empty public constructor
    }

    public static CookFragment newInstance() {
        return new CookFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        b = FragmentCookBinding.inflate(inflater, container, false);

        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        b.toolbar.inflateMenu(R.menu.main_menu);
        updateToolbar();

        b.toolbar.setOnMenuItemClickListener(item -> {

            if (item.getItemId() == R.id.action_settings) {
                Toast.makeText(getActivity(), "Opcions", Toast.LENGTH_SHORT).show();

            } else if (item.getItemId() == R.id.action_logout) {
                logout();
            }
            return super.onOptionsItemSelected(item);
        });

        b.toolbar.setNavigationOnClickListener(view1 -> Toast.makeText(getActivity(), "Endarrera", Toast.LENGTH_SHORT).show());
    }

    // Mètode que modifica el menu de forma dinàmica
    public void updateToolbar() {
        MenuItem itemOption = b.toolbar.getMenu().findItem(R.id.action_settings);
        itemOption.setVisible(false);
    }

    // Mètode que fa de "logout", reemplacem el fragment
    public void logout() {
        requireActivity().getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.container, LoginFragment.newInstance(), null)
                //.addToBackStack(null)
                .commit();
    }
}