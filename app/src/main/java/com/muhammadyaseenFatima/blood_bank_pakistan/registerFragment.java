package com.muhammadyaseenFatima.blood_bank_pakistan;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link registerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class registerFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private NavController navController;
    EditText editTextname,editTextemail,editTextpass,editTextnumber,editTextcity,editTextcountry;
    TextView textSignIn,textregister;
    private Spinner spinnerOption;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference myRef ;
    // Variable that we used in Firebase Real_Time_data
    String user_email,user_pass,user_name, user_number, user_city,  user_country;
    private String userBloodGroup;

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
                  //  "(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    //"(?=.*[a-zA-Z])" +      //any letter
                    //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                    // "(?=\\S+$)" +           //no white spaces
                  ".{11,11}" +               //at least 4 characters
                    "$");


    private static final Pattern PASS_PATTERN =
            Pattern.compile("^" +
                  //  "(?=.*[0-9])" +         //at least 1 digit
                   // "(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                   // "(?=.*[a-zA-Z])" +      //any letter
                   // "(?=.*[@#$%^&+=])" +    //at least 1 special character
                   // "(?=\\S+$)" +           //no white spaces
                    ".{6,100}" +               //at least 4 characters
                    "$");


    private    Pattern Email_Pattern = Pattern.compile( "^(.+)@(.+)$");

    private boolean validateName() {

        String usernameInput = editTextname.getText().toString().trim();
        if (usernameInput.isEmpty()) {
            editTextname.setError("Field can't be empty");
            return false;
        } else if (NAME_PATTERN.matcher(usernameInput).matches()) {
            editTextname.setError(null);
            return true;
        } else {
            editTextname.setError("Like : Ali");
            return false;
        }
    }


    private boolean validateEmail() {
        String emailInput = editTextemail.getText().toString().trim();
        if (emailInput.isEmpty()) {
            editTextemail.setError("Field can't be empty");
            return false;
        }
        else if(Email_Pattern.matcher(emailInput).matches()){
            editTextemail.setError(null);
            return true;
        }else{
            editTextemail.setError("Like: yasinayub7172@gmail.com");
            return false;
        }

    }

    private boolean validateCity() {
        String cityInput = editTextcity.getText().toString().trim();
        if (cityInput.isEmpty()) {
            editTextcity.setError("Field can't be empty");
            return false;
        }
        else if(NAME_PATTERN.matcher(cityInput).matches()){
            editTextcity.setError(null);
            return true;
        }else{
            editTextcity.setError("Like :Lahore");
            return false;
        }

    }

    private  boolean validatePass() {

        String passInput = editTextpass.getText().toString().trim();

        if (passInput.isEmpty()) {
            editTextpass.setError("Field can't be empty");
            return false;
        }
        else if(PASS_PATTERN.matcher(passInput).matches()) {
            return true;
        }
        else {
            editTextpass.setError("Like:1@Yasin");
            return false;
        }
    }

    private boolean validateCountry() {

        String countryInput = editTextcountry.getText().toString().trim();
        if (countryInput.isEmpty()) {
            editTextcountry.setError("Field can't be empty");
            return false;
        } else if (NAME_PATTERN.matcher(countryInput).matches()) {
            editTextcountry.setError(null);
            return true;
        }else{
            editTextcountry.setError("Like : Pakistan");
            return false;
        }

    }
    private boolean validatePhoneNumber() {

        String numberInput = editTextnumber.getText().toString().trim();
        if (numberInput.isEmpty()) {
            editTextnumber.setError("Field can't be empty");
            return false;
        } else if (NUMBER_PATTERN.matcher(numberInput).matches()) {
            editTextnumber.setError(null);
            return true;
        }else{
            editTextnumber.setError("Like : 03048996421");
            return false;
        }

    }

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public registerFragment() {
        // Required empty public constructor
    }

      public static registerFragment newInstance(String param1, String param2) {
        registerFragment fragment = new registerFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController= Navigation.findNavController(view);
        firebaseAuth=FirebaseAuth.getInstance();

        progressDialog=new ProgressDialog(getContext());
        editTextname=view.findViewById(R.id.editTextname);
        editTextemail=view.findViewById(R.id.editTextemail);
        editTextpass=view.findViewById(R.id.editTextpass);
        editTextnumber=view.findViewById(R.id.editTextnumber);
        spinnerOption=view.findViewById(R.id.spinnerOption);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.bloodGroup, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOption.setAdapter(adapter);
        spinnerOption.setOnItemSelectedListener(this);

        editTextcity=view.findViewById(R.id.editTextcity);
        editTextcountry=view.findViewById(R.id.editTextcountry);
        textSignIn=view.findViewById(R.id.textSignIn);
        textSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_registerFragment_to_loginFragment);
            }
        });
        textregister=view.findViewById(R.id.textregister);
        textregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateName()&&validateEmail()&&validatePass()&&validatePhoneNumber()&&validateCity()&&validateCountry()){
                    progressDialog.setMessage(" Data Loading ");
                    progressDialog.show();
                    // ..................................DataBase write code here................................
                    user_email=editTextemail.getText().toString();
                    user_pass=editTextpass.getText().toString();
                    user_name= editTextname.getText().toString();
                    user_number=editTextnumber.getText().toString();
                    user_city =editTextcity.getText().toString();
                    user_country=editTextcountry.getText().toString();
                    firebaseAuth.createUserWithEmailAndPassword(user_email,user_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                 sendEmailVerfication();
                                 sendUserData();
                                 Toast.makeText(getActivity(), "Successfully Registered ", Toast.LENGTH_SHORT).show();
                                textregister.setEnabled(false);
                                navController.navigate(R.id.action_registerFragment_to_loginFragment);
                                progressDialog.dismiss();

                            }else{
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(), "Already register", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    return;

                }
            }
        });
    }

    private void sendUserData() {
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference("User");
        UserRegisterData userRegisterData=new UserRegisterData(user_name,user_email,user_number, user_city, user_country,userBloodGroup);
        myRef.child(firebaseAuth.getUid()).setValue(userRegisterData);


    }

    private void sendEmailVerfication() {
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(task.isSuccessful()){
                        sendUserData();
                      //  Toast.makeText(getActivity(), "Successfully Registered ", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getActivity(), "Verification Mail has not been send ", Toast.LENGTH_SHORT).show();
                        textregister.setEnabled(true);
                    }
                }
            });
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
          userBloodGroup=adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}