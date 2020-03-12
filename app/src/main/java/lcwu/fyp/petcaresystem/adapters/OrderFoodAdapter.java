package lcwu.fyp.petcaresystem.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import lcwu.fyp.petcaresystem.R;
import lcwu.fyp.petcaresystem.model.CartFood;

public class OrderFoodAdapter extends RecyclerView.Adapter<OrderFoodAdapter.OrderFoodHolder> {

    private List<CartFood> data;
    private Context context;

    public OrderFoodAdapter(Context c) {
        data = new ArrayList<>();
        context = c;
    }

    public void setData(List<CartFood> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderFoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_food, parent, false);
        return new OrderFoodHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderFoodHolder holder, int position) {
        CartFood cartFood = data.get(position);
        Glide.with(context).load(cartFood.getFood().getImage()).into(holder.foodImage);
        holder.foodName.setText(cartFood.getFood().getName());
        holder.foodType.setText(cartFood.getFood().getType());
        holder.foodPrice.setText(cartFood.getFood().getPrice() + " RS.");
        holder.foodQuantity.setText(cartFood.getQuantity() + "");
        int total = cartFood.getQuantity() * cartFood.getFood().getPrice();
        holder.foodTotal.setText(total + " RS.");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class OrderFoodHolder extends RecyclerView.ViewHolder {
        CircleImageView foodImage;
        TextView foodName, foodPrice, foodQuantity, foodType, foodTotal;

        OrderFoodHolder(@NonNull View itemView) {
            super(itemView);
            foodImage = itemView.findViewById(R.id.foodImage);
            foodName = itemView.findViewById(R.id.foodName);
            foodTotal = itemView.findViewById(R.id.foodTotal);
            foodType = itemView.findViewById(R.id.foodType);
            foodPrice = itemView.findViewById(R.id.foodPrice);
            foodQuantity = itemView.findViewById(R.id.foodQuantity);
        }
    }
}
