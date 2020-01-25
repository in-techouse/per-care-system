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
public class OrderFragment extends Fragment {


    private LinearLayout loading;
    private TextView noOrder;
    private RecyclerView orders;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_order, container, false);

        loading = root.findViewById(R.id.Loading);
        noOrder = root.findViewById(R.id.noOrder);
        orders = root.findViewById(R.id.orders);


        return root;
    }

}
