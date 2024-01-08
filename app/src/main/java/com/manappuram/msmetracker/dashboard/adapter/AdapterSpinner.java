package com.manappuram.msmetracker.dashboard.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.manappuram.msmetracker.R;
import com.manappuram.msmetracker.dashboard.modelclass.ActivitylistResponse;
import com.manappuram.msmetracker.dashboard.view.DashboardActivity;
import com.manappuram.msmetracker.databinding.ListSpinnerItemActivityBinding;

import java.util.List;

public class AdapterSpinner extends RecyclerView.Adapter<AdapterSpinner.ViewHolder> {

    Context context;
    public List<ActivitylistResponse.get_activity_list_data> spinnerlist;


    public spinnerclick spinnerclick;

    public interface spinnerclick {

        void spinnerclick(String name, String id);
    }


    public AdapterSpinner(Context context, List<ActivitylistResponse.get_activity_list_data> spinnerlist, spinnerclick spinnerclick) {

        this.context = context;
        this.spinnerlist = spinnerlist;
        this.spinnerclick = spinnerclick;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListSpinnerItemActivityBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_spinner_item_activity, parent, false);
        return new ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.binding.itemname.setText(spinnerlist.get(position).getActivity_name());

        holder.binding.itemclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerclick.spinnerclick(spinnerlist.get(position).getActivity_name(), spinnerlist.get(position).getActivity_id());

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
