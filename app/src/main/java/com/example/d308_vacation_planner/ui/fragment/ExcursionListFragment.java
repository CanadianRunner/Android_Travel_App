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

import com.example.d308_vacation_planner.R;
import com.example.d308_vacation_planner.model.Excursion;
import com.example.d308_vacation_planner.ui.adapter.ExcursionAdapter;
import com.example.d308_vacation_planner.viewmodel.ExcursionViewModel;

import java.util.List;

public class ExcursionListFragment extends Fragment {

    private ExcursionViewModel excursionViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_excursion_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_excursions);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        final ExcursionAdapter adapter = new ExcursionAdapter();
        recyclerView.setAdapter(adapter);

        excursionViewModel = new ViewModelProvider(this).get(ExcursionViewModel.class);
        adapter.setExcursionViewModel(excursionViewModel);

        excursionViewModel.getAllExcursions().observe(getViewLifecycleOwner(), new Observer<List<Excursion>>() {
            @Override
            public void onChanged(List<Excursion> excursions) {
                adapter.setExcursions(excursions);
            }
        });

        adapter.setOnItemClickListener(excursion -> {
            // Implement click logic here
        });

        return view;
    }
}
