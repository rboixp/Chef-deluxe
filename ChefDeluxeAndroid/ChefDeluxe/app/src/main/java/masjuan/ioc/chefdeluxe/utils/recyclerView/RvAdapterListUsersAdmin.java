package masjuan.ioc.chefdeluxe.utils.recyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

import masjuan.ioc.chefdeluxe.R;
import masjuan.ioc.chefdeluxe.fragment.admin.admin.DialogOptionsUserAdmin;
import masjuan.ioc.chefdeluxe.model.User;

/**
 * Classe Adapter del RecyclerView utilitzat per mostrar una llista amb tots els usuaris
 *
 * @author Eduard Masjuan
 */
public class RvAdapterListUsersAdmin extends RecyclerView.Adapter<RvAdapterListUsersAdmin.ViewHolder> {

    private static List<User> mUserData = null;
    private final Context mContext;

    /**
     * Constructor que passa a les dades del les reserves
     *
     * @param context Context
     * @param items   Items del la llista Reservation
     */
    public RvAdapterListUsersAdmin(Context context, List<User> items) {
        mContext = context;
        mUserData = items;
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

        return new RvAdapterListUsersAdmin.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.cv_item_list_users_admin,
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
        User currentUser = mUserData.get(position);

        // Completa les vistes de text amb dades.
        holder.bindTo(currentUser);
    }

    /**
     * Obtenim el tamany del conjunt de dades
     *
     * @return int, tamany de la llista
     */
    @Override
    public int getItemCount() {
        return mUserData.size();
    }


    /**
     * Obté referencies dels components visuals per cada element de la llista
     */
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Inicialitzem les vistes.
        private final FragmentManager fragmentManager;

        public final CardView mCardview;
        public final TextView tvMId;
        public final TextView tvMUsername;
        public final TextView tvMEmail;
        public final TextView tvMName;
        public final TextView tvMSurname;
        public final TextView tvMAge;
        public final TextView tvMPhone;
        public final TextView tvMDireccion;
        public final TextView tvMVillage;
        public final TextView tvMCountry;
        public final TextView tvMPostalcode;
        public final TextView tvMIban;

        /**
         * Cosntructor
         *
         * @param view view
         */
        public ViewHolder(View view) {
            super(view);
            fragmentManager = ((FragmentActivity) view.getContext()).getSupportFragmentManager();

            mCardview = view.findViewById(R.id.card_view);
            tvMId = view.findViewById(R.id.tv_mId);
            tvMUsername = view.findViewById(R.id.tv_mUsername);
            tvMEmail = view.findViewById(R.id.tv_mEmail);
            tvMName = view.findViewById(R.id.tv_mName);
            tvMSurname = view.findViewById(R.id.tv_mSurname);
            tvMAge = view.findViewById(R.id.tv_mAge);
            tvMPhone = view.findViewById(R.id.tv_mPhone);
            tvMDireccion = view.findViewById(R.id.tv_mStreet);
            tvMVillage = view.findViewById(R.id.tv_mVillage);
            tvMPostalcode = view.findViewById(R.id.tv_mPostalcode);
            tvMCountry = view.findViewById(R.id.tv_mCountry);
            tvMIban = view.findViewById(R.id.tv_mIban);
            mCardview.setOnClickListener(this);
        }

        /**
         * Carguem les dades de cada usuari  a Textview
         *
         * @param currentUser Usuari
         */
        @SuppressLint("SetTextI18n")
        public void bindTo(User currentUser) {
            tvMId.setText(String.valueOf(currentUser.getId()));
            tvMUsername.setText(currentUser.getUsername());
            tvMEmail.setText(currentUser.getEmail());
            tvMName.setText(currentUser.getNombre());
            tvMSurname.setText(currentUser.getApellidos());
            tvMAge.setText(String.valueOf(currentUser.getEdad()));
            tvMPhone.setText(String.valueOf(currentUser.getTelefono()));
            tvMDireccion.setText(String.valueOf(currentUser.getDireccion()));
            tvMVillage.setText(String.valueOf(currentUser.getPoblacion()));
            tvMPostalcode.setText(String.valueOf(currentUser.getCodigoPostal()));
            tvMCountry.setText(String.valueOf(currentUser.getNacionalidad()));
            tvMIban.setText(String.valueOf(currentUser.getIban()));

        }

        /**
         * Al fer clic a un usuari
         * Passem al diàleg per paràmetre el nom i el nom d'usuari
         *
         * @param view Vista
         */
        @Override
        public void onClick(View view) {
            User user = mUserData.get(getBindingAdapterPosition());
            String name = user.getNombre();
            String userName = user.getUsername();

            // Obre un diàleg BottomSheet per seleccionar opcions
            BottomSheetDialogFragment sheetDialog = DialogOptionsUserAdmin.newInstance(name, userName);
            sheetDialog.show(fragmentManager, "BottomSheett");

        }
    }
}