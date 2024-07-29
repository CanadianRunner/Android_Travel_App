package com.example.d308_vacation_planner.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;

import com.example.d308_vacation_planner.database.AppDatabase;
import com.example.d308_vacation_planner.database.VacationDao;
import com.example.d308_vacation_planner.model.Vacation;
import java.util.List;

public class VacationRepository {
    private VacationDao vacationDao;
    private LiveData<List<Vacation>> allVacations;

    public VacationRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        vacationDao = database.vacationDao();
        allVacations = vacationDao.getAllVacations();
    }

    public LiveData<List<Vacation>> getAllVacations() {
        return allVacations;
    }

    public void insert(Vacation vacation) {
        AppDatabase.databaseWriteExecutor.execute(() -> vacationDao.insert(vacation));
    }

    public void update(Vacation vacation) {
        AppDatabase.databaseWriteExecutor.execute(() -> vacationDao.update(vacation));
    }

    public void delete(Vacation vacation) {
        AppDatabase.databaseWriteExecutor.execute(() -> vacationDao.delete(vacation));
    }

    public LiveData<List<Vacation>> getVacations() {
        return allVacations;
    }
}
