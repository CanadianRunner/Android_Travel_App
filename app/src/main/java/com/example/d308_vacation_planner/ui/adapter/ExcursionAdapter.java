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
import com.example.d308_vacation_planner.model.Excursion;
import com.example.d308_vacation_planner.viewmodel.ExcursionViewModel;
import java.util.ArrayList;
import java.util.List;

public class ExcursionAdapter extends RecyclerView.Adapter<ExcursionAdapter.ExcursionHolder> {

    private List<Excursion> excursions = new ArrayList<>();
    private ExcursionViewModel excursionViewModel;
    private String vacationStartDate;
    private String vacationEndDate;

    public ExcursionAdapter(String vacationStartDate, String vacationEndDate) {
        this.vacationStartDate = vacationStartDate;
        this.vacationEndDate = vacationEndDate;
    }

    @NonNull
    @Override
    public ExcursionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.excursion_item, parent, false);
        return new ExcursionHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExcursionHolder holder, int position) {
        Excursion currentExcursion = excursions.get(position);
        holder.textViewTitle.setText(currentExcursion.getExcursionName());
        holder.textViewDate.setText(currentExcursion.getDate());

        holder.buttonDelete.setOnClickListener(v -> {
            excursionViewModel.delete(currentExcursion);
        });

        holder.buttonEdit.setOnClickListener(v -> {
            ((MainActivity) holder.itemView.getContext()).navigateToUpdateExcursion(currentExcursion, vacationStartDate, vacationEndDate);
        });
    }

    @Override
    public int getItemCount() {
        return excursions.size();
    }

    public void setExcursions(List<Excursion> excursions) {
        this.excursions = excursions;
        notifyDataSetChanged();
    }

    public void setExcursionViewModel(ExcursionViewModel excursionViewModel) {
        this.excursionViewModel = excursionViewModel;
    }

    static class ExcursionHolder extends RecyclerView.ViewHolder {
        private final TextView textViewTitle;
        private final TextView textViewDate;
        private final Button buttonDelete;
        private final Button buttonEdit;

        public ExcursionHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_excursion_title);
            textViewDate = itemView.findViewById(R.id.text_view_excursion_date);
            buttonDelete = itemView.findViewById(R.id.button_delete_excursion);
            buttonEdit = itemView.findViewById(R.id.button_edit_excursion);
        }
    }
}
