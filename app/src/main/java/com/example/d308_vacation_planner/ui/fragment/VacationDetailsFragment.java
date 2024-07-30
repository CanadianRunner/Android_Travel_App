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
import android.widget.Button;
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

        recyclerView = view.findViewById(R.id.recycler_view_excursions);
        buttonAddExcursion = view.findViewById(R.id.button_add_excursion);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        adapter = new ExcursionAdapter(vacation.getStartDate(), vacation.getEndDate());
        recyclerView.setAdapter(adapter);

        excursionViewModel = new ViewModelProvider(this).get(ExcursionViewModel.class);

        excursionViewModel.getExcursionsForVacation(vacation.getId()).observe(getViewLifecycleOwner(), new Observer<List<Excursion>>() {
            @Override
            public void onChanged(List<Excursion> excursions) {
                adapter.setExcursions(excursions);
            }
        });

        buttonAddExcursion.setOnClickListener(v -> {
            ((MainActivity) getActivity()).navigateToAddExcursion(vacation.getId(), vacation.getStartDate(), vacation.getEndDate());
        });

        return view;
    }
}
