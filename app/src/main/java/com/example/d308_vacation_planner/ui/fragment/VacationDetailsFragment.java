package com.example.d308_vacation_planner.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.d308_vacation_planner.MainActivity;
import com.example.d308_vacation_planner.R;
import com.example.d308_vacation_planner.model.Excursion;
import com.example.d308_vacation_planner.model.Vacation;
import com.example.d308_vacation_planner.ui.adapter.ExcursionAdapter;
import com.example.d308_vacation_planner.viewmodel.ExcursionViewModel;
import java.util.List;

public class VacationDetailsFragment extends Fragment {

    private static final String ARG_VACATION = "arg_vacation";
    private Vacation vacation;
    private ExcursionViewModel excursionViewModel;
    private RecyclerView recyclerView;
    private ExcursionAdapter adapter;
    private Button buttonAddExcursion;
    private Button buttonShareVacation;
    private TextView textViewNoExcursions;
    private TextView vacationTitleTextView;

    public static VacationDetailsFragment newInstance(Vacation vacation) {
        VacationDetailsFragment fragment = new VacationDetailsFragment();
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
        View view = inflater.inflate(R.layout.fragment_vacation_details, container, false);

        vacationTitleTextView = view.findViewById(R.id.vacation_title);
        textViewNoExcursions = view.findViewById(R.id.text_view_no_excursions);
        recyclerView = view.findViewById(R.id.recycler_view_excursions);
        buttonAddExcursion = view.findViewById(R.id.button_add_excursion);
        buttonShareVacation = view.findViewById(R.id.button_share_vacation);

        vacationTitleTextView.setText(vacation.getTitle());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        excursionViewModel = new ViewModelProvider(this).get(ExcursionViewModel.class);
        adapter = new ExcursionAdapter(excursionViewModel, vacation.getStartDate(), vacation.getEndDate());
        recyclerView.setAdapter(adapter);

        excursionViewModel.getExcursionsForVacation(vacation.getId()).observe(getViewLifecycleOwner(), new Observer<List<Excursion>>() {
            @Override
            public void onChanged(List<Excursion> excursions) {
                if (excursions.isEmpty()) {
                    recyclerView.setVisibility(View.GONE);
                    textViewNoExcursions.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    textViewNoExcursions.setVisibility(View.GONE);
                }
                adapter.setExcursions(excursions);
            }
        });

        buttonAddExcursion.setOnClickListener(v -> {
            ((MainActivity) getActivity()).navigateToAddExcursion(vacation.getId(), vacation.getStartDate(), vacation.getEndDate());
        });

        adapter.setOnItemClickListener(new ExcursionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Excursion excursion) {
                ((MainActivity) getActivity()).navigateToUpdateExcursion(excursion, vacation.getStartDate(), vacation.getEndDate());
            }
        });

        buttonShareVacation.setOnClickListener(v -> shareVacationDetails());

        return view;
    }

    private void shareVacationDetails() {
        String vacationDetails = "Vacation: " + vacation.getTitle() + "\n" +
                "Hotel: " + vacation.getHotel() + "\n" +
                "Start Date: " + vacation.getStartDate() + "\n" +
                "End Date: " + vacation.getEndDate() + "\n" +
                "Excursions:\n";

        for (Excursion excursion : adapter.getExcursions()) {
            vacationDetails += " - " + excursion.getExcursionName() + " on " + excursion.getDate() + "\n";
        }

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, vacationDetails);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }
}
