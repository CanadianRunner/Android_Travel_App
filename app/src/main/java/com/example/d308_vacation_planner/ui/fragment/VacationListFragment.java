package com.example.d308_vacation_planner.ui.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.d308_vacation_planner.R;
import com.example.d308_vacation_planner.model.Vacation;
import com.example.d308_vacation_planner.ui.adapter.VacationAdapter;
import com.example.d308_vacation_planner.viewmodel.VacationViewModel;

import java.util.List;

public class VacationListFragment extends Fragment {

    private VacationViewModel vacationViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vacation_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        final VacationAdapter adapter = new VacationAdapter();
        recyclerView.setAdapter(adapter);

        vacationViewModel = new ViewModelProvider(this).get(VacationViewModel.class);
        adapter.setVacationViewModel(vacationViewModel);

        vacationViewModel.getAllVacations().observe(getViewLifecycleOwner(), new Observer<List<Vacation>>() {
            @Override
            public void onChanged(List<Vacation> vacations) {
                adapter.setVacations(vacations);
            }
        });

        return view;
    }
}
