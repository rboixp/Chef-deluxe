package masjuan.ioc.chefdeluxe.utils.recyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import masjuan.ioc.chefdeluxe.R;
import masjuan.ioc.chefdeluxe.databinding.CvItemReservationBinding;
import masjuan.ioc.chefdeluxe.model.Reservation;
import masjuan.ioc.chefdeluxe.utils.SharedPreferences;

/**
 * Classe Adapter del RecyclerView utilitzat per mostrar una llista on els cuiners volen ser contractats pels clients
 *
 * @author Eduard Masjuan
 */
public class RvAdapterListReservationCook extends RecyclerView.Adapter<RvAdapterListReservationCook.ViewHolder> {
    private SharedPreferences preferences;
    private static List<Reservation> mReservaData = null;
    private static Context mContext;
    private CvItemReservationBinding b;

    /**
     * Constructor que passa a les dades del les reserves
     *
     * @param context Context
     * @param items   Items del la llista Reservation
     */
    public RvAdapterListReservationCook(Context context, List<Reservation> items) {
        mContext = context;
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
        b = CvItemReservationBinding.inflate(LayoutInflater.from(mContext.getApplicationContext()), parent, false);
        preferences = new SharedPreferences(mContext);
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
        CvItemReservationBinding b;


        /**
         * Constructor
         *
         * @param cardView CardView
         */
        public ViewHolder(@NonNull CvItemReservationBinding cardView) {
            super(cardView.getRoot());
            this.b = cardView;
        }

        @SuppressLint("SetTextI18n")
        public void bindTo(Reservation currentReservation) {
            b.tvMState.setText(String.valueOf(currentReservation.getEstado()));
            b.tvMDate.setText(String.valueOf(currentReservation.getIncio()));
            b.tvMDateFinal.setText(String.valueOf(currentReservation.getFin()));
            b.tvMNameClient.setText(currentReservation.getCliente());
            b.tvMNameCook.setText(currentReservation.getChef());
            b.tvMNameComensales.setText(String.valueOf(currentReservation.getComensales()));
            b.tvMPrice.setText(currentReservation.getPrecio() + "€");

            // Si els estats....
            if (b.tvMState.getText().equals("pendiente")) {
                b.tvMState.setTextColor(Color.rgb(0, 0, 0));
                b.shape.setImageResource(R.drawable.ic_touch_app_24);

            } else if (b.tvMState.getText().equals("confirmado")) {
                b.tvMState.setTextColor(Color.rgb(22, 83, 126));
                b.shape.setVisibility(View.INVISIBLE);

            } else if (b.tvMState.getText().equals("pagado")) {
                b.tvMState.setTextColor(Color.rgb(120, 63, 4));
                b.shape.setImageResource(R.drawable.ic_touch_app_24);

            } else if (b.tvMState.getText().equals("rechazado")) {
                b.tvMState.setTextColor(Color.rgb(124, 13, 0));
                b.shape.setVisibility(View.INVISIBLE);

            } else if (b.tvMState.getText().equals("conciliado")) {
                b.tvMState.setTextColor(Color.rgb(0, 100, 0));
                b.shape.setVisibility(View.INVISIBLE);
            }
        }
    }
}