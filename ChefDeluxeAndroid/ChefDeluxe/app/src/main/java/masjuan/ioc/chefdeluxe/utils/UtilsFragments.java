package masjuan.ioc.chefdeluxe.utils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * Classe d'utilitats per utilitzar Fragment
 */
public class UtilsFragments {
    private final FragmentManager mFragmentManager;

    /**
     * Constructor
     *
     * @param fragmentManager responsable de realitzar accions en els fragments
     */
    public UtilsFragments(@NonNull FragmentManager fragmentManager) {
        mFragmentManager = fragmentManager;
    }

    /**
     * Agrega un fragment en el contenidor.
     *
     * @param containerId Contenidor on s'agregara el fragment
     * @param fragment    Fragment
     * @param tag         Tag del fragment
     */
    public void addFragment(int containerId, Fragment fragment, String tag) {
        addFragment(mFragmentManager, containerId, fragment, tag);
    }

    /**
     * Remplaça el contingut del contenidor del fragment.
     *
     * @param containerId Contenidor on s'agregara el fragment
     * @param fragment    Fragment
     * @param tag         Tag del fragment
     */
    public void replaceFragment(int containerId, Fragment fragment, String tag) {
        replaceFragment(mFragmentManager, containerId, fragment, tag);
    }

    /**
     * // Remplaça el contingut del contenidor del fragment. Sense tag
     *
     * @param containerId Contenidor on s'agregara el fragment
     * @param fragment    Fragment
     */
    public void replaceFragment(int containerId, Fragment fragment) {
        replaceFragment(mFragmentManager, containerId, fragment);
    }


    /**
     * Elimina un fragment
     *
     * @param tag Tag del fragment
     */
    public void removeFragment(String tag) {
        removeFragment(mFragmentManager, tag);
    }

    /**
     * Al clicar endarrere, la transacció del fragment que es troba a la part superior es treu.
     *
     * @param manager Responsable de realitzar accions en els fragments
     */
    public void popBackStack(@NonNull FragmentManager manager) {
        if (!manager.isStateSaved()) {
            manager.popBackStack();
        }
    }

    /**
     * Realitzar la transacció per afegir un nou fragment
     *
     * @param manager     Responsable de realitzar accions en els fragments
     * @param containerId Contenidor on s'agregara el fragment
     * @param fragment    Fragment
     * @param tag         Tag del fragment
     */
    public static void addFragment(@NonNull FragmentManager manager, int containerId, Fragment fragment, String tag) {
        if (!manager.isStateSaved()) {
            manager.beginTransaction().add(containerId, fragment, tag).commit();
        }
    }

    /**
     * Realitzar la transacció per reemplaçar un nou fragment
     *
     * @param manager     Responsable de realitzar accions en els fragments
     * @param containerId Contenidor on s'agregara el fragment
     * @param fragment    Fragment
     * @param tag         Tag del fragment
     */
    public static void replaceFragment(@NonNull FragmentManager manager, int containerId, Fragment fragment, String tag) {
        manager.beginTransaction().replace(containerId, fragment, tag).addToBackStack(tag).commit();
    }

    /**
     * Realitzar la transacció per reemplaçar un nou fragment, sense tag
     *
     * @param manager     responsable de realitzar accions en els fragments
     * @param containerId Contenidor on s'agregara el fragment
     * @param fragment    Fragment
     */
    public static void replaceFragment(@NonNull FragmentManager manager, int containerId, Fragment fragment) {
        manager.beginTransaction().replace(containerId, fragment).commit();

    }

    /**
     * Realitzar la transacció per eliminar un nou fragment
     *
     * @param manager Responsable de realitzar accions en els fragments
     * @param tag     Tag del fragment
     */
    public static void removeFragment(FragmentManager manager, String tag) {
        Fragment fragment = manager.findFragmentByTag(tag);
        if (!manager.isStateSaved() && fragment != null) {
            manager.beginTransaction().remove(fragment).commit();
        }
    }

}