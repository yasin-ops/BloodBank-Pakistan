package com.muhammadyaseenFatima.blood_bank_pakistan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity {
    NavController navController;
    private NavigationView navigationView;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private long backPressedTime;
    private Toast backToast;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        final DrawerLayout drawerLayout=findViewById(R.id.drawerLayout);
        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        navigationView=findViewById(R.id.navigationview);

        // NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

/*
      View v = navigationView.getHeaderView(0);
      Intent intent=getIntent();
      String nav_nameHeader=intent.getStringExtra("login_Name");
      String nav_emailHeader=intent.getStringExtra("login_Email");
      nav_header_username = (TextView) v.findViewById(R.id.nav_header_username);
      nav_header_useremail = (TextView) v.findViewById(R.id.nav_header_useremail);
      nav_header_username.setText(nav_nameHeader);
      nav_header_useremail.setText(nav_emailHeader);

 */
        navigationView.setItemIconTintList(null);
        navController= Navigation.findNavController(this,R.id.nav_Hostfragment);
        NavigationUI.setupWithNavController(navigationView,navController);
        final TextView textTittle=findViewById(R.id.menu_tittle);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                textTittle.setText(destination.getLabel());
            }
        });

        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int menuid=item.getItemId();
               /*
                if(menuid==R.id.menu_logout){
                    firebaseAuth.signOut();

                    Intent intent=new Intent(BloodInfo.this,MainActivity.class);
                    startActivity(intent);
                    finish();

                    Toast.makeText(BloodInfo.this, "Logout User ", Toast.LENGTH_LONG).show();
                    drawerLayout.closeDrawers();
                    return true;
                }
                */
                if(menuid==R.id.home_Fragment)
                {
                    navController.navigate(R.id.home_Fragment);
                    drawerLayout.closeDrawers();

                    return true;
                }
                if(menuid==R.id.info_Fragment){
                    navController.navigate(R.id.info_Fragment);
                    drawerLayout.closeDrawers();
                    return true;
                }
                if(menuid==R.id.profile_Fragment){
                    navController.navigate(R.id.profile_Fragment);
                    drawerLayout.closeDrawers();
                    return true;
                }
                return false;
            }

        });

    }


   // @Override
   // public void onDestroy()
   // {
     //   android.os.Process.killProcess(android.os.Process.myPid());
      //  super.onDestroy();
   // }
   /*
    @Override
   public void onBackPressed() {
       super.onBackPressed();
       exitFromApp();
   }

    private void exitFromApp() {
        Intent intent = new Intent(Intent.;
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
    */

}
