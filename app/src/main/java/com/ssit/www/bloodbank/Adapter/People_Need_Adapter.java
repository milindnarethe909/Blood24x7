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
import com.ssit.www.bloodbank.User.People_Need_POJO_Class;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Android on 07-03-2018.
 */

public class People_Need_Adapter extends RecyclerView.Adapter<People_Need_Adapter.People_ViewHolder> {

    private Context context;
    private List<People_Need_POJO_Class> list;


    public People_Need_Adapter(Context context, List<People_Need_POJO_Class> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public People_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.people_need_list_design,parent,false);
        return new People_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(People_ViewHolder holder, int position) {
        People_Need_POJO_Class item=list.get(position);
        holder.tv_name.setText(item.getClientName());
        holder.tv_email.setText(item.getClientEmail());
        holder.tv_contact.setText(item.getClientContact());
        holder.tv_bloodgp.setText(item.getClientBloodGroup());
        holder.tv_gender.setText(item.getClientGender());
        holder.tv_address.setText(item.getClientAddress());
        holder.tv_city.setText(item.getClientCity());
        holder.tv_state.setText(item.getClientState());
        holder.tv_date.setText(item.getDonarSearchDate());

        Glide.with(context)
                .load(item.getClientPic())
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.civ_pic);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class People_ViewHolder extends RecyclerView.ViewHolder{

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

        LinearLayout ll_bloodgp,ll_gender,ll_address,ll_city,ll_state;

        TextView tv_see_more_people,tv_see_less_people;




        public People_ViewHolder(View itemView) {
            super(itemView);
            tv_name=(TextView)itemView.findViewById(R.id.tv_name_people);
            tv_email=(TextView)itemView.findViewById(R.id.tv_email_people);
            tv_contact=(TextView)itemView.findViewById(R.id.tv_contact_people);
            tv_bloodgp=(TextView)itemView.findViewById(R.id.tv_bloodgp_people);
            tv_gender=(TextView)itemView.findViewById(R.id.tv_gender_people);
            tv_address=(TextView)itemView.findViewById(R.id.tv_address_people);
            tv_city=(TextView)itemView.findViewById(R.id.tv_city_people);
            tv_state=(TextView)itemView.findViewById(R.id.tv_state_people);
            tv_date=(TextView)itemView.findViewById(R.id.tv_date_people);

            civ_pic=(CircleImageView)itemView.findViewById(R.id.clv_profile_people);

            ll_bloodgp=(LinearLayout)itemView.findViewById(R.id.ll_bloodgp);
            ll_gender=(LinearLayout)itemView.findViewById(R.id.ll_gender);
            ll_address=(LinearLayout)itemView.findViewById(R.id.ll_address);
            ll_city=(LinearLayout)itemView.findViewById(R.id.ll_city);
            ll_state=(LinearLayout)itemView.findViewById(R.id.ll_state);

            tv_see_less_people=(TextView)itemView.findViewById(R.id.tv_see_less_people);
            tv_see_more_people=(TextView)itemView.findViewById(R.id.tv_see_more_people);

            tv_see_more_people.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ll_bloodgp.setVisibility(View.VISIBLE);
                    ll_gender.setVisibility(View.VISIBLE);
                    ll_address.setVisibility(View.VISIBLE);
                    ll_city.setVisibility(View.VISIBLE);
                    ll_state.setVisibility(View.VISIBLE);
                    tv_see_less_people.setVisibility(View.VISIBLE);
                    tv_see_more_people.setVisibility(View.GONE);
                }
            });

            tv_see_less_people.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ll_bloodgp.setVisibility(View.GONE);
                    ll_gender.setVisibility(View.GONE);
                    ll_address.setVisibility(View.GONE);
                    ll_city.setVisibility(View.GONE);
                    ll_state.setVisibility(View.GONE);
                    tv_see_less_people.setVisibility(View.GONE);
                    tv_see_more_people.setVisibility(View.VISIBLE);
                }
            });
        }
    }
}
