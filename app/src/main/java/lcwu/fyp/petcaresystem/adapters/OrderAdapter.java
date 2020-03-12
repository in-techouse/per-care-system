package lcwu.fyp.petcaresystem.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import lcwu.fyp.petcaresystem.R;
import lcwu.fyp.petcaresystem.activities.OrderDetail;
import lcwu.fyp.petcaresystem.model.Order;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderHolder> {
    private List<Order> data;
    private Context context;

    public OrderAdapter(Context c) {
        data = new ArrayList<>();
        context = c;
    }

    public void setData(List<Order> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHolder holder, int position) {
        final Order o = data.get(position);
        holder.address.setText(o.getAddress());
        holder.email.setText(o.getEmail());
        holder.phoneNumber.setText(o.getPhoneNumber());
        holder.totalPrice.setText(o.getTotalPrice() + " RS.");
        holder.totalItems.setText(o.getTotalItems() + "");
        holder.name.setText(o.getFirstName() + " " + o.getLastName());
        holder.main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(context, OrderDetail.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("order", o);
                it.putExtras(bundle);
                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(it);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class OrderHolder extends RecyclerView.ViewHolder {
        TextView address, name, email, phoneNumber, totalPrice, totalItems;
        CardView main;

        OrderHolder(@NonNull View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.address);
            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
            phoneNumber = itemView.findViewById(R.id.phoneNumber);
            totalPrice = itemView.findViewById(R.id.totalPrice);
            totalItems = itemView.findViewById(R.id.totalItems);
            main = itemView.findViewById(R.id.main);
        }
    }
}
