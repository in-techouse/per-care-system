package lcwu.fyp.petcaresystem.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import lcwu.fyp.petcaresystem.R;
import lcwu.fyp.petcaresystem.fragments.ClinicFragment;

public class NotificationsFragment extends Fragment {


    private LinearLayout loading;
    private TextView noNotification;
    private RecyclerView notifications;

    public static NotificationsFragment newInstance() {
        NotificationsFragment myFragment = new NotificationsFragment();
        return myFragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        loading = root.findViewById(R.id.Loading);
        noNotification = root.findViewById(R.id.noNotification);
        notifications = root.findViewById(R.id.notifications);

        return root;
    }
}