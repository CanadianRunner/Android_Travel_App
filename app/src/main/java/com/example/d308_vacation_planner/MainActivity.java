package com.example.d308_vacation_planner;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.d308_vacation_planner.model.Excursion;
import com.example.d308_vacation_planner.model.Vacation;
import com.example.d308_vacation_planner.ui.fragment.UpdateExcursionFragment;
import com.example.d308_vacation_planner.ui.fragment.UpdateVacationFragment;
import com.example.d308_vacation_planner.ui.fragment.VacationDetailsFragment;
import com.example.d308_vacation_planner.ui.fragment.VacationListFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ImageView homeImageView;
    private TextView homeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button buttonViewVacations = findViewById(R.id.button_view_vacations);
        homeImageView = findViewById(R.id.home_image);
        homeTextView = findViewById(R.id.text_home);

        buttonViewVacations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "View Vacations button clicked");
                navigateToVacationList();
            }
        });
    }

    private void hideHomeElements() {
        homeImageView.setVisibility(View.GONE);
        homeTextView.setVisibility(View.GONE);
    }

    private void navigateToVacationList() {
        Log.d(TAG, "Navigating to VacationListFragment");
        hideHomeElements();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new VacationListFragment())
                .addToBackStack(null)
                .commit();
        Log.d(TAG, "VacationListFragment transaction committed");
    }

    public void navigateToUpdateVacation(Vacation vacation) {
        Log.d(TAG, vacation == null ? "Navigating to AddVacationFragment" : "Navigating to UpdateVacationFragment with Vacation: " + vacation.getTitle());
        hideHomeElements();
        UpdateVacationFragment fragment = UpdateVacationFragment.newInstance(vacation);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
        Log.d(TAG, "UpdateVacationFragment transaction committed");
    }

    public void navigateToVacationDetails(Vacation vacation) {
        if (vacation == null) {
            Log.e(TAG, "Vacation is null in navigateToVacationDetails");
            return;
        }
        Log.d(TAG, "Navigating to VacationDetailsFragment with Vacation: " + vacation.getTitle());
        hideHomeElements();
        VacationDetailsFragment fragment = VacationDetailsFragment.newInstance(vacation);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
        Log.d(TAG, "VacationDetailsFragment transaction committed");
    }

    public void navigateToUpdateExcursion(Excursion excursion, String vacationStartDate, String vacationEndDate) {
        if (excursion == null) {
            Log.e(TAG, "Excursion is null in navigateToUpdateExcursion");
            return;
        }
        Log.d(TAG, "Navigating to UpdateExcursionFragment with Excursion: " + excursion.getExcursionName());
        hideHomeElements();
        UpdateExcursionFragment fragment = UpdateExcursionFragment.newInstance(excursion, vacationStartDate, vacationEndDate);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
        Log.d(TAG, "UpdateExcursionFragment transaction committed");
    }

    public void navigateToAddExcursion(int vacationId, String vacationStartDate, String vacationEndDate) {
        Log.d(TAG, "Navigating to AddExcursionFragment");
        hideHomeElements();
        Excursion newExcursion = new Excursion(vacationId, "", "");
        UpdateExcursionFragment fragment = UpdateExcursionFragment.newInstance(newExcursion, vacationStartDate, vacationEndDate);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
        Log.d(TAG, "AddExcursionFragment transaction committed");
    }

//    @Override
//    public void onBackPressed() {
//        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
//            homeImageView.setVisibility(View.VISIBLE);
//            homeTextView.setVisibility(View.VISIBLE);
//        }
//        super.onBackPressed();
//    }
}
