package masjuan.ioc.chefdeluxe.fragment.cook;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import masjuan.ioc.chefdeluxe.MainActivity;
import masjuan.ioc.chefdeluxe.R;
import masjuan.ioc.chefdeluxe.api.ApiGlobal;
import masjuan.ioc.chefdeluxe.databinding.FragmentCookNavHomeBinding;
import masjuan.ioc.chefdeluxe.model.Reservation;
import masjuan.ioc.chefdeluxe.utils.ApiCodes;
import masjuan.ioc.chefdeluxe.utils.SharedPreferences;
import masjuan.ioc.chefdeluxe.utils.recyclerView.RvAdapterListReservationCook;
import masjuan.ioc.chefdeluxe.utils.recyclerView.RvItemClickListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Classe on els cuiners se li mostraran un llistat de reserves amb l'estat Confirmat o pendent
 *
 * @author Eduard Masjuan
 */
public class NavListReservationCook extends Fragment {
    private FragmentCookNavHomeBinding b;
    private SharedPreferences preferences;
    private ApiCodes apiCodes;
    private ApiGlobal apiGlobal;

    // Reservation
    private List<Reservation> mShowChefReservationList;
    private RecyclerView recyclerViewChef = null;
    private RvAdapterListReservationCook rvReservationAdapter;

    /**
     * Constructor
     */
    public NavListReservationCook() {
        // Required empty public constructor
    }

    /**
     * Crea una nova instancia de NavValidateReservationCook
     *
     * @return NavValidateReservationCook
     */
    public static NavListReservationCook newInstance() {
        return new NavListReservationCook();
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
        b = FragmentCookNavHomeBinding.inflate(inflater, container, false);
        preferences = new SharedPreferences(requireActivity());
        apiCodes = new ApiCodes();

        // Títol per la toolbar
        b.lyToolbar.toolbar.setTitle(getResources().getString(R.string.tv_acc_home));
        ((MainActivity) requireActivity()).setSupportActionBar(b.lyToolbar.toolbar);
        setHasOptionsMenu(true);


        // Llista reserves
        mShowChefReservationList = new ArrayList<>();
        // Creem i obtenim un vista per RecyclerView
        recyclerViewChef = b.lyIncludeRecyclerView.rvList;
        recyclerViewChef.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewChef.setHasFixedSize(false);

        // Refresca la llista
        refreshList();

        // Listener Touch, al fer clic sobre una reserva s'obre un diàleg
        recyclerViewChef.addOnItemTouchListener(new RvItemClickListener(getActivity(), recyclerViewChef, new RvItemClickListener.OnItemClickListener() {

            /**
             * Quan es fa clic a un item es guarden el nom d'usuari del item seleccionat
             * @param view Vista
             * @param position int, posició de l'element
             */
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onItemClick(View view, int position) {

                Reservation cookReservation = mShowChefReservationList.get(position);
                String nameClient = cookReservation.getCliente();
                long id = cookReservation.getId();
                String state = cookReservation.getEstado();
                String startDate = String.valueOf(cookReservation.getIncio());
                String finalDate = String.valueOf(cookReservation.getFin());
                String person = String.valueOf(cookReservation.getComensales());
                String price = String.valueOf(cookReservation.getPrecio());

                if (state.equalsIgnoreCase("pendiente")) {
                    // Obrim diàleg passant el id i el nom del client
                    BottomSheetDialogFragment reservaDialog = DialogReservationCook.newInstance(id, nameClient);
                    reservaDialog.show(requireActivity().getSupportFragmentManager(), "BottomSheet");

                } else if (state.equalsIgnoreCase("pagado")) {

                    BottomSheetDialogFragment confirmDialog = DialogConfirmPayCook.newInstance(id, nameClient, startDate, finalDate, person, price);
                    confirmDialog.show(requireActivity().getSupportFragmentManager(), "BottomSheetConfirmPay");
                }

            }

            /**
             * Quan es manté el clic a un item
             * @param view Vista
             * @param position int, posició de l'element
             */
            @Override
            public void onLongItemClick(View view, int position) {

            }

        }));

        return b.getRoot();
    }

    /**
     * Especifica les opcions del menu
     *
     * @param menu     menu
     * @param inflater Infla la vista menu
     */
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_update_reservation, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * Selecció d'items del menu
     *
     * @param item Item del menu
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_update) {
            refreshList();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Mètode on realitza una petició GET al servidor.
     * Rep una llista dels clients que volen contractar el servei d'un cuiner
     *
     * @param userName  String, nom d'usuari
     * @param pageIndex int, inici paginació
     * @param pageSize  int, final paginació
     * @author Eduard Masjuan
     */
    public void getReservationPageListCook(String userName, int pageIndex, int pageSize) {
        //Call<List<Reservation>> reservation = apiGlobal.apiClientCert(getActivity(), preferences.getToken()).getPagChefReservation(userName, pageIndex, pageSize);
        Call<List<Reservation>> reservation = apiGlobal.apiClient(preferences.getToken()).getPagChefReservation(userName, pageIndex, pageSize);

        reservation.enqueue(new Callback<List<Reservation>>() {
            /**
             * Es crida si ja una resposta HTTP correcte
             * @param call Sol.licita al API les dades
             * @param response  Obtenir les dades
             */
            @Override
            public void onResponse(@NonNull Call<List<Reservation>> call, @NonNull Response<List<Reservation>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (apiCodes.codeHttp(response.code())) {

                            mShowChefReservationList = response.body();
                            // Creem un Adaptador i obtenim la llista de les reserves
                            rvReservationAdapter = new RvAdapterListReservationCook(getActivity(), mShowChefReservationList);
                            recyclerViewChef.setAdapter(rvReservationAdapter);

                            // Ordena l'Array per ordre alfabètic
                            Collections.sort(mShowChefReservationList, new Comparator<Reservation>() {
                                @Override
                                public int compare(Reservation reservation, Reservation t1) {
                                    return reservation.getEstado().compareTo(t1.getEstado());
                                }
                            });


                        }
                    }
                } else {
                    //Toast.makeText(getActivity(), "No tienes peticiones de reserva", Toast.LENGTH_SHORT).show();
                    apiCodes.codeHttp(response.code());
                }
            }

            /**
             * Es produeix una excepció de xarxa en la comunicació amb el servidor o una excepció en la gestió de la sol·licitud
             * @param call Sol·licita al API les dades
             * @param t Captura l’excepció
             */
            @Override
            public void onFailure(@NonNull Call<List<Reservation>> call, @NonNull Throwable t) {
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
     * Mètode on netegem la array y mostrem de nou el llistat de reserves
     */
    public void refreshList() {
        // Refresca la llista
        mShowChefReservationList.clear();
        getReservationPageListCook(preferences.getUsername(), 0, 1000);
    }

}