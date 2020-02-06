package lcwu.fyp.petcaresystem.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import lcwu.fyp.petcaresystem.R;
import lcwu.fyp.petcaresystem.director.Helpers;
import lcwu.fyp.petcaresystem.director.Session;
import lcwu.fyp.petcaresystem.model.Doctor;
import lcwu.fyp.petcaresystem.model.Food;
import lcwu.fyp.petcaresystem.model.Order;
import lcwu.fyp.petcaresystem.model.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class FoodFragment extends Fragment {


    private LinearLayout loading;
    private TextView noFood;
    private RecyclerView food;
    private Session session;
    private User user;
    private Helpers helpers;
    private List<Food> data;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Foods");

    public static FoodFragment newInstance() {
        FoodFragment myFragment = new FoodFragment();
        return myFragment;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_food, container, false);


        loading = v.findViewById(R.id.Loading);
        noFood = v.findViewById(R.id.noFood);
        food = v.findViewById(R.id.food);
        session = new Session(getActivity());
        user = session.getUser();
        helpers = new Helpers();
        data = new ArrayList<>();
        loadFoods();


        return  v;
    }
    private void loadFoods() {
        if (!helpers.isConnected(getActivity())) {
            helpers.showError(getActivity(), "Internet Error", "No Internet Connection!");
            return;
        }

        loading.setVisibility(View.VISIBLE);
        noFood.setVisibility(View.GONE);
        food.setVisibility(View.GONE);
        reference.orderByChild("userId").equalTo(user.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d: dataSnapshot.getChildren()){
                    Food f = d.getValue(Food.class);
                    if (f != null)  {
                        data.add(f);
                    }
                }
                if (data.size()>0){
                    food.setVisibility(View.VISIBLE);
                    noFood.setVisibility(View.GONE);
                }
                else{
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


