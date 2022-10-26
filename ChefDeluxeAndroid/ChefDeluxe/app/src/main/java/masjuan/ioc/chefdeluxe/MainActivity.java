package masjuan.ioc.chefdeluxe;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import masjuan.ioc.chefdeluxe.databinding.ActivityMainBinding;
import masjuan.ioc.chefdeluxe.fragment.LoginFragment;
import masjuan.ioc.chefdeluxe.utils.FragmentUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Infla el disseny .xml
        // Crida la classe d'enllaç .xml
        ActivityMainBinding b = ActivityMainBinding.inflate(getLayoutInflater());
        // Vista de contingut del disseny
        setContentView(b.getRoot());

        FragmentUtils frag = new FragmentUtils(getSupportFragmentManager());


        // Comprova si l'estat de l'instancia de l'activitat ha sigut guardada
        if (savedInstanceState == null) {
            frag.addFragment(R.id.container, LoginFragment.newInstance(), "loginFrag");
        }

    }

}