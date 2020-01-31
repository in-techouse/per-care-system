package lcwu.fyp.petcaresystem.adapters;

import android.provider.ContactsContract;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import lcwu.fyp.petcaresystem.R;
import lcwu.fyp.petcaresystem.model.Clinic;

public class ClinicAdapter extends RecyclerView.Adapter<ClinicAdapter.ClinicHolder> {
    private List<Clinic> data;
    public ClinicAdapter(){
        data = new ArrayList<>();
    }

    public void setData(List<Clinic> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ClinicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clinic,parent, false);
        return new ClinicHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ClinicHolder holder, int position) {
    final Clinic c = data.get(position);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ClinicHolder extends RecyclerView.ViewHolder {

        public ClinicHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
