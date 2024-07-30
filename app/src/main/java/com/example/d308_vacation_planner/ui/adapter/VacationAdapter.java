package com.example.d308_vacation_planner.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.d308_vacation_planner.MainActivity;
import com.example.d308_vacation_planner.R;
import com.example.d308_vacation_planner.model.Vacation;
import com.example.d308_vacation_planner.viewmodel.VacationViewModel;

import java.util.ArrayList;
import java.util.List;

public class VacationAdapter extends RecyclerView.Adapter<VacationAdapter.VacationHolder> {

    private List<Vacation> vacations = new ArrayList<>();
    private VacationViewModel vacationViewModel;
    private OnItemClickListener listener;

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
        holder.textViewTitle.setText(currentVacation.getTitle());
        holder.textViewHotel.setText(currentVacation.getHotel());
        holder.textViewStartDate.setText(currentVacation.getStartDate());
        holder.textViewEndDate.setText(currentVacation.getEndDate());

        holder.buttonDelete.setOnClickListener(v -> {
            vacationViewModel.delete(currentVacation);
        });

        holder.buttonEdit.setOnClickListener(v -> {
            ((MainActivity) holder.itemView.getContext()).navigateToUpdateVacation(currentVacation);
        });

        holder.itemView.setOnClickListener(v -> {
            if (listener != null && position != RecyclerView.NO_POSITION) {
                listener.onItemClick(currentVacation);
            }
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

    public interface OnItemClickListener {
        void onItemClick(Vacation vacation);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    static class VacationHolder extends RecyclerView.ViewHolder {
        private final TextView textViewTitle;
        private final TextView textViewHotel;
        private final TextView textViewStartDate;
        private final TextView textViewEndDate;
        private final Button buttonDelete;
        private final Button buttonEdit;

        public VacationHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_vacation_name);
            textViewHotel = itemView.findViewById(R.id.text_view_hotel);
            textViewStartDate = itemView.findViewById(R.id.text_view_start_date);
            textViewEndDate = itemView.findViewById(R.id.text_view_end_date);
            buttonDelete = itemView.findViewById(R.id.button_delete);
            buttonEdit = itemView.findViewById(R.id.button_edit);
        }
    }
}
