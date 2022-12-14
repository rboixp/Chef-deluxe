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
import masjuan.ioc.chefdeluxe.api.ApiClientToken;
import masjuan.ioc.chefdeluxe.databinding.FragmentClientBinding;
import masjuan.ioc.chefdeluxe.utils.ApiCodes;
import masjuan.ioc.chefdeluxe.utils.FragmentUtils;
import masjuan.ioc.chefdeluxe.utils.SharedPreferences;

public class ClientFragment extends Fragment {

    private FragmentClientBinding b;
    private SharedPreferences preferences;
    private FragmentUtils frag = null;

    ApiCodes apiCodes = new ApiCodes();

    public ClientFragment() {
        // Required empty public constructor
    }

    public static ClientFragment newInstance() {
        return new ClientFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        frag = new FragmentUtils(requireActivity().getSupportFragmentManager());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        b = FragmentClientBinding.inflate(inflater, container, false);
        preferences = new SharedPreferences(requireActivity());

        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        b.toolbar.inflateMenu(R.menu.main_menu);
        updateToolbar();
        b.toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_logout) {
                logout();
            }
            return super.onOptionsItemSelected(item);
        });
    }

    public void updateToolbar() {
        MenuItem itemOption = b.toolbar.getMenu().findItem(R.id.action_settings);
        itemOption.setVisible(false);
    }

    // M??tode que tanca sessi??
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