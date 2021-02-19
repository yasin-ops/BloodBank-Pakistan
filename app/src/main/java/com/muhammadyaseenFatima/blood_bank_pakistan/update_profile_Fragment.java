package com.muhammadyaseenFatima.blood_bank_pakistan;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import java.util.regex.Pattern;


public class update_profile_Fragment extends Fragment {
    NavController navController;
    ProgressBar profile_update_progressBar;
    private EditText new_Name,new_Phonenumber,new_City,new_Country;
    TextView update_Save,update_bloodgroup,update_email;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference dataBaseReference ;
    private FirebaseDatabase firebaseDatabase;
    // DataBase variable which is save
    String userName,  userEmail,  userNumber,  userCity,  userCountry,  userBloodGroup;




    private static final Pattern NAME_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                    //"(?=\\S+$)" +           //no white spaces
                    ".{3,100}" +               //at least 4 characters
                    "$");

    private static final Pattern NUMBER_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    //"(?=.*[a-zA-Z])" +      //any letter
                    //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                    // "(?=\\S+$)" +           //no white spaces
                    ".{11,11}" +               //at least 4 characters
                    "$");

    private static final Pattern PASS_PATTERN =
            Pattern.compile("^" +
                    //    "(?=.*[0-9])" +         //at least 1 digit
                    //  "(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{6,12}" +               //at least 4 characters
                    "$");


    private    Pattern Email_Pattern = Pattern.compile( "^(.+)@(.+)$");

    private boolean validate_Name() {
        String usernameInput =  new_Name.getText().toString().trim();
        if (usernameInput.isEmpty()) {
            new_Name.setError("Field can't be empty");
            return false;
        } else if (NAME_PATTERN.matcher(usernameInput).matches()) {
            new_Name.setError(null);
            return true;
        } else {
            new_Name.setError("Like : Ali");
            return false;
        }
    }

    private boolean validate_City() {
        String cityInput = new_City.getText().toString().trim();
        if (cityInput.isEmpty()) {
            new_City.setError("Field can't be empty");
            return false;
        }
        else if(NAME_PATTERN.matcher(cityInput).matches()){
            new_City.setError(null);
            return true;
        }else{
            new_City.setError("Like:Lahore");
            return false;
        }
    }
    private boolean validate_Phonenumber() {
        String numberInput = new_Phonenumber.getText().toString().trim();
        if (numberInput.isEmpty()) {
            new_Phonenumber.setError("Field can't be empty");
            return false;
        } else if (NUMBER_PATTERN.matcher(numberInput).matches()) {
            new_Phonenumber.setError(null);
            return true;
        } else {
            new_Phonenumber.setError("Like : 03048996421");
            return false;
        }
    }

    private boolean validate_Country() {

        String countryInput = new_Country.getText().toString().trim();
        if (countryInput.isEmpty()) {
            new_Country.setError("Field can't be empty");
            return false;
        } else if (NAME_PATTERN.matcher(countryInput).matches()) {
            new_Country.setError(null);
            return true;
        }else{
            new_Country.setError("Like : Pakistan");
            return false;
        }
    }

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public update_profile_Fragment() {

    }

      public static update_profile_Fragment newInstance(String param1, String param2) {
        update_profile_Fragment fragment = new update_profile_Fragment();
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

        return inflater.inflate(R.layout.fragment_update_profile_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController= Navigation.findNavController(view);
        profile_update_progressBar=view.findViewById(R.id.profile_update_progressBar);
        navController = Navigation.findNavController(view);
        new_Name=view.findViewById(R.id.update_editTextname);
        new_Phonenumber=view.findViewById(R.id.update_editTextnumber);
        new_City=view.findViewById(R.id.update_editTextcity);
        new_Country=view.findViewById(R.id.update_editTextcountry);
        update_bloodgroup=view.findViewById(R.id.update_bloodgroup);
        update_email=view.findViewById(R.id.update_email);
        update_Save=view.findViewById(R.id.update_Save);
        firebaseAuth= FirebaseAuth.getInstance();
        firebaseDatabase= FirebaseDatabase.getInstance();
        dataBaseReference=firebaseDatabase.getReference("User").child(firebaseAuth.getUid());


        dataBaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserRegisterData userRegisterData=snapshot.getValue(UserRegisterData.class);
                update_bloodgroup.setText(userRegisterData.getUserBloodGroup());
                update_email.setText(userRegisterData.getUserEmail());
                new_Name .setText(userRegisterData.getUserName());
                new_Phonenumber .setText(userRegisterData.getUserNumber());
                new_City.setText(userRegisterData.getUserCity());
                new_Country.setText(userRegisterData.getUserCountry());
                profile_update_progressBar.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), error.getCode(), Toast.LENGTH_SHORT).show();
                new_Name.setVisibility(View.INVISIBLE);
                new_Phonenumber.setVisibility(View.INVISIBLE);
                new_City.setVisibility(View.INVISIBLE);
                new_Country.setVisibility(View.INVISIBLE);
                profile_update_progressBar.setVisibility(View.VISIBLE);
            }
        });
        update_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validate_Name()&&validate_Phonenumber()&&validate_City()&&validate_Country()){
                    UIUtil.hideKeyboard(getActivity());
                    userName= new_Name.getText().toString();
                    userEmail=update_email.getText().toString();
                    userNumber=new_Phonenumber.getText().toString();
                    userCity=new_City.getText().toString();
                    userCountry=new_Country.getText().toString();
                    userBloodGroup=update_bloodgroup.getText().toString();
                    UserRegisterData userRegisterData=new UserRegisterData(userName,  userEmail,  userNumber,
                            userCity,  userCountry,  userBloodGroup);
                    dataBaseReference.setValue(userRegisterData);
                    Toast.makeText(getActivity(), "User Profile Update", Toast.LENGTH_SHORT).show();
                    navController.navigate(R.id.action_update_profile_Fragment_to_profile_Fragment);

                }
            }
        });



    }
}