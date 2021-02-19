package com.muhammadyaseenFatima.blood_bank_pakistan;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class profile_Fragment extends Fragment {
    NavController navController;
    ProgressBar profile_progressBar;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference dataBaseReference ;
    private FirebaseDatabase firebaseDatabase;


    TextView p_BloodGroup,p_username,p_user_email,p_usercity,p_usercountry,p_number,p_updateProfile,p_blood_logo,p_save_life, p_developer;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public profile_Fragment() {

      }

     public static profile_Fragment newInstance(String param1, String param2) {
        profile_Fragment fragment = new profile_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController= Navigation.findNavController(view);
        profile_progressBar=view.findViewById(R.id.profile_progressBar);
        p_BloodGroup=view.findViewById(R.id.p_BloodGroup);
        p_username=view.findViewById(R.id.p_username);
        p_user_email=view.findViewById(R.id.p_user_email);
        p_usercity=view.findViewById(R.id.p_usercity);
        p_usercountry=view.findViewById(R.id.p_usercountry);
        p_number=view.findViewById(R.id.p_number);
        p_updateProfile=view.findViewById(R.id.p_updateProfile);
        p_developer=view.findViewById(R.id. p_developer);
        p_blood_logo=view.findViewById(R.id.p_blood_logo);
        p_save_life=view.findViewById(R.id.p_save_life);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        dataBaseReference=firebaseDatabase.getReference("User").child(firebaseAuth.getUid());
        dataBaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserRegisterData userRegisterData=snapshot.getValue(UserRegisterData.class);
                p_BloodGroup.setText(userRegisterData.getUserBloodGroup());
                p_usercity.setText(userRegisterData.getUserCity());
                p_usercountry.setText(userRegisterData.getUserCountry());
                p_user_email.setText(userRegisterData.getUserEmail());
                p_username.setText(userRegisterData.getUserName());
                p_number.setText(userRegisterData.getUserNumber());
                profile_progressBar.setVisibility(View.INVISIBLE);
                p_username.setVisibility(View.VISIBLE);
                p_number.setVisibility(View.VISIBLE);
                p_BloodGroup.setVisibility(View.VISIBLE);
                p_user_email.setVisibility(View.VISIBLE);
                p_usercountry.setVisibility(View.VISIBLE);
                p_usercity.setVisibility(View.VISIBLE);
                p_updateProfile.setVisibility(View.VISIBLE);
                p_blood_logo.setVisibility(View.VISIBLE);
                p_save_life.setVisibility(View.VISIBLE);
                p_developer.setVisibility(View.VISIBLE);


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), error.getCode(), Toast.LENGTH_SHORT).show();
                profile_progressBar.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "Check your Internet Connection", Toast.LENGTH_SHORT).show();



            }
        });
p_updateProfile.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
navController.navigate(R.id.action_profile_Fragment_to_update_profile_Fragment);
    }
});
    }
}