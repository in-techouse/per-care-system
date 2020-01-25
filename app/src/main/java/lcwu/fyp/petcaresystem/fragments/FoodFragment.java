package lcwu.fyp.petcaresystem.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import lcwu.fyp.petcaresystem.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FoodFragment extends Fragment {


    private LinearLayout loading;
    private TextView noFood;
    private RecyclerView food;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_food, container, false);


        loading = v.findViewById(R.id.Loading);
        noFood = v.findViewById(R.id.noFood);
        food = v.findViewById(R.id.food);

        return  v;
    }

}
