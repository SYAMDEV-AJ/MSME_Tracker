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
import com.manappuram.msmetracker.reports.modelclass.ReportActivityListReponse;

import java.util.List;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {
    Context context;
    public List<ReportActivityListReponse.get_activity_list_data> spinnerlist;
    public Spinnerclick spinnerclick;

    public interface Spinnerclick {

        void Spinnerclick(String id, String name);

    }

    public ActivityAdapter(Context context, List<ReportActivityListReponse.get_activity_list_data> spinnerlist) {
        this.context = context;
        this.spinnerlist = spinnerlist;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public ActivityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListSpinnerItemActivityBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_spinner_item_activity, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.itemname.setText(spinnerlist.get(position).getActivity_name());
        holder.binding.itemclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerclick.Spinnerclick(spinnerlist.get(position).activity_id, spinnerlist.get(position).getActivity_name());
            }
        });

    }

    @Override
    public int getItemCount() {
        if (spinnerlist != null)
            return spinnerlist.size();
        else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ListSpinnerItemActivityBinding binding;

        public ViewHolder(@NonNull ListSpinnerItemActivityBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
