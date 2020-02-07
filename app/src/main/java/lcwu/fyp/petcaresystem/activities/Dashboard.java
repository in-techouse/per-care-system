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
import lcwu.fyp.petcaresystem.fragments.ClinicFragment;
import lcwu.fyp.petcaresystem.fragments.DoctorFragment;
import lcwu.fyp.petcaresystem.fragments.FoodFragment;
import lcwu.fyp.petcaresystem.fragments.ProfileFragment;

public class Dashboard extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private ViewPager pager;
    private BottomNavigationView navView;
    private PagerAdapter adapter;
    private ClinicFragment clinic;
    private DoctorFragment  doctor;
    private FoodFragment food;
    private ProfileFragment profileFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        navView = findViewById(R.id.nav_view);
        pager = findViewById(R.id.pager);
        clinic = new ClinicFragment();
        doctor = new DoctorFragment();
        food = new FoodFragment();
        profileFragment = new ProfileFragment();




        navView.setOnNavigationItemSelectedListener(this);


        adapter = new PagerAdapter(getSupportFragmentManager(), 1);
        pager.setAdapter(adapter);


        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        navView.getMenu().findItem(R.id.navigation_food).setChecked(true);
                        break;
                    case 1:
                        navView.getMenu().findItem(R.id.navigation_doctor).setChecked(true);
                        break;
                    case 2:
                        navView.getMenu().findItem(R.id.navigation_clinic).setChecked(true);
                        break;
                    case 3:
                        navView.getMenu().findItem(R.id.navigation_profile).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    class PagerAdapter extends FragmentPagerAdapter{

        public PagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

            @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position)
            {
                case  0:
                {
                    Log.e("Click" , "food Clicked");
                    return food;
                }
                case  1:
                {
                    Log.e("click" , "doctor Clicked");
                    return doctor;
                }

                case 2:
                {
                    Log.e("click" , "clinic click");
                    return clinic;
                }
                case 3:
                 {
                    return profileFragment;
                 }
            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }
    }




    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.navigation_food:
                pager.setCurrentItem(0);
                break;
            case R.id.navigation_doctor:
                pager.setCurrentItem(1);
                break;
            case R.id.navigation_clinic:
                pager.setCurrentItem(2);
                break;
            case R.id.navigation_profile:
                pager.setCurrentItem(3);
                break;
        }

        return true;
    }
}
