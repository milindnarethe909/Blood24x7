package com.ssit.www.bloodbank.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ssit.www.bloodbank.R;
import com.ssit.www.bloodbank.User.Search_Donar_Pojo_class;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Android on 28-02-2018.
 */

public class Search_Donar_Adapter extends RecyclerView.Adapter<Search_Donar_Adapter.ViewHolderDemo>{

    private Context context;
    private List<Search_Donar_Pojo_class> list;

    public Search_Donar_Adapter(Context context, List<Search_Donar_Pojo_class> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolderDemo onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater= LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.search_list_design,parent,false);
        return new ViewHolderDemo(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderDemo holder, int position) {
        Search_Donar_Pojo_class search_donar_pojo_class=list.get(position);
        holder.tv_name.setText(search_donar_pojo_class.getClientName());
        holder.tv_email.setText(search_donar_pojo_class.getClientEmail());
        holder.tv_contact.setText(search_donar_pojo_class.getClientContact());
        holder.tv_bloodgp.setText(search_donar_pojo_class.getClientBloodGroup());
        holder.tv_gender.setText(search_donar_pojo_class.getClientGender());
        holder.tv_address.setText(search_donar_pojo_class.getClientAddress());
        holder.tv_city.setText(search_donar_pojo_class.getClientCity());
        holder.tv_state.setText(search_donar_pojo_class.getClientState());
//        holder.tv_date.setText(search_donar_pojo_class.getDonateDate());

        String date=search_donar_pojo_class.getDonateDate();
        if (date.equals("0000-00-00")){
            holder.tv_date.setText("YYYY-MM-DD");
        }else {
            holder.tv_date.setText(date);
        }

        Glide
                .with(context)
                .load(search_donar_pojo_class.getClientPic())
                .into(holder.civ_pic);

    }
   

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolderDemo extends RecyclerView.ViewHolder {
        TextView tv_name;
        TextView tv_email;
        TextView tv_contact;
        TextView tv_bloodgp;
        TextView tv_gender;
        TextView tv_address;
        TextView tv_city;
        TextView tv_state;
        TextView tv_date;
        CircleImageView civ_pic;

        LinearLayout ll_bloodgp,ll_gender,ll_address,ll_city,ll_state,ll_last_donate_date;
        TextView tv_see_more,tv_see_less;
        public ViewHolderDemo(View itemView) {
            super(itemView);

            tv_name=(TextView)itemView.findViewById(R.id.tv_name_search);
            tv_email=(TextView)itemView.findViewById(R.id.tv_email_search);
            tv_contact=(TextView)itemView.findViewById(R.id.tv_contact_search);
            tv_bloodgp=(TextView)itemView.findViewById(R.id.tv_bloodgp_search);
            tv_gender=(TextView)itemView.findViewById(R.id.tv_gender_search);
            tv_address=(TextView)itemView.findViewById(R.id.tv_address_search);
            tv_city=(TextView)itemView.findViewById(R.id.tv_city_search);
            tv_state=(TextView)itemView.findViewById(R.id.tv_state_search);
            tv_date=(TextView)itemView.findViewById(R.id.tv_date_search);

            civ_pic=(CircleImageView)itemView.findViewById(R.id.clv_profile);



            ll_bloodgp=(LinearLayout)itemView.findViewById(R.id.ll_bloodgp);
            ll_gender=(LinearLayout)itemView.findViewById(R.id.ll_gender);
            ll_address=(LinearLayout)itemView.findViewById(R.id.ll_address);
            ll_city=(LinearLayout)itemView.findViewById(R.id.ll_city);
            ll_state=(LinearLayout)itemView.findViewById(R.id.ll_state);
            ll_last_donate_date=(LinearLayout)itemView.findViewById(R.id.ll_last_donate_date);

            tv_see_more=itemView.findViewById(R.id.tv_see_more);
            tv_see_less=itemView.findViewById(R.id.tv_see_less);

            tv_see_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ll_bloodgp.setVisibility(View.VISIBLE);
                    ll_gender.setVisibility(View.VISIBLE);
                    ll_address.setVisibility(View.VISIBLE);
                    ll_city.setVisibility(View.VISIBLE);
                    ll_state.setVisibility(View.VISIBLE);
                    tv_see_less.setVisibility(View.VISIBLE);
                    tv_see_more.setVisibility(View.GONE);
                }
            });

            tv_see_less.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ll_bloodgp.setVisibility(View.GONE);
                    ll_gender.setVisibility(View.GONE);
                    ll_address.setVisibility(View.GONE);
                    ll_city.setVisibility(View.GONE);
                    ll_state.setVisibility(View.GONE);
                    tv_see_more.setVisibility(View.VISIBLE);
                    tv_see_less.setVisibility(View.GONE);
                }
            });






        }
    }
}
