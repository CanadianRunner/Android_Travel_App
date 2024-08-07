package com.example.d308_vacation_planner.ui.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.d308_vacation_planner.MainActivity;
import com.example.d308_vacation_planner.R;
import com.example.d308_vacation_planner.model.Vacation;
import com.example.d308_vacation_planner.ui.adapter.VacationAdapter;
import com.example.d308_vacation_planner.viewmodel.VacationViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.widget.TextView;
import java.util.List;

public class VacationListFragment extends Fragment {

    private VacationViewModel vacationViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vacation_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_vacations);
        TextView textViewNoVacations = view.findViewById(R.id.text_view_no_vacations);
        FloatingActionButton fabAddVacation = view.findViewById(R.id.fab_add_vacation);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        final VacationAdapter adapter = new VacationAdapter();
        recyclerView.setAdapter(adapter);

        vacationViewModel = new ViewModelProvider(this).get(VacationViewModel.class);
        adapter.setVacationViewModel(vacationViewModel);

        vacationViewModel.getAllVacations().observe(getViewLifecycleOwner(), new Observer<List<Vacation>>() {
            @Override
            public void onChanged(List<Vacation> vacations) {
                if (vacations.isEmpty()) {
                    recyclerView.setVisibility(View.GONE);
                    textViewNoVacations.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    textViewNoVacations.setVisibility(View.GONE);
                }
                adapter.setVacations(vacations);
                adapter.notifyDataSetChanged();
            }
        });

        adapter.setOnItemClickListener(new VacationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Vacation vacation) {
                ((MainActivity) getActivity()).navigateToVacationDetails(vacation);
            }

            @Override
            public void onEditClick(Vacation vacation) {
                ((MainActivity) getActivity()).navigateToUpdateVacation(vacation);
            }
        });

        fabAddVacation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).navigateToUpdateVacation(null);
            }
        });

        return view;
    }
}
