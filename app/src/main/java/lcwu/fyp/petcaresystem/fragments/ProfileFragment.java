package lcwu.fyp.petcaresystem.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;
import lcwu.fyp.petcaresystem.R;
import lcwu.fyp.petcaresystem.activities.Appointments;
import lcwu.fyp.petcaresystem.activities.EditUserProfile;
import lcwu.fyp.petcaresystem.activities.LoginActivity;
import lcwu.fyp.petcaresystem.activities.Notifications;
import lcwu.fyp.petcaresystem.activities.Orders;
import lcwu.fyp.petcaresystem.director.Session;
import lcwu.fyp.petcaresystem.model.User;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private Session session;
    private User user;
    private TextView name, email, phone;
    private LinearLayout logout;
    private RelativeLayout orders, notifications, appointments;
    private TextView edit;
    private CircleImageView profile_image;

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
        email = v.findViewById(R.id.email);
        phone = v.findViewById(R.id.phnNumber);
        edit = v.findViewById(R.id.edit);
        profile_image = v.findViewById(R.id.profile_image);
        orders = v.findViewById(R.id.orders);
        notifications = v.findViewById(R.id.notifications);
        appointments = v.findViewById(R.id.appointments);
        logout = v.findViewById(R.id.logout);

        if (user.getImage() != null && !user.getImage().equalsIgnoreCase("")) {
            Glide.with(getActivity()).load(user.getImage()).into(profile_image);
        }

        name.setText(user.getFirstName() + " " + user.getLastName());
        email.setText(user.getEmail());
        phone.setText(user.getPhNo());
        edit.setOnClickListener(this);
        orders.setOnClickListener(this);
        notifications.setOnClickListener(this);
        appointments.setOnClickListener(this);
        logout.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.edit: {
                Intent it = new Intent(getActivity(), EditUserProfile.class);
                startActivity(it);
                break;
            }
            case R.id.orders: {
                Log.e("Profile", "Order");
                Intent it = new Intent(getActivity(), Orders.class);
                startActivity(it);
                break;
            }
            case R.id.notifications: {
                Log.e("Profile", "Notifications");
                Intent it = new Intent(getActivity(), Notifications.class);
                startActivity(it);
                break;
            }
            case R.id.appointments: {
                Log.e("Profile", "Appointments");
                Intent it = new Intent(getActivity(), Appointments.class);
                startActivity(it);
                break;
            }
            case R.id.logout: {
                Log.e("Profile", "Logout");
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signOut();
                session.destroySession();
                Intent it = new Intent(getActivity(), LoginActivity.class);
                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(it);
                getActivity().finish();
                break;
            }
        }
    }
}
