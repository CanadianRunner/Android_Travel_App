package com.example.d308_vacation_planner.ui.fragment;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.d308_vacation_planner.NotificationReceiver;
import com.example.d308_vacation_planner.R;
import com.example.d308_vacation_planner.model.Excursion;
import com.example.d308_vacation_planner.viewmodel.ExcursionViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UpdateExcursionFragment extends Fragment {

    private static final String TAG = "UpdateExcursionFragment";
    private static final String ARG_EXCURSION = "arg_excursion";
    private static final String ARG_VACATION_START_DATE = "arg_vacation_start_date";
    private static final String ARG_VACATION_END_DATE = "arg_vacation_end_date";
    private static final String DATE_FORMAT = "MM/dd/yy";

    private Excursion excursion;
    private ExcursionViewModel excursionViewModel;
    private EditText editTextTitle, editTextDate;
    private Button buttonSave;

    private String vacationStartDate;
    private String vacationEndDate;

    public static UpdateExcursionFragment newInstance(Excursion excursion, String vacationStartDate, String vacationEndDate) {
        UpdateExcursionFragment fragment = new UpdateExcursionFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_EXCURSION, excursion);
        args.putString(ARG_VACATION_START_DATE, vacationStartDate);
        args.putString(ARG_VACATION_END_DATE, vacationEndDate);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            excursion = (Excursion) getArguments().getSerializable(ARG_EXCURSION);
            vacationStartDate = getArguments().getString(ARG_VACATION_START_DATE);
            vacationEndDate = getArguments().getString(ARG_VACATION_END_DATE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_excursion, container, false);

        editTextTitle = view.findViewById(R.id.edit_text_excursion_title);
        editTextDate = view.findViewById(R.id.edit_text_excursion_date);
        buttonSave = view.findViewById(R.id.button_save_excursion);

        if (excursion != null) {
            editTextTitle.setText(excursion.getExcursionName());
            editTextDate.setText(excursion.getDate());
        }

        excursionViewModel = new ViewModelProvider(this).get(ExcursionViewModel.class);

        buttonSave.setOnClickListener(v -> {
            String title = editTextTitle.getText().toString().trim();
            String date = editTextDate.getText().toString().trim();

            if (title.isEmpty() || date.isEmpty()) {
                Toast.makeText(getContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!isValidDate(date)) {
                Toast.makeText(getContext(), "Invalid date format", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!isDateWithinVacation(date, vacationStartDate, vacationEndDate)) {
                Toast.makeText(getContext(), "Excursion date must be within vacation dates", Toast.LENGTH_SHORT).show();
                return;
            }

            if (excursion.getId() == 0) {
                excursion = new Excursion(excursion.getVacationId(), title, date);
                excursionViewModel.insert(excursion);
            } else {
                excursion.setExcursionName(title);
                excursion.setDate(date);
                excursionViewModel.update(excursion);
            }

            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.US);
            try {
                Date excursionDate = sdf.parse(date);
                long excursionMillis = excursionDate.getTime();
                scheduleNotification("Excursion Alert", "Excursion: " + title + " on " + date, excursionMillis);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            getActivity().onBackPressed();
        });

        editTextDate.setOnClickListener(v -> showDatePickerDialog((EditText) v));

        return view;
    }

    private void showDatePickerDialog(final EditText editText) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.US);
            editText.setText(sdf.format(calendar.getTime()));
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private boolean isValidDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        sdf.setLenient(false);
        try {
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private boolean isDateWithinVacation(String date, String startDate, String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        try {
            Date excursionDate = sdf.parse(date);
            Date vacationStartDate = sdf.parse(startDate);
            Date vacationEndDate = sdf.parse(endDate);

            Log.d(TAG, "Excursion Date: " + excursionDate);
            Log.d(TAG, "Vacation Start Date: " + vacationStartDate);
            Log.d(TAG, "Vacation End Date: " + vacationEndDate);

            return !excursionDate.before(vacationStartDate) && !excursionDate.after(vacationEndDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void scheduleNotification(String title, String message, long triggerAtMillis) {
        Intent intent = new Intent(getContext(), NotificationReceiver.class);
        intent.putExtra("title", title);
        intent.putExtra("message", message);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
    }
}
