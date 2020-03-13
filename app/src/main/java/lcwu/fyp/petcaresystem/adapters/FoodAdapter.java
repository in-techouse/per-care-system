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
import lcwu.fyp.petcaresystem.activities.FoodDetailActivity;
import lcwu.fyp.petcaresystem.model.Food;


public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodHolder> {
    private List<Food> data;
    private Context context;

    public FoodAdapter(Context c) {
        context = c;
        data = new ArrayList<>();
    }

    public void setData(List<Food> data) {
        this.data = data;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public FoodAdapter.FoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new FoodAdapter.FoodHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.FoodHolder holder, final int position) {
        final Food f = data.get(position);
        Glide.with(context).load(f.getImage()).into(holder.image);
        holder.name.setText(f.getName());
        holder.type.setText(f.getType());
        holder.mainCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Food", "Item Clicked at position: " + position);
                Intent intent = new Intent(context, FoodDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("food", f);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class FoodHolder extends RecyclerView.ViewHolder {
        TextView name, type;
        ImageView image;
        CardView mainCard;

        FoodHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            type = itemView.findViewById(R.id.type);
            image = itemView.findViewById(R.id.image);
            mainCard = itemView.findViewById(R.id.mainCard);
        }
    }
}
