package com.example.d308_vacation_planner.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.d308_vacation_planner.model.Excursion;
import com.example.d308_vacation_planner.repository.ExcursionRepository;
import java.util.List;

public class ExcursionViewModel extends AndroidViewModel {

    private final ExcursionRepository repository;
    private final LiveData<List<Excursion>> allExcursions;

    public ExcursionViewModel(@NonNull Application application) {
        super(application);
        repository = new ExcursionRepository(application);
        allExcursions = repository.getAllExcursions();
    }

    public void insert(Excursion excursion) {
        repository.insert(excursion);
    }

    public void update(Excursion excursion) {
        repository.update(excursion);
    }

    public void delete(Excursion excursion) {
        repository.delete(excursion);
    }

    public LiveData<List<Excursion>> getAllExcursions() {
        return allExcursions;
    }

    public LiveData<List<Excursion>> getExcursionsForVacation(int vacationId) {
        return repository.getExcursionsForVacation(vacationId);
    }
}
