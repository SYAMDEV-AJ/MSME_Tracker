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
import com.manappuram.msmetracker.reports.modelclass.StateListReponse;
import com.manappuram.msmetracker.reports.view.ReportDateSelectionActivity;

import java.util.List;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.ViewHolder> {
    Context context;
    public List<StateListReponse.get_activity_list_data> statelist;
    public ReportDateSelectionActivity.SpinnerOnclickListener listener;


    public StateAdapter(Context context, List<StateListReponse.get_activity_list_data> statelist) {
        this.context = context;
        this.statelist = statelist;
    }


    @NonNull
    @Override
    public StateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListSpinnerItemActivityBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_spinner_item_activity, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull StateAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.itemname.setText(statelist.get(position).getStatename());
        holder.binding.itemclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(statelist.get(position).getStateid(), statelist.get(position).getStatename());
            }
        });

    }

    @Override
    public int getItemCount() {
        if (statelist != null)
            return statelist.size();
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
