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
import lcwu.fyp.petcaresystem.model.Notification;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationHolder> {

    private List<Notification> data;
    private int type;

    public NotificationAdapter(int t) {
        data = new ArrayList<>();
        type = t;
    }

    @NonNull
    @Override
    public NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new NotificationHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationHolder holder, int position) {
        Notification n = data.get(position);
        if (type == 1) {
            holder.text.setText(n.getUserMessage());
        } else {
            holder.text.setText(n.getDoctorMessage());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Notification> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    class NotificationHolder extends RecyclerView.ViewHolder {
        TextView text;

        NotificationHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
        }
    }
}
