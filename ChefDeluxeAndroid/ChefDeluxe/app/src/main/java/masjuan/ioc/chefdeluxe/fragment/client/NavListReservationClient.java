package masjuan.ioc.chefdeluxe.fragment.client;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import masjuan.ioc.chefdeluxe.R;
import masjuan.ioc.chefdeluxe.api.ApiClientToken;
import masjuan.ioc.chefdeluxe.api.ApiService;
import masjuan.ioc.chefdeluxe.databinding.FragmentClientNavReservationBinding;
import masjuan.ioc.chefdeluxe.model.Reservation;
import masjuan.ioc.chefdeluxe.utils.ApiCodes;
import masjuan.ioc.chefdeluxe.utils.SharedPreferences;
import masjuan.ioc.chefdeluxe.utils.UtilsFragments;
import masjuan.ioc.chefdeluxe.utils.recyclerView.RvItemClickListener;
import masjuan.ioc.chefdeluxe.utils.recyclerView.RvAdapterListReservationClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Classe on mostrarà les reserves fetes per els clients
 */
public class NavListReservationClient extends Fragment {
    private FragmentClientNavReservationBinding b;
    private SharedPreferences preferences;
    private final UtilsFragments frag = null;
    private final FragmentManager fragmentManager = null;
    private ApiCodes apiCodes;

    // Reservation
    private List<Reservation> mShowClientReservationList;
    private RecyclerView recyclerViewClient = null;
    private RvAdapterListReservationClient rvReservationAdapter;

    boolean reserves = false;


    /**
     * Constructor buit
     */
    public NavListReservationClient() {
        // Required empty public constructor
    }

    /**
     * Crea una nova instancia de NavListReservationClient
     *
     * @return NavListReservationClient
     */
    public static NavListReservationClient newInstance() {
        return new NavListReservationClient();
    }

    /**
     * S'executa quan l'activitat  es crea per primera vegada
     *
     * @param savedInstanceState Objecte Bundle que conte l'estat de l'activitat guardat
     * @author Eduard Masjuan
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Dissenya la interfície d'usuari per primera vegada.
     *
     * @param inflater           Infla la vista
     * @param container          Vista que s'adjuntarà a la interfície d'usuari
     * @param savedInstanceState Bundle
     * @return Vista
     * @author Eduard Masjuan
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        b = FragmentClientNavReservationBinding.inflate(inflater, container, false);
        preferences = new SharedPreferences(requireActivity());
        apiCodes = new ApiCodes();

        // Títol per la toolbar
        b.lyToolbar.toolbar.setTitle(getResources().getString(R.string.tv_acc_reservation));

        // Llista reserves
        mShowClientReservationList = new ArrayList<>();
        // Creem i obtenim un vista per RecyclerView
        recyclerViewClient = b.lyIncludeRecyclerView.rvList;
        recyclerViewClient.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewClient.setHasFixedSize(false);

        // Comprova si hi han reserves fetes
        if (reserves) {
            b.tvInfoReserva.setText(getResources().getString(R.string.tv_info_reservation));
        } else {
            b.tvInfoReserva.setVisibility(View.GONE);
        }

        // Mostra les reserves
        getReservationPageListClient(preferences.getUsername(), 0, 1000);

        // Controla el onItemclick al fer clic a un element del Recyclerview
        recyclerViewClient.addOnItemTouchListener(new RvItemClickListener(getActivity(), recyclerViewClient, new RvItemClickListener.OnItemClickListener() {

            /**
             * Quan es fa clic a un item es guarden el nom d'usuari del item seleccionat
             * @param view Vista
             * @param position int, posició de l'element
             */
            @Override
            public void onItemClick(View view, int position) {
                Reservation clientReservation = mShowClientReservationList.get(position);
                long id = clientReservation.getId();
                Toast.makeText(getActivity(), "Número de reserva:" + id, Toast.LENGTH_SHORT).show();
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

        // Permet eliminar un element arrosegant-lo cap a la esquerre.
        ItemTouchHelper mIth = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.RIGHT, ItemTouchHelper.LEFT) {
            /**
             * Arrastra l'element d'una posició a un altre
             * @param recyclerView RecyclerView que esta connectat al ItemTouchHelper
             * @param viewHolder ViewHolder que s’està arrastrant
             * @param target ViewHolder sobre el que s’arrastra l'element
             * @return boolean, si retorna true, entenc que ViewHolder s'ha mogut a la posició de l’adaptador del ViewHolder destí
             */
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                final int from = viewHolder.getBindingAdapterPosition();
                final int to = target.getBindingAdapterPosition();
                Collections.swap(mShowClientReservationList, from, to);
                return false;
            }

            /**
             * Llisca un ViewHodler
             * Eliminem de la List i la BD un element
             * @param viewHolder ViewHolder que ha sigut desplaçat
             * @param direction int, direcció la qual es mou el ViewHolder
             */
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int pos = viewHolder.getBindingAdapterPosition();
                Reservation clientReservation = mShowClientReservationList.get(pos);
                long id = clientReservation.getId();
                dialogDeleteReservation(id).show(); // Mostrem el AlertDialog
                // Esborrar l'element
                mShowClientReservationList.remove(viewHolder.getBindingAdapterPosition());
                // Avisa que ha borrat un element i que actualitzi
                rvReservationAdapter.notifyItemRemoved(viewHolder.getBindingAdapterPosition());
            }
        });

        // Associa l'objecte creat al recyclerView
        mIth.attachToRecyclerView(recyclerViewClient);

        return b.getRoot();
    }

    /**
     * Diàleg per confirmar si es vol eliminar una reserva
     *
     * @param id long, id de la reserva
     * @return AlertDialog
     */
    public AlertDialog dialogDeleteReservation(long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(getResources().getString(R.string.tv_info_reservation_deleteTitle))
                .setCancelable(false)
                .setMessage(getResources().getString(R.string.tv_info_reservation_deleteInfo))
                .setPositiveButton(getResources().getString(R.string.tv_info_reservation_deleteYes),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteReservationClient(id);
                            }
                        })
                .setNegativeButton(getResources().getString(R.string.tv_info_reservation_deleteNo),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getReservationPageListClient(preferences.getUsername(), 0, 100);
                            }
                        });

        return builder.create();
    }

    /**
     * Mètode on realitza una petició GET al servidor.
     * Rebem una llista amb les reserves del client
     *
     * @param userName  String, nom d'usuari
     * @param pageIndex int, inici paginació
     * @param pageSize  int, final paginació
     * @author Eduard Masjuan
     */
    public void getReservationPageListClient(String userName, int pageIndex, int pageSize) {
        ApiService apiService = ApiClientToken.getInstance(preferences.getToken());
        Call<List<Reservation>> reservation = apiService.getPagClientReservation(userName, pageIndex, pageSize);
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

                            reserves = true;
                            mShowClientReservationList = response.body();
                            // Creem un Adaptador i obtenim la llista de les reserves
                            rvReservationAdapter = new RvAdapterListReservationClient(getActivity(), mShowClientReservationList);
                            recyclerViewClient.setAdapter(rvReservationAdapter);
                        }
                    }
                } else {
                    Toast.makeText(getActivity(), "No hay reservas de este usuario", Toast.LENGTH_SHORT).show();
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
     * Mètode on realitza una petició DELETE al servidor.
     * Elimina una reserva feta per un client
     *
     * @param id int, id de la reserva
     * @author Eduard Masjuan
     */
    public void deleteReservationClient(long id) {
        ApiService apiService = ApiClientToken.getInstance(preferences.getToken());
        Call<String> deleteReservation = apiService.deleteClientReservation(id);
        deleteReservation.enqueue(new Callback<String>() {
            /**
             * Es crida si ja una resposta HTTP correcte
             * @param call Sol.licita al API les dades
             * @param response  Obtenir les dades
             */
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (apiCodes.codeHttp(response.code())) {
                            Toast.makeText(getActivity(), getResources().getString(R.string.tv_info_reservation_cancel), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.tv_info_reservation_no), Toast.LENGTH_SHORT).show();
                    apiCodes.codeHttp(response.code());
                }
            }

            /**
             * Es produeix una excepció de xarxa en la comunicació amb el servidor o una excepció en la gestió de la sol·licitud
             * @param call Sol·licita al API les dades
             * @param t Captura l’excepció
             */
            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
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

}