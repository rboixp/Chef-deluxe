package masjuan.ioc.chefdeluxe.utils.recyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import masjuan.ioc.chefdeluxe.databinding.CvItemReservationCookBinding;
import masjuan.ioc.chefdeluxe.model.Reservation;

/**
 * Classe Adapter del RecyclerView utilitzat per mostrar una llista on els cuiners volen ser contractats pels clients
 *
 * @author Eduard Masjuan
 */
public class RvAdapterListReservationCook extends RecyclerView.Adapter<RvAdapterListReservationCook.ViewHolder> {

    private static List<Reservation> mReservaData = null;
    private final Context mContext;
    private CvItemReservationCookBinding b;

    /**
     * Constructor que passa a les dades del les reserves
     *
     * @param context Context
     * @param items   Items del la llista Reservation
     */
    public RvAdapterListReservationCook(Context context, List<Reservation> items) {
        this.mContext = context;
        mReservaData = items;
    }

    /**
     * Mètode que crea noves vistes
     *
     * @param parent   ViewGroup que conte les vistes a les dades
     * @param viewType int
     * @return ViewHolder
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflem la vista, per poder utilitzar els elements
        b = CvItemReservationCookBinding.inflate(LayoutInflater.from(mContext.getApplicationContext()), parent, false);
        return new ViewHolder(b);
    }

    /**
     * Mètode que enllaça les dades amb el ViewHolder.
     *
     * @param holder   ViewHolder
     * @param position int, posició de l'element
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // S'obté un element de la llista segons la seva posició
        Reservation currentReservation = mReservaData.get(position);

        // Completa les vistes de text amb dades.
        holder.bindTo(currentReservation);
    }

    /**
     * Obtenim el tamany del conjunt de dades
     *
     * @return int, tamany de la llista
     */
    @Override
    public int getItemCount() {
        return mReservaData.size();
    }


    /**
     * Obté referencies dels components visuals per cada element de la llista
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Inicialitzem les vistes.
        CvItemReservationCookBinding b;

        /**
         * Constructor
         *
         * @param cardView CardView
         */
        public ViewHolder(@NonNull CvItemReservationCookBinding cardView) {
            super(cardView.getRoot());
            this.b = cardView;
            // b.cardView.setOnClickListener(this);
        }

        public void bindTo(Reservation currentReservation) {
            //b.tvMId.setText(String.valueOf(currentReservation.getId()));
            b.tvMState.setText(String.valueOf(currentReservation.getEstado()));
            b.tvMDate.setText(String.valueOf(currentReservation.getIncio()));
            b.tvMDateFinal.setText(String.valueOf(currentReservation.getFin()));
            b.tvMNameClient.setText(currentReservation.getCliente());
            b.tvMNameCook.setText(currentReservation.getChef());
            b.tvMPrice.setText(currentReservation.getPrecio() + "€");
        }

    }


}