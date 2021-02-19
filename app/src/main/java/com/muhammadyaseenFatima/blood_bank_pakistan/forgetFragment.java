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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import java.util.regex.Pattern;


public class forgetFragment extends Fragment {
    EditText forget_User_Email;
    NavController navController;
    Button bttnResetPasswords;
    TextView tv_ReloginAgain;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    private Pattern Email_Pattern = Pattern.compile( "^(.+)@(.+)$");
    private boolean validateEmail() {
        String emailInput = forget_User_Email.getText().toString().trim();
        if (emailInput.isEmpty()) {
            forget_User_Email.setError("Field can't be empty");
            return false;

        }else if(Email_Pattern.matcher(emailInput).matches()){
            forget_User_Email.setError(null);
            return true;
        }else{
            forget_User_Email.setError("Like : yasinayub7172@gmail.com");
            return false;
        }
    }


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public forgetFragment() {

    }

     public static forgetFragment newInstance(String param1, String param2) {
        forgetFragment fragment = new forgetFragment();
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
        return inflater.inflate(R.layout.fragment_forget, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController= Navigation.findNavController(view);
        firebaseAuth= FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(getContext());
        forget_User_Email=view.findViewById(R.id.forget_User_Email);
        bttnResetPasswords=view.findViewById(R.id.bttnResetPasswords);
        tv_ReloginAgain=view.findViewById(R.id.tv_ReloginAgain);
         bttnResetPasswords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateEmail()){
                    String User_email=forget_User_Email.getText().toString();
                    progressDialog.setMessage(" Check your Register Email ");
                    progressDialog.show();
                    bttnResetPasswords.setEnabled(false);
                      firebaseAuth.sendPasswordResetEmail(User_email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getActivity(), "Passwords resend to your email", Toast.LENGTH_SHORT).show();
                                tv_ReloginAgain.setVisibility(View.VISIBLE);
                                tv_ReloginAgain.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        navController.navigate(R.id.action_forgetFragment_to_loginFragment);
                                    }
                                });

                                progressDialog.dismiss();
                            }else{
                                tv_ReloginAgain.setVisibility(View.INVISIBLE);
                                Toast.makeText(getActivity(), "Register Data valid Email ", Toast.LENGTH_SHORT).show();
                                navController.navigate(R.id.action_forgetFragment_to_registerFragment);
                                progressDialog.dismiss();
                                bttnResetPasswords.setEnabled(true);
                            }
                        }
                    });
                }
            }
        });
    }
}