package com.example.d308_vacation_planner;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.d308_vacation_planner.model.Vacation;
import com.example.d308_vacation_planner.ui.fragment.UpdateVacationFragment;
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
}
