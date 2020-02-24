package lcwu.fyp.petcaresystem.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import lcwu.fyp.petcaresystem.R;
import lcwu.fyp.petcaresystem.model.Clinic;

public class ClinicAdapter extends RecyclerView.Adapter<ClinicAdapter.ClinicHolder> {
    private List<Clinic> data;

    public ClinicAdapter() {
        data = new ArrayList<>();
    }

    public void setData(List<Clinic> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ClinicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clinic, parent, false);
        return new ClinicHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ClinicHolder holder, int position) {
        final Clinic c = data.get(position);

        holder.name.setText(c.getName());
        holder.number.setText(c.getNumber());
        holder.address.setText(c.getAddress());
        holder.startTiming.setText(c.getStartTiming());
        holder.endTiming.setText(c.getEndTiming());
        holder.fee.setText(c.getFee() + " RS.");

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ClinicHolder extends RecyclerView.ViewHolder {
        TextView name, number, address, startTiming, endTiming, fee;

        ClinicHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            number = itemView.findViewById(R.id.number);
            address = itemView.findViewById(R.id.address);
            startTiming = itemView.findViewById(R.id.startTiming);
            endTiming = itemView.findViewById(R.id.endTiming);
            fee = itemView.findViewById(R.id.fee);


        }
    }
}
