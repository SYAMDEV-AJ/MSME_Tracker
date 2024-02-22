package com.manappuram.msmetracker.reports.adapterclass;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.manappuram.msmetracker.R;
import com.manappuram.msmetracker.databinding.ListSpinnerItemActivityBinding;
import com.manappuram.msmetracker.reports.modelclass.StateListReponse;
import com.manappuram.msmetracker.reports.modelclass.StatusmodelClass;
import com.manappuram.msmetracker.reports.view.ReportBranchDtsActivity;
import com.manappuram.msmetracker.reports.view.ReportDateSelectionActivity;

import java.util.List;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.ViewHolder> {
    Context context;
    List<StatusmodelClass> StatusSpinnerList;
    public Spinnerclick spinnerclick;

    public interface Spinnerclick {

        void Spinnerrclick(String name);

    }


    public StatusAdapter(Context context, List<StatusmodelClass> StatusSpinnerList, Spinnerclick spinnerclick) {
        this.context = context;
        this.StatusSpinnerList = StatusSpinnerList;
        this.spinnerclick = spinnerclick;
    }


    @NonNull
    @Override
    public StatusAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListSpinnerItemActivityBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_spinner_item_activity, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull StatusAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.itemname.setText(StatusSpinnerList.get(position).getMenuname());
        holder.binding.itemclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerclick.Spinnerrclick(StatusSpinnerList.get(position).getMenuname());
            }
        });

    }

    @Override
    public int getItemCount() {
        if (StatusSpinnerList != null)
            return StatusSpinnerList.size();
        else
            return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ListSpinnerItemActivityBinding binding;

        public ViewHolder(@NonNull ListSpinnerItemActivityBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
