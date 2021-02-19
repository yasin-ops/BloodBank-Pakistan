package com.muhammadyaseenFatima.blood_bank_pakistan;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class home_Fragment extends Fragment {
    private NavController navController;
    private List<UserRegisterData> mUserdata;
    private RecyclerView recyclerView;
    private HelperAdapter helperAdapter;
    private ProgressBar progressBar;
    SearchView searchView;
    private DatabaseReference dataBaseReference;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    CardView CardView;

    public home_Fragment() {
        // Required empty public constructor
    }

    public static home_Fragment newInstance(String param1, String param2) {
        home_Fragment fragment = new home_Fragment();
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
        return inflater.inflate(R.layout.fragment_home_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setHasOptionsMenu(true);
        recyclerView = view.findViewById(R.id.user_recylerview);
        searchView = view.findViewById(R.id.search_donor);
        progressBar = view.findViewById(R.id.progressBar);
        CardView=view.findViewById(R.id.cardSearch);

        ImageSlider slider = view.findViewById(R.id.slider);
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.b1, "Compatability Tabel"));
        slideModels.add(new SlideModel(R.drawable.b2, "Blood Type"));
        slideModels.add(new SlideModel(R.drawable.testkit, "Test Kit"));
        slideModels.add(new SlideModel(R.drawable.serum, "Serum"));
        slideModels.add(new SlideModel(R.drawable.universalrecipetent, "Universal Recipient"));
        slideModels.add(new SlideModel(R.drawable.blood_sample, "Blood Sample"));
        slider.setImageList(slideModels, false);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mUserdata = new ArrayList<>();

        dataBaseReference = FirebaseDatabase.getInstance().getReference("User");
        dataBaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        UserRegisterData data = ds.getValue(UserRegisterData.class);
                        mUserdata.add(data);
                    }
                    helperAdapter = new HelperAdapter(mUserdata);
                    recyclerView.setAdapter(helperAdapter);
                    progressBar.setVisibility(View.INVISIBLE);
                    CardView.setVisibility(View.VISIBLE);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "Check your Internet Connection", Toast.LENGTH_SHORT).show();
                CardView.setVisibility(View.INVISIBLE);

            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                helperAdapter.getFilter().filter(s.toString());
                return false;
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP)
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        // handle back button's click listener
                       Toast.makeText(getActivity(), "No Back", Toast.LENGTH_SHORT).show();
                       return true;
                    }
                return false;
            }
        });

    }


}