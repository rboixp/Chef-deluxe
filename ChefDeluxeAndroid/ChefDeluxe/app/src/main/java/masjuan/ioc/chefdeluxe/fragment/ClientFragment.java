package masjuan.ioc.chefdeluxe.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import masjuan.ioc.chefdeluxe.R;
import masjuan.ioc.chefdeluxe.api.ApiClientToken;
import masjuan.ioc.chefdeluxe.api.ApiServiceToken;
import masjuan.ioc.chefdeluxe.databinding.FragmentClientBinding;
import masjuan.ioc.chefdeluxe.model.User;
import masjuan.ioc.chefdeluxe.utils.ApiCodes;
import masjuan.ioc.chefdeluxe.utils.FragmentUtils;
import masjuan.ioc.chefdeluxe.utils.SharedPreferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientFragment extends Fragment {

    private FragmentClientBinding b;
    private SharedPreferences preferences;
    private FragmentUtils frag = null;
    private ArrayList<String> userList;
    private ArrayAdapter arrayAdapter;
    private final ApiCodes apiCodes = new ApiCodes();

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
        userList = new ArrayList<>();

        arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, userList);
        b.listView.setAdapter(arrayAdapter);
        getAllClients();

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

    private void getAllClients() {
        ApiServiceToken apiService = ApiClientToken.getInstance(preferences.getToken());
        Call<List<User>> dataUser = apiService.getAllUsers();
        dataUser.enqueue(new Callback<List<User>>() {

            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        if (apiCodes.codeHttp(response.code())) {

                            for (User usuario : response.body()) {
                                userList.add("Usuario: " + usuario.getNombre() + " --> Password: " + usuario.getPassword());
                            }
                            arrayAdapter.notifyDataSetChanged();

                        }
                    }
                } else {
                    apiCodes.codeHttp(response.code());
                }

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                // Comprova si l'objecte t es una classe
                if (t instanceof IOException) {
                    Log.v("Código", getResources().getString(R.string.codigo_onFailure_connexion));
                    Toast.makeText(getActivity(), getResources().getString(R.string.codigo_onFailure_connexion), Toast.LENGTH_SHORT).show();

                } else {
                    Log.v("Código", getResources().getString(R.string.codigo_onFailure_conversion));
                    Toast.makeText(getActivity(), getResources().getString(R.string.codigo_onFailure_conversion), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    // Mètode que tanca sessió
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