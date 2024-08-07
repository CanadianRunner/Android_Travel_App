package com.example.d308_vacation_planner.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.d308_vacation_planner.model.Vacation;
import com.example.d308_vacation_planner.repository.VacationRepository;

import java.util.List;

public class VacationViewModel extends AndroidViewModel {
    private final VacationRepository repository;
    private final LiveData<List<Vacation>> allVacations;

    public VacationViewModel(@NonNull Application application) {
        super(application);
        repository = new VacationRepository(application);
        allVacations = repository.getAllVacations();
    }

    public void insert(Vacation vacation) {
        repository.insert(vacation);
    }

    public void update(Vacation vacation) {
        repository.update(vacation);
    }

    public void delete(Vacation vacation) {
        repository.delete(vacation);
    }

    public LiveData<List<Vacation>> getAllVacations() {
        return allVacations;
    }
}
