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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.d308_vacation_planner.NotificationReceiver;
import com.example.d308_vacation_planner.R;
import com.example.d308_vacation_planner.model.Vacation;
import com.example.d308_vacation_planner.viewmodel.VacationViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UpdateVacationFragment extends Fragment {

    private static final String ARG_VACATION = "arg_vacation";
    private static final String DATE_FORMAT = "MM/dd/yy";

    private Vacation vacation;
    private VacationViewModel vacationViewModel;
    private EditText editTextTitle, editTextHotel, editTextStartDate, editTextEndDate;
    private Button buttonSave;

    public static UpdateVacationFragment newInstance(Vacation vacation) {
        UpdateVacationFragment fragment = new UpdateVacationFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_VACATION, vacation);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            vacation = (Vacation) getArguments().getSerializable(ARG_VACATION);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_vacation, container, false);

        initializeViews(view);
        vacationViewModel = new ViewModelProvider(this).get(VacationViewModel.class);
        populateFields();

        buttonSave.setOnClickListener(v -> saveVacation());
        setupDatePickers();
        return view;
    }

    private void initializeViews(View view) {
        editTextTitle = view.findViewById(R.id.edit_text_title);
        editTextHotel = view.findViewById(R.id.edit_text_hotel);
        editTextStartDate = view.findViewById(R.id.edit_text_start_date);
        editTextEndDate = view.findViewById(R.id.edit_text_end_date);
        buttonSave = view.findViewById(R.id.button_save);
    }

    private void populateFields() {
        if (vacation != null) {
            editTextTitle.setText(vacation.getTitle());
            editTextHotel.setText(vacation.getHotel());
            editTextStartDate.setText(vacation.getStartDate());
            editTextEndDate.setText(vacation.getEndDate());
        }
    }

    private void saveVacation() {
        String title = editTextTitle.getText().toString().trim();
        String hotel = editTextHotel.getText().toString().trim();
        String startDate = editTextStartDate.getText().toString().trim();
        String endDate = editTextEndDate.getText().toString().trim();

        if (!areFieldsValid(title, hotel, startDate, endDate)) {
            return;
        }

        // Create or update the vacation object
        if (vacation == null) {
            vacation = new Vacation(title, startDate, endDate, hotel);
            vacationViewModel.insert(vacation);
        } else {
            vacation.setTitle(title);
            vacation.setHotel(hotel);
            vacation.setStartDate(startDate);
            vacation.setEndDate(endDate);
            vacationViewModel.update(vacation);
        }

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        try {
            Date start = sdf.parse(startDate);
            long startMillis = start.getTime();
            scheduleNotification("Vacation Alert", "Vacation: " + title + " starts on " + startDate, startMillis);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Navigate back to the vacation list after saving
        requireActivity().getSupportFragmentManager().popBackStack();
    }

    private boolean areFieldsValid(String title, String hotel, String startDate, String endDate) {
        if (title.isEmpty() || hotel.isEmpty() || startDate.isEmpty() || endDate.isEmpty()) {
            Toast.makeText(getContext(), "Please fill out all fields!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!isValidDateRange(startDate, endDate)) {
            Toast.makeText(getContext(), "Vacation end date must be after start date", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void setupDatePickers() {
        editTextStartDate.setOnClickListener(v -> showDatePickerDialog((EditText) v));
        editTextEndDate.setOnClickListener(v -> showDatePickerDialog((EditText) v));
    }

    private void showDatePickerDialog(final EditText editText) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (view, year, month, dayOfMonth) -> {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.US);
                    editText.setText(sdf.format(calendar.getTime()));
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private boolean isValidDateRange(String startDate, String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        try {
            Date start = sdf.parse(startDate);
            Date end = sdf.parse(endDate);
            return end != null && !end.before(start);
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Invalid date format", Toast.LENGTH_SHORT).show();
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
