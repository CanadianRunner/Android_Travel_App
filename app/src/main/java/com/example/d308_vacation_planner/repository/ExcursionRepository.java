package com.example.d308_vacation_planner.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.d308_vacation_planner.database.AppDatabase;
import com.example.d308_vacation_planner.database.ExcursionDao;
import com.example.d308_vacation_planner.model.Excursion;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExcursionRepository {

    private final ExcursionDao excursionDao;
    private final ExecutorService executorService;

    public ExcursionRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        excursionDao = database.excursionDao();
        executorService = Executors.newFixedThreadPool(2);
    }

    public void insert(Excursion excursion) {
        executorService.execute(() -> excursionDao.insert(excursion));
    }

    public void update(Excursion excursion) {
        executorService.execute(() -> excursionDao.update(excursion));
    }

    public void delete(Excursion excursion) {
        executorService.execute(() -> excursionDao.delete(excursion));
    }

    public LiveData<List<Excursion>> getExcursionsForVacation(int vacationId) {
        return excursionDao.getExcursionsForVacation(vacationId);
    }

    public LiveData<List<Excursion>> getAllExcursions() {
        return excursionDao.getAllExcursions();
    }
}
