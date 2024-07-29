package com.example.d308_vacation_planner.ui.fragment;

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

import com.example.d308_vacation_planner.R;
import com.example.d308_vacation_planner.model.Vacation;
import com.example.d308_vacation_planner.viewmodel.VacationViewModel;

public class UpdateVacationFragment extends Fragment {

    private static final String ARG_VACATION = "arg_vacation";
    private Vacation vacation;
    private VacationViewModel vacationViewModel;
    private EditText editTextTitle, editTextHotel, editTextStartDate, editTextEndDate;

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

        editTextTitle = view.findViewById(R.id.edit_text_title);
        editTextHotel = view.findViewById(R.id.edit_text_hotel);
        editTextStartDate = view.findViewById(R.id.edit_text_start_date);
        editTextEndDate = view.findViewById(R.id.edit_text_end_date);
        Button buttonSave = view.findViewById(R.id.button_save);

        editTextTitle.setText(vacation.getTitle());
        editTextHotel.setText(vacation.getHotel());
        editTextStartDate.setText(vacation.getStartDate());
        editTextEndDate.setText(vacation.getEndDate());

        vacationViewModel = new ViewModelProvider(this).get(VacationViewModel.class);

        buttonSave.setOnClickListener(v -> {
            vacation.setTitle(editTextTitle.getText().toString());
            vacation.setHotel(editTextHotel.getText().toString());
            vacation.setStartDate(editTextStartDate.getText().toString());
            vacation.setEndDate(editTextEndDate.getText().toString());
            vacationViewModel.update(vacation);
            getActivity().onBackPressed();
        });

        return view;
    }
}
