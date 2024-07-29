package com.example.d308_vacation_planner.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.d308_vacation_planner.R;
import com.example.d308_vacation_planner.model.Vacation;
import com.example.d308_vacation_planner.viewmodel.VacationViewModel;

import java.util.ArrayList;
import java.util.List;

public class VacationAdapter extends RecyclerView.Adapter<VacationAdapter.VacationHolder> {

    private List<Vacation> vacations = new ArrayList<>();
    private VacationViewModel vacationViewModel;

    @NonNull
    @Override
    public VacationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vacation_item, parent, false);
        return new VacationHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VacationHolder holder, int position) {
        Vacation currentVacation = vacations.get(position);
        holder.textViewVacationName.setText(currentVacation.getVacationName());
        holder.textViewHotel.setText(currentVacation.getHotel());

        holder.buttonDelete.setOnClickListener(v -> {
            vacationViewModel.delete(currentVacation);
        });
    }

    @Override
    public int getItemCount() {
        return vacations.size();
    }

    public void setVacations(List<Vacation> vacations) {
        this.vacations = vacations;
        notifyDataSetChanged();
    }

    public void setVacationViewModel(VacationViewModel vacationViewModel) {
        this.vacationViewModel = vacationViewModel;
    }

    class VacationHolder extends RecyclerView.ViewHolder {
        private TextView textViewVacationName;
        private TextView textViewHotel;
        private Button buttonDelete;

        public VacationHolder(View itemView) {
            super(itemView);
            textViewVacationName = itemView.findViewById(R.id.text_view_vacation_name);
            textViewHotel = itemView.findViewById(R.id.text_view_hotel);
            buttonDelete = itemView.findViewById(R.id.button_delete);
        }
    }
}
