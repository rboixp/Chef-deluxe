package masjuan.ioc.chefdeluxe.utils.recyclerView;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import masjuan.ioc.chefdeluxe.R;
import masjuan.ioc.chefdeluxe.databinding.CvItemAvailableClientBinding;
import masjuan.ioc.chefdeluxe.model.Disponibilidad;

/**
 * Classe Adapter del RecyclerView utilitzat per mostrar els cuiners disponibles als clients
 *
 * @author Eduard Masjuan
 */
public class RvAdapterListCook extends RecyclerView.Adapter<RvAdapterListCook.ViewHolder> {

    private static List<Disponibilidad> mReservaData = null;
    private final Context mContext;
    private CvItemAvailableClientBinding b;

    /**
     * Constructor que passa a les dades del les reserves
     *
     * @param context Context
     * @param items   Items del la llista Disponibilitat
     */
    public RvAdapterListCook(Context context, List<Disponibilidad> items) {
        this.mContext = context;
        mReservaData = items;
    }

    /**
     * Mètode que crea noves vistes
     *
     * @param parent   ViewGroup quye conte les vistes a les dades
     * @param viewType int
     * @return ViewHolder
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflem la vista, per poder utilitzar els elements
        b = CvItemAvailableClientBinding.inflate(LayoutInflater.from(mContext.getApplicationContext()), parent, false);
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
        Disponibilidad currentDisponi = mReservaData.get(position);

        // Completa les vistes de text amb dades.
        holder.bindTo(currentDisponi);
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
        CvItemAvailableClientBinding b;

        /**
         * Constructor
         *
         * @param cardView CardView
         */
        public ViewHolder(@NonNull CvItemAvailableClientBinding cardView) {
            super(cardView.getRoot());
            this.b = cardView;
        }

        public void bindTo(Disponibilidad currentDispo) {
            b.tvMId.setText(String.valueOf(currentDispo.getId()));
            b.tvMNameCook.setText(currentDispo.getUsernameOrEmail());
            b.tvMDispo.setText(currentDispo.getEstado());
            b.tvMVillage.setText(currentDispo.getPoblacion());

            // Si la disponibilitat es Activo
            if (b.tvMDispo.getText().equals("Activo")) {
                b.tvMDispo.setTextColor(Color.rgb(0, 100, 0));
                b.lyInsideCardView.setBackgroundResource(R.color.background_main);
            }

        }
    }
}