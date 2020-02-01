package lcwu.fyp.petcaresystem.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import lcwu.fyp.petcaresystem.R;
import lcwu.fyp.petcaresystem.model.Doctor;


public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DocHolder> {
    private List<Doctor> data;

    public DoctorAdapter(){
        data = new ArrayList<>();
    }

    public void setData(List<Doctor> data) {
        this.data = data;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public DoctorAdapter.DocHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order,parent,false);
        return new DoctorAdapter.DocHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull DoctorAdapter.DocHolder holder, int position) {
        final Doctor doc = data.get(position);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class DocHolder extends RecyclerView.ViewHolder {
        public DocHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
