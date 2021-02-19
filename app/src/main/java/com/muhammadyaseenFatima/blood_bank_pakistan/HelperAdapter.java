package com.muhammadyaseenFatima.blood_bank_pakistan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HelperAdapter extends RecyclerView.Adapter implements Filterable {
    List<UserRegisterData> mUserdata;
    List<UserRegisterData> listFull;


    public HelperAdapter(List<UserRegisterData> uploads)
    {

        mUserdata=uploads;
        listFull=new ArrayList<>(uploads);
    }
    private NavController navController;




    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.donnor_recylerview_item,parent,false);
        ViewHolderClass viewHolderClass=new ViewHolderClass(view);
        return viewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderClass viewHolderClass=(ViewHolderClass)holder;
        final UserRegisterData userRegisterData=mUserdata.get(position);
        final String bloodGroup=userRegisterData.getUserBloodGroup();
        final String name=userRegisterData.getUserName();
        final String number=userRegisterData.getUserNumber();
        final String usercity=userRegisterData.getUserCity();
        final String country=userRegisterData.getUserCountry();

        ((ViewHolderClass) holder).view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController = Navigation.findNavController(view);
                home_FragmentDirections.ActionHomeFragmentToDetailsDonnerFragment2 action=home_FragmentDirections.actionHomeFragmentToDetailsDonnerFragment2(userRegisterData);
                action.setNessage(bloodGroup);
                action.setNessage(name);
                action.setNessage(number);
                action.setNessage(usercity);
                action.setNessage(country);
                navController.navigate(action);



            }
        });

        viewHolderClass.UserName.setText(userRegisterData.getUserName());
        viewHolderClass.BloodGroup.setText(userRegisterData.getUserBloodGroup());
        viewHolderClass.UserNumber.setText(userRegisterData.getUserNumber());
        viewHolderClass.UserCity.setText(userRegisterData.getUserCity());


    }

    @Override
    public int getItemCount() {
        return mUserdata.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public Filter getFilter() {
          return FilterUser;

    }

    private Filter FilterUser=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String searchText = charSequence.toString().toLowerCase();
            List<UserRegisterData> temList = new ArrayList<>();
            if (searchText.length() == 0 || searchText.isEmpty()) {
                temList.addAll(listFull);


            } else {
                 for (UserRegisterData user : listFull) {
                    if (user.getUserBloodGroup().toLowerCase().contains(searchText)) {
                        temList.add(user);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = temList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mUserdata.clear();
            mUserdata.addAll((Collection<? extends UserRegisterData>) filterResults.values);
            notifyDataSetChanged();
        }
    };


    public  class ViewHolderClass extends RecyclerView.ViewHolder{
        View view;
        TextView BloodGroup,UserName, UserNumber,UserCity;
        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            view=itemView;
            BloodGroup=itemView.findViewById(R.id.groupSample);
            UserName=itemView.findViewById(R.id.donnor_name);
            UserNumber=itemView.findViewById(R.id.donnor_number);
            UserCity=itemView.findViewById(R.id.donnor_city);
        }
    }




}
