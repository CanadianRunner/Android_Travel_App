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
    private OnItemClickListener listener;

    @NonNull
    @Override
    public VacationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vacation_item, parent, false);
        return new VacationHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VacationHolder holder, int position) {
        Vacation currentVacation = vacations.get(position);
        holder.textViewVacationName.setText(currentVacation.getTitle());
        holder.textViewHotel.setText("Hotel: " + currentVacation.getHotel());
        holder.textViewStartDate.setText("Start Date: " + currentVacation.getStartDate());
        holder.textViewEndDate.setText("End Date: " + currentVacation.getEndDate());

        holder.buttonEdit.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEditClick(currentVacation);
            }
        });

        holder.buttonDelete.setOnClickListener(v -> {
            if (vacationViewModel != null) {
                vacationViewModel.delete(currentVacation);
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

    public void setVacationViewModel(VacationViewModel viewModel) {
        this.vacationViewModel = viewModel;
    }

    class VacationHolder extends RecyclerView.ViewHolder {
        private TextView textViewVacationName;
        private TextView textViewHotel;
        private TextView textViewStartDate;
        private TextView textViewEndDate;
        private Button buttonEdit;
        private Button buttonDelete;

        public VacationHolder(View itemView) {
            super(itemView);
            textViewVacationName = itemView.findViewById(R.id.text_view_vacation_name);
            textViewHotel = itemView.findViewById(R.id.text_view_hotel);
            textViewStartDate = itemView.findViewById(R.id.text_view_start_date);
            textViewEndDate = itemView.findViewById(R.id.text_view_end_date);
            buttonEdit = itemView.findViewById(R.id.button_edit);
            buttonDelete = itemView.findViewById(R.id.button_delete);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(vacations.get(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Vacation vacation);
        void onEditClick(Vacation vacation);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
