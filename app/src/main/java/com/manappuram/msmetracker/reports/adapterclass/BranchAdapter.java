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
import com.manappuram.msmetracker.reports.modelclass.BranchListReponse;
import com.manappuram.msmetracker.reports.modelclass.StateListReponse;

import java.util.List;

public class BranchAdapter extends RecyclerView.Adapter<BranchAdapter.ViewHolder> {
    Context context;
    public List<BranchListReponse.get_activity_list_data> branchlist;
    public Spinnerclick spinnerclick;

    public interface Spinnerclick {

        void Spinnerrclick(String id, String name);

    }

    public BranchAdapter(Context context, List<BranchListReponse.get_activity_list_data> branchlist, Spinnerclick spinnerclick) {
        this.context = context;
        this.branchlist = branchlist;
        this.spinnerclick = spinnerclick;
    }


    @NonNull
    @Override
    public BranchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListSpinnerItemActivityBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_spinner_item_activity, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BranchAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.itemname.setText(branchlist.get(position).getBranchname());
        holder.binding.itemclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerclick.Spinnerrclick(branchlist.get(position).getBranchid(), branchlist.get(position).getBranchname());
            }
        });

    }

    @Override
    public int getItemCount() {
        if (branchlist != null)
            return branchlist.size();
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        ListSpinnerItemActivityBinding binding;

        public ViewHolder(@NonNull ListSpinnerItemActivityBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
