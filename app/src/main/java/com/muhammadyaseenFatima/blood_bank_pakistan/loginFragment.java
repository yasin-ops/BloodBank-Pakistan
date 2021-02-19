package com.muhammadyaseenFatima.blood_bank_pakistan;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import java.util.regex.Pattern;


public class loginFragment extends Fragment {
    EditText email_login,passwords_login;
    CheckBox remember;
    Button register_login,signIn_login;
    TextView forget_passwords_login;
    NavController navController;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;


    private static final Pattern PASS_PATTERN =
            Pattern.compile("^" +
                 //   "(?=.*[0-9])" +         //at least 1 digit
                  //  "(?=.*[a-z])" +         //at least 1 lower case letter
                  //  "(?=.*[A-Z])" +         //at least 1 upper case letter
                   // "(?=.*[a-zA-Z])" +      //any letter
                   // "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    //"(?=\\S+$)" +           //no white spaces
                    ".{6,100}" +               //at least 4 characters
                    "$");
    private    Pattern Email_Pattern = Pattern.compile( "^(.+)@(.+)$");

    private boolean validateEmail() {
        String emailInput = email_login.getText().toString().trim();
        if (emailInput.isEmpty()) {
            email_login.setError("Field can't be empty");
            return false;

        }else if(Email_Pattern.matcher(emailInput).matches()){
            email_login.setError(null);
            return true;
        }else{
            email_login.setError("Like : yasinayub7172@gmail.com");
            return false;
        }
    }
    private  boolean validatePass() {

        String passInput = passwords_login.getText().toString().trim();

        if (passInput.isEmpty()) {
            passwords_login.setError("Field can't be empty");
            return false;
        }
        else if(PASS_PATTERN.matcher(passInput).matches()) {
            return true;
        }else{
            passwords_login.setError("Like : 1@Yasin");
            return false;
        }

    }

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public loginFragment() {

    }

      public static loginFragment newInstance(String param1, String param2) {
        loginFragment fragment = new loginFragment();
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
        return inflater.inflate(R.layout.fragment_login, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController= Navigation.findNavController(view);
        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(getContext());
        email_login=view.findViewById(R.id.email_login);
        passwords_login=view.findViewById(R.id.passwords_login);
        remember=view.findViewById(R.id.remember);

        SharedPreferences preferences=getActivity().getSharedPreferences("checkbox", Context.MODE_PRIVATE);
        String checkbox= preferences.getString("remember","");
        if(checkbox.equals("true")){
            Intent intentcheck=new Intent(getActivity(),HomeActivity.class);
            startActivity(intentcheck);
            getActivity();

        }else if(checkbox.equals("false")){
            Toast.makeText(getActivity(), "Please Sign in", Toast.LENGTH_SHORT).show();
        }



        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    SharedPreferences preferences=getActivity().getSharedPreferences("checkbox", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("remember","true");
                    editor.apply();
                    //Toast.makeText(getActivity(), "checked", Toast.LENGTH_SHORT).show();



                }else if(!compoundButton.isChecked()){
                    SharedPreferences preferences=getActivity().getSharedPreferences("checkbox", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("remember","false");
                    editor.apply();
                    // Toast.makeText(getActivity(), "un-checked", Toast.LENGTH_SHORT).show();

                }


            }
        });



        signIn_login=view.findViewById(R.id.signIn_login);
        signIn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateEmail()&&validatePass()){
                    String login_user_email=email_login.getText().toString();
                    String login_user_pass=passwords_login.getText().toString();
                    progressDialog.setMessage(" processing ");
                        progressDialog.show();

                    firebaseAuth.signInWithEmailAndPassword(login_user_email,login_user_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                checkEmailVerification();
                                progressDialog.dismiss();
                                signIn_login.setEnabled(false);

                            }else{
                                Toast.makeText(getContext(), "Check your connection", Toast.LENGTH_SHORT).show();
                                     signIn_login.setEnabled(true);
                                     progressDialog.dismiss();

                            }

                        }
                    }) ;

                }
            }
        });
        remember=view.findViewById(R.id.remember);
        register_login=view.findViewById(R.id.register_login);
        forget_passwords_login=view.findViewById(R.id.forget_passwords_login);
        forget_passwords_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_loginFragment_to_forgetFragment);
            }
        });
        register_login.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        navController.navigate(R.id.action_loginFragment_to_registerFragment);

    }
});

    }

    private void checkEmailVerification() {
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        Boolean emailflag = firebaseUser.isEmailVerified();
        if(emailflag){
            Intent i=new Intent(getActivity(),HomeActivity.class);
            startActivity(i);
            getActivity();

        }else{
            Toast.makeText(getActivity(), "Verify your Email", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();

        }

    }

}