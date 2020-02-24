package lcwu.fyp.petcaresystem.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import lcwu.fyp.petcaresystem.R;
import lcwu.fyp.petcaresystem.adapters.FoodAdapter;
import lcwu.fyp.petcaresystem.director.Helpers;
import lcwu.fyp.petcaresystem.director.Session;
import lcwu.fyp.petcaresystem.model.Food;
import lcwu.fyp.petcaresystem.model.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class FoodFragment extends Fragment {


    private LinearLayout loading;
    private TextView noFood;
    private RecyclerView food;
    private Helpers helpers;
    private List<Food> data;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Foods");
    private FoodAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_food, container, false);


        loading = v.findViewById(R.id.Loading);
        noFood = v.findViewById(R.id.noFood);
        food = v.findViewById(R.id.food);
        food.setLayoutManager(new LinearLayoutManager(getActivity()));
        Session session = new Session(getActivity());
        User user = session.getUser();
        helpers = new Helpers();
        data = new ArrayList<>();
        Log.e("Food", "Food Fragment Started");
        loadFoods();


        return v;
    }

    private void loadFoods() {
        if (!helpers.isConnected(getActivity())) {
            helpers.showError(getActivity(), "Internet Error", "No Internet Connection!");
            return;
        }

        loading.setVisibility(View.VISIBLE);
        noFood.setVisibility(View.GONE);
        food.setVisibility(View.GONE);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                data.clear();
                adapter = new FoodAdapter(getActivity());
                food.setAdapter(adapter);
                Log.e("Food", "Food DataSnapshot: " + dataSnapshot.toString());
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Log.e("Food", "Food Loop Data: " + d.toString());
                    Food f = d.getValue(Food.class);
                    if (f != null) {
                        Log.e("Food", "If Food: " + f.getName());
                        data.add(f);
                    }
                }
                if (data.size() > 0) {
                    adapter.setData(data);
                    food.setVisibility(View.VISIBLE);
                    noFood.setVisibility(View.GONE);
                } else {
                    food.setVisibility(View.GONE);
                    noFood.setVisibility(View.VISIBLE);
                }
                loading.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loading.setVisibility(View.GONE);
                noFood.setVisibility(View.VISIBLE);
                food.setVisibility(View.GONE);
            }
        });
    }
}


