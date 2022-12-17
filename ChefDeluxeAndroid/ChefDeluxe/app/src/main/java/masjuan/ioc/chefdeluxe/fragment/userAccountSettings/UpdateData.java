package masjuan.ioc.chefdeluxe.fragment.userAccountSettings;

import static masjuan.ioc.chefdeluxe.utils.Methods.navigationToolbar;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.io.IOException;

import masjuan.ioc.chefdeluxe.R;
import masjuan.ioc.chefdeluxe.api.ApiGlobal;
import masjuan.ioc.chefdeluxe.databinding.FragmentUserAccountPerfilBinding;
import masjuan.ioc.chefdeluxe.model.Registration;
import masjuan.ioc.chefdeluxe.model.User;
import masjuan.ioc.chefdeluxe.utils.ApiCodes;
import masjuan.ioc.chefdeluxe.utils.Methods;
import masjuan.ioc.chefdeluxe.utils.SharedPreferences;
import masjuan.ioc.chefdeluxe.utils.UtilsFragments;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Classe que modifica dades del registre de l'usuari
 *
 * @author Eduard Masjuan
 */
public class UpdateData extends Fragment {

    private FragmentUserAccountPerfilBinding b;
    private SharedPreferences preferences;
    private FragmentManager fragmentManager = null;
    private UtilsFragments frag = null;
    private Methods method;
    private ApiCodes apiCodes;
    private ApiGlobal apiGlobal;

    private String name, surname, age, phone, address, village, postalcode, country, iban;

    /**
     * Constructor buit
     */
    public UpdateData() {
        // Required empty public constructor
    }

    /**
     * Crea una nova instancia de UpdateData
     *
     * @return UpdateData
     */
    public static UpdateData newInstance() {
        return new UpdateData();
    }

    /**
     * onCreate s'executa quan l'activitat es crea per primera vegada
     *
     * @param savedInstanceState Objecte Bundle que conte l'estat de l'activitat guardat
     * @author Eduard Masjuan
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        frag = new UtilsFragments(requireActivity().getSupportFragmentManager());
        fragmentManager = requireActivity().getSupportFragmentManager();
        apiGlobal = new ApiGlobal();
    }

    /**
     * Dissenya la interfície d'usuari
     *
     * @param inflater           Infla la vista
     * @param container          Vista que s'adjuntarà a la interfície d'usuari
     * @param savedInstanceState Bundle
     * @return Vista
     * @author Eduard Masjuan
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        b = FragmentUserAccountPerfilBinding.inflate(inflater, container, false);
        preferences = new SharedPreferences(requireActivity());
        method = new Methods();
        apiCodes = new ApiCodes();

        // Toolbar popBackStack (ens retorna al fragment)
        navigationToolbar(b.lyToolbar.toolbar, frag, fragmentManager);
        // Títol per la toolbar
        b.lyToolbar.toolbar.setTitle(getResources().getString(R.string.txt_title_acc_information));

        //TextInputLayout disabled
        enableEdtv(false);

        // Botons visibilitat
        b.bttEditData.setVisibility(View.VISIBLE);
        b.bttSaveDataUser.setVisibility(View.INVISIBLE);
        b.lyInputData.edtvUsername.setEnabled(false);
        b.lyInputData.edtvEmail.setEnabled(false);
        b.lyInputData.edtvPassword.setVisibility(View.GONE);

        //Recuperem dades de l' usuari
        getUserApi(preferences.getUsername());

        // Boto edició dades
        b.bttEditData.setOnClickListener(view -> {
            enableEdtv(true);
            b.bttEditData.setVisibility(View.INVISIBLE);
            b.bttSaveDataUser.setVisibility(View.VISIBLE);
        });

        // TextWatcher- Escolta els canvis que es produeixen en els camps següents
        b.lyInputData.inputEdtvName.addTextChangedListener(textWatcher);
        b.lyInputData.inputEdtvSurname.addTextChangedListener(textWatcher);
        b.lyInputData.inputEdtvAge.addTextChangedListener(textWatcher);
        b.lyInputData.inputEdtvPhone.addTextChangedListener(textWatcher);
        b.lyInputData.inputEdtvAddress.addTextChangedListener(textWatcher);
        b.lyInputData.inputEdtvVillage.addTextChangedListener(textWatcher);
        b.lyInputData.inputEdtvPostalcode.addTextChangedListener(textWatcher);
        b.lyInputData.inputEdtvCountry.addTextChangedListener(textWatcher);
        b.lyInputData.inputEdtvIban.addTextChangedListener(textWatcher);

        // Boto guarda les dades modificades
        b.bttSaveDataUser.setOnClickListener(view -> {
            if (dataValidation()) {
                if (writeValidation(name, surname, age, phone, address, village, postalcode, country, iban)) {
                    Registration user = userRegistrationData();
                    putUserApi(preferences.getUsername(), user);
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.tv_update_error), Toast.LENGTH_SHORT).show();
                }
            }
        });


        return b.getRoot();
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
            boolean validat = method.dataEmpty(name, surname, age, phone, address, village, postalcode, country, iban);
            b.bttSaveDataUser.setEnabled(validat);

            // Si tots els camps estan omplerts
            if (validat) {
                b.bttSaveDataUser.setStrokeWidth(1);

            } else { // Si tots els camps estan en blanc o alguns d'ells
                b.bttSaveDataUser.setStrokeWidth(0);
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

    /**
     * Comprovació i validació de les dades introduïdes en els camps de text
     *
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
    private boolean writeValidation(String name, String surname, String age, String
            phone, String address, String village, String postalcode, String country, String iban) {

        Methods method = new Methods();
        Boolean regName = method.patternValidation(b.lyInputData.edtvName, getString(R.string.txtLay_invalid_name), "^[a-zA-Z]+$", name);
        Boolean regSurname = method.patternValidation(b.lyInputData.edtvSurname, getString(R.string.txtLay_invalid_surname), "^[a-zA-Z\\s]+$", surname);
        Boolean regAge = method.patternValidation(b.lyInputData.edtvAge, getString(R.string.txtLay_invalid_name), "^[0-9]+$", String.valueOf(age));
        Boolean regPhone = method.patternValidation(b.lyInputData.edtvPhone, getString(R.string.txtLay_invalid_name), String.valueOf(Patterns.PHONE), String.valueOf(phone));
        Boolean regAddress = method.patternValidation(b.lyInputData.edtvAddress, getString(R.string.txtLay_invalid_name), "^[a-zA-Z0-9\\ñ\\.\\,\\-\\°\\ª\\/\\s]+$", address);
        Boolean regVillage = method.patternValidation(b.lyInputData.edtvVillage, getString(R.string.txtLay_invalid_name), "^[a-zA-Z\\ñ\\s]+$", village);
        Boolean regPostalcole = method.patternValidation(b.lyInputData.edtvPostalcode, getString(R.string.txtLay_invalid_name), "^[0-9]+$", postalcode);
        Boolean regCountry = method.patternValidation(b.lyInputData.edtvCountry, getString(R.string.txtLay_invalid_name), "^[a-zA-Z\\ñ\\s]+$", country);
        Boolean regIban = method.patternValidation(b.lyInputData.edtvIban, getString(R.string.txtLay_invalid_name), "^[A-Z]{2,3}[0-9]{1,30}+$", iban);

        return regName && regSurname && regAge && regPhone && regAddress && regVillage && regPostalcole && regCountry && regIban;
    }

    /**
     * TextInputLayout seleccionar si estan habilitats o no
     *
     * @param enabled Boolean
     */
    private void enableEdtv(boolean enabled) {
        b.lyInputData.edtvName.setEnabled(enabled);
        b.lyInputData.edtvSurname.setEnabled(enabled);
        b.lyInputData.edtvAge.setEnabled(enabled);
        b.lyInputData.edtvPhone.setEnabled(enabled);
        b.lyInputData.edtvAddress.setEnabled(enabled);
        b.lyInputData.edtvVillage.setEnabled(enabled);
        b.lyInputData.edtvPostalcode.setEnabled(enabled);
        b.lyInputData.edtvCountry.setEnabled(enabled);
        b.lyInputData.edtvIban.setEnabled(enabled);
    }

    /**
     * Mètode on realitza una petició GET al servidor. Rebem les dades de l'usuari
     *
     * @param username String, nom d'usuari del cuiner
     * @author Eduard Masjuan
     */
    private void getUserApi(String username) {
        //Call<User> user = apiGlobal.apiClientCert(getActivity(), preferences.getToken()).getRol(username);
        Call<User> user = apiGlobal.apiClient(preferences.getToken()).getRol(username);

        user.enqueue(new Callback<User>() {
            /**
             * Es crida si ja una resposta HTTP correcte
             * @param call Sol.licita al API les dades
             * @param response  Obtenir les dades
             */
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        // Mostra les dades de l'usuari en TextInputEditText
                        b.lyInputData.inputEdtvUsername.setText(response.body().getUsername());
                        b.lyInputData.inputEdtvEmail.setText(response.body().getEmail());
                        b.lyInputData.inputEdtvName.setText(response.body().getNombre());
                        b.lyInputData.inputEdtvSurname.setText(response.body().getApellidos());

                        if (response.body().getEdad() == 0) {
                            b.lyInputData.inputEdtvAge.setText("");
                        } else {
                            b.lyInputData.inputEdtvAge.setText(String.valueOf(response.body().getEdad()));
                        }

                        if (response.body().getTelefono() == 0) {
                            b.lyInputData.inputEdtvAge.setText("");
                        } else {
                            b.lyInputData.inputEdtvPhone.setText(String.valueOf(response.body().getTelefono()));
                        }

                        b.lyInputData.inputEdtvAddress.setText(response.body().getDireccion());
                        b.lyInputData.inputEdtvVillage.setText(response.body().getPoblacion());
                        b.lyInputData.inputEdtvPostalcode.setText(response.body().getCodigoPostal());
                        b.lyInputData.inputEdtvCountry.setText(response.body().getNacionalidad());

                        if (response.body().getIban() == null) {
                            b.lyInputData.inputEdtvIban.setText("");
                        } else {
                            b.lyInputData.inputEdtvIban.setText(response.body().getIban());
                        }


                    } else {
                        Toast.makeText(getActivity(), getResources().getString(R.string.tv_update_error2), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getActivity(), "Codi:" + response.code() + getResources().getString(R.string.codigo_401), Toast.LENGTH_SHORT).show();
                    apiCodes.codeHttp(response.code());
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
     * Mètode que realitza una petició PUT al servidor. Actualiza les dades de l'usuari
     *
     * @param username     String nom d'usuari
     * @param registration Registration
     * @author Eduard Masjuan
     */
    private void putUserApi(String username, Registration registration) {
       // Call<User> user = apiGlobal.apiClientCert(getActivity(), preferences.getToken()).putUserData(username, registration);
        Call<User> user = apiGlobal.apiClient(preferences.getToken()).putUserData(username, registration);

        user.enqueue(new Callback<User>() {

            /**
             * Es crida si ja una resposta HTTP correcte
             * @param call Sol.licita al API les dades
             * @param response  Obtenir les dades
             */
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        // Comprova el codi rebut, si hi ha error mostrarà un missatge
                        if (apiCodes.codeHttp(response.code())) {
                            Toast.makeText(getActivity(), "Cambios realizados con éxito!", Toast.LENGTH_SHORT).show();
                            enableEdtv(false);
                            b.bttEditData.setVisibility(View.VISIBLE);
                            b.bttSaveDataUser.setVisibility(View.INVISIBLE);
                        }
                    }
                } else {
                    Toast.makeText(getActivity(), "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                    apiCodes.codeHttp(response.code());
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
     * Dades de l'objecte Registration
     *
     * @return Retorna l'objecte Registration amb les dades introduïdes per l'usuari
     * @author Eduard Masjuan
     */
    private Registration userRegistrationData() {
        Registration dataUser = new Registration();

        dataUser.setNombre(String.valueOf(b.lyInputData.inputEdtvName.getText()));
        dataUser.setApellidos(String.valueOf(b.lyInputData.inputEdtvSurname.getText()));
        dataUser.setDireccion(String.valueOf(b.lyInputData.inputEdtvAddress.getText()));
        dataUser.setCodigoPostal(String.valueOf(b.lyInputData.inputEdtvPostalcode.getText()));
        dataUser.setPoblacion(String.valueOf(b.lyInputData.inputEdtvVillage.getText()));
        dataUser.setNacionalidad(String.valueOf(b.lyInputData.inputEdtvCountry.getText()));
        dataUser.setEdad(Integer.parseInt(String.valueOf(b.lyInputData.inputEdtvAge.getText())));
        dataUser.setTelefono(Long.parseLong(String.valueOf(b.lyInputData.inputEdtvPhone.getText())));
        dataUser.setIban(String.valueOf(b.lyInputData.inputEdtvIban.getText()));

        // No es modifica
        dataUser.setPassword(preferences.getPassword());
        dataUser.setUsername(preferences.getUsername());
        dataUser.setEmail(preferences.getEmail());

        if (preferences.getRole() == 1) {
            dataUser.setPerfil("ROLE_ADMIN");
        } else if (preferences.getRole() == 2) {
            dataUser.setPerfil("ROLE_CHEF");
        } else if (preferences.getRole() == 3) {
            dataUser.setPerfil("ROLE_CLIENT");
        }

        return dataUser;
    }

}