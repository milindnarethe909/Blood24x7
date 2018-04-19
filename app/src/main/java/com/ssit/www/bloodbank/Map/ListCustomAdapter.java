package com.ssit.www.bloodbank.Map;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.ssit.www.bloodbank.R;

import java.util.ArrayList;

/**
 * Created by SSIT on 12/6/2017.
 */


public class ListCustomAdapter extends ArrayAdapter<AddressData> {
    Context context;
    ArrayList<AddressData> addreslist;
    public GoogleMap mMap;
    double latitude, longitude;
    public Marker currentLocationmMarker;

   // GPSTracker gpsTracker = new GPSTracker(context);
    public ListCustomAdapter(Context context, ArrayList<AddressData> addreslist, double latitude, double longitude) {
        super(context, R.layout.list_item, addreslist);
        this.context = context;
        this.addreslist = addreslist;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    private static class ViewHolder
    {
        TextView tv_Tittle, tv_Address, tv_phone;
        ImageView place_icon, image_search, phone_icon;
        LinearLayout listView_click;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        ViewHolder viewHolder; // view lookup cache stored in tag

        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item, parent, false);

            viewHolder.tv_Tittle = (TextView) convertView.findViewById(R.id.tv_Tittle);
            viewHolder.tv_Address = (TextView) convertView.findViewById(R.id.tv_Address);
            viewHolder.tv_phone = (TextView) convertView.findViewById(R.id.tv_phone);
            viewHolder.place_icon = (ImageView) convertView.findViewById(R.id.place_icon);
            viewHolder.image_search = (ImageView) convertView.findViewById(R.id.image_search);
            viewHolder.phone_icon = (ImageView) convertView.findViewById(R.id.phone_icon);
            viewHolder.listView_click = (LinearLayout) convertView.findViewById(R.id.listView_click);

            convertView.setTag(viewHolder);
        }
        else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_Tittle.setText(addreslist.get(position).getPlaceName());
        viewHolder.tv_Address.setText(addreslist.get(position).getVicinity());

        Glide.with(context)
                .load(addreslist.get(position).getPhotolink())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.place_icon);


        //imageview.setImageDrawable(getResources().getDrawable(R.drawable.frnd_inactive));
        viewHolder.image_search.setImageResource(R.drawable.ic_directions_black_24dp);
        viewHolder.phone_icon.setImageResource(R.drawable.ic_call_black_24dp);


        viewHolder.tv_Tittle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "click", Toast.LENGTH_SHORT).show();

            }
        });


        viewHolder.listView_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "click", Toast.LENGTH_SHORT).show();

//                if (currentLocationmMarker != null) {
//                    currentLocationmMarker.remove();
//                }
//                MarkerOptions markerOptions = new MarkerOptions();
//                LatLng latLng = new LatLng(addreslist.get(position).getLat(), addreslist.get(position).getLng());
//                markerOptions.position(latLng);
//                 markerOptions.title(addreslist.get(position).getPlaceName());
//                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
//                mMap.addMarker(markerOptions);
//                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//                mMap.animateCamera(CameraUpdateFactory.zoomTo(10));


            }
        });

        viewHolder.image_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String uri = "http://maps.google.com/maps?saddr=" + latitude + "," + longitude +
                        "&daddr=" + addreslist.get(position).getVicinity();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));

                context.startActivity(intent);

            }
        });


        return convertView;


    }


}