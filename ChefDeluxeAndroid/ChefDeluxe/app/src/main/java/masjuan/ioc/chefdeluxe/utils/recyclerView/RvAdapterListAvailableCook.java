package masjuan.ioc.chefdeluxe.utils.recyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import masjuan.ioc.chefdeluxe.databinding.CvItemAvailableCookBinding;
import masjuan.ioc.chefdeluxe.model.Disponibilidad;

/**
 * Classe Adapter del RecyclerView utilitzat per mostrar una llista de les disponibilitats dels cuiners
 *
 * @author Eduard Masjuan
 */
public class RvAdapterListAvailableCook extends RecyclerView.Adapter<RvAdapterListAvailableCook.ViewHolder> {

    private static List<Disponibilidad> mAvailableData = null;
    private final Context mContext;
    private CvItemAvailableCookBinding b;

    /**
     * Constructor que passa a les dades del les reserves
     *
     * @param context Context
     * @param items   Items del la llista Disponibilidad
     */
    public RvAdapterListAvailableCook(Context context, List<Disponibilidad> items) {
        this.mContext = context;
        mAvailableData = items;
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
        b = CvItemAvailableCookBinding.inflate(LayoutInflater.from(mContext.getApplicationContext()), parent, false);
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
        Disponibilidad currentAvailable = mAvailableData.get(position);

        // Completa les vistes de text amb dades.
        holder.bindTo(currentAvailable);
    }

    /**
     * Obtenim el tamany del conjunt de dades
     *
     * @return int, tamany de la llista
     */
    @Override
    public int getItemCount() {
        return mAvailableData.size();
    }

    /**
     * Obté referencies dels components visuals per cada element de la llista
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Inicialitzem les vistes.
        CvItemAvailableCookBinding b;

        /**
         * Constructor
         *
         * @param cardView CardView
         */
        public ViewHolder(@NonNull CvItemAvailableCookBinding cardView) {
            super(cardView.getRoot());
            this.b = cardView;
        }

        public void bindTo(Disponibilidad currentReservation) {
            b.tvMState.setText(String.valueOf(currentReservation.getEstado()));
            b.tvMVillage.setText(String.valueOf(currentReservation.getPoblacion()));
        }

    }

}