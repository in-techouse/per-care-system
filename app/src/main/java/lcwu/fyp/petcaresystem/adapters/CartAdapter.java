package lcwu.fyp.petcaresystem.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import lcwu.fyp.petcaresystem.R;
import lcwu.fyp.petcaresystem.activities.CartActivity;
import lcwu.fyp.petcaresystem.model.CartFood;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {

    private List<CartFood> cartFoods;
    private Context context;
    private CartActivity cartActivity;

    public CartAdapter(Context c, CartActivity ca) {
        cartFoods = new ArrayList<>();
        context = c;
        cartActivity = ca;
    }

    public void setCartFoods(List<CartFood> cartFoods) {
        this.cartFoods = cartFoods;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, final int position) {
        CartFood cartFood = cartFoods.get(position);
        Glide.with(context).load(cartFood.getFood().getImage()).into(holder.foodImage);
        holder.foodName.setText(cartFood.getFood().getName());
        holder.foodType.setText(cartFood.getFood().getType());
        holder.foodPrice.setText(cartFood.getFood().getPrice() + " RS.");
        holder.foodQuantity.setText(cartFood.getQuantity() + "");
        int total = cartFood.getQuantity() * cartFood.getFood().getPrice();
        holder.foodTotal.setText(total + " RS.");
        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("CartAdapter", "Delete item for: " + position);
                cartActivity.deleteItem(position);
            }
        });

        holder.minusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("CartAdapter", "Minus item for: " + position);
                cartActivity.minusItem(position);
            }
        });


        holder.addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("CartAdapter", "Add item for: " + position);
                cartActivity.addItem(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return cartFoods.size();
    }

    class CartHolder extends RecyclerView.ViewHolder {
        CircleImageView foodImage;
        TextView foodName, foodPrice, foodQuantity, foodType, foodTotal;
        ImageView deleteItem, minusItem, addItem;

        CartHolder(@NonNull View itemView) {
            super(itemView);
            foodImage = itemView.findViewById(R.id.foodImage);
            foodName = itemView.findViewById(R.id.foodName);
            foodTotal = itemView.findViewById(R.id.foodTotal);
            foodType = itemView.findViewById(R.id.foodType);
            foodPrice = itemView.findViewById(R.id.foodPrice);
            foodQuantity = itemView.findViewById(R.id.foodQuantity);
            deleteItem = itemView.findViewById(R.id.deleteItem);
            minusItem = itemView.findViewById(R.id.minusItem);
            addItem = itemView.findViewById(R.id.addItem);
        }
    }
}
