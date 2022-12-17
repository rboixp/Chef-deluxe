package masjuan.ioc.chefdeluxe.utils.recyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.math.BigDecimal;
import java.util.List;

import masjuan.ioc.chefdeluxe.R;
import masjuan.ioc.chefdeluxe.fragment.client.DialogReservationPayClient;
import masjuan.ioc.chefdeluxe.model.Reservation;

/**
 * Classe Adapter del RecyclerView utilitzat per mostrar les reserves fetes pels clients
 *
 * @author Eduard Masjuan
 */
public class RvAdapterListReservationClient extends RecyclerView.Adapter<RvAdapterListReservationClient.ViewHolder> {

    private static List<Reservation> mReservaData = null;
    private final Context mContext;


    /**
     * Constructor que passa a les dades del les reserves
     *
     * @param context Context
     * @param items   Items del la llista Reservation
     */
    public RvAdapterListReservationClient(Context context, List<Reservation> items) {
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
        //b = CvItemReservationClientBinding.inflate(LayoutInflater.from(mContext.getApplicationContext()), parent, false);
        //return new ViewHolder(b);
        //Inflem la vista, per poder utilitzar els elements
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.cv_item_reservation,
                parent, false));
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
        private final FragmentManager fragmentManager;

        public final CardView mCardview;
        public final MaterialTextView tvMState;
        public final MaterialTextView tvMDate;
        public final MaterialTextView tvMDateFinal;
        public final MaterialTextView tvMNameClient;
        public final MaterialTextView tvMNameCook;
        public final MaterialTextView tvMNameComensales;
        public final MaterialTextView tvMPrice;
        public final ConstraintLayout ly_reserv;
        public final ShapeableImageView shapeableImgView;
        public final ChipGroup chipGroup;

        /**
         * Constructor
         */
        public ViewHolder(View view) {
            super(view);
            fragmentManager = ((FragmentActivity) view.getContext()).getSupportFragmentManager();

            mCardview = view.findViewById(R.id.card_view);
            tvMState = view.findViewById(R.id.tv_mState);
            tvMDate = view.findViewById(R.id.tv_mDate);
            tvMDateFinal = view.findViewById(R.id.tv_mDate_final);
            tvMNameClient = view.findViewById(R.id.tv_mNameClient);
            tvMNameCook = view.findViewById(R.id.tv_mNameCook);
            tvMNameComensales = view.findViewById(R.id.tv_mNameComensales);
            tvMPrice = view.findViewById(R.id.tv_mPrice);
            ly_reserv = view.findViewById(R.id.ly_reservationClient);
            shapeableImgView = view.findViewById(R.id.shape);
            chipGroup = view.findViewById(R.id.chip_group_filter);

            //mCardview.setOnClickListener(this);
        }

        @SuppressLint("SetTextI18n")
        public void bindTo(Reservation currentReservation) {

            tvMState.setText(String.valueOf(currentReservation.getEstado()));
            tvMDate.setText(String.valueOf(currentReservation.getIncio()));
            tvMDateFinal.setText(String.valueOf(currentReservation.getFin()));
            tvMNameClient.setText(currentReservation.getCliente());
            tvMNameCook.setText(currentReservation.getChef());
            tvMNameComensales.setText(String.valueOf(currentReservation.getComensales()));
            tvMPrice.setText(currentReservation.getPrecio() + "€");


            if (tvMState.getText().equals("pendiente")) {
                tvMState.setTextColor(Color.rgb(0, 0, 0));
                shapeableImgView.setImageResource(R.drawable.ic_swipe_left_24);

            } else if (tvMState.getText().equals("confirmado")) {
                tvMState.setTextColor(Color.rgb(22, 83, 126));
                shapeableImgView.setImageResource(R.drawable.ic_touch_app_24);

            } else if (tvMState.getText().equals("pagado")) {
                tvMState.setTextColor(Color.rgb(120, 63, 4));
                shapeableImgView.setVisibility(View.INVISIBLE);

            } else if (tvMState.getText().equals("rechazado")) {
                tvMState.setTextColor(Color.rgb(124, 13, 0));

            } else if (tvMState.getText().equals("conciliado")) {
                tvMState.setTextColor(Color.rgb(0, 100, 0));
                shapeableImgView.setVisibility(View.INVISIBLE);
            }
        }


    }

}