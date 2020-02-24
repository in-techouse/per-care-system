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

import lcwu.fyp.petcaresystem.R;
import lcwu.fyp.petcaresystem.fragments.AppointmentsFragment;
import lcwu.fyp.petcaresystem.fragments.DoctorProfileFragment;
import lcwu.fyp.petcaresystem.fragments.NotificationsFragment;

public class DoctorDashboard extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private BottomNavigationView navView;
    private DoctorProfileFragment profileFragment;
    private AppointmentsFragment appointmentsFragment;
    private NotificationsFragment notificationsFragment;
    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_dashboard);

        navView = findViewById(R.id.nav_view);
        pager = findViewById(R.id.pager);
        profileFragment = new DoctorProfileFragment();
        appointmentsFragment = new AppointmentsFragment();
        notificationsFragment = new NotificationsFragment();

        navView.setOnNavigationItemSelectedListener(this);
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), 1);
        pager.setAdapter(adapter);


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
}
