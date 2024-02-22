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
import com.manappuram.msmetracker.databinding.ItemBranchDetailsBinding;
import com.manappuram.msmetracker.databinding.ItemDeptDetailsBinding;
import com.manappuram.msmetracker.reports.modelclass.BranchDetailsReponse;
import com.manappuram.msmetracker.reports.modelclass.DepartmentWiseListReponse;

import java.util.List;

public class BranchDetailsAdapter extends RecyclerView.Adapter<BranchDetailsAdapter.ViewHolder> {
    Context context;
    public List<BranchDetailsReponse.get_activity_list_data> branchlist;
    Spinnerclick spinnerclick;

    public interface Spinnerclick {

        void spinnerclick(String id);

    }


    public BranchDetailsAdapter(Context context, List<BranchDetailsReponse.get_activity_list_data> branchlist, Spinnerclick spinnerclick) {
        this.context = context;
        this.branchlist = branchlist;
        this.spinnerclick = spinnerclick;

    }


    @NonNull
    @Override
    public BranchDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBranchDetailsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_branch_details, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BranchDetailsAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.empname.setText(branchlist.get(position).getEmp_name());
        holder.binding.empcode.setText(branchlist.get(position).getEmp_code() + "-" + branchlist.get(position).getMsme_branch());
        holder.binding.activitycount.setText(branchlist.get(position).getNumber_of_activity());
        holder.binding.tttravelkm.setText(branchlist.get(position).getTotal_distance());
        holder.binding.statename.setText(branchlist.get(position).getState_name());
        holder.binding.phonenumber.setText(branchlist.get(position).getMobile_no());
        holder.binding.punchedbranchid.setText(branchlist.get(position).getPunched_branch());
        holder.binding.punchedbranchname.setText(branchlist.get(position).getPunched_branchname());

        holder.binding.phonenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerclick.spinnerclick(branchlist.get(position).getMobile_no());
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
        ItemBranchDetailsBinding binding;

        public ViewHolder(@NonNull ItemBranchDetailsBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
