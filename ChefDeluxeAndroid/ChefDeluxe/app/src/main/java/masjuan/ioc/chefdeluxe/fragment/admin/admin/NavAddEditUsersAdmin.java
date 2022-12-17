package masjuan.ioc.chefdeluxe.fragment.admin.admin;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import java.io.IOException;
import java.util.Set;

import masjuan.ioc.chefdeluxe.R;
import masjuan.ioc.chefdeluxe.api.ApiGlobal;
import masjuan.ioc.chefdeluxe.databinding.FragmentAdminAddEditUserBinding;
import masjuan.ioc.chefdeluxe.model.Registration;
import masjuan.ioc.chefdeluxe.model.Role;
import masjuan.ioc.chefdeluxe.model.User;
import masjuan.ioc.chefdeluxe.utils.ApiCodes;
import masjuan.ioc.chefdeluxe.utils.GlobalVariables;
import masjuan.ioc.chefdeluxe.utils.Methods;
import masjuan.ioc.chefdeluxe.utils.SharedPreferences;
import masjuan.ioc.chefdeluxe.utils.UtilsFragments;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Classe per afegir o editar usuaris només per admins
 *
 * @author Eduard Masjuan
 */
public class NavAddEditUsersAdmin extends Fragment {

    private UtilsFragments frag = null;
    private SharedPreferences preferences;
    private FragmentAdminAddEditUserBinding b;
    private ApiCodes apiCode;
    private String userName, password, name, surname, age, phone, address, village,
            postalcode, country, iban;
    private String rol_user = "";
    private Methods method;
    private ApiGlobal apiGlobal;

    /**
     * Constructor buit
     */
    public NavAddEditUsersAdmin() {
        // Required empty public constructor
    }

    /**
     * Crea una nova instancia de NavAddUsersAdmin
     *
     * @return NavAddUsersAdmin
     */
    public static NavAddEditUsersAdmin newInstance() {
        return new NavAddEditUsersAdmin();
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
        method = new Methods();
        apiCode = new ApiCodes();
        apiGlobal = new ApiGlobal();
    }

    /**
     * Dissenya la interfície d'usuari per primera vegada.
     * Crea un nou usuari
     * Edita les dades de l'usuari seleccionat
     *
     * @param inflater           Infla la vista
     * @param container          Vista que s'adjuntarà a la interfície d'usuari
     * @param savedInstanceState Bundle
     * @return Vista
     * @author Eduard Masjuan
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        b = FragmentAdminAddEditUserBinding.inflate(inflater, container, false);
        preferences = new SharedPreferences(requireActivity());
        roleSelection();

        // Spinner població
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.rol_array, R.layout.spinner_item);
        b.spinnerRol.setAdapter(adapter);

        // TextWatcher- Escolta els canvis que es produeixen en els camps següents
        b.lyInputData.inputEdtvEdtvPassword.addTextChangedListener(textWatcher);
        b.lyInputData.inputEdtvName.addTextChangedListener(textWatcher);
        b.lyInputData.inputEdtvSurname.addTextChangedListener(textWatcher);
        b.lyInputData.inputEdtvAge.addTextChangedListener(textWatcher);
        b.lyInputData.inputEdtvPhone.addTextChangedListener(textWatcher);
        b.lyInputData.inputEdtvAddress.addTextChangedListener(textWatcher);
        b.lyInputData.inputEdtvVillage.addTextChangedListener(textWatcher);
        b.lyInputData.inputEdtvPostalcode.addTextChangedListener(textWatcher);
        b.lyInputData.inputEdtvCountry.addTextChangedListener(textWatcher);
        b.lyInputData.inputEdtvIban.addTextChangedListener(textWatcher);


        // Cridem a DialogOptionsUserAdmin per rebre el valor boolean (editem o no) i el nom d'usuari que s'ha escollit
        getParentFragmentManager().setFragmentResultListener("key", this, new FragmentResultListener() {
            /**
             * Rebem les dades passades per el RegistrationFragment
             * @param requestKey Mateixa clau que s'ha donat al Bundle
             * @param result Bundle
             * @author Eduard Masjuan
             */
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                // Una vegada rebut les dades s'eliminen automàticament
                boolean saveEdit = result.getBoolean("keyEdit");
                String user = result.getString("keyUserName");
                userName = user;

                // S'edita l'usuari passat per parametre
                if (saveEdit) {
                    b.bttAction.setEnabled(false);
                    b.bttAction.setText("Guardar");
                    getUser(user);

                    b.edtvRol.setEnabled(false);
                    b.lyInputData.edtvUsername.setEnabled(false);
                    b.lyInputData.edtvEmail.setEnabled(false);
                }
            }
        });

        //Boto per crea un nou usuari o editar un usuari ja creat
        b.bttAction.setOnClickListener(view -> {

            // Validació de les dades introduïdes
            if (dataValidation()) {
                if (writeValidation(password, name, surname, age, phone, address, village, postalcode, country, iban)) {
                    if (b.bttAction.getText().equals("Crear cuenta")) {
                        // Crear nou usuari
                        postRegister(userRegistrationData());
                    } else {
                        // Actualitzar un usuari
                        putUserApi(userName, updateRegistrationData());
                    }
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.tv_update_error), Toast.LENGTH_SHORT).show();
                }
            }
        });

        return b.getRoot();
    }

    /**
     * Registre, crea un nou usuari fet per l'admin
     *
     * @param user Objecte de la classe Registration
     * @author Eduard Masjuan
     */
    private void postRegister(Registration user) {
       //Call<String> callRegister = apiGlobal.apiClientCert(getActivity(), preferences.getToken()).addUser(user);
        Call<String> callRegister = apiGlobal.apiClient(preferences.getToken()).addUser(user);

        callRegister.enqueue(new Callback<String>() {
            /**
             * Es crida si ja una resposta HTTP correcte
             * @param call Sol.licita al API les dades
             * @param response  Obtenir les dades
             */
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        // Comprova el codi rebut, si hi ha error mostrarà un missatge
                        if (apiCode.codeHttp(response.code())) {
                            Toast.makeText(getActivity(), "Nuevo usuari creado!", Toast.LENGTH_SHORT).show();

                        }
                    }
                } else {
                    Toast.makeText(getActivity(), "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                    apiCode.codeHttp(response.code());
                }
            }

            /**
             * Es produeix una excepció de xarxa en la comunicació amb el servidor o una excepció en la gestió de la sol·licitud
             * @param call Sol·licita al API les dades
             * @param t Captura l’excepció
             */
            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                // Comprova si l'objecte t es una classe
                if (t instanceof IOException) {
                    Log.v("Código", t.getMessage() + "  " + getResources().getString(R.string.codigo_onFailure_connexion));
                    Toast.makeText(getActivity(), getResources().getString(R.string.codigo_onFailure_connexion), Toast.LENGTH_SHORT).show();

                } else {
                    Log.v("Código", t.getMessage() + "  " + getResources().getString(R.string.codigo_onFailure_conversion));
                    Toast.makeText(getActivity(), getResources().getString(R.string.codigo_onFailure_conversion), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Crea un objecte de la classe Registration
     *
     * @return Retorna l'objecte Registration amb les dades introduïdes per l'admin
     * @author Eduard Masjuan
     */
    private Registration userRegistrationData() {
        Registration dataUser = new Registration();

        dataUser.setPerfil(roleSelection());
        dataUser.setUsername(String.valueOf(b.lyInputData.inputEdtvUsername.getText()));
        dataUser.setEmail(String.valueOf(b.lyInputData.inputEdtvEmail.getText()));
        dataUser.setPassword(String.valueOf(b.lyInputData.inputEdtvEdtvPassword.getText()));
        dataUser.setNombre(String.valueOf(b.lyInputData.inputEdtvName.getText()));
        dataUser.setApellidos(String.valueOf(b.lyInputData.inputEdtvSurname.getText()));
        dataUser.setEdad(Integer.parseInt(String.valueOf(b.lyInputData.inputEdtvAge.getText())));
        dataUser.setTelefono(Long.parseLong(String.valueOf(b.lyInputData.inputEdtvPhone.getText())));
        dataUser.setDireccion(String.valueOf(b.lyInputData.inputEdtvAddress.getText()));
        dataUser.setPoblacion(String.valueOf(b.lyInputData.inputEdtvVillage.getText()));
        dataUser.setCodigoPostal(String.valueOf(b.lyInputData.inputEdtvPostalcode.getText()));
        dataUser.setNacionalidad(String.valueOf(b.lyInputData.inputEdtvCountry.getText()));
        dataUser.setIban(String.valueOf(b.lyInputData.inputEdtvIban.getText()));

        return dataUser;
    }

    /**
     * Selecciona el rol d'usuari amb Spinner
     *
     * @return Retorna el rol
     * @author Eduard Masjuan
     */
    private String roleSelection() {

        b.spinnerRol.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i) {
                    case 0:
                        rol_user = GlobalVariables.nameRolAdmin;
                        break;
                    case 1:
                        rol_user = GlobalVariables.nameRolCook;
                        break;
                    case 2:
                        rol_user = GlobalVariables.nameRolClient;
                        break;
                }
            }
        });
        return rol_user;
    }

    /**
     * Crea un objecte de la classe Registration
     *
     * @return Retorna l'objecte Registration amb les dades introduïdes per l'admin
     * @author Eduard Masjuan
     */
    private Registration updateRegistrationData() {
        Registration dataUser = new Registration();

        String rolSpinner = String.valueOf(b.spinnerRol.getText());

        switch (rolSpinner) {
            case "Administrador":
                dataUser.setPerfil("ROLE_ADMIN");
                break;
            case "Cocinero":
                dataUser.setPerfil("ROLE_CHEF");
                break;
            case "Cliente":
                dataUser.setPerfil("ROLE_CLIENT");
                break;
        }

        dataUser.setUsername(String.valueOf(b.lyInputData.inputEdtvUsername.getText()));
        dataUser.setEmail(String.valueOf(b.lyInputData.inputEdtvEmail.getText()));
        dataUser.setPassword(String.valueOf(b.lyInputData.inputEdtvEdtvPassword.getText()));
        dataUser.setNombre(String.valueOf(b.lyInputData.inputEdtvName.getText()));
        dataUser.setApellidos(String.valueOf(b.lyInputData.inputEdtvSurname.getText()));
        dataUser.setEdad(Integer.parseInt(String.valueOf(b.lyInputData.inputEdtvAge.getText())));
        dataUser.setTelefono(Long.parseLong(String.valueOf(b.lyInputData.inputEdtvPhone.getText())));
        dataUser.setDireccion(String.valueOf(b.lyInputData.inputEdtvAddress.getText()));
        dataUser.setPoblacion(String.valueOf(b.lyInputData.inputEdtvVillage.getText()));
        dataUser.setCodigoPostal(String.valueOf(b.lyInputData.inputEdtvPostalcode.getText()));
        dataUser.setNacionalidad(String.valueOf(b.lyInputData.inputEdtvCountry.getText()));
        dataUser.setIban(String.valueOf(b.lyInputData.inputEdtvIban.getText()));

        return dataUser;
    }

    /**
     * Mètode que realitza una petició PUT al servidor. Actualiza les dades de l'usuari
     *
     * @param username     String nom d'usuari
     * @param registration Registration
     * @author Eduard Masjuan
     */
    private void putUserApi(String username, Registration registration) {
        //Call<User> user = apiGlobal.apiClientCert(getActivity(), preferences.getToken()).putUserData(username, registration);
        Call<User> user = apiGlobal.apiClient(preferences.getToken()).putUserData(username, registration);

        user.enqueue(new Callback<User>() {

            /**
             * Es crida si ja una resposta HTTPS correcte
             * @param call Sol.licita al API les dades
             * @param response  Obtenir les dades
             */
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        // Comprova el codi rebut, si hi ha error mostrarà un missatge
                        if (apiCode.codeHttp(response.code())) {
                            Toast.makeText(getActivity(), "Cambios realizados con éxito!", Toast.LENGTH_SHORT).show();
                            frag.replaceFragment(R.id.container_users_admin, NavDataUsersAdmin.newInstance());
                        }
                    }
                } else {
                    Toast.makeText(getActivity(), "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                    apiCode.codeHttp(response.code());
                }
            }

            /**
             * Es produeix una excepció de xarxa en la comunicació amb el servidor o una excepció en la gestió de la sol·licitud
             * @param call Sol·licita al API les dades
             * @param t Captura l’excepció
             */
            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                if (t instanceof IOException) {
                    Log.v("Código", t.getMessage() + "  " + getResources().getString(R.string.codigo_onFailure_connexion));
                    Toast.makeText(getActivity(), getResources().getString(R.string.codigo_onFailure_connexion), Toast.LENGTH_SHORT).show();
                } else {
                    Log.v("Código", t.getMessage() + "  " + getResources().getString(R.string.codigo_onFailure_conversion));
                    Toast.makeText(getActivity(), getResources().getString(R.string.codigo_onFailure_conversion), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Mètode on realitza una petició GET al servidor. Rebem les dades de l'usuari passat per parÀmetre
     *
     * @author Eduard Masjuan
     */
    private void getUser(String username) {
        //Call<User> user = apiGlobal.apiClientCert(getActivity(), preferences.getToken()).getRol(username);
        Call<User> user = apiGlobal.apiClient(preferences.getToken()).getRol(username);

        user.enqueue(new Callback<User>() {

            /**
             * Es crida si ja una resposta HTTPS correcte
             * @param call Sol.licita al API les dades
             * @param response  Obtenir les dades
             */
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        User user = response.body();

                        // Recuperem el rol
                        Role rol = new Role();
                        Set<Role> stRole = response.body().getRoles();
                        stRole.add(rol);

                        for (Role role : stRole) {
                            chooseRole((int) role.getId());
                        }

                        b.lyInputData.inputEdtvUsername.setText(user.getUsername());
                        b.lyInputData.inputEdtvEmail.setText(user.getEmail());
                        b.lyInputData.inputEdtvName.setText(user.getNombre());
                        b.lyInputData.inputEdtvSurname.setText(user.getApellidos());
                        b.lyInputData.inputEdtvAge.setText(String.valueOf(user.getEdad()));
                        b.lyInputData.inputEdtvPhone.setText(String.valueOf(user.getTelefono()));
                        b.lyInputData.inputEdtvAddress.setText(user.getDireccion());
                        b.lyInputData.inputEdtvVillage.setText(user.getPoblacion());
                        b.lyInputData.inputEdtvPostalcode.setText(String.valueOf(user.getCodigoPostal()));
                        b.lyInputData.inputEdtvCountry.setText(user.getNacionalidad());
                        b.lyInputData.inputEdtvIban.setText(String.valueOf(user.getIban()));

                    } else {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Codi:" + response.code() + getResources().getString(R.string.codigo_401), Toast.LENGTH_SHORT).show();
                    apiCode.codeHttp(response.code());
                }
            }

            /**
             * Es produeix una excepció de xarxa en la comunicació amb el servidor o una excepció en la gestió de la sol·licitud
             * @param call Sol·licita al API les dades
             * @param t Captura l’excepció
             */
            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                if (t instanceof IOException) {
                    Log.v("Código", getResources().getString(R.string.codigo_onFailure_connexion));
                    Toast.makeText(getActivity(), getResources().getString(R.string.codigo_onFailure_connexion), Toast.LENGTH_SHORT).show();
                } else {
                    Log.v("Código", getResources().getString(R.string.codigo_onFailure_conversion));
                    Toast.makeText(getActivity(), getResources().getString(R.string.codigo_onFailure_connexion), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Obtenim el id del rol de l'usuari
     *
     * @param idRol Valor int dels rols
     * @author Eduard Masjuan
     */
    private void chooseRole(int idRol) {

        if (idRol == 1) { // Admin
            b.spinnerRol.setText("Administrador");
        } else if (idRol == 2) { // Chef
            b.spinnerRol.setText("Cocinero");
        } else if (idRol == 3) { // Client
            b.spinnerRol.setText("Cliente");
        }
    }

    /**
     * Comprovació i validació de les dades introduïdes en els camps de text
     *
     * @param password   String contrasenya
     * @param name       String nom de l'usuari
     * @param surname    String cognom de l'usuari
     * @param age        String edat de l'usuari
     * @param phone      String telefon de l'usuari
     * @param address    String adreça de l'usuari
     * @param village    String poblaciño
     * @param postalcode String codi postal
     * @param country    String país
     * @param iban       String iban
     * @return Boolean validació
     * @author Eduard Masjuan
     */
    private boolean writeValidation(String password, String name, String surname, String age, String
            phone, String address, String village, String postalcode, String country, String iban) {

        Methods method = new Methods();

        Boolean regPassword = method.patternValidation(b.lyInputData.edtvPassword, getString(R.string.txtLay_invalid_password), "^[\\S]+$", password);
        Boolean regName = method.patternValidation(b.lyInputData.edtvName, getString(R.string.txtLay_invalid_name), "^[a-zA-Z\\s]+$", name);
        Boolean regSurname = method.patternValidation(b.lyInputData.edtvSurname, getString(R.string.txtLay_invalid_surname), "^[a-zA-Z\\s]+$", surname);
        Boolean regAge = method.patternValidation(b.lyInputData.edtvAge, getString(R.string.txtLay_invalid_name), "^[0-9]+$", String.valueOf(age));
        Boolean regPhone = method.patternValidation(b.lyInputData.edtvPhone, getString(R.string.txtLay_invalid_name), String.valueOf(Patterns.PHONE), String.valueOf(phone));
        Boolean regAddress = method.patternValidation(b.lyInputData.edtvAddress, getString(R.string.txtLay_invalid_name), "^[a-zA-Z0-9\\ñ\\.\\,\\-\\°\\ª\\/\\s]+$", address);
        Boolean regVillage = method.patternValidation(b.lyInputData.edtvVillage, getString(R.string.txtLay_invalid_name), "^[a-zA-Z\\ñ\\s]+$", village);
        Boolean regPostalcole = method.patternValidation(b.lyInputData.edtvPostalcode, getString(R.string.txtLay_invalid_name), "^[0-9]+$", postalcode);
        Boolean regCountry = method.patternValidation(b.lyInputData.edtvCountry, getString(R.string.txtLay_invalid_name), "^[a-zA-Z\\ñ\\s]+$", country);
        Boolean regIban = method.patternValidation(b.lyInputData.edtvIban, getString(R.string.txtLay_invalid_name), "^[A-Z]{2,3}[0-9]{1,30}+$", iban);

        return regPassword && regName && regSurname && regAge && regPhone && regAddress && regVillage && regPostalcole && regCountry && regIban;
    }

    /**
     * Validació de dades
     *
     * @return boolea
     */
    private boolean dataValidation() {
        boolean ok = true;

        if (age.length() > 3) {
            method.dataInputLayoutEmpty(b.lyInputData.edtvAge, "Edad incorrecta", age);
            ok = false;
        } else {
            method.dataInputLayoutEmpty(b.lyInputData.edtvAge, null, age);
        }

        if (!(phone.length() == 9)) {
            method.dataInputLayoutEmpty(b.lyInputData.edtvPhone, "Número de teléfono incorrecto", phone);
            ok = false;
        } else {
            method.dataInputLayoutEmpty(b.lyInputData.edtvPhone, null, phone);
        }

        if (!(postalcode.length() == 5)) {
            method.dataInputLayoutEmpty(b.lyInputData.edtvPostalcode, "Código postal incorrecto", postalcode);
            ok = false;
        } else {
            method.dataInputLayoutEmpty(b.lyInputData.edtvPostalcode, null, postalcode);
        }

        if ((iban.length() > 30)) {
            method.dataInputLayoutEmpty(b.lyInputData.edtvIban, "Iban incorrecto", iban);
            ok = false;
        } else {
            method.dataInputLayoutEmpty(b.lyInputData.edtvIban, null, iban);
        }

        return ok;
    }

    /**
     * Interfície que observa els canvis de text
     *
     * @author Eduard Masjuan
     */
    private final TextWatcher textWatcher = new TextWatcher() {

        /**
         * Es crida quan es rep el nou caràcter inserit però encara no es mostra en pantalla
         */
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        /**
         * Es crida quan ha acabat de canviar el text
         * Si els camps estan omplerts s’habilitarà el boto per poder acceptar la modificació de les dades
         */
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            // Capturem la escriptura
            password = String.valueOf(b.lyInputData.inputEdtvEdtvPassword.getText());
            name = String.valueOf(b.lyInputData.inputEdtvName.getText());
            surname = String.valueOf(b.lyInputData.inputEdtvSurname.getText());
            age = String.valueOf(b.lyInputData.inputEdtvAge.getText());
            phone = String.valueOf(b.lyInputData.inputEdtvPhone.getText());
            address = String.valueOf(b.lyInputData.inputEdtvAddress.getText());
            village = String.valueOf(b.lyInputData.inputEdtvVillage.getText());
            postalcode = String.valueOf(b.lyInputData.inputEdtvPostalcode.getText());
            country = String.valueOf(b.lyInputData.inputEdtvCountry.getText());
            iban = String.valueOf(b.lyInputData.inputEdtvIban.getText());

            // Si els camps estan plens s'activa el boto de guardar.
            boolean validat = method.dataEmpty(password, name, surname, age, phone, address, village, postalcode, country, iban);
            b.bttAction.setEnabled(validat);

            // Si tots els camps estan omplerts
            if (validat) {
                b.bttAction.setStrokeWidth(1);

            } else { // Si tots els camps estan en blanc o alguns d'ells
                b.bttAction.setStrokeWidth(0);
                method.dataInputLayoutEmpty(b.lyInputData.edtvPassword, null, name);
                method.dataInputLayoutEmpty(b.lyInputData.edtvName, null, name);
                method.dataInputLayoutEmpty(b.lyInputData.edtvSurname, null, surname);
                method.dataInputLayoutEmpty(b.lyInputData.edtvAge, null, age);
                method.dataInputLayoutEmpty(b.lyInputData.edtvPhone, null, phone);
                method.dataInputLayoutEmpty(b.lyInputData.edtvAddress, null, address);
                method.dataInputLayoutEmpty(b.lyInputData.edtvVillage, null, village);
                method.dataInputLayoutEmpty(b.lyInputData.edtvPostalcode, null, postalcode);
                method.dataInputLayoutEmpty(b.lyInputData.edtvCountry, null, country);
                method.dataInputLayoutEmpty(b.lyInputData.edtvIban, null, iban);
            }
        }

        /**
         * Es crida després de que s'hagi canviat el text d'un camp
         */
        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

}