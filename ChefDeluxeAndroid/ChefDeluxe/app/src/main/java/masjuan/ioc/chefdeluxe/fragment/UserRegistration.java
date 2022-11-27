package masjuan.ioc.chefdeluxe.fragment;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.Objects;

import masjuan.ioc.chefdeluxe.R;
import masjuan.ioc.chefdeluxe.databinding.FragmentRegistrationBinding;
import masjuan.ioc.chefdeluxe.utils.Methods;
import masjuan.ioc.chefdeluxe.utils.UtilsFragments;

/**
 * Classe on permet fer una part del registre d'usuari per el rol Client i/o Cuiner.
 *
 * @author Eduard Masjuan
 */
public class UserRegistration extends Fragment {

    private FragmentRegistrationBinding b;
    private UtilsFragments frag = null;
    private FragmentManager fragmentManager = null;

    /**
     * Constructor buit
     */
    public UserRegistration() {
        // Required empty public constructor
    }

    /**
     * Crea una nova instancia de UserRegistration
     *
     * @return UserRegistration
     */
    public static UserRegistration newInstance() {
        return new UserRegistration();
    }

    /**
     * onCreate s'executa quan l'activitat  es crea per primera vegada
     *
     * @param savedInstanceState Objecte Bundle que conte l'estat de l'activitat guardat
     * @author Eduard Masjuan
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        frag = new UtilsFragments(requireActivity().getSupportFragmentManager());
        fragmentManager = requireActivity().getSupportFragmentManager();
    }

    /**
     * Dissenya la interfície d'usuari per primera vegada
     *
     * @param inflater           Infla la vista
     * @param container          Vista que s'adjuntarà a la interfície d'usuari
     * @param savedInstanceState Bundle
     * @return Vista
     * @author Eduard Masjuan
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        b = FragmentRegistrationBinding.inflate(inflater, container, false);
        b.tvLogin.setOnClickListener(view -> frag.popBackStack(fragmentManager));

        // Boto de continuació amb el registre si les dades són valides
        b.bttRegisterNext.setOnClickListener(view -> {

            String name = String.valueOf(b.inputEdtvName.getText());
            String surname = String.valueOf(b.inputEdtvSurname.getText());
            String email = String.valueOf(Objects.requireNonNull(b.inputEdtvEmail.getText()));
            String username = String.valueOf(Objects.requireNonNull(b.inputEdtvNameUser.getText()));
            String password = String.valueOf(b.inputEdtvPassword.getText());

            // Validació de les dades introduïdes
            if (dataValidation(name, surname, email, username, password)) {
                dataSend(name, surname, email, username, password);
                frag.replaceFragment(R.id.container, UserRegistrationRol.newInstance(), "registrationRol");
            }
        });

        return b.getRoot();
    }

    /**
     * Comprovació i validació de les dades introduïdes dels camps de text
     *
     * @param name     Nom de l'usuari
     * @param surname  Cognom de l'usuari
     * @param email    Correo electronic de l'usuari
     * @param username Nom d'usuari
     * @param password Contrasenya
     * @return
     * @author Eduard Masjuan
     */
    private boolean dataValidation(String name, String surname, String email, String username, String password) {
        Methods method = new Methods();
        Boolean regName = method.patternValidation(b.edtvName, getString(R.string.txtLay_invalid_name), "^[a-zA-Z\\s]+$", name);
        Boolean regSurname = method.patternValidation(b.edtvSurname, getString(R.string.txtLay_invalid_surname), "^[a-zA-Z\\s]+$", surname);
        Boolean regEmail = method.patternValidation(b.edtvEmail, getString(R.string.txtLay_invalid_email), String.valueOf(Patterns.EMAIL_ADDRESS), email);
        Boolean regUser = method.patternValidation(b.edtvUser, getString(R.string.txtLay_invalid_user), "^[a-zA-Z0-9\\-\\_\\.]+$", username);
        Boolean regPassword = method.patternValidation(b.edtvPassword, getString(R.string.txtLay_invalid_password), "^[\\S]+$", password);
        return regName && regSurname && regEmail && regUser && regPassword;
    }

    /**
     * Enviem les dades introduïdes dels camps de text al fragment RegistrationFragmentRol
     * mitjançant un Bundle
     *
     * @param name     Nom de l'usuari
     * @param surname  Cognom de l'usuari
     * @param email    Correo electronic de l'usuari
     * @param username Nom d'usuari
     * @param password Contrasenya
     * @author Eduard Masjuan
     */
    private void dataSend(String name, String surname, String email, String username, String password) {
        Bundle dataSend = new Bundle();
        dataSend.putString("keyName", name);
        dataSend.putString("keySurname", surname);
        dataSend.putString("keyEmail", email);
        dataSend.putString("keyUserName", username);
        dataSend.putString("keyPassword", password);
        getParentFragmentManager().setFragmentResult("key", dataSend);
    }

}