package lcwu.fyp.petcaresystem.activities;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

import lcwu.fyp.petcaresystem.R;
import lcwu.fyp.petcaresystem.fragments.ClinicFragment;
import lcwu.fyp.petcaresystem.fragments.DoctorFragment;
import lcwu.fyp.petcaresystem.fragments.FoodFragment;
import lcwu.fyp.petcaresystem.fragments.PetFragment;
import lcwu.fyp.petcaresystem.fragments.ProfileFragment;

public class Dashboard extends AppCompatActivity {

    private ViewPager pager;
    private BottomNavigationView navView;
    private PagerAdapter adapter;
    private PetFragment pet;
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
        pet = new PetFragment();
        clinic = new ClinicFragment();
        doctor = new DoctorFragment();
        food = new FoodFragment();
        profileFragment = new ProfileFragment();



        adapter = new PagerAdapter(getSupportFragmentManager(), 1);
        pager.setAdapter(adapter);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navView, navController);
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
                    return food;
                }
                case  1:
                {
                    return doctor;
                }

                case 2:
                {
                    return clinic;
                }
                case 3:
                {
                    return pet;
                }
                case 4:
                {
                    return profileFragment;
                }
            }
            return null;
        }

        @Override
        public int getCount() {
            return 5;
        }
    }
}
