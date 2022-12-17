package masjuan.ioc.chefdeluxe.fragment.client;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textview.MaterialTextView;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import masjuan.ioc.chefdeluxe.MainActivity;
import masjuan.ioc.chefdeluxe.R;
import masjuan.ioc.chefdeluxe.api.ApiGlobal;
import masjuan.ioc.chefdeluxe.databinding.FragmentClientNavHomeBinding;
import masjuan.ioc.chefdeluxe.model.Reservation;
import masjuan.ioc.chefdeluxe.model.Tarifa;
import masjuan.ioc.chefdeluxe.utils.ApiCodes;
import masjuan.ioc.chefdeluxe.utils.Methods;
import masjuan.ioc.chefdeluxe.utils.SharedPreferences;
import masjuan.ioc.chefdeluxe.utils.UtilsFragments;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Classe on el client pot fer una nova reserva
 *
 * @author Eduard Masjuan
 */
public class NavReservationClient extends Fragment {
    private FragmentClientNavHomeBinding b;
    private SharedPreferences preferences;
    private ApiCodes apiCodes;
    private Methods method;
    private UtilsFragments frag = null;
    private ApiGlobal apiGlobal;

    private String[] villageArray;

    // Variables per Reservation
    private String timeBegin;
    private String timeFinal;
    private String dateBegin;
    private String dateFinal;
    private String villageInput;
    private String chooseCook;
    private String comensales;
    private BigDecimal tarifaCook;

    private long calculTotal = 0;

    /**
     * Constructor buit
     */
    public NavReservationClient() {
        // Required empty public constructor
    }

    /**
     * Crea una nova instancia de NavReservationClient
     *
     * @return NavReservationClient
     */
    public static NavReservationClient newInstance() {
        return new NavReservationClient();
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
        frag = new UtilsFragments(requireActivity().getSupportFragmentManager());
        preferences = new SharedPreferences(requireActivity());
        apiGlobal = new ApiGlobal();
    }

    /**
     * Dissenya la interfície d'usuari per primera vegada.
     * Opcions de: Seleccionar data i hora, població i cuiner
     *
     * @param inflater           Infla la vista
     * @param container          Vista que s'adjuntarà a la interfície d'usuari
     * @param savedInstanceState Bundle
     * @return Vista
     * @author Eduard Masjuan
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        b = FragmentClientNavHomeBinding.inflate(inflater, container, false);
        apiCodes = new ApiCodes();
        method = new Methods();

        // Títol per la toolbar
        b.lyToolbar.toolbar.setTitle(getResources().getString(R.string.tv_acc_home));
        ((MainActivity) requireActivity()).setSupportActionBar(b.lyToolbar.toolbar);
        setHasOptionsMenu(true);

        // Array poblacions
        villageArray = getResources().getStringArray(R.array.villages_array);

        // TextWatcher- Escolta els canvis que es produeixen en el camps
        b.spinnerVillage.addTextChangedListener(textWatcher);
        b.tvDateBeginning.addTextChangedListener(textWatcher);
        b.tvDateFinal.addTextChangedListener(textWatcher);
        b.tvTimeBeginning.addTextChangedListener(textWatcher);
        b.tvTimeFinal.addTextChangedListener(textWatcher);
        b.tvCook.addTextChangedListener(textWatcher);
        b.inputPay.addTextChangedListener(textWatcher);
        b.inputComensales.addTextChangedListener(textWatcher);

        // DatePicker- Seleccionar Data
        MaterialDatePicker.Builder<Long> datePicker = MaterialDatePicker.Builder.datePicker();
        datePicker.setTitleText(getResources().getString(R.string.txt_datePicker));
        MaterialDatePicker<Long> materialDatePickerBegin = datePicker.build();
        MaterialDatePicker<Long> materialDatePickerfinal = datePicker.build();

        // TimePicker- Seleccionar Hora
        MaterialTimePicker.Builder timePicker = new MaterialTimePicker.Builder();
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("CET"));
        timePicker.setTitleText("RESERVAR HORA").setTimeFormat(TimeFormat.CLOCK_24H).setHour(calendar.get(Calendar.HOUR_OF_DAY)).setMinute(calendar.get(Calendar.MINUTE));
        MaterialTimePicker mTimePickerBegin = timePicker.build();
        MaterialTimePicker mTimePickerFinal = timePicker.build();

        // Boto dataPicker, mostra la data seleccionada
        b.lyDateBegin.setOnClickListener(view -> materialDatePickerBegin.show(getChildFragmentManager(), "DATE_PICKER_BEGIN"));
        b.lyDateFinal.setOnClickListener(view -> materialDatePickerfinal.show(getChildFragmentManager(), "DATE_PICKER_FINAL"));
        materialDatePickerBegin.addOnPositiveButtonClickListener(selection -> {
            dateBegin = dateSet(selection, b.tvDateBeginning);
            preferences.setDateBegin(dateBegin);
        });
        materialDatePickerfinal.addOnPositiveButtonClickListener(selection -> {
            dateFinal = dateSet(selection, b.tvDateFinal);
            preferences.setDateFinal(dateFinal);
        });

        // Boto timePicker, mostra l'hora seleccionada
        b.lyTimeBegin.setOnClickListener(view -> mTimePickerBegin.show(getChildFragmentManager(), "TIME_PICKER_BEGIN"));
        b.lyTimeFinal.setOnClickListener(view -> mTimePickerFinal.show(getChildFragmentManager(), "TIME_PICKER_FINAL"));
        mTimePickerBegin.addOnPositiveButtonClickListener(view -> {
            timeBegin = timeSet(mTimePickerBegin.getHour(), mTimePickerBegin.getMinute(), b.tvTimeBeginning);
            preferences.setTimeBegin(timeBegin);
        });
        mTimePickerFinal.addOnPositiveButtonClickListener(view -> {
            timeFinal = timeSet(mTimePickerFinal.getHour(), mTimePickerFinal.getMinute(), b.tvTimeFinal);
            preferences.setTimeFinal(timeFinal);
        });

        // Spinner població
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.villages_array, R.layout.spinner_item);
        b.spinnerVillage.setAdapter(adapter);

        // Boto llista cuiners disponibles
        b.tvCook.setOnClickListener(view -> frag.replaceFragment(R.id.user_container, CookListClient.newInstance(), "cookListFrag"));

        // Rebem el cuiners
        getParentFragmentManager().setFragmentResultListener("keyChooseCook", this, new FragmentResultListener() {
            /**
             * Rebem les dades passades per el
             * @param requestKey Mateixa clau que s'ha donat al Bundle
             * @param result Bundle
             * @author Eduard Masjuan
             */
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                // Guardem la dada en una variable
                chooseCook = result.getString("keyNameUser");
                b.tvCook.setText(chooseCook);
            }
        });

        // Boto per fer la reserva
        b.bttReservation.setOnClickListener(view -> postReservation(dataReservation()));

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
        inflater.inflate(R.menu.menu_empty_text_reservation, menu);
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
        if (item.getItemId() == R.id.action_clean) {
            textEmpty();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Mètode que ens fa format de l'hora
     *
     * @param newHour   int hora
     * @param newMinute int minut
     * @param text      Textview, text on es mostrarà l'hora
     * @author Eduard Masjuan
     */
    @NonNull
    private String timeSet(int newHour, int newMinute, @NonNull MaterialTextView text) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, newHour);
        cal.set(Calendar.MINUTE, newMinute);
        cal.set(Calendar.SECOND, newMinute);
        cal.setLenient(false);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        String timeSelect = sdf.format(cal.getTime());
        text.setText(timeSelect);

        return timeSelect;
    }

    /**
     * Mètode que ens fa format a la data
     *
     * @param ml   long, temps actual del calendari
     * @param text Textview, text on es mostrarà la data
     * @author Eduard Masjuan
     */
    @NonNull
    private String dateSet(long ml, @NonNull MaterialTextView text) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(ml);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dataSelect = sdf.format(cal.getTime());
        text.setText(dataSelect);

        return dataSelect;
    }

    /**
     * Interfície que observa els canvis de text
     */
    private final TextWatcher textWatcher = new TextWatcher() {

        /**
         * Es crida quan es rep el nou caràcter inserit però encara no es mostra en pantalla
         */
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        /**
         * Es crida quan ha acabat de canviar el text
         * Si en l'ArrayList conte el poble introduït per l'usuari, enviarà les dades Data i
         * disponibilitat necessari per mostrar el llistat dels cuiners
         */
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            // Capturem la escriptura
            villageInput = String.valueOf(b.spinnerVillage.getText());
            String ofertPay = String.valueOf(b.inputPay.getText());
            comensales = String.valueOf(b.inputComensales.getText());

            String cookInput = String.valueOf(b.tvCook.getText());

            dateBegin = String.valueOf(b.tvDateBeginning.getText());
            dateFinal = String.valueOf(b.tvDateFinal.getText());
            timeBegin = String.valueOf(b.tvTimeBeginning.getText());
            timeFinal = String.valueOf(b.tvTimeFinal.getText());


            if (!dateBegin.isEmpty() && !dateFinal.isEmpty() && !timeBegin.isEmpty() && !timeFinal.isEmpty()) {
                b.spinnerVillage.setEnabled(true);
                // b.lyTime.setBackgroundResource(R.color.white);
            }

            if (Arrays.asList(villageArray).contains(villageInput)) {
                b.spinnerVillage.dismissDropDown();
                //method.closeKeyboard(b.spinnerVillage, requireActivity());
                b.tvCook.setEnabled(true);

                // Enviem les dades Data i Disponibilitat
                dataBundle(villageInput);
            }

            // Si no esta buit
            boolean cookValid = !cookInput.isEmpty();
            b.inputComensales.setEnabled(cookValid);

            if (cookValid) {
                getTarifa(cookInput);
            } else if (cookInput.equals("")) {
                b.tvTarifa.setText("");
            }

            boolean comensalesValid = !comensales.isEmpty();
            b.bttReservation.setEnabled(comensalesValid);

            // Calcula el preu de la reserva
            if (comensalesValid) {
                calculTotal = tarifaCook.longValue() * Long.parseLong(comensales) * hores();
            }

        }

        /**
         * Es crida després de que s'hagi canviat el text d'un camp i al clicar el boto Done del teclat
         * Si en l'ArrayList conte el poble introduït per l'usuari, portarà a terme unes accions
         */
        @Override
        public void afterTextChanged(Editable editable) {

            // Al clicar l'acció del teclat (done)
            b.spinnerVillage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                    if (!Arrays.asList(villageArray).contains(villageInput)) {
                        b.spinnerVillage.dismissDropDown();
                        b.spinnerVillage.setText("");
                        Toast.makeText(getActivity(), getResources().getString(R.string.txt_cook_dispo_send4), Toast.LENGTH_SHORT).show();
                    } else {
                        // Amaga el teclat virtual
                        method.closeKeyboard(textView, requireActivity());
                        b.spinnerVillage.dismissDropDown();

                        // Enviem les dades Data i Disponibilitat
                        dataBundle(villageInput);
                    }
                    return true;
                }
            });

            b.inputComensales.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                    String calcul = String.valueOf(calculTotal);

                    b.inputPay.setText(calcul + " €");
                    method.closeKeyboard(textView, requireActivity());

                    return true;
                }
            });
        }
    };

    /**
     * Calcula la diferencia entre les hores d'inici i final d'una reserva
     *
     * @return long diferencia hores
     */
    private long hores() {

        String horaInici = String.valueOf(b.tvTimeBeginning.getText());
        String horaFinal = String.valueOf(b.tvTimeFinal.getText());

        int indiceInici = 6 > horaInici.length() ? 0 : horaInici.length() - 6;
        int indiceFinal = 6 > horaFinal.length() ? 0 : horaFinal.length() - 6;

        String horaIniciCalcul = horaInici.substring(0, indiceInici);
        String horaFinalCalcul = horaFinal.substring(0, indiceFinal);

        long lhorainici = Long.parseLong(horaIniciCalcul);
        long lhoraifinal = Long.parseLong(horaFinalCalcul);

        return Math.abs(lhorainici - lhoraifinal);
    }

    /**
     * Guarda i envia la disponibilitat i la població
     *
     * @param village String, població de la reserva
     * @author Eduard Masjuan
     */
    private void dataBundle(String village) {
        Bundle dataSend = new Bundle();
        dataSend.putString("keyVillage", village);

        getParentFragmentManager().setFragmentResult("keyCook", dataSend);
    }

    /**
     * Mètode que ens retorna la dara i hora en format Timestampa
     *
     * @param date String, data de la reserva
     * @param time String, hora de la reserva
     * @return Timestamp
     * @author Eduard Masjuan
     */
    private Timestamp timesStamp(String date, String time) {

        Timestamp dateTime = null;
        String mDateBegin = date + "T" + time;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());

        try {
            Date mDate = df.parse(mDateBegin);
            if (mDate != null) {
                dateTime = new Timestamp(mDate.getTime());
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateTime;
    }

    /**
     * Mètode on realitza una petició POST.
     * Agrega noves reserves
     *
     * @param reservation Objecte de la classe Reservation
     * @author Eduard Masjuan
     */
    public void postReservation(Reservation reservation) {
        //Call<String> reservat = apiGlobal.apiClientCert(getActivity(), preferences.getToken()).postReservaClient(reservation);
        Call<String> reservat = apiGlobal.apiClient(preferences.getToken()).postReservaClient(reservation);

        reservat.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (apiCodes.codeHttp(response.code())) {
                            preferences.cleanDateTime();
                            Toast.makeText(getActivity(), "Reserva completada!", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(getActivity(), "Ya hay una reserva con esa fecha y hora", Toast.LENGTH_SHORT).show();
                    apiCodes.codeHttp(response.code());
                }
            }

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

    /**
     * Dades de l'objecte Reservation
     *
     * @return Retorna l'objecte Reservation amb les dades de la reserva
     * @author Eduard Masjuan
     */
    @NonNull
    private Reservation dataReservation() {
        Reservation reservation = new Reservation();

        String statusReservation = "pendiente";
        reservation.setEstado(statusReservation);
        reservation.setCliente(preferences.getUsername());
        reservation.setChef(chooseCook);
        reservation.setIncio(timesStamp(dateBegin, timeBegin));
        reservation.setFin(timesStamp(dateFinal, timeFinal));
        reservation.setComensales(Long.parseLong(comensales));
        //reservation.setPrecio(BigDecimal.valueOf(0));

        return reservation;
    }

    /**
     * Mètode on realitza una petició GET. Retorna la tarifa d'un chef
     *
     * @param username , String usuari del chef
     * @author Eduard Masjuan
     */
    public void getTarifa(String username) {
       // Call<Tarifa> mTarifa = apiGlobal.apiClientCert(getActivity(), preferences.getToken()).getTarifaUser(username);
        Call<Tarifa> mTarifa = apiGlobal.apiClient(preferences.getToken()).getTarifaUser(username);

        mTarifa.enqueue(new Callback<Tarifa>() {
            /**
             * Es crida si ja una resposta HTTPS correcte
             * @param call Sol.licita al API les dades
             * @param response  Obtenir les dades
             */
            @Override
            public void onResponse(@NonNull Call<Tarifa> call, @NonNull Response<Tarifa> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        Tarifa tarifa = response.body();

                        tarifaCook = tarifa.getPrecioHora();
                        b.tvTarifa.setText(tarifaCook + "€ p/h");


                    } else {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //Toast.makeText(getActivity(), "Codi:" + response.code() + getResources().getString(R.string.codigo_401), Toast.LENGTH_SHORT).show();
                    apiCodes.codeHttp(response.code());
                }
            }

            /**
             * Es produeix una excepció de xarxa en la comunicació amb el servidor o una excepció en la gestió de la sol·licitud
             * @param call Sol·licita al API les dades
             * @param t Captura l’excepció
             */
            @Override
            public void onFailure(@NonNull Call<Tarifa> call, @NonNull Throwable t) {
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
     * Un cop destruït l'estat del fragment al restaurar la vista ens mostra la data i hora que teníem
     *
     * @param view               Vista
     * @param savedInstanceState Bundle
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        b.tvDateBeginning.setText(preferences.getDateBegin());
        b.tvDateFinal.setText(preferences.getDateFinal());
        b.tvTimeBeginning.setText(preferences.getTimeBegin());
        b.tvTimeFinal.setText(preferences.getTimeFinal());
    }

    /**
     * Camps de text buits
     */
    private void textEmpty() {
        b.tvTarifa.setEnabled(false);
        b.tvDateBeginning.setText("");
        b.tvDateFinal.setText("");
        b.tvTimeBeginning.setText("");
        b.tvTimeFinal.setText("");
        b.spinnerVillage.setText("");
        b.tvCook.setText("");
        b.tvTarifa.setText("");
        b.inputComensales.setText("");
        b.inputPay.setText("");
    }

}