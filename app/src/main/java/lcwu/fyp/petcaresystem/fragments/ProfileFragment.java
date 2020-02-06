package lcwu.fyp.petcaresystem.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigator;

import android.view.FrameStats;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.jar.Attributes;

import lcwu.fyp.petcaresystem.R;
import lcwu.fyp.petcaresystem.activities.EditUserProfile;
import lcwu.fyp.petcaresystem.director.Session;
import lcwu.fyp.petcaresystem.model.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    private Session session;
    private User user;
    private TextView name, email, phone;
    private TextView edit;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        session = new Session(getActivity());
        user = session.getUser();

        name = v.findViewById(R.id.name);
        email= v.findViewById(R.id.email);
        phone= v.findViewById(R.id.phnNumber);
        edit= v.findViewById(R.id.edit);

        name.setText(user.getFirstName() + " " + user.getLastName());
        email.setText(user.getEmail());
        phone.setText(user.getPhNo());
        edit.setOnClickListener(this);


        return  v;
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        switch (id)
        {
            case R.id.edit:{
                Intent it= new Intent( getActivity(), EditUserProfile.class);
                startActivity(it);
                break;
            }
        }



    }
}
