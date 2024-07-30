package com.example.d308_vacation_planner;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.d308_vacation_planner.model.Excursion;
import com.example.d308_vacation_planner.model.Vacation;
import com.example.d308_vacation_planner.ui.fragment.UpdateExcursionFragment;
import com.example.d308_vacation_planner.ui.fragment.UpdateVacationFragment;
import com.example.d308_vacation_planner.ui.fragment.VacationDetailsFragment;
import com.example.d308_vacation_planner.ui.fragment.VacationListFragment;
import com.example.d308_vacation_planner.ui.fragment.ExcursionListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button buttonViewVacations = findViewById(R.id.button_view_vacations);
        Button buttonViewExcursions = findViewById(R.id.button_view_excursions);

        buttonViewVacations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToVacationList();
            }
        });

        buttonViewExcursions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToExcursionList();
            }
        });
    }

    private void navigateToVacationList() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new VacationListFragment())
                .addToBackStack(null)
                .commit();
    }

    private void navigateToExcursionList() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new ExcursionListFragment())
                .addToBackStack(null)
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

    public void navigateToUpdateExcursion(Excursion excursion, String vacationStartDate, String vacationEndDate) {
        UpdateExcursionFragment fragment = UpdateExcursionFragment.newInstance(excursion, vacationStartDate, vacationEndDate);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void navigateToAddExcursion(int vacationId, String vacationStartDate, String vacationEndDate) {
        Excursion newExcursion = new Excursion(vacationId, "", "");
        UpdateExcursionFragment fragment = UpdateExcursionFragment.newInstance(newExcursion, vacationStartDate, vacationEndDate);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
