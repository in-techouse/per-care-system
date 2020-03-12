package lcwu.fyp.petcaresystem.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import lcwu.fyp.petcaresystem.R;
import lcwu.fyp.petcaresystem.activities.ShowClinicDetails;
import lcwu.fyp.petcaresystem.model.Clinic;

public class ClinicAdapter extends RecyclerView.Adapter<ClinicAdapter.ClinicHolder> {
    private List<Clinic> data;
    Context context;

    public ClinicAdapter(Context c) {
        data = new ArrayList<>();
        context = c;
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
        Log.e("adapter", "in BindViewHolder Holder");
        Log.e("adapter", c.getName() + " in BindViewHolder Holder");
        holder.name.setText(c.getName());
        if (c.getImage() != null && c.getImage().length() > 0) {
            Log.e("adapter", "image added");
            Glide.with(context).load(c.getImage()).into(holder.imageView);
        } else {
            Log.e("adapter", "Image not  found");
        }
        holder.fee.setText(c.getFee() + " RS.");

        holder.main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                Intent in = new Intent(context, ShowClinicDetails.class);
                bundle.putSerializable("clinic", c);
                in.putExtras(bundle);
                context.startActivity(in);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ClinicHolder extends RecyclerView.ViewHolder {
        TextView name, fee;
        CardView main;
        ImageView imageView;

        ClinicHolder(@NonNull View itemView) {
            super(itemView);
            Log.e("adapter", "in Clinic Holder");
            imageView = itemView.findViewById(R.id.clinic_image);
            name = itemView.findViewById(R.id.clinic_name);
            fee = itemView.findViewById(R.id.clinic_fee);
            main = itemView.findViewById(R.id.main);

        }
    }
}
