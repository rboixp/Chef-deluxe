package masjuan.ioc.chefdeluxe.utils;

import static android.content.Context.INPUT_METHOD_SERVICE;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import masjuan.ioc.chefdeluxe.R;

/**
 * Classe que conte varis mètodes que poden ser reutilitzats
 *
 * @author Eduard Masjuan
 */
public class Methods {

    /**
     * Mètode per comparar el Pattern (expressions regulars) amb el text introduït.
     *
     * @param txtInputLayout TextInputLayout, on es mostrarà el text d'error
     * @param message        String, mostrara un text si no es vàlid
     * @param pattern        String, expressió regular
     * @param match          String, text a comparar
     * @return Boolean, si els valors són validats
     */
    public boolean pattern(TextInputLayout txtInputLayout, String message, String pattern, String match) {
        Pattern pName = Pattern.compile(pattern);
        Matcher m = pName.matcher(match);
        if (!m.matches()) {
            txtInputLayout.setError(message);
        } else {
            txtInputLayout.setError(null);
        }
        return m.matches();
    }

    /**
     * Comprovació i validació de les dades introduïdes en els camps de text.
     *
     * @param txtInputLayout TextInputLayout, on es mostrarà el text d'error
     * @param text           String, mostrara un text si no es vàlid
     * @param pattern        String, expressió regular
     * @param inputEdtv      String, text que es vol validar
     * @return Boolean
     */
    public boolean patternValidation(TextInputLayout txtInputLayout, String text, String pattern, String inputEdtv) {
        return pattern(txtInputLayout, text, pattern, inputEdtv);
    }

    /**
     * Comprovació si els camps no estan buits
     *
     * @param name       String nom
     * @param surname    String Cognoms
     * @param age        String Edat
     * @param phone      String Numero de telèfon
     * @param address    String Domicili
     * @param village    String Població
     * @param postalcode String Codi postal
     * @param country    String País
     * @param iban       String Iban
     * @return Boolean, si els camps estan buits o no
     */
    public boolean dataEmpty(@NonNull String name, String surname, String age, String phone, String address, String village, String postalcode, String country, String iban) {
        return !name.isEmpty() && !surname.isEmpty() && !age.isEmpty() && !phone.isEmpty() && !address.isEmpty() && !village.isEmpty() && !postalcode.isEmpty() && !country.isEmpty() && !iban.isEmpty();
    }

    /**
     * Comprovació si els camps no estan buits
     *
     * @param password       String Contrasenya
     * @param newpassword    String Nova contrasenya
     * @param repeatpassword String Repetir nova contrasenya
     * @return Boolean, si els camps estan buits o no
     */
    public boolean dataEmpty(@NonNull String password, String newpassword, String repeatpassword) {
        return !password.isEmpty() && !newpassword.isEmpty() && !repeatpassword.isEmpty();
    }

    /**
     * Comprovació de una dada en blanc amb text d'Error en el TextInputLayout
     *
     * @param txtInputLayout TextInputLayout, on es mostrarà el text d'error
     * @param error          String, mostrara un text si no es vàlid
     * @param text           String, de on comprova si esta buit
     * @return Boolean, si els camps estan buits o no
     */
    public boolean dataInputLayoutEmpty(@NonNull TextInputLayout txtInputLayout, String error, @NonNull String text) {
        txtInputLayout.setError(error);
        return !text.isEmpty();
    }

    /**
     * Toolbar, retorna endarrere
     *
     * @param toolbar         Toolbar
     * @param frag            Fragment
     * @param fragmentManager FragmentManager
     */
    public static void navigationToolbar(@NonNull Toolbar toolbar, UtilsFragments frag, FragmentManager fragmentManager) {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24);
        toolbar.setNavigationOnClickListener(view -> frag.popBackStack(fragmentManager));
    }

    /**
     * Amaga el teclat
     *
     * @param textView Vista
     * @param context  Context
     */
    public void closeKeyboard(@NonNull View textView, @NonNull Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
    }

    /**
     * Mostra el teclat
     *
     * @param textView Vista
     * @param context  Context
     */
    public void showKeyboard(@NonNull View textView, @NonNull Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        imm.showSoftInput(textView, 0);
    }

}