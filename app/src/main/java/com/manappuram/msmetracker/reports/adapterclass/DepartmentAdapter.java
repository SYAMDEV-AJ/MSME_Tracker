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
import com.manappuram.msmetracker.databinding.ItemDeptDetailsBinding;
import com.manappuram.msmetracker.databinding.ListSpinnerItemActivityBinding;
import com.manappuram.msmetracker.reports.modelclass.BranchListReponse;
import com.manappuram.msmetracker.reports.modelclass.DepartmentWiseListReponse;

import java.util.List;

public class DepartmentAdapter extends RecyclerView.Adapter<DepartmentAdapter.ViewHolder> {
    Context context;
    public List<DepartmentWiseListReponse.get_activity_list_data> deptlist;
    public Spinnerclick spinnerclick;

    public interface Spinnerclick {

        void Spinnerrclick(String id);

    }

    public DepartmentAdapter(Context context, List<DepartmentWiseListReponse.get_activity_list_data> deptlist, Spinnerclick spinnerclick) {
        this.context = context;
        this.deptlist = deptlist;
        this.spinnerclick = spinnerclick;
    }


    @NonNull
    @Override
    public DepartmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDeptDetailsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_dept_details, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DepartmentAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.deptname.setText(deptlist.get(position).getDepartment_name());
        holder.binding.deptcount.setText(deptlist.get(position).getTotal_count());
        holder.binding.present.setText(deptlist.get(position).getPresent());
        holder.binding.absent.setText(deptlist.get(position).getAbsent());
        holder.binding.moved.setText(deptlist.get(position).getMoved());
        holder.binding.notmoved.setText(deptlist.get(position).getNot_moved());
        holder.binding.itemclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerclick.Spinnerrclick(deptlist.get(position).getDep_id());
            }
        });

    }

    @Override
    public int getItemCount() {
        if (deptlist != null)
            return deptlist.size();
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
        ItemDeptDetailsBinding binding;

        public ViewHolder(@NonNull ItemDeptDetailsBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
