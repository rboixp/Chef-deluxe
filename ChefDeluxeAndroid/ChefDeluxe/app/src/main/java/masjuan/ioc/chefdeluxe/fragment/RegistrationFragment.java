package masjuan.ioc.chefdeluxe.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.io.IOException;

import masjuan.ioc.chefdeluxe.R;
import masjuan.ioc.chefdeluxe.api.ApiClient;
import masjuan.ioc.chefdeluxe.api.ApiService;
import masjuan.ioc.chefdeluxe.databinding.FragmentRegistrationBinding;
import masjuan.ioc.chefdeluxe.model.Registration;
import masjuan.ioc.chefdeluxe.utils.ApiCodes;
import masjuan.ioc.chefdeluxe.utils.FragmentUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationFragment extends Fragment {

    private FragmentRegistrationBinding b;
    private FragmentUtils frag = null;
    private FragmentManager fragmentManager = null;
    private String nameRole = "";
    private int roleUser = 0;
    ApiCodes apiCodes = new ApiCodes();

    public RegistrationFragment() {
        // Required empty public constructor
    }

    public static RegistrationFragment newInstance() {
        return new RegistrationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        frag = new FragmentUtils(requireActivity().getSupportFragmentManager());
        fragmentManager = requireActivity().getSupportFragmentManager();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        b = FragmentRegistrationBinding.inflate(inflater, container, false);
        b.tvLogin.setOnClickListener(view -> frag.popBackStack(fragmentManager));

        idRole();
        b.bttCreateAcc.setOnClickListener(view -> {
            // Nou Usuari
            Registration user = userRegistrationData();
            addUser(user);

        });

        return b.getRoot();
    }

    // Retorna el nom del rol seleccionat
    private String checkRole(int idRole) {
        if (idRole == 2) {
            nameRole = "ROLE_CLIENT";
        } else if (idRole == 3) {
            nameRole = "ROLE_CHEF";
        }
        return nameRole;
    }

    // Mètode que retorna el id del rol seleccionar
    private int idRole() {
        b.rgRol.setOnCheckedChangeListener((radioGroup, checkId) -> {
            if (checkId == b.rbRolClient.getId()) {
                roleUser = 2; // Rol Chef
            } else if (checkId == b.rbRolCook.getId()) {
                roleUser = 3; // Rol Client
            }
        });
        return roleUser;
    }

    // Mètode que agregar un objecte Register
    private void addUser(Registration user) {

        ApiService apiService = ApiClient.getInstance();
        Call<Registration> callRegister = apiService.addUser(user); // haurai de ser potser User i fer array el rol?

        callRegister.enqueue(new Callback<Registration>() {
            @Override
            public void onResponse(@NonNull Call<Registration> call, @NonNull Response<Registration> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        //Log.v("Código", response.code() + "  " + response.body().getS());

                        // Comprova el codi rebut
                        if (apiCodes.codeHttp(response.code())) {
                            Toast.makeText(getActivity(), "WIN!", Toast.LENGTH_SHORT).show();
                            frag.popBackStack(fragmentManager);

                        }
                    }
                } else {
                    Toast.makeText(getActivity(), "error: " + response.code(), Toast.LENGTH_SHORT).show();
                    apiCodes.codeHttp(response.code());
                }
            }


            @Override
            public void onFailure(@NonNull Call<Registration> call, @NonNull Throwable t) {
                // Comprova si l'objecte t es una classe
                if (t instanceof IOException) {
                    Log.v("Código", t.getMessage() + "  " + getResources().getString(R.string.codigo_onFailure_connexion));
                    //Toast.makeText(getActivity(), getResources().getString(R.string.codigo_onFailure_connexion), Toast.LENGTH_SHORT).show();

                } else {
                    Log.v("Código", t.getMessage() + "  " + "Usuario Registrado. " + getResources().getString(R.string.codigo_onFailure_conversion));
                    //Toast.makeText(getActivity(), getResources().getString(R.string.codigo_onFailure_conversion), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    // Dades de registre d'usuari
    @NonNull
    private Registration userRegistrationData() {

        Registration dataUser = new Registration();
        dataUser.setNombre(String.valueOf(b.edtvName.getText()));
        dataUser.setEmail(String.valueOf(b.edtvMail.getText()));
        dataUser.setUsername(String.valueOf(b.edtvUserName.getText()));
        dataUser.setPassword(String.valueOf(b.edtvPassword.getText()));
        dataUser.setPerfil(checkRole(idRole()));
        dataUser.setApellidos(String.valueOf(b.edtvSurname.getText()));
        dataUser.setDireccion("Calle Felipe Nº2 4.1");
        dataUser.setCodigoPostal(230009);
        dataUser.setPoblacion("Barcelona");
        dataUser.setNacionalidad("Española");
        dataUser.setEdad(55);
        dataUser.setTelefono(56754678);
        dataUser.setIban("ES2114650100722030876293");

        return dataUser;
    }
}
