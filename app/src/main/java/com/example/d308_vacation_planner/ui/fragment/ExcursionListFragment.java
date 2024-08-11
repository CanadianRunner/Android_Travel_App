package com.example.d308_vacation_planner.ui.fragment;

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
import android.widget.TextView;
import com.example.d308_vacation_planner.MainActivity;
import com.example.d308_vacation_planner.R;
import com.example.d308_vacation_planner.model.Excursion;
import com.example.d308_vacation_planner.model.Vacation;
import com.example.d308_vacation_planner.ui.adapter.ExcursionAdapter;
import com.example.d308_vacation_planner.viewmodel.ExcursionViewModel;
import com.example.d308_vacation_planner.viewmodel.VacationViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcursionListFragment extends Fragment {

    private static final String ARG_VACATION_ID = "vacation_id";
    private int vacationId;

    private ExcursionViewModel excursionViewModel;
    private VacationViewModel vacationViewModel;
    private TextView textViewNoExcursions;
    private Map<Integer, Vacation> vacationMap = new HashMap<>();

    public static ExcursionListFragment newInstance(int vacationId) {
        ExcursionListFragment fragment = new ExcursionListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_VACATION_ID, vacationId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            vacationId = getArguments().getInt(ARG_VACATION_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_excursion_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_excursions);
        textViewNoExcursions = view.findViewById(R.id.text_view_no_excursions);
        FloatingActionButton fabAddExcursion = view.findViewById(R.id.fab_add_excursion);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        excursionViewModel = new ViewModelProvider(this).get(ExcursionViewModel.class);
        vacationViewModel = new ViewModelProvider(this).get(VacationViewModel.class);

        final ExcursionAdapter adapter = new ExcursionAdapter(excursionViewModel, "", "");
        recyclerView.setAdapter(adapter);

        vacationViewModel.getAllVacations().observe(getViewLifecycleOwner(), new Observer<List<Vacation>>() {
            @Override
            public void onChanged(List<Vacation> vacations) {
                for (Vacation vacation : vacations) {
                    vacationMap.put(vacation.getId(), vacation);
                }
            }
        });

        excursionViewModel.getExcursionsForVacation(vacationId).observe(getViewLifecycleOwner(), new Observer<List<Excursion>>() {
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

        adapter.setOnItemClickListener(excursion -> {
            Vacation associatedVacation = vacationMap.get(excursion.getVacationId());
            if (associatedVacation != null) {
                String vacationStartDate = associatedVacation.getStartDate();
                String vacationEndDate = associatedVacation.getEndDate();
                ((MainActivity) getActivity()).navigateToUpdateExcursion(excursion, vacationStartDate, vacationEndDate);
            }
        });

        fabAddExcursion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).navigateToAddExcursion(vacationId, "01/01/24", "12/31/24");
            }
        });

        return view;
    }
}
