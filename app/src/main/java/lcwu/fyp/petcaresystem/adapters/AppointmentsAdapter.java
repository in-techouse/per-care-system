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
import androidx.appcompat.view.menu.MenuView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import lcwu.fyp.petcaresystem.R;
import lcwu.fyp.petcaresystem.activities.ShowAppointmentDetails;
import lcwu.fyp.petcaresystem.model.Appointment;

public class AppointmentsAdapter extends RecyclerView.Adapter<AppointmentsAdapter.AppointmentsHolder> {
    private List<Appointment> data;
    Context context;

    public AppointmentsAdapter(Context context){
        this.context = context;
        data = new ArrayList<>();

    }

    public void setData(List<Appointment> data) {
        this.data = data;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public AppointmentsAdapter.AppointmentsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_appointment, parent, false);
        return new AppointmentsAdapter.AppointmentsHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentsAdapter.AppointmentsHolder holder, int position) {
        final Appointment appointment = data.get(position);
        holder.appointmentTime.setText(appointment.getTime());
        holder.appointmentDate.setText(appointment.getDate());
        holder.petCategory.setText(appointment.getCategory());
        holder.appointmentMainCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(context , ShowAppointmentDetails.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("appointment" , appointment);
                in.putExtras(bundle);
                context.startActivity(in);
            }
        });

    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class AppointmentsHolder extends RecyclerView.ViewHolder {
        TextView appointmentDate, appointmentTime, petCategory;
        CardView appointmentMainCard;


        AppointmentsHolder(@NonNull View itemView) {
            super(itemView);
            appointmentDate = itemView.findViewById(R.id.appointmentDate);
            appointmentTime = itemView.findViewById(R.id.appintmentTime);
            petCategory = itemView.findViewById(R.id.appintmentCategory);
            appointmentMainCard = itemView.findViewById(R.id.appointmentMainCard);

        }
    }
}
