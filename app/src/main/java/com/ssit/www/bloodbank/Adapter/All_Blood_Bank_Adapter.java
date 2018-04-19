package com.ssit.www.bloodbank.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ssit.www.bloodbank.R;
import com.ssit.www.bloodbank.User.AllBloodbank_Data;

import java.util.List;

/**
 * Created by Android on 10-02-2018.
 */

public class All_Blood_Bank_Adapter extends RecyclerView.Adapter<All_Blood_Bank_Adapter.ViewHolder> {
    private Context context;
    private List<AllBloodbank_Data> items;

    public All_Blood_Bank_Adapter(Context context, List<AllBloodbank_Data> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.all_blood_bank_desgin,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AllBloodbank_Data allBloodbank_data=items.get(position);
        holder.tv_blood_name.setText(allBloodbank_data.getBbName());
        holder.tv_blood_contact.setText(allBloodbank_data.getBbContact());
        holder.tv_blood_address.setText(allBloodbank_data.getBbAddress());
        holder.tv_blood_city.setText(allBloodbank_data.getBbCity());
        holder.tv_blood_district.setText(allBloodbank_data.getBbDistrict());
        holder.tv_blood_state.setText(allBloodbank_data.getBbState());
        holder.tv_blood_pincode.setText(allBloodbank_data.getBbPincode());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_blood_name;
        TextView tv_blood_contact;
        TextView tv_blood_address;
        TextView tv_blood_city;
        TextView tv_blood_district;
        TextView tv_blood_state;
        TextView tv_blood_pincode;
        TextView tv_seen_more;
        TextView tv_seen_less;

        LinearLayout ll_state,ll_city,ll_pincode,ll_district;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_blood_name=(TextView)itemView.findViewById(R.id.tv_blood_name);
            tv_blood_contact=(TextView)itemView.findViewById(R.id.tv_blood_contact);
            tv_blood_address=(TextView)itemView.findViewById(R.id.tv_blood_address);
            tv_blood_city=(TextView)itemView.findViewById(R.id.tv_blood_city);
            tv_blood_district=(TextView)itemView.findViewById(R.id.tv_blood_district);
            tv_blood_state=(TextView)itemView.findViewById(R.id.tv_blood_state);
            tv_blood_pincode=(TextView)itemView.findViewById(R.id.tv_blood_pincode);
            tv_seen_more=(TextView)itemView.findViewById(R.id.tv_seen_more);
            tv_seen_less=(TextView)itemView.findViewById(R.id.tv_seen_less);

            ll_state=(LinearLayout)itemView.findViewById(R.id.ll_state) ;
            ll_district=(LinearLayout)itemView.findViewById(R.id.ll_district);
            ll_pincode=(LinearLayout)itemView.findViewById(R.id.ll_pincode);

            tv_seen_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ll_state.setVisibility(View.VISIBLE);
                    ll_district.setVisibility(View.VISIBLE);
                    ll_pincode.setVisibility(View.VISIBLE);
                    tv_seen_more.setVisibility(View.GONE);
                    tv_seen_less.setVisibility(View.VISIBLE);
                }
            });
            tv_seen_less.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ll_state.setVisibility(View.GONE);
                    ll_district.setVisibility(View.GONE);
                    ll_pincode.setVisibility(View.GONE);
                    tv_seen_more.setVisibility(View.VISIBLE);
                    tv_seen_less.setVisibility(View.GONE);
                }
            });



        }
    }


}
