package masjuan.ioc.chefdeluxe.utils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class FragmentUtils {
    private final FragmentManager mFragmentManager;

    //Constructor
    public FragmentUtils(@NonNull FragmentManager fragmentManager) {
        mFragmentManager = fragmentManager;
    }

    // Agrega un fragment en el contenidor.
    public void addFragment(int containerId, Fragment fragment, String tag) {
        addFragment(mFragmentManager, containerId, fragment, tag);
    }

    // Remplaça el contingut del contenidor del fragment.
    public void replaceFragment(int containerId, Fragment fragment, String tag) {
        replaceFragment(mFragmentManager, containerId, fragment, tag);
    }

    // Remplaça el contingut del contenidor del fragment. Sense tag per no tenir addToBackStack
    public void replaceFragment(int containerId, Fragment fragmentSin) {
        replaceFragment(mFragmentManager, containerId, fragmentSin);
    }

    // Elimina un fragment per l'etiqueta
    public void removeFragment(String tag) {
        removeFragment(mFragmentManager, tag);
    }

    //
    public void popBackStack(FragmentManager manager) {
        if (!manager.isStateSaved()) {
            manager.popBackStack();
        }
    }

    //
    public static void addFragment(FragmentManager manager, int containerId, Fragment fragment, String tag) {
        if (!manager.isStateSaved()) {
            manager.beginTransaction().add(containerId, fragment, tag).commit();
        }
    }

    public static void replaceFragment(FragmentManager manager, int containerId, Fragment fragment, String tag) {
        manager.beginTransaction().replace(containerId, fragment, tag).addToBackStack(tag).commit();
    }


    // Sense el addToBackStack
    public static void replaceFragment(FragmentManager manager, int containerId, Fragment fragment) {
        manager.beginTransaction().replace(containerId, fragment).commit();

    }

    public static void removeFragment(FragmentManager manager, String tag) {
        Fragment fragment = manager.findFragmentByTag(tag);
        if (!manager.isStateSaved() && fragment != null) {
            manager.beginTransaction().remove(fragment).commit();
        }
    }

    /*
    public static <T> T getFragmentByTag(FragmentManager manager, Class<T> fragmentClass, String tag) {
        Fragment fragment = manager.findFragmentByTag(tag);

        if (fragment != null) {
            if (fragment.getClass().isAssignableFrom(fragmentClass)) {
                return fragmentClass.cast(fragment);
            } else if (Objects.requireNonNull(fragment.getClass().getSuperclass()).isAssignableFrom(fragmentClass)) {
                return (T) fragment;
            }
        }

        return null;
    }


    public <T> T getFragmentByTag(Class<T> fragmentClass, String tag) {
        return getFragmentByTag(mFragmentManager, fragmentClass, tag);
    }
     */

}