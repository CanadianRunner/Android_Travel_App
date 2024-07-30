package com.example.d308_vacation_planner;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.d308_vacation_planner.model.Excursion;
import com.example.d308_vacation_planner.model.Vacation;
import com.example.d308_vacation_planner.ui.fragment.UpdateExcursionFragment;
import com.example.d308_vacation_planner.ui.fragment.UpdateVacationFragment;
import com.example.d308_vacation_planner.ui.fragment.VacationDetailsFragment;
import com.example.d308_vacation_planner.ui.fragment.VacationListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new VacationListFragment())
                .commit();
    }

    public void navigateToUpdateVacation(Vacation vacation) {
        UpdateVacationFragment fragment = UpdateVacationFragment.newInstance(vacation);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void navigateToVacationDetails(Vacation vacation) {
        VacationDetailsFragment fragment = VacationDetailsFragment.newInstance(vacation);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void navigateToUpdateExcursion(Excursion excursion) {
        UpdateExcursionFragment fragment = UpdateExcursionFragment.newInstance(excursion);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void navigateToAddExcursion(int vacationId) {
        Excursion newExcursion = new Excursion(vacationId, "", "");
        UpdateExcursionFragment fragment = UpdateExcursionFragment.newInstance(newExcursion);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
