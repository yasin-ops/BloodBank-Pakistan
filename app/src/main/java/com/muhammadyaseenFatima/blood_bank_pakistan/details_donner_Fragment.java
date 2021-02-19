package com.muhammadyaseenFatima.blood_bank_pakistan;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class details_donner_Fragment extends Fragment {
    NavController navController;
    TextView d_BloodGroup,d_username,d_usercity,d_usercountry;
    Button d_number,d_user_email;
    private UserRegisterData userRegisterData;
    private  static final String TAG="BloodDonor";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public details_donner_Fragment() {

    }

    public static details_donner_Fragment newInstance(String param1, String param2) {
        details_donner_Fragment fragment = new details_donner_Fragment();
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
        return inflater.inflate(R.layout.fragment_details_donner_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        d_BloodGroup=view.findViewById(R.id.d_BloodGroup);
        d_username=view.findViewById(R.id.d_username);
        d_user_email=view.findViewById(R.id.d_user_email);
        d_usercity=view.findViewById(R.id.d_usercity);
        d_usercountry=view.findViewById(R.id.d_usercountry);
        d_number=view.findViewById(R.id.d_number);

        if(getArguments()!=null){
            details_donner_FragmentArgs args=details_donner_FragmentArgs.fromBundle(getArguments());
            /*just one Msg show
            String BloodGroup=args.getMessage();
             Log.i(TAG,"BloodGroup"+BloodGroup);
             */
            userRegisterData=args.getUser();
            //Log.i(TAG,"Info of Donor :"+userRegisterData.toString());
            d_BloodGroup.setText(userRegisterData.getUserBloodGroup());
            d_username.setText(userRegisterData.getUserName());
            d_number.setText(userRegisterData.getUserNumber());
            d_usercity.setText(userRegisterData.getUserCity());
            d_usercountry.setText(userRegisterData.getUserCountry());
            d_user_email.setText(userRegisterData.getUserEmail());

        }


        d_user_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                String  emailList=userRegisterData.getUserEmail().toString();
                String re[]=emailList.split(",");
                emailIntent.putExtra(Intent.EXTRA_EMAIL,re);
                emailIntent.setType("message/rfc822");
                startActivity(Intent.createChooser(emailIntent,"chose your Gmail APP"));
            }
        });
        d_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String str=userRegisterData.getUserNumber();
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+str));
                    startActivity(intent);
                }
                catch (android.content.ActivityNotFoundException e){
                    Toast.makeText(getActivity(),"App failed",Toast.LENGTH_LONG).show();
                }
            }
        });


    }

}