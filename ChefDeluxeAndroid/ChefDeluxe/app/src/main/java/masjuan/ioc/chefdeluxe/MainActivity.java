package masjuan.ioc.chefdeluxe;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import masjuan.ioc.chefdeluxe.databinding.ActivityMainBinding;
import masjuan.ioc.chefdeluxe.fragment.UserAdmin;
import masjuan.ioc.chefdeluxe.fragment.UserClient;
import masjuan.ioc.chefdeluxe.fragment.UserCook;
import masjuan.ioc.chefdeluxe.fragment.UserLogin;
import masjuan.ioc.chefdeluxe.utils.GlobalVariables;
import masjuan.ioc.chefdeluxe.utils.SharedPreferences;
import masjuan.ioc.chefdeluxe.utils.UtilsFragments;

/**
 * Activitat principal on mostra el login o si ja ha iniciat sessió afegeix o reemplaça un fragment
 * o un altre, depenent de quin rol ha iniciat sessió.
 *
 * @author Eduard Masjuan
 */
public class MainActivity extends AppCompatActivity {
    private SharedPreferences preferences;
    private UtilsFragments frag;

    /**
     * S'executa quan l'activitat es crea per primera vegada
     *
     * @param savedInstanceState Objecte Bundle que conte l'estat de l'activitat guardat
     * @author Eduard Masjuan
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding b = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        preferences = new SharedPreferences(this);
        frag = new UtilsFragments(getSupportFragmentManager());

        // Comprova si l'estat de l'instancia de l'activitat no ha sigut guardada
        if (savedInstanceState == null) {
            showFrag();
        }

    }

    /**
     * Mostra el fragment del rol que hagi iniciat sessió.
     * Si no ha inciat sessió, mostrarà el fragment Login.
     *
     * @author Eduard Masjuan
     */
    private void showFrag() {
        if (!preferences.getToken().equals("")) { // Token omplert
            if (preferences.getRole() == GlobalVariables.idRolAdmin) {
                frag.replaceFragment(R.id.container, UserAdmin.newInstance());
            } else if (preferences.getRole() == GlobalVariables.idRolCook) {
                frag.replaceFragment(R.id.container, UserCook.newInstance());
            } else if (preferences.getRole() == GlobalVariables.idRolClient) {
                frag.replaceFragment(R.id.container, UserClient.newInstance());
            } else { // Token omplert
                preferences.cleanToken();
                frag.addFragment(R.id.container, UserLogin.newInstance(), "loginFrag");
            }

        } else { // Token buit
            frag.addFragment(R.id.container, UserLogin.newInstance(), "loginFrag");
        }
    }

}