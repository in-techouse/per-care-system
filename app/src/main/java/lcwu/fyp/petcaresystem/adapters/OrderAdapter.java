package lcwu.fyp.petcaresystem.adapters;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import lcwu.fyp.petcaresystem.R;
import lcwu.fyp.petcaresystem.model.Order;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderHolder> {
    private List<Order> data;

    public OrderAdapter() {
        data = new ArrayList<>();
    }

    public void setData(List<Order> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order,parent,false);
        return new OrderHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHolder holder, int position) {
        final Order o = data.get(position);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class OrderHolder extends RecyclerView.ViewHolder {
        public OrderHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
