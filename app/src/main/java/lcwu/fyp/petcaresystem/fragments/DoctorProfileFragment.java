package lcwu.fyp.petcaresystem.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lcwu.fyp.petcaresystem.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorProfileFragment extends Fragment {


    public DoctorProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_doctor_profile, container, false);
        return root;
    }

}
