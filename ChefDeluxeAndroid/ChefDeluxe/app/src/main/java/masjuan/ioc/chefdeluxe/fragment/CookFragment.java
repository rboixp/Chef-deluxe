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
import masjuan.ioc.chefdeluxe.api.ApiClientToken;
import masjuan.ioc.chefdeluxe.databinding.FragmentCookBinding;
import masjuan.ioc.chefdeluxe.utils.FragmentUtils;
import masjuan.ioc.chefdeluxe.utils.SharedPreferences;

public class CookFragment extends Fragment {
    private FragmentUtils frag = null;
    private FragmentCookBinding b;
    private SharedPreferences preferences;

    public CookFragment() {
        // Required empty public constructor
    }

    public static CookFragment newInstance() {
        return new CookFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        frag = new FragmentUtils(requireActivity().getSupportFragmentManager());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        b = FragmentCookBinding.inflate(inflater, container, false);
        preferences = new SharedPreferences(requireActivity());
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

    // M??tode que modifica el menu de forma din??mica
    public void updateToolbar() {
        MenuItem itemOption = b.toolbar.getMenu().findItem(R.id.action_settings);
        itemOption.setVisible(false);
    }

    // M??tode que fa de "logout", reemplacem el fragment
    public void logout() {
        preferences.cleanToken();
        preferences.cleanUsername();
        String token = preferences.getToken();
        if (token.equals("")) {
            ApiClientToken.deleteInstance();
            frag.replaceFragment(R.id.container, LoginFragment.newInstance());
        }
    }
}