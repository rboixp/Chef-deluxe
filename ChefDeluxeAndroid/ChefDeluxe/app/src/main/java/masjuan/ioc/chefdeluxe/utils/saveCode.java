package masjuan.ioc.chefdeluxe.utils;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import masjuan.ioc.chefdeluxe.R;
import masjuan.ioc.chefdeluxe.api.ApiClientToken;
import masjuan.ioc.chefdeluxe.api.ApiService;
import masjuan.ioc.chefdeluxe.model.Reservation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class saveCode {


/*
            // Validació login
            if (validate.usernameEmpty(usernameInput) || validate.passwordEmpty(passwordInput)) {
                countDownTimer(getResources().getString(R.string.tv_empty_user_pass));
            } else {
                // Objecte Login rep la informació del servidor
                Login login = userLoginData(usernameInput, passwordInput);
                getToken(login);

            }


            ---------
                <androidx.constraintlayout.widget.ConstraintLayout
        android:id="@+id/lyconst_failed_user_pass"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginStart="24dp"
        android:layout_marginEnd="24dp"
        android:background="#D98283"
        android:padding="5dp"
        android:visibility="visible"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/edtv_password">


        <ImageView
            android:id="@+id/iv_failed_user_pass"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginStart="8dp"
            android:layout_marginTop="8dp"
            android:layout_marginEnd="8dp"
            android:layout_marginBottom="8dp"
            android:contentDescription="@string/img_content_description_fail"
            android:src="@drawable/ic_alert"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toStartOf="@+id/tv_failed_user_pass"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent"
            tools:srcCompat="@drawable/ic_alert" />

        <TextView
            android:id="@+id/tv_failed_user_pass"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_marginStart="4dp"
            android:layout_marginTop="8dp"
            android:layout_marginEnd="8dp"
            android:layout_marginBottom="8dp"
            android:text="@string/tv_failed_user_pass"
            android:textColor="#A83F3F"
            android:textSize="13sp"
            android:visibility="visible"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toEndOf="@+id/iv_failed_user_pass"
            app:layout_constraintTop_toTopOf="parent" />

    </androidx.constraintlayout.widget.ConstraintLayout>




----
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/white"
    tools:context=".MainActivity">

    <TextView
        android:id="@+id/tv_title"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="32dp"
        android:text="@string/tv_title"
        android:textColor="@color/black"
        android:textSize="40sp"
        android:visibility="invisible"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />


    <TextView
        android:id="@+id/tv_title_createAcc"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="60dp"
        android:text="Crea tu cuenta"
        android:textSize="22sp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />


    <EditText
        android:id="@+id/edtv_name"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginStart="24dp"
        android:layout_marginTop="16dp"
        android:layout_marginEnd="24dp"
        android:ems="10"
        android:hint="Nombre"
        android:inputType="textPersonName"
        android:minHeight="48dp"
        android:textSize="16sp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="1.0"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/rgRol" />


    <EditText
        android:id="@+id/edtv_surname"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginStart="24dp"
        android:layout_marginTop="16dp"
        android:layout_marginEnd="24dp"
        android:ems="10"
        android:hint="Apellido"
        android:inputType="textPersonName"
        android:minHeight="48dp"
        android:textSize="16sp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="1.0"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/edtv_name" />

    <EditText
        android:id="@+id/edtv_userName"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginStart="24dp"
        android:layout_marginTop="16dp"
        android:layout_marginEnd="24dp"
        android:ems="10"
        android:hint="Usuario"
        android:inputType="textPersonName"
        android:minHeight="48dp"
        android:textSize="16sp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/edtv_surname" />


    <EditText
        android:id="@+id/edtv_mail"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginStart="24dp"
        android:layout_marginTop="16dp"
        android:layout_marginEnd="24dp"
        android:ems="10"
        android:hint="Correo electrónico"
        android:inputType="textWebEmailAddress"
        android:minHeight="48dp"
        android:textSize="16sp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="1.0"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/edtv_userName" />

    <EditText
        android:id="@+id/edtv_password"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginStart="24dp"
        android:layout_marginTop="16dp"
        android:layout_marginEnd="24dp"
        android:ems="10"
        android:hint="Contraseña"
        android:inputType="textPassword"
        android:textSize="16sp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.0"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/edtv_mail"
        tools:ignore="TouchTargetSizeCheck" />

    <TextView
        android:id="@+id/tv_register_select_rol"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginStart="24dp"
        android:layout_marginTop="40dp"
        android:layout_marginEnd="24dp"
        android:text="@string/btt_register_select_rol"
        android:textSize="16sp"
        app:layout_constraintEnd_toEndOf="@+id/tv_title_createAcc"
        app:layout_constraintStart_toStartOf="@+id/tv_title_createAcc"
        app:layout_constraintTop_toBottomOf="@+id/tv_title_createAcc" />

    <RadioGroup
        android:id="@+id/rgRol"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginStart="24dp"
        android:layout_marginTop="16dp"
        android:layout_marginEnd="24dp"
        android:orientation="horizontal"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/tv_register_select_rol">

        <RadioButton
            android:id="@+id/rbRolClient"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_weight="1"

            android:text="@string/btt_register_rol_client"
            android:textSize="16sp" />

        <RadioButton
            android:id="@+id/rbRolCook"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_weight="1"

            android:text="@string/btt_register_rol_cook"
            android:textSize="16sp" />
    </RadioGroup>


    <Button
        android:id="@+id/btt_create_acc"
        android:layout_width="match_parent"
        android:layout_height="48dp"
        android:layout_marginStart="24dp"
        android:layout_marginTop="16dp"
        android:layout_marginEnd="24dp"
        android:ems="10"
        android:text="@string/btt_enter"
        android:textSize="14sp"
        app:layout_constraintEnd_toEndOf="@+id/edtv_password"
        app:layout_constraintStart_toStartOf="@+id/edtv_password"
        app:layout_constraintTop_toBottomOf="@+id/edtv_password" />

    <View
        android:id="@+id/dividerRegister"
        android:layout_width="match_parent"
        android:layout_height="1dp"
        android:layout_marginBottom="14dp"
        android:background="?android:attr/listDivider"
        app:layout_constraintBottom_toTopOf="@+id/tv_login"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent" />

    <TextView
        android:id="@+id/tv_login"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:gravity="center"
        android:paddingBottom="16dp"
        android:text="@string/tv_register_login"
        android:textSize="13sp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent" />


</androidx.constraintlayout.widget.ConstraintLayout>

*/


/*
    public class AccountClient extends BottomSheetDialogFragment {


Altre classe:
       BottomSheetDialogFragment bsdFragment = AccountClient.newInstance();
         bsdFragment.show(requireActivity().getSupportFragmentManager(), "BottomSheet");






    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        b = FragmentChangePasswordClientBinding.inflate(inflater, container, false);
        preferences = new SharedPreferences(requireActivity());


        // Cambiar contrasenya
        b.bttConfirmPassword.setOnClickListener(view -> {
            // Capturem la escriptura
            currentPassword = String.valueOf(b.inputEdtvCurrentPassword.getText());
            newPassword = String.valueOf(b.inputEdtvNewPassword.getText());
            repeatNewPassword = String.valueOf(b.inputEdtvRepeatNewPassword.getText());

            if (currentPassword.equals(preferences.getPassword())) { // Aqui hauriem d agafar dades de l'usuari, GGET USER  i agafa el password

                if (!newPassword.isEmpty() && !repeatNewPassword.isEmpty() && newPassword.equals(repeatNewPassword)) { //VALIDACIO PASSWORD


                    Registration dataUserr = new Registration();
                    dataUserr.setPassword(newPassword);
                    dataUserr.setUsername(preferences.getUsername());
                    dataUserr.setEmail(preferences.getEmail());  // Que no es pugui canviar, xk si no cau el token

                    if (preferences.getRole() == 1) {
                        dataUserr.setPerfil("ROLE_ADMIN");
                    } else if (preferences.getRole() == 2) {
                        dataUserr.setPerfil("ROLE_CHEF");
                    } else if (preferences.getRole() == 3) {
                        dataUserr.setPerfil("ROLE_CLIENT");
                    }

                    updateUserApi(preferences.getUsername(), dataUserr);

                } else {
                    Toast.makeText(getActivity(), "Password son diferents", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "El password es incorrecte", Toast.LENGTH_SHORT).show();
            }
        });


        return b.getRoot();
    }

    public void updateUserApi(String username, Registration registration) {
        ApiService apiService = ApiClientToken.getInstance(preferences.getToken());

        Call<User> user = apiService.putUserData(username, registration);
        user.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        // Comprova el codi rebut, si hi ha error mostrarà un missatge
                     // Toast.makeText(getActivity(), "Cambios realizados con éxito!", Toast.LENGTH_SHORT).show();
                      //  Toast.makeText(getActivity(), "Antic pas!: " + preferences.getPassword(), Toast.LENGTH_SHORT).show();


                     //   Toast.makeText(getActivity(), "Nou pass!: " + preferences.getPassword(), Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(getActivity(), "Error: " + response.code(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
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






/// DIALOG ALERT

        b.tvAccDeleted.setOnClickListener(view -> new DeleteAccountClient().show(getChildFragmentManager(), "deletedAccFrag"));

    // Dialeg
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        return new MaterialAlertDialogBuilder(requireActivity(), com.google.android.material.R.style.MaterialAlertDialog_Material3)
                .setTitle(getResources().getString(R.string.txt_title_accountDelete))
                .setMessage(getResources().getString(R.string.txt_delete_accountInfo))
                .setPositiveButton(getResources().getString(R.string.txt_delete_yes), (dialog, which) -> deleteUserApi(preferences.getUsername()))
                .setNegativeButton(getResources().getString(R.string.txt_delete_no), (dialog, which) -> dialog.cancel())
                .create();
    }












         <androidx.constraintlayout.widget.ConstraintLayout
                android:id="@+id/constraintLayout3"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginStart="16dp"
                android:layout_marginTop="8dp"
                android:layout_marginEnd="16dp"
                android:padding="2dp"
                android:visibility="visible"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toBottomOf="@+id/constraintLayout2">

                <com.google.android.material.textfield.TextInputLayout
                    android:id="@+id/edt_poblacio"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:layout_marginTop="300dp"
                    android:ems="10"
                    app:errorEnabled="false"
                    app:errorIconDrawable="@null"
                    app:layout_constraintEnd_toEndOf="parent"
                    app:layout_constraintStart_toStartOf="parent"
                    app:layout_constraintTop_toTopOf="parent"
                    app:startIconDrawable="@drawable/ic_search_24">

                    <com.google.android.material.textfield.TextInputEditText
                        android:id="@+id/input_edtv_poblacio"
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content"
                        android:hint="Població"
                        android:imeOptions="actionDone"
                        android:inputType="text"
                        android:singleLine="true"
                        android:textColor="@color/black"
                        android:textColorHint="@color/black"
                        android:textSize="14sp" />
                </com.google.android.material.textfield.TextInputLayout>


            </androidx.constraintlayout.widget.ConstraintLayout>

           <com.google.android.material.card.MaterialCardView
                android:id="@+id/card_view_client"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginStart="24dp"
                android:layout_marginTop="16dp"
                android:layout_marginEnd="24dp"
                android:visibility="gone"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toBottomOf="@+id/constraintLayout3"
                app:strokeColor="@color/dark_red"
                app:strokeWidth="2dp"
                card_view:cardCornerRadius="10dp"
                card_view:cardElevation="5dp"
                card_view:cardUseCompatPadding="true">

                <androidx.constraintlayout.widget.ConstraintLayout
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    card_view:layout_constraintBottom_toBottomOf="parent"
                    card_view:layout_constraintTop_toTopOf="parent">


                    <androidx.constraintlayout.widget.ConstraintLayout
                        android:id="@+id/ly_date"
                        android:layout_width="0dp"
                        android:layout_height="wrap_content"
                        android:background="@color/yellow_green"
                        app:layout_constraintBottom_toTopOf="@+id/divider_hori"
                        app:layout_constraintEnd_toStartOf="@+id/ly_time"
                        app:layout_constraintStart_toStartOf="parent"
                        card_view:layout_constraintTop_toTopOf="parent">

                        <ImageView
                            android:id="@+id/img_date"
                            android:layout_width="match_parent"
                            android:layout_height="wrap_content"
                            android:layout_marginTop="8dp"
                            android:adjustViewBounds="true"
                            android:alpha="0.7"
                            android:contentDescription="Dia"

                            android:src="@drawable/ic_calendar_month_28"
                            app:layout_constraintEnd_toEndOf="parent"
                            app:layout_constraintStart_toStartOf="parent"
                            app:layout_constraintTop_toTopOf="parent" />

                        <TextView
                            android:id="@+id/tv_dia"
                            android:layout_width="0dp"
                            android:layout_height="wrap_content"
                            android:layout_marginTop="16dp"
                            android:gravity="center"
                            android:text="Dia"
                            android:textSize="16sp"
                            android:visibility="invisible"
                            app:layout_constraintEnd_toEndOf="parent"
                            app:layout_constraintStart_toStartOf="parent"
                            app:layout_constraintTop_toBottomOf="@+id/img_date" />

                    </androidx.constraintlayout.widget.ConstraintLayout>

                    <androidx.constraintlayout.widget.ConstraintLayout
                        android:id="@+id/ly_time"
                        android:layout_width="0dp"
                        android:layout_height="wrap_content"
                        android:background="@color/wheat"
                        app:layout_constraintBottom_toTopOf="@+id/divider_hori"
                        app:layout_constraintEnd_toEndOf="parent"
                        app:layout_constraintStart_toEndOf="@+id/ly_date"
                        card_view:layout_constraintTop_toTopOf="parent">

                        <ImageView
                            android:id="@+id/img_time"
                            android:layout_width="match_parent"
                            android:layout_height="wrap_content"
                            android:layout_marginTop="8dp"
                            android:adjustViewBounds="true"
                            android:alpha="0.7"
                            android:contentDescription="Hora"
                            android:src="@drawable/ic_time_28"
                            app:layout_constraintEnd_toEndOf="parent"
                            app:layout_constraintStart_toStartOf="parent"
                            app:layout_constraintTop_toTopOf="parent" />

                        <TextView
                            android:id="@+id/tv_time"
                            android:layout_width="0dp"
                            android:layout_height="wrap_content"
                            android:layout_marginTop="16dp"
                            android:gravity="center"
                            android:text="Hora"
                            android:textSize="16sp"
                            android:visibility="invisible"
                            app:layout_constraintEnd_toEndOf="parent"
                            app:layout_constraintStart_toStartOf="parent"
                            app:layout_constraintTop_toBottomOf="@+id/img_time" />

                    </androidx.constraintlayout.widget.ConstraintLayout>


                    <com.google.android.material.divider.MaterialDivider
                        android:id="@+id/divider_hori"
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content"
                        app:dividerColor="@color/black"
                        app:dividerThickness="1dp"
                        app:layout_constraintBottom_toBottomOf="@+id/ly_date"
                        app:layout_constraintEnd_toEndOf="parent"
                        app:layout_constraintStart_toStartOf="parent"
                        app:layout_constraintTop_toBottomOf="@+id/ly_time" />


                    <androidx.constraintlayout.widget.ConstraintLayout
                        android:id="@+id/ly_village"
                        android:layout_width="0dp"
                        android:layout_height="wrap_content"
                        android:background="@color/light_yellow"
                        app:layout_constraintBottom_toBottomOf="parent"
                        app:layout_constraintEnd_toStartOf="@+id/ly_cook"
                        app:layout_constraintStart_toStartOf="parent"
                        app:layout_constraintTop_toBottomOf="@+id/divider_hori">

                        <ImageView
                            android:id="@+id/img_village"
                            android:layout_width="match_parent"
                            android:layout_height="wrap_content"
                            android:layout_marginTop="8dp"
                            android:adjustViewBounds="true"
                            android:alpha="0.7"
                            android:contentDescription="Poble"
                            android:src="@drawable/ic_villa_28"
                            app:layout_constraintEnd_toEndOf="parent"
                            app:layout_constraintStart_toStartOf="parent"
                            app:layout_constraintTop_toTopOf="parent" />

                        <TextView
                            android:id="@+id/tv_village"
                            android:layout_width="0dp"
                            android:layout_height="wrap_content"
                            android:layout_marginTop="16dp"
                            android:gravity="center"
                            android:text="Poble"
                            android:textSize="16sp"
                            android:visibility="invisible"
                            app:layout_constraintEnd_toEndOf="parent"
                            app:layout_constraintStart_toStartOf="parent"
                            app:layout_constraintTop_toBottomOf="@+id/img_village" />

                    </androidx.constraintlayout.widget.ConstraintLayout>

                    <androidx.constraintlayout.widget.ConstraintLayout
                        android:id="@+id/ly_cook"
                        android:layout_width="0dp"
                        android:layout_height="wrap_content"
                        android:background="@color/honeydew"
                        app:layout_constraintBottom_toBottomOf="parent"
                        app:layout_constraintEnd_toEndOf="parent"
                        app:layout_constraintStart_toEndOf="@+id/ly_village"
                        app:layout_constraintTop_toBottomOf="@+id/divider_hori">

                        <ImageView
                            android:id="@+id/img_cook"
                            android:layout_width="match_parent"
                            android:layout_height="wrap_content"
                            android:layout_marginTop="8dp"
                            android:adjustViewBounds="true"
                            android:alpha="0.7"
                            android:contentDescription="Cuiner"
                            android:src="@drawable/ic_cookie_28"
                            app:layout_constraintEnd_toEndOf="parent"
                            app:layout_constraintStart_toStartOf="parent"
                            app:layout_constraintTop_toTopOf="parent" />

                        <TextView
                            android:id="@+id/tv_cook"
                            android:layout_width="0dp"
                            android:layout_height="wrap_content"
                            android:layout_marginTop="16dp"
                            android:gravity="center"
                            android:text="Cuiner"
                            android:textSize="16sp"
                            android:visibility="invisible"
                            app:layout_constraintEnd_toEndOf="parent"
                            app:layout_constraintStart_toStartOf="parent"
                            app:layout_constraintTop_toBottomOf="@+id/img_cook" />

                    </androidx.constraintlayout.widget.ConstraintLayout>


                </androidx.constraintlayout.widget.ConstraintLayout>

            </com.google.android.material.card.MaterialCardView>









PEROSNES!!! XML

 <androidx.constraintlayout.widget.ConstraintLayout
                android:id="@+id/constraintLayout2"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginStart="16dp"
                android:layout_marginTop="8dp"
                android:layout_marginEnd="16dp"
                android:padding="2dp"
                android:visibility="gone"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toTopOf="parent">

                <com.google.android.material.textview.MaterialTextView
                    android:id="@+id/tv_select_person_number"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:drawablePadding="3dp"
                    android:text="Personas"
                    android:textSize="16sp"
                    app:drawableLeftCompat="@drawable/ic_people_24"
                    app:layout_constraintBottom_toBottomOf="parent"
                    app:layout_constraintStart_toStartOf="parent"
                    app:layout_constraintTop_toTopOf="parent" />

                <com.google.android.material.textview.MaterialTextView
                    android:id="@+id/tv_select_person_substract"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_marginEnd="18dp"
                    android:enabled="false"
                    android:padding="2dp"
                    app:drawableStartCompat="@drawable/selector_choose_number_people_substract"
                    app:layout_constraintBottom_toBottomOf="parent"
                    app:layout_constraintEnd_toStartOf="@+id/tv_select_person_num"
                    app:layout_constraintTop_toTopOf="parent" />

                <com.google.android.material.textview.MaterialTextView
                    android:id="@+id/tv_select_person_num"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_marginEnd="18dp"
                    android:layout_marginBottom="2dp"
                    android:text="1"
                    android:textSize="18sp"
                    app:layout_constraintBottom_toBottomOf="parent"
                    app:layout_constraintEnd_toStartOf="@+id/tv_select_person_sum"
                    app:layout_constraintTop_toTopOf="parent" />

                <com.google.android.material.textview.MaterialTextView
                    android:id="@+id/tv_select_person_sum"
                    android:layout_width="0dp"
                    android:layout_height="wrap_content"
                    android:enabled="true"
                    android:padding="2dp"
                    app:drawableStartCompat="@drawable/selector_choose_number_people_add"
                    app:layout_constraintBottom_toBottomOf="parent"
                    app:layout_constraintEnd_toEndOf="parent"
                    app:layout_constraintTop_toTopOf="parent" />

            </androidx.constraintlayout.widget.ConstraintLayout>



////////////////////////////////////////////////TOOGLE BUTTONS


    <androidx.constraintlayout.widget.ConstraintLayout
        android:id="@+id/lyconst_dispo"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_marginTop="8dp"
        android:visibility="visible"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/ly_toolbar">


        <com.google.android.material.button.MaterialButtonToggleGroup
            android:id="@+id/toggle_button_group"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:gravity="center"
            android:visibility="visible"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent"
            app:selectionRequired="true"
            app:singleSelection="true">

            <com.google.android.material.button.MaterialButton
                android:id="@+id/toogleButton_add"
                style="?attr/materialButtonOutlinedStyle"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Añadir"
                android:textSize="16sp"
                app:cornerRadius="7dp"
                app:strokeWidth="0.5dp" />

            <com.google.android.material.button.MaterialButton
                android:id="@+id/toogleButton_edit"
                style="?attr/materialButtonOutlinedStyle"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Editar"
                android:textSize="16sp"
                app:cornerRadius="7dp"
                app:strokeWidth="0.5dp" />

        </com.google.android.material.button.MaterialButtonToggleGroup>

        b.toggleButtonGroup.check(R.id.toogleButton_add);
        b.toggleButtonGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (isChecked) {
                    if (checkedId == R.id.toogleButton_add) {
                        foundDispo = false;
                    } else if (checkedId == R.id.toogleButton_edit) {
                        foundDispo = true;
                    }
                }
            }
        });

////////////////////////////////////////////////




@NonNull
private String currentDate() {
    DateFormat dateNow = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    Date date = new Date();
    return dateNow.format(date);
}


    @NonNull
    private String currentTime() {
        SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        return timeformat.format(new Date());
    }


        // Mostrem data i hora actual
        dateBegin = dateFinal = currentDate();
        timeBegin = timeFinal = currentTime();

        b.tvDateBeginning.setText(dateBegin);
        b.tvDateFinal.setText(dateFinal);
        b.tvTimeBeginning.setText(timeBegin);
        b.tvTimeFinal.setText(timeFinal);


        /*
        dateBegin = currentDate();
        dateFinal = currentDate();
        b.tvDateBeginning.setText(dateBegin);
        b.tvDateFinal.setText(dateFinal);

    /**
     * Mètode on realitza una petició GET al servidor. Rebem una reserva per ID
     *
     * @param id long id usuari
     * @author Eduard Masjuan
     */

/*public void getReservationListClient(long id) {
    ApiService apiService = ApiClientToken.getInstance(preferences.getToken());
    Call<Reservation> reservation = apiService.getFilterIdClientReservation(id);
    reservation.enqueue(new Callback<Reservation>() {

        @Override
        public void onResponse(@NonNull Call<Reservation> call, @NonNull Response<Reservation> response) {
            if (response.isSuccessful()) {
                if (response.body() != null) {
                    if (apiCodes.codeHttp(response.code())) {

                        b.tvId.setText("Numeró de reserva: " + response.body().getId());
                        b.tvNameClient.setText("Nombre reserva " + response.body().getCliente());
                        b.tvNameCook.setText("Cocinero: " + response.body().getChef());
                        b.tvDate.setText("Fecha incio: " + response.body().getIncio());
                        b.tvDateFinal.setText("Fecha final: " + response.body().getFin());
                        b.tvPrice.setText("Precio: " + response.body().getPrecio());
                        b.tvState.setText("Estado: " + response.body().getEstado());
                    }
                }
            } else {
                Toast.makeText(getActivity(), "No hay reservas con este número id", Toast.LENGTH_SHORT).show();
                apiCodes.codeHttp(response.code());
            }
        }


        @Override
        public void onFailure(@NonNull Call<Reservation> call, @NonNull Throwable t) {
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


          <androidx.constraintlayout.widget.ConstraintLayout
                android:id="@+id/lyconst_disponibilidad2"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginStart="16dp"
                android:layout_marginTop="8dp"
                android:layout_marginEnd="16dp"
                android:visibility="gone"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toBottomOf="@+id/tv_info_reserva">


                <com.google.android.material.textview.MaterialTextView
                    android:id="@+id/tv_id"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:text="Numero de reserva:"
                    android:textSize="16sp"
                    app:layout_constraintEnd_toEndOf="parent"
                    app:layout_constraintStart_toStartOf="parent"
                    app:layout_constraintTop_toTopOf="parent" />


                <com.google.android.material.textview.MaterialTextView
                    android:id="@+id/tv_nameClient"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:layout_below="@+id/tv_date_final"
                    android:layout_marginTop="8dp"
                    android:text="Nombre cliente:"
                    android:textSize="16sp"
                    app:layout_constraintEnd_toEndOf="parent"
                    app:layout_constraintStart_toStartOf="parent"
                    app:layout_constraintTop_toBottomOf="@+id/tv_id" />


                <com.google.android.material.textview.MaterialTextView
                    android:id="@+id/tv_date"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:layout_marginTop="8dp"
                    android:text="Fecha inicio:"
                    android:textSize="16sp"
                    app:layout_constraintEnd_toEndOf="parent"
                    app:layout_constraintStart_toStartOf="parent"
                    app:layout_constraintTop_toBottomOf="@+id/tv_nameClient" />

                <com.google.android.material.textview.MaterialTextView
                    android:id="@+id/tv_date_final"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:layout_marginTop="8dp"
                    android:text="Fecha final:"
                    android:textSize="16sp"
                    app:layout_constraintEnd_toEndOf="parent"
                    app:layout_constraintStart_toStartOf="parent"
                    app:layout_constraintTop_toBottomOf="@+id/tv_date" />


                <com.google.android.material.textview.MaterialTextView
                    android:id="@+id/tv_nameCook"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:layout_marginTop="8dp"
                    android:text="Nombre cocinero:"
                    android:textSize="16sp"
                    app:layout_constraintEnd_toEndOf="parent"
                    app:layout_constraintStart_toStartOf="parent"
                    app:layout_constraintTop_toBottomOf="@+id/tv_date_final" />

                <com.google.android.material.textview.MaterialTextView
                    android:id="@+id/tv_price"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:layout_below="@+id/tv_nameCook"
                    android:layout_marginTop="8dp"
                    android:text="Precio:"
                    android:textSize="16sp"
                    app:layout_constraintEnd_toEndOf="parent"
                    app:layout_constraintStart_toStartOf="parent"
                    app:layout_constraintTop_toBottomOf="@+id/tv_nameCook" />

                <com.google.android.material.textview.MaterialTextView
                    android:id="@+id/tv_state"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:layout_marginTop="8dp"
                    android:text="Estado:"
                    android:textSize="16sp"
                    app:layout_constraintEnd_toEndOf="parent"
                    app:layout_constraintStart_toStartOf="parent"
                    app:layout_constraintTop_toBottomOf="@+id/tv_price" />

            </androidx.constraintlayout.widget.ConstraintLayout>

*/






}

