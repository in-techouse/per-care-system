package lcwu.fyp.petcaresystem.fragments;


import android.media.MediaCas;
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
import lcwu.fyp.petcaresystem.model.Order;
import lcwu.fyp.petcaresystem.model.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {


    private LinearLayout loading;
    private TextView noOrder;
    private RecyclerView orders;
    private Session session;
    private User user;
    private Helpers helpers;
    private List <Order> data;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Orders");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_order, container, false);

        loading = root.findViewById(R.id.Loading);
        noOrder = root.findViewById(R.id.noOrder);
        orders = root.findViewById(R.id.orders);
        session = new Session(getActivity());
        user = session.getUser();
        helpers = new Helpers();
        data = new ArrayList<>();
        loadOrders();


        return root;
    }

    private void loadOrders(){
        if(!helpers.isConnected(getActivity())) {
            helpers.showError(getActivity(), "Internet Error", "No Internet Connection!");
            return;
        }

        loading.setVisibility(View.VISIBLE);
        noOrder.setVisibility(View.GONE);
        orders.setVisibility(View.GONE);
        reference.orderByChild("userId").equalTo(user.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              for(DataSnapshot d: dataSnapshot.getChildren()){
                  Order o = d.getValue(Order.class);
                  if (o != null)  {
                      data.add(o);
                  }
              }
              if (data.size()>0){
                  orders.setVisibility(View.VISIBLE);
                  noOrder.setVisibility(View.GONE);
                }
              else{
                  orders.setVisibility(View.GONE);
                  noOrder.setVisibility(View.VISIBLE);
                }
              loading.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loading.setVisibility(View.GONE);
                noOrder.setVisibility(View.VISIBLE);
                orders.setVisibility(View.GONE);

            }
        });
    }

}
