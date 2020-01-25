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
public class ClinicFragment extends Fragment {



    private LinearLayout loading;
    private TextView noClinic;
    private RecyclerView clinics;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_clinic, container, false);


        loading = v.findViewById(R.id.Loading);
        noClinic = v.findViewById(R.id.noClinic);
        clinics = v.findViewById(R.id.clinics);

        return v;
    }

}
