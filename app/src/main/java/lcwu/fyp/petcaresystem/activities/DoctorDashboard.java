package lcwu.fyp.petcaresystem.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import lcwu.fyp.petcaresystem.R;
import lcwu.fyp.petcaresystem.director.Session;
import lcwu.fyp.petcaresystem.fragments.AppointmentsFragment;
import lcwu.fyp.petcaresystem.fragments.DoctorProfileFragment;
import lcwu.fyp.petcaresystem.fragments.NotificationsFragment;
import lcwu.fyp.petcaresystem.model.Appointment;
import lcwu.fyp.petcaresystem.model.User;

public class DoctorDashboard extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private BottomNavigationView navView;
    private DoctorProfileFragment profileFragment;
    private AppointmentsFragment appointmentsFragment;
    private NotificationsFragment notificationsFragment;
    private ViewPager pager;
    private User user;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_dashboard);

        navView = findViewById(R.id.nav_view);
        pager = findViewById(R.id.pager);
        profileFragment = new DoctorProfileFragment();
        appointmentsFragment = new AppointmentsFragment();
        notificationsFragment = new NotificationsFragment();
        session = new Session(DoctorDashboard.this);
        user = session.getUser();

        navView.setOnNavigationItemSelectedListener(this);
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), 1);
        pager.setAdapter(adapter);
        appointmentsListener();


        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        navView.getMenu().findItem(R.id.navigation_appointments).setChecked(true);
                        break;
                    case 1:
                        navView.getMenu().findItem(R.id.navigation_notification).setChecked(true);
                        break;
                    case 2:
                        navView.getMenu().findItem(R.id.navigation_profile).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navigation_appointments:
                pager.setCurrentItem(0);
                break;
            case R.id.navigation_notification:
                pager.setCurrentItem(1);
                break;
            case R.id.navigation_profile:
                pager.setCurrentItem(2);
                break;
        }
        return true;
    }

    class PagerAdapter extends FragmentPagerAdapter {

        PagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position)
            {
                case 0:
                {
                    return appointmentsFragment;
                }
                case 1:
                {
                    return notificationsFragment;
                }
                case 2:
                {
                    return profileFragment;
                }
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }
    }

    void appointmentsListener(){
        Log.e("appointments" , "in appointments listtener");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Appointments");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Appointment appointment = new Appointment();
                Log.e("appointments" , "data received "+dataSnapshot);
                Log.e("appointments" , "data received "+dataSnapshot.getChildren());
                Log.e("appointments" , "data received "+dataSnapshot.getChildren());
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    appointment = data.getValue(Appointment.class);
                    if(appointment != null){
                        if(user.getId().equals(appointment.getDoctorId())){
                            Log.e("appointments" , "Here is an appointment for you");
                        }else {
                            Log.e("appointments" , "Appointments for other doctors");
                        }
                    }


                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
